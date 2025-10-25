package com.example.ferreteriahogar.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ferreteriahogar.data.Detalle_Hoja
import com.example.ferreteriahogar.ui.components.NavBar2
import com.example.ferreteriahogar.ui.theme.VERD_FUER
import com.example.ferreteriahogar.utils.leerProductos
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun HojaInventario(
    paddingValues: PaddingValues,
    navController: NavController
) {
    val context = LocalContext.current
    val titleNavBar = "Inventario"

    val detalles = remember { mutableStateListOf<Detalle_Hoja>() }
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(-1) }
    var cantidadEdit by remember { mutableStateOf("") }
    var showScanner by remember { mutableStateOf(false) }
    var showOptionsDialog by remember { mutableStateOf(false) }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> showScanner = granted }
    )

    val productos = remember { leerProductos(context) }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            NavBar2(navController, titleNavBar) { println("Botón Enviar presionado") }

            Spacer(Modifier.height(40.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color(0xFFCFE0B3)),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(16.dp)
                ) {
                    if (detalles.isNotEmpty()) {
                        LazyColumn {
                            itemsIndexed(detalles) { index, item ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .pointerInput(Unit) {
                                            detectTapGestures(
                                                onLongPress = {
                                                    selectedIndex = index
                                                    cantidadEdit = item.cantidad.toString()
                                                    showOptionsDialog = true
                                                }
                                            )
                                        },
                                    colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text("Código: ${item.codigo}", fontWeight = FontWeight.Bold)
                                            Text("Descripción: ${item.descripcion}")
                                            Text("Cantidad: ${item.cantidad}")
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Text(
                            text = "Escanee un objeto para ver los detalles",
                            color = androidx.compose.ui.graphics.Color.DarkGray,
                            fontSize = 16.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Cámara ML Kit
        if (showScanner) {
            MLKitCameraPreview(
                onBarcodeDetected = { codigo ->
                    val encontrado = productos.find { it.codigo == codigo }
                    detalles.add(
                        Detalle_Hoja(
                            codigo = encontrado?.codigo ?: codigo,
                            descripcion = encontrado?.descripcion ?: "Descripción",
                            cantidad = 1
                        )
                    )
                    showScanner = false
                }
            )
        }

        // Diálogo de opciones (Actualizar / Eliminar)
        if (showOptionsDialog && selectedIndex != -1) {
            AlertDialog(
                onDismissRequest = { showOptionsDialog = false },
                title = { Text("Opciones") },
                text = { Text("¿Qué deseas hacer con este producto?") },
                confirmButton = {
                    Button(onClick = {
                        showEditDialog = true
                        showOptionsDialog = false
                    }) {
                        Text("Actualizar cantidad")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        detalles.removeAt(selectedIndex)
                        showOptionsDialog = false
                    }) {
                        Text("Eliminar")
                    }
                }
            )
        }

        // Diálogo editar cantidad
        if (showEditDialog && selectedIndex != -1) {
            // Actualizamos cantidadEdit con la cantidad actual del item
            LaunchedEffect(selectedIndex, showEditDialog) {
                cantidadEdit = detalles[selectedIndex].cantidad.toString()
            }

            EditCantidadDialog(
                cantidadEdit = cantidadEdit,
                onCantidadChange = { cantidadEdit = it },
                onGuardar = {
                    cantidadEdit.toIntOrNull()?.let { newCantidad ->
                        val oldItem = detalles[selectedIndex]
                        detalles[selectedIndex] = oldItem.copy(cantidad = newCantidad)
                    }
                    showEditDialog = false
                },
                onCancelar = { showEditDialog = false }
            )
        }

        // Botón Cámara / Cerrar
        Button(
            onClick = {
                if (showScanner) {
                    showScanner = false
                } else {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_GRANTED
                    ) {
                        showScanner = true
                    } else {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 0.dp)
                .height(100.dp),
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VERD_FUER,
                contentColor = androidx.compose.ui.graphics.Color.White
            )
        ) {
            Text(
                text = if (showScanner) "Cerrar Cámara" else "Escanear Objeto",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun MLKitCameraPreview(onBarcodeDetected: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalContext.current as LifecycleOwner
    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }
    val scanner = BarcodeScanning.getClient()

    AndroidView(factory = { ctx ->
        val previewView = PreviewView(ctx)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = androidx.camera.core.Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val imageAnalyzer = ImageAnalysis.Builder().build().also { analysis ->
                var detected = false
                analysis.setAnalyzer(cameraExecutor) { imageProxy: ImageProxy ->
                    if (detected) {
                        imageProxy.close()
                        return@setAnalyzer
                    }
                    val mediaImage = imageProxy.image
                    if (mediaImage != null) {
                        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                        scanner.process(image)
                            .addOnSuccessListener { barcodes ->
                                for (barcode in barcodes) {
                                    val code = barcode.rawValue
                                    if (!code.isNullOrEmpty()) {
                                        detected = true
                                        onBarcodeDetected(code)
                                    }
                                }
                            }
                            .addOnCompleteListener { imageProxy.close() }
                    } else imageProxy.close()
                }
            }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageAnalyzer
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(ctx))

        previewView
    }, modifier = Modifier.fillMaxSize())
}

@Composable
fun EditCantidadDialog(
    cantidadEdit: String,
    onCantidadChange: (String) -> Unit,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit
) {
    Dialog(onDismissRequest = onCancelar) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Editar Cantidad", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = cantidadEdit,
                    onValueChange = onCantidadChange,
                    label = { Text("Cantidad") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(onClick = onGuardar) { Text("Guardar") }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = onCancelar,
                        colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color.Gray)
                    ) { Text("Cancelar") }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHoja() {
    HojaInventario(PaddingValues(), rememberNavController())
}
