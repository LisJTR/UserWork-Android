package com.torre.b2c2c_tfg.ui.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.torre.b2c2c_tfg.data.model.Empresa
import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import com.torre.b2c2c_tfg.data.repository.EmpresaRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.OfertaRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.CrearOfertaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.UpdateEmpresaUseCase
import com.torre.b2c2c_tfg.ui.components.BottomBar
import com.torre.b2c2c_tfg.ui.components.ButtonGeneric
import com.torre.b2c2c_tfg.ui.components.OutlinedInputTextField
import com.torre.b2c2c_tfg.ui.theme.B2C2C_TFGTheme
import com.torre.b2c2c_tfg.ui.components.UploadFileComponent
import com.torre.b2c2c_tfg.ui.components.UserSelectedImage
import com.torre.b2c2c_tfg.ui.components.OfferCardForm
import com.torre.b2c2c_tfg.ui.components.TextTitle
import com.torre.b2c2c_tfg.ui.viewmodel.OfertaViewModel
import com.torre.b2c2c_tfg.ui.viewmodel.RegisterEmpresaViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.torre.b2c2c_tfg.domain.usecase.GetOfertasUseCase
import com.torre.b2c2c_tfg.ui.util.UserType
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel
import androidx.compose.ui.platform.LocalFocusManager
import com.torre.b2c2c_tfg.domain.usecase.CreateEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.DeleteOfertaUseCase


data class OfferCardData(
    var id: Int? = null,
    var title: String = "",
    var description: String = "",
    var aptitudes: String = "",
    var queSeOfrece: String = "",
    var isPublic: Boolean = true,
    var isSaved: Boolean = false,
    var isMarkedForDeletion: Boolean = false

)


