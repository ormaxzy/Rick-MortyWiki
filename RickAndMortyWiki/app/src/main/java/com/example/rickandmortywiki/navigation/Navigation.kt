package com.example.rickandmortywiki.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val paginationViewModel = viewModel<PaginationViewModel>()
    val locationViewModel = viewModel<LocationViewModel>()
    val selectedCharacter = remember { mutableStateOf<Character?>(null) }

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                bottomItems.forEach { screen ->
                    BottomNavigationItem(
                        modifier = Modifier.background(Color(0xFF424242)),
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController, startDestination = "paginationScreen") {
                composable("paginationScreen") {
                    PaginationScreen(
                        viewModel = paginationViewModel,
                        onItemClick = { character ->
                            selectedCharacter.value = character
                            navController.navigate("characterDetailScreen/${character.id}")
                        }
                    )
                }
                composable("locationScreen") {
                    LocationScreen(locationViewModel = locationViewModel)
                }
                composable("characterDetailScreen/{characterId}") { backStackEntry ->
                    val characterId = backStackEntry.arguments?.getString("characterId")
                    val character = selectedCharacter.value
                    character?.let {
                        CharacterDetailScreen(character = it)
                    }
                }
            }
        }
    }
}

val bottomItems = listOf(
    Screen("paginationScreen", "Pagination", Icons.Default.List),
    Screen("locationScreen", "Location", Icons.Default.LocationOn)
)

data class Screen(val route: String, val title: String, val icon: ImageVector)
