package com.example.ferreteriahogar.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ferreteriahogar.R
import com.example.ferreteriahogar.ui.Routes
import androidx.compose.ui.res.colorResource
import com.example.ferreteriahogar.data.accounts
import com.example.ferreteriahogar.utils.sha256

@Composable



fun Login(paddingValues: PaddingValues, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordHashed by remember {mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        colorResource(id = R.color.VERD_FONDO)
                    )
                )
            )
    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ======= TÍTULO =======
            Image(
                painter = painterResource(id = R.drawable.logo_ferr),
                contentDescription = "Imagen de login",
                modifier = Modifier

                    .width(500.dp)
                    .height(150.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp, vertical = 20.dp),
                contentScale = ContentScale.FillWidth
            )

            // ======= CONTENEDOR DE LOS INPUTS =======
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.VERD_FUER)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .background(
                            color = colorResource(id = R.color.VERD_FUER),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(vertical = 25.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        // =========== CAMPO EMAIL ===========
                        TextField(
                            value = email,
                            onValueChange = {
                                if (it.length <= 25) email = it
                            },
                            label = { Text("Nombre") },
                            leadingIcon = {
                                Icon(Icons.Rounded.AccountCircle, contentDescription = "")
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor =  Color.Transparent,
                                unfocusedIndicatorColor =  Color.Transparent,
                                focusedContainerColor =  colorResource(id = R.color.VERD_FONDO),
                                unfocusedContainerColor =  colorResource(id = R.color.VERD_FONDO),
                                focusedLabelColor = Color.Black,
                                unfocusedLabelColor = Color.Black,
                            ),
                            supportingText = {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (emailError.isNotEmpty()) {
                                        Text(
                                            text = emailError,
                                            color = Color.Red,
                                            fontSize = 13.sp,
                                        )
                                    }
                                    Text(
                                        text = "${email.length} / 25",
                                        textAlign = TextAlign.End,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        // =========== CAMPO PASSWORD ===========
                        TextField(
                            value = password,
                            onValueChange = {
                                if (it.length <= 65) password = it
                            },
                            label = { Text("Contraseña") },
                            leadingIcon = {
                                Icon(Icons.Rounded.Lock, contentDescription = "")
                            },
                            visualTransformation = if (passwordVisible)
                                VisualTransformation.None else PasswordVisualTransformation(),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor =  Color.Transparent,
                                unfocusedIndicatorColor =  Color.Transparent,
                                focusedContainerColor =  colorResource(id = R.color.VERD_FONDO),
                                unfocusedContainerColor =  colorResource(id = R.color.VERD_FONDO),
                                focusedLabelColor = Color.Black,
                                unfocusedLabelColor = Color.Black,
                            ),
                            supportingText = {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (passwordError.isNotEmpty()) {
                                        Text(
                                            text = passwordError,
                                            color = Color.Red,
                                            fontSize = 13.sp,
                                        )
                                    }
                                    Text(
                                        text = "${password.length} / 65",
                                        textAlign = TextAlign.End,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(40.dp))


                        // =========== BOTÓN LOGIN ===========
                        Button(
                            onClick = {
                                emailError = if (email.isBlank()) "Nombre no puede estar vacio" else ""
                                passwordError = if (password.isBlank()) "Contraseña no puede estar vacia" else ""
                                if (emailError.isEmpty() && passwordError.isEmpty()){
                                    val user = accounts.find {it.user == email && it.password == password}

                                    if (user != null){
                                        passwordHashed = sha256(password)
                                        println("Contraseña bruta: $password")

                                        navController.navigate(Routes.MainMenu+"/${user.user}"+"/${passwordHashed}")
                                    }else {
                                        loginError = "El usuario o contraseña no coinciden"
                                    }

                                }

                            },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(50.dp),
                            shape = RoundedCornerShape(25.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 4.dp,
                                pressedElevation = 8.dp
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF3C3A3A),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Ingresar",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        if (loginError.isNotEmpty()){
                            Text(
                                text = loginError,
                                color = Color.Red,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(top = 17.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // =========== TEXTO "OLVIDASTE CONTRASEÑA" ===========
                        Text(
                            text = "¿Olvidaste la contraseña?",
                            color = Color.Black,
                            modifier = Modifier.clickable {
                                // Lógica para recuperar contraseña
                            },
                            fontSize = 16.sp
                        )

                    }

                }
            }



        }
        Image(
            painter = painterResource(id = R.drawable.logo_comp),
            contentDescription = "Imagen de login",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(200.dp)
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            contentScale = ContentScale.FillBounds
        )
    }


    }


@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    Login(
        paddingValues = PaddingValues(),
        navController = rememberNavController()
    )
}
