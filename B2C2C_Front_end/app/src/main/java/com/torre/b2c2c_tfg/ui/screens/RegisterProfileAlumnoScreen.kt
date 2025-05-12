package com.torre.b2c2c_tfg.ui.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.torre.b2c2c_tfg.ui.components.UploadFileImageComponent
import com.torre.b2c2c_tfg.ui.components.UploadDocComponent



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
    var tituloCurso by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var nuevaHabilidad by remember { mutableStateOf("") }
    val listaHabilidades = remember { mutableStateListOf<String>() }
    var nombreDoc by rememberSaveable { mutableStateOf<String?>(null) }
    var cvUri by remember { mutableStateOf<Uri?>(null) }

    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()



    val context = LocalContext.current
    val viewModel = remember {
        RegisterAlumnoViewModel(
            //GetAlumnoUseCase(FakeAlumnoRepository()),
            GetAlumnoUseCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context))),
            //UpdateAlumnoUserCase(FakeAlumnoRepository())
            UpdateAlumnoUserCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context))),
            CreateAlumnoUseCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }

    val alumno by viewModel.alumno.collectAsState()
    val alumnoId = sessionViewModel.userId.collectAsState().value ?: 0L

    // Si es edición, carga los datos del alumno
    if (esEdicion) {

        LaunchedEffect(alumnoId) {
            println("Alumno ID recibido: $alumnoId")
            if (alumnoId > 0) {
                viewModel.cargarDatos(alumnoId)
            }
        }

        // Actualiza los campos cuando llegue el alumno
        LaunchedEffect(alumno) {
            alumno?.let {
                alumno?.let {
                    idAlumnoForm = it.id ?: 0
                    nombre = it.nombre.orEmpty()
                    username = it.username.orEmpty()
                    password = it.password.orEmpty()
                    apellido = it.apellido.orEmpty()
                    telefono = it.telefono.orEmpty()
                    println("Correo electrónico desde backend: '${it.correoElectronico}'")
                    println(Gson().toJson(alumno))
                    correoElectronico = it.correoElectronico.orEmpty().trim()
                    ciudad = it.ciudad.orEmpty()
                    direccion = it.direccion.orEmpty()
                    nombreCentro = it.centro.orEmpty()
                    descripcion = it.descripcion.orEmpty()
                    imageUri = it.imagen?.let { uri -> uri.toUri() }
                    cvUri = it.cvUri?.let { uri -> uri.toUri() }
                    nombreDoc = it.nombreDoc.orEmpty()
                    listaHabilidades.clear()
                    listaHabilidades.addAll(
                        it.habilidades.orEmpty().split(",").filter { h -> h.isNotBlank() })
                }
            }
        }
    }

    // Si no es edición, guarda sesión y navega una vez se obtenga el ID
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
            .padding(top = 50.dp)
            .padding(horizontal = 50.dp)
            .verticalScroll(rememberScrollState()) // añade scroll
            .padding(bottom = contentPadding.calculateBottomPadding()), // se agrega el padding bottom para que no se pisen los botones con la barra de navegación
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UploadFileImageComponent(
            onFileSelected = { uri -> imageUri = uri },
            mimeType = "image/*",
            initialUri = imageUri,
            modifier = Modifier
                .width(100.dp)
                .height(50.dp),
            esEdicion = esEdicion


        )

        UserSelectedImage(imageUri)

        OutlinedInputTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = "Nombre*",
            modifier = Modifier
                .height(60.dp)
        )
        OutlinedInputTextField(
                value = apellido,
        onValueChange = { apellido = it },
        label = "Apellido*",
        modifier = Modifier
            .height(60.dp)
        )
        OutlinedInputTextField(
            value = username,
            onValueChange = { username = it },
            label = "User name*",
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
            value = telefono,
            onValueChange = { telefono = it },
            label = "Nº Teléfono",
            modifier = Modifier
                .height(60.dp)
        )
        OutlinedInputTextField(
            value = correoElectronico,
            onValueChange = { correoElectronico = it },
            label = "Correo Electrónico*",
            modifier = Modifier
                .height(60.dp)
        )
        OutlinedInputTextField(
            value = ciudad,
            onValueChange = { ciudad = it },
            label = "Ciudad",
            modifier = Modifier
                .height(60.dp)
        )
        OutlinedInputTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = "Dirección",
            modifier = Modifier
                .height(60.dp)
        )
        OutlinedInputTextField(
            value = nombreCentro,
            onValueChange = { nombreCentro = it },
            label = "Nombre del centro*",
            modifier = Modifier
                .height(60.dp)
        )
        OutlinedInputTextField(
            value = tituloCurso,
            onValueChange = { tituloCurso = it },
            label = "Título que se está cursando",
            modifier = Modifier
                .height(60.dp)
        )

        // Campo para añadir habilidad + botón "+"
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedInputTextField(
                value = nuevaHabilidad,
                onValueChange = { nuevaHabilidad = it },
                label = "Habilidades",
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    val habilidadLimpia = nuevaHabilidad.trim()
                    if (habilidadLimpia.isNotBlank() && !listaHabilidades.contains(habilidadLimpia)) {
                        listaHabilidades.add(habilidadLimpia)
                        nuevaHabilidad = ""
                    }
                }

            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir habilidad")
            }
        }

        // Mostrar habilidades añadidas
        FlowRow( // necesitas importar androidx.compose.foundation.layout.FlowRow
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            listaHabilidades.forEach { habilidad ->
                HabilidadChip(
                    habilidad = habilidad,
                    onRemove = { listaHabilidades.remove(habilidad) }
                )
            }
        }


        OutlinedInputTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = "Breve descripción",
            modifier = Modifier
                .height(60.dp)
        )
        UploadDocComponent(
            label = "Subir CV",
            storageKey = "doc_titulacion_uri",
            initialUri = cvUri,
            onFileSelected = { uri, name ->
                println("DEBUG: Doc seleccionado - URI: $uri, Nombre: $name")
                cvUri = uri
                nombreDoc = name
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedInputTextField(
            value = nombreDoc ?: "",
            onValueChange = {},
            label = "Nombre del documento",
            modifier = Modifier.fillMaxWidth(),
            enabled = false // Solo lectura
        )

        // --- BOTÓN GUARDAR ---
        ButtonGeneric(
            text = "GUARDAR",
            onClick = {
                val habilidadesTexto = listaHabilidades.joinToString(",") // Convierte la lista en una cadena separada por comas

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
                    titulacion = tituloCurso,
                    descripcion = descripcion,
                    habilidades = habilidadesTexto,
                    cvUri = cvUri?.toString(),
                    nombreDoc = nombreDoc,
                    imagen = imageUri?.toString()
                )
                println("DEBUG: nombreDoc actual antes de guardar: $nombreDoc")
                println("DEBUG FINAL ANTES DE GUARDAR: " + Gson().toJson(nuevoAlumno))

                viewModel.guardarDatos(nuevoAlumno , esEdicion)

                // Quitar el foco para que se cierre el teclado y desaparezca el cursor
                focusManager.clearFocus()

                if (!esEdicion) {
                    // Esperamos brevemente y navegamos
                    coroutineScope.launch {
                        delay(500)
                        viewModel.alumnoId?.let { newUserId ->
                            sessionViewModel.setSession(newUserId, "alumno")
                            navController.navigate("OfertasScreen?isEmpresa=false")
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



