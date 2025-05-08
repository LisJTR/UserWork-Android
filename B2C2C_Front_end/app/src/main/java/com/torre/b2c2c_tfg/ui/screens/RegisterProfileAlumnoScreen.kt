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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.torre.b2c2c_tfg.ui.components.TextTitle
import com.torre.b2c2c_tfg.ui.components.UploadFileComponent
import com.torre.b2c2c_tfg.ui.components.UserSelectedImage
import com.torre.b2c2c_tfg.ui.util.UserType
import com.torre.b2c2c_tfg.ui.viewmodel.RegisterAlumnoViewModel
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterProfileAlumnoScreen(navController: NavController , contentPadding: PaddingValues = PaddingValues(), esEdicion: Boolean = false, alumnoId: Long) {

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
    var docUri by remember { mutableStateOf<Uri?>(null) }
    var cvUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val viewModel = remember {
        RegisterAlumnoViewModel(
            //GetAlumnoUseCase(FakeAlumnoRepository()),
            GetAlumnoUseCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context))),
            //UpdateAlumnoUserCase(FakeAlumnoRepository())
            UpdateAlumnoUserCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }

    // Obtiene el alumno actual desde el ViewModel
    if (esEdicion) {
        val alumno by viewModel.alumno.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.cargarDatos()
        }

        LaunchedEffect(alumno) {
            alumno?.let {
                // Aquí actualizas los campos con sus datos
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
                    correoElectronico = it.correoElectronico.orEmpty()
                    ciudad = it.ciudad.orEmpty()
                    direccion = it.direccion.orEmpty()
                    nombreCentro = it.centro.orEmpty()
                    tituloCurso = it.titulacion.orEmpty()
                    descripcion = it.descripcion.orEmpty()
                    imageUri = it.imagen?.let { uri -> Uri.parse(uri) }
                    listaHabilidades.clear()
                    listaHabilidades.addAll(it.habilidades.orEmpty().split(",").filter { h -> h.isNotBlank() })
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
        TextTitle(
            text = "Matrícula título cursando (verificacion de titulacion)*",
            style = MaterialTheme.typography.titleSmall
        )
        UploadFileComponent(
            onFileSelected = { uri -> docUri = uri },
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)


        )
        TextTitle(
            text = "Subir CV",
            style = MaterialTheme.typography.titleSmall
        )
        UploadFileComponent(
            onFileSelected = { uri -> cvUri = uri },
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)


        )
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
                    imagen = imageUri?.toString()
                )
                viewModel.guardarDatos(nuevoAlumno)
                navController.navigate("HomeScreen?isEmpresa=false")
            },
            modifier = Modifier
                .width(300.dp)
                .padding(top = 90.dp)
        )
    }
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
        RegisterProfileAlumnoScreen(navController = navController,  contentPadding = PaddingValues(), esEdicion = true, alumnoId = 1)
    }
}



