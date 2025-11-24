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
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
import com.example.ferreteriahogar.R
import com.example.ferreteriahogar.data.Detalle_Hoja
import com.example.ferreteriahogar.ui.components.NavBar2
import com.example.ferreteriahogar.ui.theme.VERD_FUER
import com.example.ferreteriahogar.utils.leerProductos
import com.example.ferreteriahogar.viewModels.InventoryViewModel
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.example.ferreteriahogar.data.ProductAD
import androidx.compose.ui.platform.LocalContext
import com.example.ferreteriahogar.utils.RetrofitClient
@SuppressLint("UnsafeOptInUsageError")
@Composable
fun HojaInventario(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: InventoryViewModel
) {
    val context = LocalContext.current
    val titleNavBar = "Inventario"
    val scope = rememberCoroutineScope()
    val detalles = viewModel.detallesTemp

    var showEditDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(-1) }
    var cantidadEdit by remember { mutableStateOf("") }
    var showScanner by remember { mutableStateOf(false) }
    var showOptionsDialog by remember { mutableStateOf(false) }
    var productos by remember { mutableStateOf(listOf<ProductAD>()) }
    var isLoading by remember { mutableStateOf(false) } // Estado global de carga
    var mensajeError by remember { mutableStateOf<String?>(null) }

    val api = RetrofitClient.create(context)
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> showScanner = granted }
    )

    // Cargar productos desde la API
    LaunchedEffect(Unit) {
        isLoading = true
        try {
            productos = api.getProducts()
        } catch (e: Exception) {
            e.printStackTrace()
            productos = emptyList()
        }
        isLoading = false
    }

    LoadingOverlay2(isLoading = isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, colorResource(id = R.color.VERD_FONDO))
                    )
                )
        ) {

            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

                NavBar2(
                    navController = navController,
                    titleNavBar = titleNavBar,
                    onSendClick = {
                        scope.launch {
                            isLoading = true
                            viewModel.applyChanges(context)
                            navController.popBackStack()
                            isLoading = false
                        }
                    },
                    viewModel = viewModel
                )

                Spacer(Modifier.height(40.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFCFE0B3)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(550.dp)
                            .padding(16.dp)
                    ) {
                        if (detalles.isNotEmpty()) {
                            LazyColumn(modifier = Modifier.animateContentSize()) {
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
                                        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                                color = Color.DarkGray,
                                fontSize = 16.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            var puedeLeer by remember { mutableStateOf(true) }

            // Cámara ML Kit
            if (showScanner) {
                MLKitCameraPreview(
                    onBarcodeDetected = { codigo ->
                        if (!puedeLeer) return@MLKitCameraPreview
                        puedeLeer = false

                        val code = codigo.trim()
                        val encontrado = productos.find { it.code == code }
                        if (encontrado != null) {
                            viewModel.addProduct(
                                Detalle_Hoja(
                                    codigo = encontrado.code,
                                    descripcion = encontrado.description,
                                    cantidad = 1
                                )
                            )
                            showScanner = false
                        } else {
                            mensajeError = "Producto no encontrado en la BD"
                        }

                        scope.launch {
                            kotlinx.coroutines.delay(1000)
                            puedeLeer = true
                            mensajeError = null
                        }
                    }
                )

                mensajeError?.let {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 80.dp)
                    ) {
                        Text(
                            text = it,
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .background(Color.Red.copy(alpha = 0.8f), RoundedCornerShape(10.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
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
                            viewModel.removeTemp(selectedIndex)
                            showOptionsDialog = false
                        }) {
                            Text("Eliminar")
                        }
                    }
                )
            }

            // Diálogo editar cantidad
            if (showEditDialog && selectedIndex != -1) {
                LaunchedEffect(selectedIndex, showEditDialog) {
                    cantidadEdit = detalles[selectedIndex].cantidad.toString()
                }

                EditCantidadDialog(
                    cantidadEdit = cantidadEdit,
                    onCantidadChange = { cantidadEdit = it },
                    onGuardar = {
                        cantidadEdit.toIntOrNull()?.let { newCantidad ->
                            viewModel.updateCantidadTemp(selectedIndex, newCantidad)
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
                            PackageManager.PERMISSION_GRANTED) {
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
                    .height(60.dp),
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VERD_FUER)
            ) {
                Text(
                    text = if (showScanner) "Cerrar Cámara" else "Escanear Objeto",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun MLKitCameraPreview(onBarcodeDetected: (String) -> Unit) {
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
                analysis.setAnalyzer(cameraExecutor) { imageProxy: ImageProxy ->
                    val mediaImage = imageProxy.image
                    if (mediaImage != null) {
                        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                        scanner.process(image)
                            .addOnSuccessListener { barcodes ->
                                for (barcode in barcodes) {
                                    val code = barcode.rawValue
                                    if (!code.isNullOrEmpty()) onBarcodeDetected(code)
                                }
                            }
                            .addOnCompleteListener { imageProxy.close() }
                    } else {
                        imageProxy.close()
                    }
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
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) { Text("Cancelar") }
                }
            }
        }
    }
}

@Composable
fun LoadingOverlay2(isLoading: Boolean, content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        content()
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x33000000))
                    .clickable(enabled = false) {}
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .wrapContentSize()
                    .background(color = colorResource(id = R.color.VERD_FUER), shape = RoundedCornerShape(16.dp))
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