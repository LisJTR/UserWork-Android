package com.torre.b2c2c_tfg.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import com.torre.b2c2c_tfg.data.repository.LoginRepositoryImpl
//import com.torre.b2c2c_tfg.data.repository.LoginRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.LoginUseCase
import com.torre.b2c2c_tfg.ui.components.Applogo
import com.torre.b2c2c_tfg.ui.components.ButtonGeneric
import com.torre.b2c2c_tfg.ui.components.LoginDialog
import com.torre.b2c2c_tfg.ui.components.SectionDescription
import com.torre.b2c2c_tfg.ui.theme.B2C2C_TFGTheme
import com.torre.b2c2c_tfg.ui.components.RegisterTypeDialog
import com.torre.b2c2c_tfg.ui.components.TextTitle
import com.torre.b2c2c_tfg.ui.navigation.ScreenRoutes
import com.torre.b2c2c_tfg.ui.viewmodel.LoginViewModel
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel
import kotlinx.coroutines.delay


// UI principal
@SuppressLint("SuspiciousIndentation")
@Composable
fun WelcomeScreen(navController: NavController, sessionViewModel: SessionViewModel) {

    // Estados para controlar el diálogo y los inputs
    var showLoginDialog by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showRegisterDialog by remember { mutableStateOf(false) }
    var mensajeErrorLocal by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val loginViewModel = remember {
        //LoginViewModel(LoginUseCase(FakeLoginRepository()))
        LoginViewModel(LoginUseCase(LoginRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }

    val loginResult = loginViewModel.loginResult.collectAsState().value
    val userId = loginViewModel.loggedUserId.collectAsState().value
    val userType = loginViewModel.loggedUserType.collectAsState().value

    LaunchedEffect(loginResult) {
        if (loginResult.contains("exitoso") && userId != null && userType != null) {
            showLoginDialog = false

            // Guardar la sesión
            sessionViewModel.setSession(userId, userType)

            val route = when (userType) {
                "alumno" -> ScreenRoutes.ofertas(isEmpresa = false)
                "empresa" -> ScreenRoutes.ofertas(isEmpresa = true)
                else -> ScreenRoutes.Welcome
            }

            navController.navigate(route)
        } else if (loginResult.isNotBlank() && !loginResult.contains("exitoso")) {
            mensajeErrorLocal = loginResult // mensaje de error login
        }
    }
   
    Column(
        verticalArrangement = Arrangement.spacedBy(
            10.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(30.dp),


    ) {

        Applogo(modifier = Modifier.size(300.dp))

        SectionDescription(
            text = "Explora ofertas de prácticas, conecta con empresas y da el primer paso en tu carrera profesional.",


        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Botón que abre el diálogo
            ButtonGeneric(
                text = "Iniciar Sesión",
                onClick = { showLoginDialog = true },
                modifier = Modifier
                    .width(300.dp)
                    .padding(top = 90.dp)
            )


            TextTitle(
                text = "¿No tienes una cuenta?   Regístrate",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 80.dp)
                    .clickable { showRegisterDialog = true }
            )
        }
    }

    //Dialog que muestra el login de usuario
    //Diálogo condicional que se muestra si showLoginDialog es true
    if (showLoginDialog) {
        LoginDialog(
            username = username,
            password = password,
            onUsernameChange = { username = it },
            onPasswordChange = { password = it },
            onDismiss = {
                showLoginDialog = false
                mensajeErrorLocal = null },
            onLoginClick = {
                println("BOTÓN LOGIN PULSADO")

                if (username.isBlank() || password.isBlank()) {
                    mensajeErrorLocal = "Por favor, completa todos los campos."
                    println("Campos vacíos: username='$username' password='$password'")
                    return@LoginDialog
                }

                val isEmail = username.contains("@")
                val usernameFinal = if (!isEmail) username else null
                val correoFinal = if (isEmail) username else null

                if (usernameFinal == null && correoFinal == null) {
                    println("No se proporcionó username ni correo")
                    return@LoginDialog
                }

                loginViewModel.login(
                    username = usernameFinal,
                    email = correoFinal,
                    password = password
                )
            },
            mensajeErrorLocal = mensajeErrorLocal
        )
    }
    // Hace que el error desaparezca solo después de 3 segundos
    LaunchedEffect(mensajeErrorLocal) {
        if (!mensajeErrorLocal.isNullOrBlank()) {
            delay(2000)
            mensajeErrorLocal = null
        }
    }


    //Dialog que muestra el tipo de usuario a registrar
    if (showRegisterDialog) {
        RegisterTypeDialog(
            onDismiss = { showRegisterDialog = false },
            onAlumnoClick = {
                showRegisterDialog = false
                navController.navigate(
                    ScreenRoutes.alumnoProfile(true)
                )
            },

            onEmpresaClick = {
                showRegisterDialog = false
                navController.navigate(
                    ScreenRoutes.empresaProfile(true)
                )
            }
        )
    }


}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    B2C2C_TFGTheme {
        Scaffold {
            WelcomeScreen(navController = rememberNavController(), sessionViewModel = SessionViewModel())
        }
    }
}
