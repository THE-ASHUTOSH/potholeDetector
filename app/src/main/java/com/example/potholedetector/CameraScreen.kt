package com.example.potholedetector

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.potholedetector.sampledata.LoginRequest
import com.google.android.gms.location.LocationServices

@Composable
fun CameraScreen(navController: NavController, viewModel1: UserHistoryModel = androidx.lifecycle
    .viewmodel.compose.viewModel(), viewModel2: LoginViewModel = androidx.lifecycle.viewmodel
        .compose.viewModel(), viewModel3 : AIViewModel = viewModel()) {
    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmapImage by remember { mutableStateOf<Bitmap?>(null) }
    val locationState = remember { mutableStateOf<String?>(null) }
    var locBol by remember { mutableStateOf(false) }

    val isLoading = viewModel3.isLoading // Directly observe from ViewModel
    val errorMessage = viewModel3.errorMessage
    val uploadState = viewModel3.uploadState

    // Launcher for gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    // Launcher for camera
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmapImage = bitmap
    }

    // Permission launcher for camera
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "Camera permission is required", Toast.LENGTH_SHORT).show()
        }
    }



    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                tonalElevation = 4.dp,
            ) {
                Spacer(Modifier.weight(0.5f))

                IconButton(
                    onClick = {
                        viewModel1.getUserHistory()
                        navController.navigate("history")
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Group",
                        tint = Color(66, 135, 245),
                        modifier = Modifier.size(50.dp)
                    )
                }


                Spacer(Modifier.weight(1f))
                FloatingActionButton(
                    onClick = {
                        val hasPermission = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        ) == PermissionChecker.PERMISSION_GRANTED

                        if (hasPermission) {
                            cameraLauncher.launch()
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                        locBol=true
                    },
                    containerColor = Color.White,
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cam),
                        contentDescription = "Logo",
                        contentScale = ContentScale.Fit,
                    )
                }
                Spacer(Modifier.weight(1f))


                IconButton(onClick = {navController.navigate("profile") }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Assignment",
                        tint = Color(66, 135, 245),
                        modifier = Modifier.size(70.dp)
                    )
                }
                Spacer(Modifier.weight(0.5f))


            }
        },
        modifier = Modifier.background(Color.White)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.car),
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = "RoadFix",
                style = TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
            )
            Spacer(modifier = Modifier.size(30.dp))
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            } else if (bitmapImage != null) {
                Image(
                    bitmap = bitmapImage!!.asImageBitmap(),
                    contentDescription = "Captured Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            Spacer(modifier = Modifier.size(30.dp))
            if(locBol && bitmapImage!=null) {
                locationState.value?.let { placeName ->
                    Text(
                        "LOCATION:  $placeName",
                        modifier = Modifier.padding(30.dp)
                    )
                }
                if(isLoading){
                    Text(text = "Analysing...", color = Color.Gray)
                }else {
                    Button(
                        onClick = {
                            viewModel3.uploadImage(context, bitmapImage!!)
                        },
                        colors = ButtonDefaults.buttonColors(Color(38, 191, 41))
                    ) {
                        Text(
                            text = "Analyse",
                            color = Color.White,
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                }
                if (uploadState != null) {
                    if(uploadState.predictions.isNotEmpty() && uploadState.inference_id!=null ){
                        Text(text = "It is a Pothole",
                            color = Color(38, 191, 41),
                            modifier = Modifier.padding(20.dp)
                            )
                    }else{
                        Text(text = "It is not a Pothole",
                            color = Color.Red,
                            modifier = Modifier.padding(20.dp)
                            )
                    }
                }
            }
        }
    }





    LocationScreen(locationState)
}
@Composable
fun GetLocation(locationState:MutableState<String?>) {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val geocoder = Geocoder(context)
                        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        if (addresses != null && addresses.isNotEmpty()) {
                            locationState.value = addresses[0].getAddressLine(0)
                        } else {
                            locationState.value = "Unable to fetch address"
                        }
                    } else {
                        locationState.value = "Location not available"
                    }
                }
                .addOnFailureListener {
                    locationState.value = "Failed to get location"
                }
        } else {
            locationState.value = "Location permission not granted"
        }
    }


}
@Composable
fun LocationScreen(locationState: MutableState<String?>) {
    var permissionGranted by remember { mutableStateOf(false) }

    if (permissionGranted) {
        GetLocation(locationState)
    } else {
        RequestLocationPermission {
            permissionGranted = true
        }
    }
}
@Composable
fun RequestLocationPermission(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                onPermissionGranted()
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }
}
