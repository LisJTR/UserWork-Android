package com.torre.b2c2c_tfg.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import com.torre.b2c2c_tfg.data.repository.AlumnoRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.AplicacionOfertaRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.EmpresaRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.InvitacionRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.OfertaRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetAplicacionesPorAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetInvitacionPorEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetSectoresUnicosUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetTitulacionesUnicasUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetTodasLasOfertasUseCase
import com.torre.b2c2c_tfg.ui.components.BottomBar
import com.torre.b2c2c_tfg.ui.components.EmpresaCard
import com.torre.b2c2c_tfg.ui.components.HeaderContentofScreens
import com.torre.b2c2c_tfg.ui.navigation.ScreenRoutes
import com.torre.b2c2c_tfg.ui.theme.B2C2C_TFGTheme
import com.torre.b2c2c_tfg.ui.util.UserType
import com.torre.b2c2c_tfg.ui.viewmodel.MisOfertasScreenViewModel
import com.torre.b2c2c_tfg.ui.viewmodel.OfertasScreenViewModel
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel

@Composable
fun MisOfertasScreen(
    navController: NavController,
    isUserEmpresa: Boolean,
    sessionViewModel: SessionViewModel
) {
    val context = LocalContext.current

    val viewModel = remember {
        MisOfertasScreenViewModel(
            getAplicacionesUseCase = GetAplicacionesPorAlumnoUseCase(AplicacionOfertaRepositoryImpl(RetrofitInstance.getInstance(context))),
            getTodasLasOfertasUseCase = GetTodasLasOfertasUseCase(OfertaRepositoryImpl(RetrofitInstance.getInstance(context))),
            getInvitacionesPorEmpresaUseCase = GetInvitacionPorEmpresaUseCase(InvitacionRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }

    val headerViewModel = remember {
        OfertasScreenViewModel(
            getAlumnoUseCase = GetAlumnoUseCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context))),
            getEmpresaUseCase = GetEmpresaUseCase(EmpresaRepositoryImpl(RetrofitInstance.getInstance(context))),
            getSectoresUnicosUseCase = GetSectoresUnicosUseCase(EmpresaRepositoryImpl(RetrofitInstance.getInstance(context))),
            getTitulacionesUnicasUseCase = GetTitulacionesUnicasUseCase(AlumnoRepositoryImpl(RetrofitInstance.getInstance(context))),
            alumnoRepository = AlumnoRepositoryImpl(RetrofitInstance.getInstance(context)),
            empresaRepository = EmpresaRepositoryImpl(RetrofitInstance.getInstance(context)),
            getTodasLasOfertasUseCase = GetTodasLasOfertasUseCase(OfertaRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }

    val userId = sessionViewModel.userId.collectAsState().value ?: 0L
    val userType = sessionViewModel.userType.collectAsState().value
    val ofertas by viewModel.ofertasAplicadas.collectAsState()
    val empresas by headerViewModel.empresas.collectAsState()
    val invitaciones by viewModel.invitacionesRecibidas.collectAsState()
    val ofertasFiltradas by headerViewModel.ofertasFiltradas.collectAsState(initial = emptyList())
    val alumnosFiltrados by headerViewModel.alumnosFiltrados.collectAsState(initial = emptyList())

    LaunchedEffect(userType) {
        if (userType == "alumno") {
            headerViewModel.cargarAlumno(userId)
            headerViewModel.cargarEmpresas()
            viewModel.cargarOfertasAplicadas(userId)
        } else {
            headerViewModel.cargarEmpresa(userId)
            viewModel.cargarOfertasAplicadas(userId)
            viewModel.cargarInvitaciones(userId)
            headerViewModel.cargarAlumnos()
            headerViewModel.cargarTodasLasOfertas()
        }

    }

    LazyColumn {
        item {
            HeaderContentofScreens(
                sessionViewModel = sessionViewModel,
                viewModel = headerViewModel,
                onFiltroSeleccionado = {},
                navController = navController
            )
        }

        if (userType == "alumno") {
            items(ofertas) { oferta ->
                val empresa = empresas.find { it.id?.toLong() == oferta.empresaId.toLong() }
                empresa?.let {
                    EmpresaCard(
                        nombre = it.nombre,
                        sector = it.sector,
                        descripcion = oferta.titulo,
                        imagenUrl = it.imagen,
                        onClick = {
                            navController.navigate(ScreenRoutes.ofertaDetalle(oferta.id?.toLong() ?: 0L))
                        }
                    )
                }
            }
        }

        if (userType == "empresa") {
            items(invitaciones) { invitacion ->
                val oferta = ofertasFiltradas.find { it.id?.toLong() == invitacion.ofertaId }
                val alumno = alumnosFiltrados.find { it.id?.toLong() == invitacion.alumnoId }

                if (oferta != null && alumno != null) {
                    EmpresaCard(
                        nombre = "${alumno.nombre} ${alumno.apellido}",
                        sector = alumno.titulacion,
                        descripcion = oferta.titulo,
                        imagenUrl = alumno.imagen,
                        onClick = {
                            navController.navigate(ScreenRoutes.perfilDetalle(alumno.id?.toLong() ?: 0L))
                        }
                    )
                }
            }
        }
    }
}






@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun MisOfertasScreen() {
    val navController = rememberNavController()

    //Se especifica el bottomBar para que aparezca en la pantalla de Preview
    B2C2C_TFGTheme {
        Scaffold(
            bottomBar = {
                BottomBar(navController = navController, userType = UserType.EMPRESA)
            }
        ) {
            MisOfertasScreen( navController = navController, isUserEmpresa = true, sessionViewModel = SessionViewModel())
        }
    }
}