@Composable
fun RegisterProfileEmpresaScreen(navController: NavController, sessionViewModel: SessionViewModel, contentPadding: PaddingValues = PaddingValues(), esEdicion: Boolean = false) {

    var idEmpresaForm by remember { mutableStateOf<Int?>(null) }
    var nombreEmpresa by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var sector by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val offerCards = remember { mutableStateListOf<OfferCardData>() }
    val originalCards = remember { mutableStateListOf<OfferCardData>() } // Guarda el estado original
    val focusManager = LocalFocusManager.current
    
    val context = LocalContext.current
    val viewModel = remember {
        RegisterEmpresaViewModel(
            //GetEmpresaUseCase(FakeEmpresaRepository()),
            GetEmpresaUseCase(EmpresaRepositoryImpl(RetrofitInstance.getInstance(context))),
            //UpdateEmpresaUseCase(FakeEmpresaRepository())
            UpdateEmpresaUseCase(EmpresaRepositoryImpl(RetrofitInstance.getInstance(context))),
            CreateEmpresaUseCase(EmpresaRepositoryImpl(RetrofitInstance.getInstance(context)))

        )
    }
    val ofertaViewModel = remember {
        OfertaViewModel(
        // CrearOfertaUseCase(OfertaRepositoryImpl(RetrofitInstance.api)))
            //crearOfertaUseCase = CrearOfertaUseCase(FakeOfertaRepository()),
            CrearOfertaUseCase(OfertaRepositoryImpl(RetrofitInstance.getInstance(context))),
            //getOfertasUseCase = GetOfertasUseCase(FakeOfertaRepository())
            GetOfertasUseCase(OfertaRepositoryImpl(RetrofitInstance.getInstance(context))),
            DeleteOfertaUseCase(OfertaRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }

    val empresaId = sessionViewModel.userId.collectAsState().value ?: 0L

    // Si no es edición, guarda sesión y navega una vez se obtenga el ID
    if (!esEdicion) {
        LaunchedEffect(viewModel.empresaId) {
            viewModel.empresaId?.let { newEmpresaId ->
                sessionViewModel.setSession(newEmpresaId, "empresa")
                navController.navigate("OfertasScreen?isEmpresa=true")
            }
        }
    }

    val empresa by viewModel.empresa.collectAsState()
    // Obtiene el alumno actual desde el ViewModel
    if (esEdicion) {
        val ofertas by ofertaViewModel.ofertas.collectAsState()

        // Cargar los datos
        LaunchedEffect(empresaId) {
            println("Alumno ID recibido: $empresaId")
            if (empresaId > 0) {
            viewModel.cargarDatos(empresaId)             // carga empresa
            }
            ofertaViewModel.cargarOfertas(empresaId) // carga ofertas
        }
        // Cuando llega la empresa, actualizar campos
        LaunchedEffect(empresa) {
            empresa?.let {
                idEmpresaForm = it.id
                nombreEmpresa = it.nombre
                username = it.username
                password = it.password
                sector = it.sector
                ciudad = it.ciudad
                telefono = it.telefono
                println("Correo electrónico desde backend: '${it.correoElectronico}'")
                println(Gson().toJson(empresa))
                correo = it.correoElectronico.orEmpty().trim()
                descripcion = it.descripcion
                imageUri = it.imagen?.let { uri -> Uri.parse(uri) }
            }
        }

        // Cuando llegan las ofertas, actualiza tus cards:
        LaunchedEffect(ofertas) {
            offerCards.clear()
            originalCards.clear()
            val loadedCards = ofertas.map {
                OfferCardData(
                    id = it.id,
                    title = it.titulo,
                    description = it.descripcion,
                    aptitudes = it.aptitudes,
                    queSeOfrece = it.queSeOfrece,
                    isPublic = it.publicada,
                    isSaved = true
                )
            }
            offerCards.addAll(loadedCards)
            originalCards.addAll(loadedCards)
        }
    }

    //Campos a la izquierda
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
            .padding(horizontal = 50.dp)
            .verticalScroll(rememberScrollState()) // añade scroll
            .padding(bottom = contentPadding.calculateBottomPadding()), // se agrega el padding bottom para que no se pisen los botones con la barra de navegación
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        UploadFileComponent(
            onFileSelected = { uri -> imageUri = uri },
            mimeType = "image/*",
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
        )


        OutlinedInputTextField(
            value = nombreEmpresa,
            onValueChange = { nombreEmpresa = it },
            label = "Nombre Empresa*",
            modifier = Modifier
                .height(60.dp)
        )
        OutlinedInputTextField(
            value = username,
            onValueChange = { username = it },
            label = "User Name*",
            modifier = Modifier
                .height(60.dp)
        )
        OutlinedInputTextField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña*",
            modifier = Modifier
                .height(60.dp)
        )
        OutlinedInputTextField(
            value = sector,
            onValueChange = { sector = it },
            label = "Sector*",
            modifier = Modifier
                .height(60.dp)

        )
        OutlinedInputTextField(
            value = ciudad,
            onValueChange = { ciudad = it },
            label = "Ciudad*",
            modifier = Modifier
                .height(60.dp)

        )
        OutlinedInputTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = "Teléfono",
            modifier = Modifier
                .height(60.dp)

        )
        OutlinedInputTextField(
            value = correo,
            onValueChange = { correo = it },
            label = "Correo electrónico",
            modifier = Modifier
                .height(60.dp)
        )

        OutlinedInputTextField(
            value =  descripcion,
            onValueChange = {  descripcion = it },
            label = "Breve descripción*",
            modifier = Modifier
                .height(60.dp)
        )

        // Título + botón "+"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextTitle(
                text = "OFERTAS",
                style = MaterialTheme.typography.titleSmall
            )

            IconButton(onClick = {
                offerCards.add(OfferCardData(isSaved = false)) // Añadir nueva card vacía
            }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir oferta")
            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        // Mostrar la tarjeta si el botón se ha pulsado
        offerCards
            .filter { !it.isMarkedForDeletion }
            .forEachIndexed { index, card ->
            OfferCardForm(
                id = card.id,
                title = card.title,
                description = card.description,
                aptitudes = card.aptitudes,
                queSeOfrece = card.queSeOfrece,
                isPublic = card.isPublic,
                isSaved = card.isSaved,
                onTitleChange = { newTitle ->
                    offerCards[index] = card.copy(title = newTitle)
                },
                onDescriptionChange = { newDesc ->
                    offerCards[index] = card.copy(description = newDesc)
                },
                onAptitudesChange = { newAptitudes ->
                    offerCards[index] = card.copy(aptitudes = newAptitudes)
                },
                onQueSeOfreceChange = { newValue ->
                    offerCards[index] = card.copy(queSeOfrece = newValue)
                },
                onDelete = {
                    offerCards[index] = card.copy(isMarkedForDeletion = true)
                },
                onView = {
                    offerCards[index] = card.copy(isPublic = true)
                },
                onHide = {
                    offerCards[index] = card.copy(isPublic = false)
                }
            )
        }

        // Botón de Guardar
        ButtonGeneric(
            text = "GUARDAR",
            onClick = {
                val nuevaEmpresa = Empresa(
                    id = idEmpresaForm,
                    nombre = nombreEmpresa,
                    username = username,
                    password = password,
                    sector = sector,
                    ciudad = ciudad,
                    telefono = telefono,
                    correoElectronico = correo,
                    descripcion = descripcion,
                    imagen = imageUri?.toString()
                )

                val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                focusManager.clearFocus()

                if (esEdicion) {
                    // Actualiza empresa existente
                    viewModel.guardarDatos(nuevaEmpresa)

                    // Luego quítalas de la UI
                    offerCards.removeAll { card ->
                        if (card.isMarkedForDeletion && card.id != null) {
                            ofertaViewModel.eliminarOferta(card.id!!)
                            true // se elimina de la lista
                        } else {
                            false
                        }
                    }


                    empresa?.id?.let { idEmpresa ->

                        offerCards.forEachIndexed { index, card ->
                            // Oculta las que están marcadas para borrar
                            if (card.isMarkedForDeletion) return@forEachIndexed
                            val nuevaOferta = Oferta(
                                id = card.id,
                                titulo = card.title,
                                descripcion = card.description,
                                aptitudes = card.aptitudes,
                                queSeOfrece = card.queSeOfrece,
                                empresaId = idEmpresa.toInt(),
                                publicada = card.isPublic,
                                fechaPublicacion = fechaActual
                            )
                            ofertaViewModel.guardarOferta(nuevaOferta) { ofertaGuardada ->
                                if (ofertaGuardada?.id != null) {
                                    val updatedCard = card.copy(id = ofertaGuardada.id, isSaved = true)
                                    offerCards[index] = updatedCard

                                } else {
                                    offerCards[index] = card.copy(isSaved = true)
                                }
                                // Recarga datos
                                viewModel.cargarDatos(empresaId)
                                ofertaViewModel.cargarOfertas(empresaId)
                            }
                        }
                        // Actualiza originalCards después de guardar todas
                        originalCards.clear()
                        originalCards.addAll(offerCards.map { it.copy() })

                    }
                } else {
                    // Crea nueva empresa y luego ofertas
                    viewModel.crearEmpresa(nuevaEmpresa) { empresaCreada ->
                        val empresaIdValido = empresaCreada.id

                        if (empresaIdValido != null && empresaIdValido > 0) {

                            sessionViewModel.setSession(empresaIdValido.toLong(), "empresa")

                            offerCards.forEach { card ->
                                val nuevaOferta = Oferta(
                                    id = card.id,
                                    titulo = card.title,
                                    descripcion = card.description,
                                    aptitudes = card.aptitudes,
                                    queSeOfrece = card.queSeOfrece,
                                    empresaId = empresaIdValido.toInt(), // Utilizamos la ID de la empresa creada
                                    publicada = card.isPublic,
                                    fechaPublicacion = fechaActual
                                )
                                ofertaViewModel.guardarOferta(nuevaOferta)
                            }

                            navController.navigate("OfertasScreen?isEmpresa=true")
                        } else {
                            println("ERROR: empresaCreada.id no es válido. No se guardan ofertas.")
                        }
                    }
                }
            },
            modifier = Modifier
                .width(300.dp)
                .padding(top = 90.dp)
        )

    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun RegisterProfileEmpresaScreenPreview() {
    val navController = rememberNavController()

    //Se especifica el bottomBar para que aparezca en la pantalla de Preview
    B2C2C_TFGTheme {
        Scaffold(
            bottomBar = {
                BottomBar(navController = navController, userType = UserType.EMPRESA)
            }
        ) {
            RegisterProfileEmpresaScreen(navController = navController, sessionViewModel = SessionViewModel())
        }
    }
}



