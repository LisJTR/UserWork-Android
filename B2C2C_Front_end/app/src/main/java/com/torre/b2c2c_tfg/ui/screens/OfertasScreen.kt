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
import com.torre.b2c2c_tfg.data.repository.EmpresaRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.OfertaRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetOfertasUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetSectoresUnicosUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetTitulacionesUnicasUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetTodasLasOfertasUseCase
import com.torre.b2c2c_tfg.ui.components.BottomBar
import com.torre.b2c2c_tfg.ui.components.HeaderContentofScreens
import com.torre.b2c2c_tfg.ui.theme.B2C2C_TFGTheme
import com.torre.b2c2c_tfg.ui.util.UserType
import com.torre.b2c2c_tfg.ui.viewmodel.OfertasScreenViewModel
import com.torre.b2c2c_tfg.ui.viewmodel.SessionViewModel
import com.torre.b2c2c_tfg.ui.components.EmpresaCard
import com.torre.b2c2c_tfg.ui.components.AlumnoCard
import com.torre.b2c2c_tfg.ui.navigation.ScreenRoutes
import kotlinx.coroutines.delay


@Composable
fun OfertasScreen(navController: NavController,sessionViewModel: SessionViewModel, isUserEmpresa: Boolean) {
    val context = LocalContext.current


    val viewModel = remember {
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
    val empresas by viewModel.empresas.collectAsState()
   // val alumnos by viewModel.alumnos.collectAsState()
    //val ofertas by viewModel.ofertas.collectAsState()
    val ofertas by viewModel.ofertasFiltradas.collectAsState(initial = emptyList())
    val alumnos by viewModel.alumnosFiltrados.collectAsState(initial = emptyList())

    LaunchedEffect(userType) {
        if (userType == "alumno") {
            viewModel.cargarEmpresas()
            viewModel.cargarTodasLasOfertas()
        } else if (userType == "empresa") {
            viewModel.cargarAlumnos()
        }
    }


    println("Empresas: ${empresas.size}")
    println("Ofertas: ${ofertas.size}")


    LazyColumn {
        item {
            HeaderContentofScreens(
                sessionViewModel = sessionViewModel,
                viewModel = viewModel,
                onFiltroSeleccionado = { seleccion ->
                    println("Filtro seleccionado en pantalla: $seleccion")
                    viewModel.filtroSeleccionado.value = seleccion
                },
                navController = navController
            )
        }

        if (userType == "alumno") {
            items(ofertas) { oferta ->

                println("Oferta: ${oferta.titulo} | empresaId: ${oferta.empresaId}")
                val empresa = empresas.find { it.id?.toLong() == oferta.empresaId.toLong() }
                println("Empresa encontrada: ${empresa?.nombre ?: "NO ENCONTRADA"}")

                empresa?.let {
                    EmpresaCard(
                        nombre = it.nombre,
                        sector = it.sector,
                        descripcion = oferta.titulo,
                        imagenUri = RetrofitInstance.buildUri(it.imagen),
                        onClick = {
                            navController.navigate(ScreenRoutes.ofertaDetalle(oferta.id?.toLong() ?: 0L))
                        }
                    )
                }
                println("Oferta: ${oferta.titulo} | empresaId: ${oferta.empresaId}")

            }
        }

        if (userType == "empresa") {
            items(alumnos) { alumno ->
                AlumnoCard(
                    nombre = alumno.nombre,
                    apellido = alumno.apellido,
                    titulacion = alumno.titulacion,
                    ciudad = alumno.ciudad,
                    imagenUri = RetrofitInstance.buildUri(alumno.imagen),
                    onClick = {
                        navController.navigate(ScreenRoutes.perfilDetalle(alumno.id?.toLong() ?: 0L))
                    }
                )
            }
        }
    }


}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun OfertasScreen() {
    val navController = rememberNavController()

    //Se especifica el bottomBar para que aparezca en la pantalla de Preview
    B2C2C_TFGTheme {
        Scaffold(

            bottomBar = {
                BottomBar(navController = navController, userType = UserType.EMPRESA)
            }
        ) {
            OfertasScreen(navController = navController, sessionViewModel = SessionViewModel() , isUserEmpresa = true)
        }
    }
}
