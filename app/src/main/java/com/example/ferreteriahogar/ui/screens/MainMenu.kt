package com.example.ferreteriahogar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ferreteriahogar.R
import com.example.ferreteriahogar.ui.Routes
import com.example.ferreteriahogar.ui.components.BackIconButton
import com.example.ferreteriahogar.ui.components.NavBar
import com.example.ferreteriahogar.ui.theme.ICON_BG
import com.example.ferreteriahogar.ui.theme.VERD_FUER
import org.w3c.dom.Text

@Composable
fun MainMenu(paddingValues: PaddingValues, usuario : String, passwordHashed : String , navController : NavController){
    val titleNavBar = ""

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
    )

    NavBar(navController, titleNavBar)

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
    ){

        Spacer(Modifier.height(90.dp))

        Text(text = "Bienvenido, $usuario!",
            style = TextStyle(
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                shadow = Shadow(
                    color = Color.Gray,
                    offset = Offset(1f, 1f),
                    blurRadius = 2f
                )
            )
        )

        Spacer(Modifier.height(30.dp))

        Button(
            onClick = {
                navController.navigate(Routes.Inventory)
            },
            Modifier.fillMaxWidth().padding(horizontal = 35.dp).height(52.dp),
            shape = RoundedCornerShape(25.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            ),
            colors = ButtonDefaults.buttonColors(
                VERD_FUER,
                Color(0xFF3C3A3A)
            )
        ) {
            Text(
                text = "Centro de Inventario",
                style = TextStyle(fontSize = 21.sp),
                fontWeight = FontWeight.Bold
            )
        }
    }

    println("Contraseña cifrada: $passwordHashed")
}
@Preview(showBackground = true)
@Composable
fun MAIN() {
    MainMenu(
        paddingValues = PaddingValues(),
        usuario = "Villalobos",
        passwordHashed = "abc123123abc",
        navController = rememberNavController()
    )
}