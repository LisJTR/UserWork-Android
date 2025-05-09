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


// UI principal
@SuppressLint("SuspiciousIndentation")
@Composable
fun WelcomeScreen(navController: NavController, sessionViewModel: SessionViewModel) {

    // Estados para controlar el diálogo y los inputs
    var showLoginDialog by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showRegisterDialog by remember { mutableStateOf(false) }

    // Viewmodel para el login
    val context = LocalContext.current
    val loginViewModel = remember {
        //LoginViewModel(LoginUseCase(FakeLoginRepository()))
        LoginViewModel(LoginUseCase(LoginRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }

    val loginResult by loginViewModel.loginResult.collectAsState()

    val userId = loginViewModel.loggedUserId.collectAsState().value
    val userType = loginViewModel.loggedUserType.collectAsState().value

    LaunchedEffect(loginResult) {
        if (loginResult.contains("exitoso") && userId != null && userType != null) {
            showLoginDialog = false

            // Guardar la sesión
            sessionViewModel.setSession(userId, userType)

            val route = when (userType) {
                "alumno" -> ScreenRoutes.home(isEmpresa = false)
                "empresa" -> ScreenRoutes.home(isEmpresa = true)
                else -> ScreenRoutes.Welcome
            }

            navController.navigate(route)
        }
    }


    Column(
        verticalArrangement = Arrangement.spacedBy(
            10.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(30.dp)

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
                text = "¿Has olvidado tu contraseña?",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(top = 10.dp)

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
            onDismiss = { showLoginDialog = false },
            onLoginClick = {
                //loginViewModel.login(username, "", password)
                val isEmail = username.contains("@")
                loginViewModel.login(
                    username = if (!isEmail) username else null,
                    email = if (isEmail) username else null,
                    password = password
                )
            },
        )
    }

    //Dialog que muestra el tipo de usuario a registrar
    if (showRegisterDialog) {
        RegisterTypeDialog(
            onDismiss = { showRegisterDialog = false },
            onAlumnoClick = {
                showRegisterDialog = false
                navController.navigate(
                    ScreenRoutes.empresaProfile(true)
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
