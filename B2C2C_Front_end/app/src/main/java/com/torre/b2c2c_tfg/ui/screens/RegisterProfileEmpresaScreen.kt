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


data class OfferCardData(
    var id: Int? = null,
    var title: String = "",
    var description: String = "",
    var aptitudes: String = "",
    var queSeOfrece: String = "",
    var isPublic: Boolean = true
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

    val context = LocalContext.current
    val viewModel = remember {
        RegisterEmpresaViewModel(
            //GetEmpresaUseCase(FakeEmpresaRepository()),
            GetEmpresaUseCase(EmpresaRepositoryImpl(RetrofitInstance.getInstance(context))),
            //UpdateEmpresaUseCase(FakeEmpresaRepository())
            UpdateEmpresaUseCase(EmpresaRepositoryImpl(RetrofitInstance.getInstance(context)))

        )
    }
    val ofertaViewModel = remember {
        OfertaViewModel(
        // CrearOfertaUseCase(OfertaRepositoryImpl(RetrofitInstance.api)))
            //crearOfertaUseCase = CrearOfertaUseCase(FakeOfertaRepository()),
            CrearOfertaUseCase(OfertaRepositoryImpl(RetrofitInstance.getInstance(context))),
            //getOfertasUseCase = GetOfertasUseCase(FakeOfertaRepository())
            GetOfertasUseCase(OfertaRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }

    val empresaId = sessionViewModel.userId.collectAsState().value ?: 0L

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
            ofertaViewModel.cargarOfertas()       // carga ofertas
        }

        // Cuando llega la empresa, actualizar campos
        LaunchedEffect(empresa) {
            empresa?.let {
                idEmpresaForm = it.id
                nombreEmpresa = it.nombre.orEmpty()
                username = it.username.orEmpty()
                password = it.password.orEmpty()
                sector = it.sector.orEmpty()
                ciudad = it.ciudad.orEmpty()
                telefono = it.telefono.orEmpty()
                correo = it.correoElectronico.orEmpty()
                descripcion = it.descripcion.orEmpty()
                imageUri = it.imagen?.let { uri -> Uri.parse(uri) }
            }
        }

        // Cuando llegan las ofertas, actualiza tus cards:
        LaunchedEffect(ofertas) {
            offerCards.clear()
            offerCards.addAll(ofertas.map {
                OfferCardData(
                    id = it.id,
                    title = it.titulo,
                    description = it.descripcion,
                    aptitudes = it.aptitudes,
                    queSeOfrece = it.queSeOfrece,
                    isPublic = it.publicada
                )
            })
        }
    }




    // Contenedor principal en Row para poner campos a la izquierda e imagen a la derecha

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
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)


        )

        UserSelectedImage(
            imageUri = imageUri,
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
                offerCards.add(OfferCardData()) // Añadir nueva card vacía
            }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir oferta")
            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        // Mostrar la tarjeta si el botón se ha pulsado
        offerCards.forEachIndexed { index, card ->
            OfferCardForm(
                id = card.id,
                title = card.title,
                description = card.description,
                aptitudes = card.aptitudes,
                queSeOfrece = card.queSeOfrece,
                isPublic = card.isPublic,
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
                    offerCards.removeAt(index)
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
                // 1. Guardar empresa
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
                viewModel.guardarDatos(nuevaEmpresa)

                val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())


                // 2. Guardar ofertas
                offerCards.forEach { card ->
                    val nuevaOferta = Oferta(
                        id = card.id,
                        titulo = card.title,
                        descripcion = card.description,
                        aptitudes = card.aptitudes,
                        queSeOfrece = card.queSeOfrece,
                        // CAMBIAR CUANDO TENGAS BACKEND:
                        empresaId = empresa?.id ?: 0, //empresaId = 1,   cuando tengas backend real
                        publicada = card.isPublic,
                        fechaPublicacion = fechaActual
                    )
                    ofertaViewModel.guardarOferta(nuevaOferta)
                }

                // 3. Navegar a la pantalla principal
                navController.navigate("HomeScreen?isEmpresa=true")
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



