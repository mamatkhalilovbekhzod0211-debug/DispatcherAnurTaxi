package uz.bekhzod0211.dispatcheranurtaxi.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.permissions.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.bekhzod0211.dispatcheranurtaxi.data.domain.repository.MainRepository
import uz.bekhzod0211.dispatcheranurtaxi.data.model.request.main.Broadcast
import uz.bekhzod0211.dispatcheranurtaxi.data.model.request.main.DropoffLocation
import uz.bekhzod0211.dispatcheranurtaxi.data.model.request.main.Order
import uz.bekhzod0211.dispatcheranurtaxi.data.model.request.main.OrderCreateRequest
import uz.bekhzod0211.dispatcheranurtaxi.data.model.request.main.PickupLocation
import uz.bekhzod0211.dispatcheranurtaxi.login.ui.LoginActivity
import uz.bekhzod0211.dispatcheranurtaxi.ui.theme.DispatcherAnurTaxiTheme
import uz.bekhzod0211.dispatcheranurtaxi.utils.TokenProvider
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repo: MainRepository
    @Inject
    lateinit var tokenProvider: TokenProvider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DispatcherAnurTaxiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OrderCreateScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                tokenProvider.logOutEvents.collect {
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }


    @OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun OrderCreateScreen(modifier: Modifier = Modifier) {


        val context = LocalContext.current

        // --- Поля ввода
        var customerName by remember { mutableStateOf("Anur") }
        var customerPhone by remember { mutableStateOf("+998") }

        // --- Dropdown для vehicle_type
        val vehicleTypes = listOf("economy", "comfort", "business", "vip")
        var expanded by remember { mutableStateOf(false) }
        var selectedVehicle by remember { mutableStateOf(vehicleTypes.first()) }

        var pickupAddress by remember { mutableStateOf("Andijon, Oyim") }
        var dropoffAddress by remember { mutableStateOf("Andijon,Oyim") }

        // --- Координаты
        var pickupLat by remember { mutableStateOf(40.82043453616464) }
        var pickupLng by remember { mutableStateOf(72.74261596229088) }
        var dropoffLat by remember { mutableStateOf(40.82043453616464) }
        var dropoffLng by remember { mutableStateOf(72.74261596229088) }
        var isLoading by remember { mutableStateOf(false) }

        val scope = rememberCoroutineScope()

        val pickupCamera = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(pickupLat, pickupLng), 14f)
        }
        val dropoffCamera = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(dropoffLat, dropoffLng), 14f)
        }

        // --- Разрешения на геолокацию
        val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
        LaunchedEffect(Unit) {
            if (!locationPermission.status.isGranted) {
                locationPermission.launchPermissionRequest()
            }
        }

        LaunchedEffect(pickupAddress) {
            if (pickupAddress.isNotBlank()) {
                try {
                    val geocoder = Geocoder(context)
                    val result = geocoder.getFromLocationName(pickupAddress, 1)
                    if (!result.isNullOrEmpty()) {
                        val location = result[0]
                        val latLng = LatLng(location.latitude, location.longitude)
                        pickupCamera.animate(
                            update = CameraUpdateFactory.newLatLngZoom(latLng, 15f),
                            durationMs = 1000
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        LaunchedEffect(dropoffAddress) {
            if (dropoffAddress.isNotBlank()) {
                try {
                    val geocoder = Geocoder(context)
                    val result = geocoder.getFromLocationName(dropoffAddress, 1)
                    if (!result.isNullOrEmpty()) {
                        val location = result[0]
                        val latLng = LatLng(location.latitude, location.longitude)
                        dropoffCamera.animate(
                            update = CameraUpdateFactory.newLatLngZoom(latLng, 15f),
                            durationMs = 1000
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }


        Scaffold(
            topBar = { TopAppBar(title = { Text("Buyurtma yaratish") }) },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = @Composable { Text("Buyurtmani yaratish") },
                    icon = @Composable {},
                    modifier = Modifier,
                    onClick = {
                        val request = OrderCreateRequest(
                            broadcast = Broadcast(radius_km = 3),
                            order = Order(
                                customer_name = customerName,
                                customer_phone = customerPhone,
                                dropoff_location = DropoffLocation(
                                    address = dropoffAddress,
                                    city = "Andijon",
                                    lat = dropoffLat,
                                    lng = dropoffLng
                                ),
                                pickup_location = PickupLocation(
                                    address = pickupAddress,
                                    city = "Andijon",
                                    lat = pickupLat,
                                    lng = pickupLng
                                ),
                                vehicle_type = selectedVehicle
                            )
                        )
                        scope.launch {
                            isLoading = true
                            if (repo.createOrder(request)){
                                isLoading = false
                            }

                        }
                    },
                    expanded = true
                )
            }
        ) { paddingValues ->
            if (isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    /*item {

                        OutlinedTextField(
                            value = customerName,
                            onValueChange = { customerName = it },
                            label = { Text("Mijoz ismi") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }*/

                    item {
                        OutlinedTextField(
                            value = customerPhone,
                            onValueChange = { customerPhone = it },
                            label = { Text("Mijoz nomeri") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // --- Vehicle type dropdown
                   /* item {
                        Box {
                            OutlinedTextField(
                                value = selectedVehicle,
                                onValueChange = {},
                                label = { Text("Mashina tipi") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable() { expanded = true },
                                readOnly = true
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                vehicleTypes.forEach { type ->
                                    DropdownMenuItem(
                                        text = { Text(type) },
                                        onClick = {
                                            selectedVehicle = type
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }*/

                    item {
                        Divider(thickness = 1.dp)
                    }

                    // --- Pickup
                    item {
                        Text("Jo'nash joyi", style = MaterialTheme.typography.titleMedium)

                    }
                    item {
                        OutlinedTextField(
                            value = pickupAddress,
                            onValueChange = { pickupAddress = it },
                            label = { Text("Jo'nash manzili") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        PickupMap(
                            lat = pickupLat,
                            lng = pickupLng,
                            onMapClick = { lat, lng ->
                                pickupLat = lat
                                pickupLng = lng
                                scope.launch {
                                    getAddressFromLocation(
                                        context,
                                        lat,
                                        lng
                                    ) { address ->
                                        pickupAddress = address
                                    }
                                }
                            },
                            cameraPosition = pickupCamera
                        )
                    }

                    item {
                        Divider(thickness = 1.dp)
                    }



                }
            }
        }
    }

    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun PickupMap(
        lat: Double,
        lng: Double,
        onMapClick: (Double, Double) -> Unit,
        cameraPosition: CameraPositionState
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            val position = LatLng(lat, lng)

            GoogleMap(
                cameraPositionState = cameraPosition,
                onMapClick = { latLng ->
                    onMapClick(latLng.latitude, latLng.longitude)
                },
              /*  uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    compassEnabled = false,      // убрать компас
                    mapToolbarEnabled = false    // убрать кнопки "Маршрут"
                ),
                properties = MapProperties(
                    isMyLocationEnabled = false // на всякий случай
                )*/
            ) {
                if (lat != 0.0 && lng != 0.0) {
                    Marker(
                        state = MarkerState(position = LatLng(lat, lng)),
                        title = "Siz bu yerdasiz"
                    )
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getAddressFromLocation(
        context: Context,
        lat: Double,
        lng: Double,
        onResult: (String) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(lat, lng, 1)
                val address = addresses?.firstOrNull()?.getAddressLine(0)
                withContext(Dispatchers.Main) {
                    onResult(address ?: "Aniq bo'lmagan address")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onResult("Xato: ${e.localizedMessage}")
                }
            }
        }

    }
}

@Preview
@Composable
private fun ScreenPreview(){
    MainActivity().OrderCreateScreen()
}