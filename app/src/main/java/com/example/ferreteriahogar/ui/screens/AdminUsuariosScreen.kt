package com.example.ferreteriahogar.ui.screens

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ferreteriahogar.ui.components.EditDialog
import com.example.ferreteriahogar.utils.RetrofitClient
import com.example.ferreteriahogar.data.RegisterRequest
import com.example.ferreteriahogar.data.UpdateUserRequest
import com.example.ferreteriahogar.data.UserAD
import com.example.ferreteriahogar.ui.components.NavBar
import com.example.ferreteriahogar.ui.components.UsersTable
import kotlinx.coroutines.launch
// --------- UI EXTRAS ---------
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.ferreteriahogar.R

// =========================
//     LOADING OVERLAY
// =========================
@Composable
fun LoadingOverlay(isLoading: Boolean, content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        content()

        if (isLoading) {
            // Fondo bloqueante
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x33000000))
                    .clickable(enabled = false) {}
            )

            // Mini ventana elegante
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .wrapContentSize()
                    .background(
                        color = colorResource(id = R.color.VERD_FUER),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.VERD_FONDO),
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

// =========================
//  ADMIN USUARIOS SCREEN
// =========================
@Composable
fun AdminUsuariosScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    context: Context
){
    val titleNavBar = ""
    val scope = rememberCoroutineScope()
    val api = RetrofitClient.create(context)

    var users by remember { mutableStateOf(listOf<UserAD>()) }
    var editingUser by remember { mutableStateOf<UserAD?>(null) }
    var isNewUser by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Estado de carga global
    var isLoading by remember { mutableStateOf(false) }

    LoadingOverlay(isLoading = isLoading) {


        // Cargar usuarios al inicio
        LaunchedEffect(Unit) {
            try {
                isLoading = true
                users = api.getAllUsers()
            } catch (_: Exception) {
                errorMessage = "Error al cargar usuarios"
            }
            isLoading = false
        }

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

            Column(modifier = Modifier.fillMaxSize()) {
                NavBar(navController, titleNavBar)

                UsersTable(
                    users = users,
                    onEdit = { user ->
                        editingUser = user
                        isNewUser = false
                    },
                    onDelete = { user ->
                        scope.launch {
                            try {
                                isLoading = true
                                api.deleteUser(user.username)
                                users = users.filter { it.username != user.username }
                            } catch (_: Exception) {
                                errorMessage = "Error al eliminar usuario"
                            }
                            isLoading = false
                        }
                    },
                    onAdd = {}
                )

                Spacer(modifier = Modifier.weight(1f))

                // Botón para agregar usuario
                Button(
                    onClick = {
                        editingUser = UserAD(null, "", "", "")
                        isNewUser = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.VERD_FUER)
                    )
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Agregar +")
                }
            }

            // -----------------------------
            //       DIALOG DE EDICIÓN
            // -----------------------------
            editingUser?.let { user ->
                EditDialog(
                    username = user.username,
                    role = user.role,
                    password = user.password,
                    isNewUser = isNewUser,
                    onDismiss = { editingUser = null },
                    onConfirm = { newUsername, newPassword, newRole ->

                        // 👇 CERRAR EL DIALOGO **INMEDIATAMENTE**
                        editingUser = null

                        scope.launch {
                            try {
                                isLoading = true

                                if (isNewUser) {
                                    api.createUser(
                                        RegisterRequest(
                                            username = newUsername,
                                            password = newPassword,
                                            role = newRole
                                        )
                                    )
                                } else {
                                    api.updateUser(
                                        user.username,
                                        UpdateUserRequest(
                                            username = newUsername,
                                            role = newRole
                                        )
                                    )
                                }

                                users = api.getAllUsers()

                            } catch (_: Exception) {
                                errorMessage = "Error al guardar cambios"
                            }
                            isLoading = false
                        }
                    }
                )
            }
        }
    }
}
