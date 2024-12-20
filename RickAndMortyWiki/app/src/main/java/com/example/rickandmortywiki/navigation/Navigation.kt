package com.example.rickandmortywiki.navigation


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortywiki.presentation.locationScreen.LocationViewModel
import com.example.rickandmortywiki.presentation.paginationScreen.PaginationViewModel
import com.example.rickandmortywiki.api.Character
import com.example.rickandmortywiki.presentation.characterDetailScreen.CharacterDetailScreen
import com.example.rickandmortywiki.presentation.locationScreen.LocationScreen
import com.example.rickandmortywiki.presentation.paginationScreen.PaginationScreen
import androidx.compose.material.BottomNavigationItem
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val paginationViewModel: PaginationViewModel = viewModel()
    val locationViewModel: LocationViewModel = viewModel()
    val selectedCharacter = remember { mutableStateOf<Character?>(null) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "paginationScreen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("paginationScreen") {
                PaginationScreen(
                    viewModel = paginationViewModel,
                    onItemClick = { character ->
                        selectedCharacter.value = character
                        navController.navigate("characterDetailScreen")
                    }
                )
            }
            composable("locationScreen") {
                LocationScreen(locationViewModel = locationViewModel)
            }
            composable("characterDetailScreen") {
                selectedCharacter.value?.let {
                    CharacterDetailScreen(character = it)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: androidx.navigation.NavController) {
    val items = listOf(
        Screen("paginationScreen", "Characters", Icons.AutoMirrored.Filled.List),
        Screen("locationScreen", "Locations", Icons.Default.LocationOn)
    )

    androidx.compose.material.BottomNavigation(
        backgroundColor = Color(0xFF424242),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


data class Screen(val route: String, val title: String, val icon: ImageVector)

