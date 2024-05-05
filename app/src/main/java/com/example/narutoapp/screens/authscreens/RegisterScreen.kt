package com.example.customjetpack.customscreens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.narutoapp.screens.AppScreen
import com.example.narutoapp.screens.authscreens.AuthViewModel
import com.example.narutoapp.ui.theme.LightBlue
import com.example.narutoapp.util.Res

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val authResult = viewModel.signupFlow.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            var name by remember {
                mutableStateOf("")
            }
            var emailText by remember {
                mutableStateOf("")
            }
            var password by remember {
                mutableStateOf("")
            }
            TextField(
                modifier = Modifier
                    .padding(top = 60.dp)
                    .clip(RoundedCornerShape(10.dp)),
                label = {
                    Text(text = "Enter your name")
                },
                value = name,
                onValueChange = { name = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            TextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clip(RoundedCornerShape(10.dp)),
                label = {
                    Text(text = "Enter your email")
                },
                value = emailText,
                onValueChange = { emailText = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )
            TextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clip(RoundedCornerShape(10.dp)),
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(text = "Enter your password")
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(vertical = 15.dp),
                onClick = {
                    viewModel.signup(name, emailText, password)
                }) {
                Text(text = "SingUp")
            }
            Text(
                modifier = Modifier.clickable {
                    navController.navigate(AppScreen.AuthScreen.route) {
                        popUpTo(AppScreen.RegisterScreen.route) { inclusive = true }
                    }
                },
                text = "Already have an account?\nClick here to login",
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }

        authResult.value.let {
            when (it) {
                is Res.Failure -> {
                    Toast.makeText(LocalContext.current, it.exception.message, Toast.LENGTH_SHORT)
                        .show()
                }

                is Res.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize(0.5f)
                    )
                }

                is Res.Success -> {
                    LaunchedEffect(key1 = Unit) {
                        navController.navigate(AppScreen.UserInfo.route) {
                            popUpTo(AppScreen.RegisterScreen.route) { inclusive = true }
                        }
                    }
                }

                null -> {

                }
            }
        }
    }
}