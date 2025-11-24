package com.example.ferreteriahogar.ui.screens
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.example.ferreteriahogar.utils.RetrofitClient
import com.example.ferreteriahogar.data.*
import com.example.ferreteriahogar.utils.sha256
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.core.dsl.Keys
import com.example.ferreteriahogar.utils.TokenStore
import android.util.Base64
import org.json.JSONObject

fun RellenarUsuario(token: String): User {
    // Separar las partes del token
    val parts = token.split(".")
    if (parts.size != 3) throw IllegalArgumentException("Token inválido")

    // Decodificar el payload (segunda parte)
    val payloadJson = String(Base64.decode(parts[1], Base64.URL_SAFE))

    val jsonObject = JSONObject(payloadJson)

    val id = jsonObject.optLong("id", 0L)
    val username = jsonObject.optString("username", "")
    val role = jsonObject.optString("role", "")

    return User(
        id = id,
        username = username,
        role = role
    )
}


@Composable
fun Login(paddingValues: PaddingValues, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordHashed by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) } // <-- mutableStateOf

    // ======== LOADING OVERLAY ========
    @Composable
    fun LoadingOverlay(isLoading: Boolean, content: @Composable () -> Unit) {
        Box(modifier = Modifier.fillMaxSize()) {
            content() // UI normal

            if (isLoading) {
                // Fondo semitransparente que bloquea interacción
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x33000000)) // Fondo tenue
                        .clickable(enabled = false) {} // Bloquea clicks
                )

                // Mini ventana flotante limpia
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .wrapContentSize()
                        .background(
                            color = colorResource(id = R.color.VERD_FUER), // color principal de la app
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(
                            color = colorResource(id = R.color.VERD_FONDO), // color secundario de la app
                            strokeWidth = 4.dp,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Cargando...",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
    LoadingOverlay(isLoading = isLoading) {
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
        ) {

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
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .background(
                                color = colorResource(id = R.color.VERD_FUER),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(vertical = 55.dp, horizontal = 16.dp),
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
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedContainerColor = colorResource(id = R.color.VERD_FONDO),
                                    unfocusedContainerColor = colorResource(id = R.color.VERD_FONDO),
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
                                trailingIcon = {
                                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                        if (passwordVisible) {
                                            Icon(
                                                imageVector = Icons.Filled.Visibility,
                                                contentDescription = "Ocultar contraseña"
                                            )
                                        } else {
                                            Icon(
                                                imageVector = Icons.Filled.VisibilityOff,
                                                contentDescription = "Mostrar contraseña"
                                            )
                                        }
                                    }
                                },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedContainerColor = colorResource(id = R.color.VERD_FONDO),
                                    unfocusedContainerColor = colorResource(id = R.color.VERD_FONDO),
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
                                    scope.launch {
                                        isLoading = true
                                        loginError = ""
                                        try {
                                            val api = RetrofitClient.create(context)
                                            val response = api.login(LoginRequest(email, password))

                                            if (response.status == "ok" && response.token != null) {
                                                TokenStore.saveToken(context, response.token)
                                                Globals.ActiveUser = RellenarUsuario(response.token)

                                                navController.navigate(Routes.MainMenu ) {
                                                    popUpTo(Routes.Login) { inclusive = true }
                                                }
                                            } else {
                                                loginError = "Usuario o contraseña incorrectos"
                                            }

                                        } catch (e: Exception) {
                                            loginError = "Error de conexión"
                                        } finally {
                                            isLoading = false
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(55.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF79520))
                            ) {
                                Text("Iniciar Sesión", color = Color.White)
                            }

                            if (loginError.isNotEmpty()) {
                                Text(
                                    text = loginError,
                                    color = Color.Black,
                                    modifier = Modifier.clickable {
                                        // Lógica para recuperar contraseña
                                    },
                                    fontSize = 16.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

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
                    .width(300.dp)
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}