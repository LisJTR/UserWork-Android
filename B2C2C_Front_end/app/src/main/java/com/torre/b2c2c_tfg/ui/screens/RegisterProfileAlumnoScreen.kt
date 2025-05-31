package com.torre.b2c2c_tfg.ui.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import com.torre.b2c2c_tfg.data.repository.AlumnoRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.UpdateAlumnoUserCase
import com.torre.b2c2c_tfg.ui.components.BottomBar
import com.torre.b2c2c_tfg.ui.components.ButtonGeneric
import com.torre.b2c2c_tfg.ui.components.HabilidadChip
import com.torre.b2c2c_tfg.ui.components.OutlinedInputTextField
import com.torre.b2c2c_tfg.ui.components.UserSelectedImage
import com.torre.b2c2c_tfg.ui.util.UserType
import com.torre.b2c2c_tfg.ui.viewmodel.RegisterAlumnoViewModel
import androidx.compose.ui.platform.LocalContext
import com.google.gson.Gson
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel
import androidx.compose.ui.platform.LocalFocusManager
import com.torre.b2c2c_tfg.domain.usecase.CreateAlumnoUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import com.torre.b2c2c_tfg.ui.components.AutoDismissCorrectText
import com.torre.b2c2c_tfg.ui.components.AutoDismissErrorText
import com.torre.b2c2c_tfg.ui.components.PerfilProgresoCompleto
import com.torre.b2c2c_tfg.ui.components.UploadFileComponent
import com.torre.b2c2c_tfg.ui.util.FileUtils
import java.io.File


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterProfileAlumnoScreen(navController: NavController, sessionViewModel: SessionViewModel, contentPadding: PaddingValues = PaddingValues(), esEdicion: Boolean = false) {

    var idAlumnoForm by remember { mutableStateOf<Int?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var nombre by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correoElectronico by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var nombreCentro by remember { mutableStateOf("") }
    var titulacion by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var nuevaHabilidad by remember { mutableStateOf("") }
    val listaHabilidades = remember { mutableStateListOf<String>() }
    var nombreDoc by rememberSaveable { mutableStateOf<String?>(null) }
    var cvUri by remember { mutableStateOf<Uri?>(null) }
    var archivoCv by remember { mutableStateOf<File?>(null) }
    var mensajeErrorLocal by remember { mutableStateOf<String?>(null) }
    var mensajeCorectLocal by remember { mutableStateOf<String?>(null) }

    val porcentajeCompletado by remember(
        imageUri, nombre, username, apellido, telefono, correoElectronico,
        ciudad, direccion, nombreCentro, titulacion, descripcion,
        listaHabilidades, cvUri
    ) {
        derivedStateOf {
            val totalCampos = 13
            var completados = 0

            if (imageUri != null) completados++
            if (nombre.isNotBlank()) completados++
            if (username.isNotBlank()) completados++
            if (apellido.isNotBlank()) completados++
            if (telefono.isNotBlank()) completados++
            if (correoElectronico.isNotBlank()) completados++
            if (ciudad.isNotBlank()) completados++
            if (direccion.isNotBlank()) completados++
            if (nombreCentro.isNotBlank()) completados++
            if (titulacion.isNotBlank()) completados++
            if (descripcion.isNotBlank()) completados++
            if (listaHabilidades.size >= 3) completados++
            if (cvUri != null) completados++

            completados / totalCampos.toFloat()
        }
    }



    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val viewModel = remember {
        RegisterAlumnoViewModel(
            GetAlumnoUseCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context))),
            UpdateAlumnoUserCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context))),
            CreateAlumnoUseCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }

    val alumno by viewModel.alumno.collectAsState()
    val alumnoId = sessionViewModel.userId.collectAsState().value ?: 0L

    if (esEdicion) {
        LaunchedEffect(alumnoId) {
            if (alumnoId > 0) {
                viewModel.cargarDatos(alumnoId)
            }
        }

        LaunchedEffect(alumno) {
            alumno?.let {
                idAlumnoForm = it.id ?: 0
                nombre = it.nombre.orEmpty()
                username = it.username.orEmpty()
                password = it.password.orEmpty()
                apellido = it.apellido.orEmpty()
                telefono = it.telefono.orEmpty()
                correoElectronico = it.correoElectronico.orEmpty().trim()
                ciudad = it.ciudad.orEmpty()
                direccion = it.direccion.orEmpty()
                nombreCentro = it.centro.orEmpty()
                titulacion = it.titulacion.orEmpty()
                descripcion = it.descripcion.orEmpty()
                imageUri = RetrofitInstance.buildUri(it.imagen)
                cvUri = it.cvUri?.toUri()
                nombreDoc = it.nombreDoc.orEmpty()
                listaHabilidades.clear()
                listaHabilidades.addAll(it.habilidades.orEmpty().split(",").filter { h -> h.isNotBlank() })
            }
        }
    }

    if (!esEdicion) {
        LaunchedEffect(viewModel.alumnoId) {
            viewModel.alumnoId?.let { newUserId ->
                sessionViewModel.setSession(newUserId, "alumno")
                navController.navigate("OfertasScreen?isEmpresa=false")
            }
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
            storageKey = "alumno_imagen_uri",
            initialUri = imageUri,
            mostrarVistaPreviaImagen = true,
            modifier = Modifier.width(100.dp).height(50.dp),
            onFileReadyToUpload = { uri, _, _ ->
                imageUri = uri
            }
        )
        PerfilProgresoCompleto(porcentajeCompletado)
        UserSelectedImage(imageUri)

        OutlinedInputTextField(nombre, { nombre = it }, "Nombre*", Modifier.height(60.dp))
        OutlinedInputTextField(apellido, { apellido = it }, "Apellido*", Modifier.height(60.dp))
        OutlinedInputTextField(username, { username = it }, "User name*", Modifier.height(60.dp))
        if (!esEdicion) {
            OutlinedInputTextField(password, { password = it }, "Contraseña*", Modifier.height(60.dp))
        }
        OutlinedInputTextField(telefono, { telefono = it }, "Nº Teléfono", Modifier.height(60.dp))
        OutlinedInputTextField(correoElectronico, { correoElectronico = it }, "Correo Electrónico*", Modifier.height(60.dp))
        OutlinedInputTextField(ciudad, { ciudad = it }, "Ciudad", Modifier.height(60.dp))
        OutlinedInputTextField(direccion, { direccion = it }, "Dirección", Modifier.height(60.dp))
        OutlinedInputTextField(nombreCentro, { nombreCentro = it }, "Nombre del centro*", Modifier.height(60.dp))
        OutlinedInputTextField(titulacion, { titulacion = it }, "Título que se está cursando", Modifier.height(60.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            OutlinedInputTextField(nuevaHabilidad, { nuevaHabilidad = it }, "Habilidades", Modifier.weight(1f))
            IconButton(onClick = {
                val habilidadLimpia = nuevaHabilidad.trim()
                if (habilidadLimpia.isNotBlank() && !listaHabilidades.contains(habilidadLimpia)) {
                    listaHabilidades.add(habilidadLimpia)
                    nuevaHabilidad = ""
                }
            }) { Icon(Icons.Default.Add, contentDescription = "Añadir habilidad") }
        }

        FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            listaHabilidades.forEach { habilidad ->
                HabilidadChip(habilidad = habilidad, onRemove = { listaHabilidades.remove(habilidad) })
            }
        }

        OutlinedInputTextField(descripcion, { descripcion = it }, "Breve descripción", Modifier.height(60.dp))

        UploadFileComponent(
            label = "Subir CV",
            mimeType = "application/pdf",
            storageKey = "cv_uri",
            initialUri = cvUri,
            mostrarVistaPreviaImagen = false,
            onFileReadyToUpload = { uri, file, nombre ->
                cvUri = uri
                archivoCv = file
                nombreDoc = nombre
            }
        )

        OutlinedInputTextField(nombreDoc ?: "", {}, "Nombre del documento", Modifier.fillMaxWidth(), enabled = false)

        AutoDismissErrorText(text = mensajeErrorLocal, onDismiss = { mensajeErrorLocal = null })
        AutoDismissCorrectText( text = mensajeCorectLocal, onDismiss = { mensajeCorectLocal = null })
        ButtonGeneric(
            text = "GUARDAR",
            onClick = {
                coroutineScope.launch {
                    viewModel.limpiarError()
                    if (nombre.isBlank() || apellido.isBlank() || username.isBlank() || password.isBlank() || correoElectronico.isBlank()) {
                        mensajeErrorLocal = "Por favor, completa todos los campos obligatorios."
                        return@launch
                    }
                    if (descripcion.length > 255) {
                        mensajeErrorLocal = "La descripción no puede tener más de 255 caracteres."
                        return@launch
                    }

                    val habilidadesTexto = listaHabilidades.joinToString(",")
                    var urlDelCV: String? = null
                    var urlImagen: String? = null

                    if (imageUri != null && imageUri.toString().startsWith("content://")) {
                        val fileName = FileUtils.getFileNameFromUri(context, imageUri!!) ?: "imagen.jpg"
                        val tempFile = FileUtils.copyUriToTempFile(context, imageUri!!)
                        val oldFileName = viewModel.alumno.value?.imagen?.substringAfterLast("/")

                        tempFile?.let {
                            urlImagen = FileUtils.subirArchivoConOldFile(
                                context = context,
                                archivo = it,
                                nombreArchivo = fileName,
                                mimeType = "image/*",
                                nombreArchivoAntiguo = oldFileName
                            )
                        }
                    } else if (esEdicion) {
                        urlImagen = alumno?.imagen
                    }

                    if (archivoCv != null && nombreDoc != null) {
                        val oldFileName = viewModel.alumno.value?.cvUri?.substringAfterLast("/")

                        urlDelCV = FileUtils.subirArchivoConOldFile(
                            context = context,
                            archivo = archivoCv!!,
                            nombreArchivo = nombreDoc!!,
                            mimeType = "application/pdf",
                            nombreArchivoAntiguo = oldFileName
                        )

                        cvUri = urlDelCV?.toUri() // asegura coherencia para el siguiente guardado
                    }

                    val nuevoAlumno = Alumno(
                        id = idAlumnoForm,
                        nombre = nombre,
                        username = username,
                        apellido = apellido,
                        password = password,
                        telefono = telefono,
                        correoElectronico = correoElectronico,
                        ciudad = ciudad,
                        direccion = direccion,
                        centro = nombreCentro,
                        titulacion = titulacion,
                        descripcion = descripcion,
                        habilidades = habilidadesTexto,
                        cvUri = urlDelCV,
                        nombreDoc = nombreDoc,
                        imagen = urlImagen
                    )

                    viewModel.guardarDatos(nuevoAlumno, esEdicion)
                    mensajeCorectLocal = "Datos guardados"
                    focusManager.clearFocus()

                    if (!esEdicion) {
                        delay(500)
                        viewModel.alumnoId?.let { newUserId ->
                            sessionViewModel.setSession(newUserId, "alumno")
                            navController.navigate("OfertasScreen?isEmpresa=false")
                        }
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
fun LoginAlumnoScreenPreview() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController, userType = UserType.ALUMNO)
        }
    ) {
        RegisterProfileAlumnoScreen(navController = navController,sessionViewModel = SessionViewModel())
    }
}



