package com.torre.b2c2c_tfg.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

data class BottomNavItem( val name:String, val route:String, val icon:ImageVector, )


@Composable
fun BottomBar(navController: NavHostController, isUserEmpresa: Boolean) {
    val currentDestination = navController.currentBackStackEntry?.destination?.route

    // Lista de ítems de navegación, dependiendo del tipo de usuario
    val bottomNavItems =
    if (isUserEmpresa) {
        listOf(
            BottomNavItem("Ofertas", "pantallaPrincipalEmpresa", Icons.Filled.Home),
            BottomNavItem("Mis Ofertas", "pantallaPrincipalEmpresa", Icons.Default.AccountBox),
            BottomNavItem("Perfil", "profileEmpresa", Icons.Default.Person),
            BottomNavItem("Ajustes", "settingsEmpresa", Icons.Default.Settings)
        )
    } else {
        listOf(
            BottomNavItem("Ofertas", "pantallaPrincipalAlumno", Icons.Filled.Home),
            BottomNavItem("Mis Ofertas", "pantallaPrincipalAlumno", Icons.Default.AccountBox),
            BottomNavItem("Perfil", "profileAlumno", Icons.Default.Person),
            BottomNavItem("Ajustes", "settingsAlumno", Icons.Default.Settings)
        )
    }

    // Componente de barra de navegación inferior
    // Nota: Es necesario especificar los colores manualmente ya que no los toma automáticamente del theme
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,

    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentDestination == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.name,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                label = {
                    Text(
                        text = item.name,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                },
                alwaysShowLabel = true
            )
        }
    }
}
