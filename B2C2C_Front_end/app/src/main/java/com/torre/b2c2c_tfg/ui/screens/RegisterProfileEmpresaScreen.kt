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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.torre.b2c2c_tfg.ui.components.AutoDismissCorrectText
import com.torre.b2c2c_tfg.ui.components.AutoDismissErrorText
import com.torre.b2c2c_tfg.ui.components.PerfilProgresoCompleto
import com.torre.b2c2c_tfg.ui.components.UploadFileComponent
import com.torre.b2c2c_tfg.ui.util.FileUtils
import kotlinx.coroutines.launch



data class OfferCardData(
    var id: Int? = null,
    var title: String = "",
    var description: String = "",
    var aptitudes: String = "",
    var queSeOfrece: String = "",
    var isPublic: Boolean = true,
    var isSaved: Boolean = false,
    var isMarkedForDeletion: Boolean = false,
    val fechaPublicacion: String = ""

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
    val originalCards = remember { mutableStateListOf<OfferCardData>() }
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var mensajeErrorLocal by remember { mutableStateOf<String?>(null) }
    var mensajeCorectLocal by remember { mutableStateOf<String?>(null) }

    val porcentajeCompletado = run {
        val totalCampos = 8
        var completados = 0

        if (nombreEmpresa.isNotBlank()) completados++
        if (username.isNotBlank()) completados++
        if (sector.isNotBlank()) completados++
        if (ciudad.isNotBlank()) completados++
        if (descripcion.isNotBlank()) completados++
        if (correo.isNotBlank()) completados++
        if (telefono.isNotBlank()) completados++
        if (imageUri != null) completados++

        completados / totalCampos.toFloat()
    }


    val viewModel = remember {
        RegisterEmpresaViewModel(
            GetEmpresaUseCase(EmpresaRepositoryImpl(RetrofitInstance.getInstance(context))),
            UpdateEmpresaUseCase(EmpresaRepositoryImpl(RetrofitInstance.getInstance(context))),
            CreateEmpresaUseCase(EmpresaRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }


    val ofertaViewModel = remember {
        OfertaViewModel(
            CrearOfertaUseCase(OfertaRepositoryImpl(RetrofitInstance.getInstance(context))),
            GetOfertasUseCase(OfertaRepositoryImpl(RetrofitInstance.getInstance(context))),
            DeleteOfertaUseCase(OfertaRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }

    val empresaId = sessionViewModel.userId.collectAsState().value ?: 0L

    if (!esEdicion) {
        LaunchedEffect(viewModel.empresaId) {
            viewModel.empresaId?.let { newEmpresaId ->
                sessionViewModel.setSession(newEmpresaId, "empresa")
                navController.navigate("OfertasScreen?isEmpresa=true")
            }
        }
    }

    val empresa by viewModel.empresa.collectAsState()
    if (esEdicion) {
        val ofertas by ofertaViewModel.ofertas.collectAsState()
        LaunchedEffect(empresaId) {
            if (empresaId > 0) {
                viewModel.cargarDatos(empresaId)
            }
            ofertaViewModel.cargarOfertas(empresaId)
        }
        LaunchedEffect(empresa) {
            empresa?.let {
                idEmpresaForm = it.id
                nombreEmpresa = it.nombre
                username = it.username
                password = it.password
                sector = it.sector
                ciudad = it.ciudad
                telefono = it.telefono
                correo = it.correoElectronico.orEmpty().trim()
                descripcion = it.descripcion
                imageUri = RetrofitInstance.buildUri(it.imagen)
            }
        }
        LaunchedEffect(ofertas) {
            offerCards.clear()
            originalCards.clear()
            val loadedCards = ofertas.map {
                OfferCardData(
                    id = it.id,
                    title = it.titulo ?: "",
                    description = it.descripcion ?: "",
                    aptitudes = it.aptitudes ?: "",
                    queSeOfrece = it.queSeOfrece ?: "",
                    isPublic = it.publicada,
                    isSaved = true
                )
            }
            offerCards.addAll(loadedCards)
            originalCards.addAll(loadedCards)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .padding(top = 50.dp)
            .padding(horizontal = 50.dp)
            .verticalScroll(rememberScrollState())
            .padding(bottom = contentPadding.calculateBottomPadding()),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        UploadFileComponent(
            label = "",
            mimeType = "image/*",
            storageKey = "empresa_imagen_uri",
            initialUri = imageUri,
            mostrarVistaPreviaImagen = true,
            modifier = Modifier.width(100.dp).height(50.dp),
            onFileReadyToUpload = { uri, _, _ ->
                imageUri = uri
            }
        )

        UserSelectedImage(imageUri)
        PerfilProgresoCompleto(porcentajeCompletado)

        OutlinedInputTextField(nombreEmpresa, { nombreEmpresa = it }, "Nombre Empresa*", Modifier.height(60.dp))
        OutlinedInputTextField(username, { username = it }, "User Name*", Modifier.height(60.dp))
        if (!esEdicion) {
            OutlinedInputTextField(password, { password = it }, "Contraseña*", Modifier.height(60.dp))
        }
        OutlinedInputTextField(sector, { sector = it }, "Sector*", Modifier.height(60.dp))
        OutlinedInputTextField(ciudad, { ciudad = it }, "Ciudad*", Modifier.height(60.dp))
        OutlinedInputTextField(telefono, { telefono = it }, "Teléfono", Modifier.height(60.dp))
        OutlinedInputTextField(correo, { correo = it }, "Correo electrónico", Modifier.height(60.dp))
        OutlinedInputTextField(descripcion, { descripcion = it }, "Breve descripción*", Modifier.height(60.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextTitle("OFERTAS", style = MaterialTheme.typography.titleSmall)
            IconButton(onClick = {
                offerCards.add(OfferCardData(isSaved = false))
            }) { Icon(Icons.Default.Add, contentDescription = "Añadir oferta") }
        }

        Spacer(modifier = Modifier.height(12.dp))

        offerCards.filter { !it.isMarkedForDeletion }.forEachIndexed { index, card ->
            OfferCardForm(
                id = card.id,
                title = card.title,
                description = card.description,
                aptitudes = card.aptitudes,
                queSeOfrece = card.queSeOfrece,
                isPublic = card.isPublic,
                isSaved = card.isSaved,
                onTitleChange = { offerCards[index] = card.copy(title = it) },
                onDescriptionChange = { offerCards[index] = card.copy(description = it) },
                onAptitudesChange = { offerCards[index] = card.copy(aptitudes = it) },
                onQueSeOfreceChange = { offerCards[index] = card.copy(queSeOfrece = it) },
                onDelete = { offerCards[index] = card.copy(isMarkedForDeletion = true) },
                onView = { offerCards[index] = card.copy(isPublic = true) },
                onHide = { offerCards[index] = card.copy(isPublic = false) }
            )
        }

        // Mostamos mensajes de error y éxito
        AutoDismissErrorText(text = mensajeErrorLocal, onDismiss = { mensajeErrorLocal = null })
        AutoDismissCorrectText( text = mensajeCorectLocal, onDismiss = { mensajeCorectLocal = null })
        ButtonGeneric(
            text = "GUARDAR",
            onClick = {
                coroutineScope.launch {
                    if (nombreEmpresa.isBlank() || username.isBlank() || password.isBlank() || sector.isBlank() || ciudad.isBlank() || descripcion.isBlank()) {
                        viewModel.limpiarError()
                        mensajeErrorLocal = "Por favor, completa todos los campos obligatorios."
                        return@launch
                    }

                    if (descripcion.length > 255) {
                        viewModel.limpiarError()
                        mensajeErrorLocal = "La descripción no puede tener más de 255 caracteres."
                        return@launch
                    }

                    var urlImagen: String? = null

                    if (imageUri != null && imageUri.toString().startsWith("content://")) {
                        val fileName = FileUtils.getFileNameFromUri(context, imageUri!!) ?: "imagen.jpg"
                        val tempFile = FileUtils.copyUriToTempFile(context, imageUri!!)
                        val oldFileName = viewModel.empresa.value?.imagen?.substringAfterLast("/")

                        tempFile?.let {
                            urlImagen = FileUtils.subirArchivoConOldFile(
                                context = context,
                                archivo = it,
                                nombreArchivo = fileName,
                                mimeType = "image/*",
                                nombreArchivoAntiguo = oldFileName
                            )
                        }
                    }else if (esEdicion) {
                        urlImagen = viewModel.empresa.value?.imagen
                    }

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
                        imagen = urlImagen
                    )

                    val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    focusManager.clearFocus()

                    viewModel.guardarDatos(nuevaEmpresa, esEdicion) { empresaGuardada ->
                        if (empresaGuardada?.id != null) {
                            val empresaIdValido = empresaGuardada.id!!

                            offerCards.removeAll { card ->
                                if (card.isMarkedForDeletion && card.id != null) {
                                    ofertaViewModel.eliminarOferta(card.id!!)
                                    true
                                } else false
                            }

                            offerCards.forEachIndexed { index, card ->
                                if (card.isMarkedForDeletion) return@forEachIndexed
                                val nuevaOferta = Oferta(
                                    id = card.id,
                                    titulo = card.title,
                                    descripcion = card.description,
                                    aptitudes = card.aptitudes,
                                    queSeOfrece = card.queSeOfrece,
                                    empresaId = empresaIdValido.toInt(),
                                    publicada = card.isPublic,
                                    fechaPublicacion = fechaActual
                                )
                                ofertaViewModel.guardarOferta(nuevaOferta) { ofertaGuardada ->
                                    ofertaGuardada?.let {
                                        offerCards[index] = card.copy(id = it.id, isSaved = true)
                                    } ?: run {
                                        offerCards[index] = card.copy(isSaved = true)
                                    }
                                    viewModel.cargarDatos(empresaIdValido.toLong())
                                    ofertaViewModel.cargarOfertas(empresaIdValido.toLong())
                                }
                            }

                            if (!esEdicion) {
                                sessionViewModel.setSession(empresaIdValido.toLong(), "empresa")
                                navController.navigate("OfertasScreen?isEmpresa=true")
                            }
                        }
                        mensajeCorectLocal = "Datos guardados"
                    }
                }
            },
            modifier = Modifier.width(300.dp).padding(top = 20.dp)
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



