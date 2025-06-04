package com.torre.b2c2c_tfg.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.torre.b2c2c_tfg.NoticiaRss.NoticiaCard
import com.torre.b2c2c_tfg.NoticiaRss.NoticiaRss
import com.torre.b2c2c_tfg.NoticiaRss.NoticiasRssViewModel
import com.torre.b2c2c_tfg.data.model.Alumno
import com.torre.b2c2c_tfg.data.model.Oferta
import com.torre.b2c2c_tfg.data.remote.RetrofitInstance
import com.torre.b2c2c_tfg.data.repository.AlumnoRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.EmpresaRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.NotificacionRepositoryImpl
import com.torre.b2c2c_tfg.data.repository.OfertaRepositoryImpl
import com.torre.b2c2c_tfg.domain.usecase.ActualizarNotificacionUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetEmpresaUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetNotificacionesPorAlumnoUseCase
import com.torre.b2c2c_tfg.domain.usecase.GetNotificacionesPorEmpresaUseCase
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
import com.torre.b2c2c_tfg.ui.viewmodel.NotificationViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.accompanist.swiperefresh.SwipeRefresh


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
            getTodasLasOfertasUseCase = GetTodasLasOfertasUseCase(OfertaRepositoryImpl(RetrofitInstance.getInstance(context)
                )
            )
        )
    }

    val userId = sessionViewModel.userId.collectAsState().value ?: 0L
    val userType = sessionViewModel.userType.collectAsState().value
    val empresas by viewModel.empresas.collectAsState()
    // val alumnos by viewModel.alumnos.collectAsState()
    //val ofertas by viewModel.ofertas.collectAsState()
    val ofertas by viewModel.ofertasFiltradas.collectAsState(initial = emptyList())
    val alumnos by viewModel.alumnosFiltrados.collectAsState(initial = emptyList())
    val noticiasRssViewModel = remember { NoticiasRssViewModel() }
    val noticias = noticiasRssViewModel.noticias

    val notificationViewModel = remember {
        NotificationViewModel(
            getNotificacionesPorAlumnoUseCase = GetNotificacionesPorAlumnoUseCase(NotificacionRepositoryImpl(RetrofitInstance.getInstance(context))),
            getNotificacionesPorEmpresaUseCase = GetNotificacionesPorEmpresaUseCase(NotificacionRepositoryImpl(RetrofitInstance.getInstance(context))),
            actualizarNotificacionUseCase = ActualizarNotificacionUseCase(NotificacionRepositoryImpl(RetrofitInstance.getInstance(context)))
        )
    }

    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    fun recargarDatos() {
        if (userType == "alumno") {
            viewModel.cargarEmpresas()
            viewModel.cargarTodasLasOfertas()
            notificationViewModel.cargarNotificaciones(userId, "alumno")
            noticiasRssViewModel.cargarNoticias("https://www.insertaempleo.es/actualidad/noticias/rss.xml")
        } else if (userType == "empresa") {
            viewModel.cargarAlumnos()
            viewModel.cargarTodasLasOfertas()
            notificationViewModel.cargarNotificaciones(userId, "empresa")
            noticiasRssViewModel.cargarNoticias("https://rss.elconfidencial.com/empresas/")
        }
    }

    LaunchedEffect(userType) { recargarDatos() }

    LaunchedEffect(Unit) {
        if (userType != null) {
            viewModel.iniciarAutoRefresco(userType, userId)
        }
    }



    println("Empresas: ${empresas.size}")
    println("Ofertas: ${ofertas.size}")

    val listaCombinada = remember(ofertas, noticias) {
        val combinada = mutableListOf<Any>()
        val noticiasLimitadas = noticias.shuffled().take(3)
        val posiciones = (0..ofertas.size).shuffled().take(noticiasLimitadas.size).sorted()

        var noticiaIndex = 0
        for (i in 0..ofertas.size) {
            if (posiciones.contains(i) && noticiaIndex < noticiasLimitadas.size) {
                combinada.add(noticiasLimitadas[noticiaIndex++])
            }
            if (i < ofertas.size) {
                combinada.add(ofertas[i])
            }
        }
        combinada
    }

    val listaCombinadaEmpresa = remember(alumnos, noticias) {
        val combinada = mutableListOf<Any>()
        val noticiasLimitadas = noticias.shuffled().take(3)
        val posiciones = (0..alumnos.size).shuffled().take(noticiasLimitadas.size).sorted()

        var noticiaIndex = 0
        for (i in 0..alumnos.size) {
            if (posiciones.contains(i) && noticiaIndex < noticiasLimitadas.size) {
                combinada.add(noticiasLimitadas[noticiaIndex++])
            }
            if (i < alumnos.size) {
                combinada.add(alumnos[i])
            }
        }
        combinada
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            isRefreshing = true
            recargarDatos()
            isRefreshing = false
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
                .systemBarsPadding()
        ) {
            HeaderContentofScreens(
                sessionViewModel = sessionViewModel,
                notificationViewModel = notificationViewModel,
                viewModel = viewModel,
                onFiltroSeleccionado = { seleccion -> viewModel.filtroSeleccionado.value = seleccion },
                navController = navController
            )


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (userType == "alumno") {
                    items(listaCombinada) { item ->
                        when (item) {
                            is Oferta -> {
                                val empresa = empresas.find { it.id?.toLong() == item.empresaId.toLong() }
                                empresa?.let {
                                    EmpresaCard(
                                        nombre = it.nombre,
                                        sector = it.sector,
                                        descripcion = item.titulo,
                                        imagenUri = RetrofitInstance.buildUri(it.imagen),
                                        onClick = { navController.navigate(ScreenRoutes.ofertaDetalle(item.id?.toLong() ?: 0L)) }
                                    )
                                }
                            }
                            is NoticiaRss -> { NoticiaCard(noticia = item) }
                        }
                    }
                }

                if (userType == "empresa") {
                    items(listaCombinadaEmpresa) { item ->
                        when (item) {
                            is Alumno -> {
                                AlumnoCard(
                                    nombre = item.nombre,
                                    apellido = item.apellido,
                                    titulacion = item.titulacion,
                                    ciudad = item.ciudad,
                                    imagenUri = RetrofitInstance.buildUri(item.imagen),
                                    onClick = { navController.navigate(ScreenRoutes.perfilDetalle(item.id?.toLong() ?: 0L)) }
                                )
                            }
                            is NoticiaRss -> { NoticiaCard(noticia = item) }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}




