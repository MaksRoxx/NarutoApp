package com.example.customjetpack.customscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.narutoapp.screens.AppScreen
import com.example.narutoapp.screens.authscreens.AuthViewModel

@Composable
fun UserInfo(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val userName = viewModel.currentUser?.displayName.toString()
    val userEmail = viewModel.currentUser?.email.toString()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hello $userName!", fontSize = 40.sp)
            Button(
                modifier = Modifier.padding(top = 20.dp),
                onClick = {
                    viewModel.logout()
                    navController.navigate(AppScreen.AuthScreen.route) {
                        popUpTo(AppScreen.AuthScreen.route) { inclusive = true}
                    }
                }) {
                Text(text = "Logout")
            }
        }
    }
}