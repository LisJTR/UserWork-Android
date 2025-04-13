package com.torre.b2c2c_tfg.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

data class BottomNavItem( val name:String, val route:String, val icon:ImageVector, )


    @Composable
    fun BottomBar(navController: NavHostController, isUserEmpresa: Boolean) {

        // Condición por tipo de usuario
        val bottomNavItems = if (isUserEmpresa) {
            listOf(
                BottomNavItem("Mis Ofertas", "pantallaPrincipalEmpresa", Icons.Default.AccountBox),
                BottomNavItem("Perfil", "profileEmpresa", Icons.Default.Person),
                BottomNavItem("Ajustes", "settingsEmpresa", Icons.Default.Settings)
            )
        } else {
            listOf(
                BottomNavItem("Mis Ofertas", "pantallaPrincipalAlumno", Icons.Default.AccountBox),
                BottomNavItem("Perfil", "profileAlumno", Icons.Default.Person),
                BottomNavItem("Ajustes", "settingsAlumno", Icons.Default.Settings)
            )
        }

        NavigationBar {
            bottomNavItems.forEach { item ->
                NavigationBarItem(
                    selected = false,
                    onClick ={ navController.navigate(item.route) },
                    icon = { Icon(item.icon, contentDescription = item.name) },
                    label = { Text(item.name) }
                )
            }
        }
    }
