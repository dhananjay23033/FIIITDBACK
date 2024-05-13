package com.example.hostelite.presentation.student_screens

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.hostelite.R
import com.example.hostelite.shared.widgets.AppBar
import com.google.android.gms.location.FusedLocationProviderClient

var latitude: String = "28.6359552"
var longitude: String = "77.2046848"

@Composable
fun MarkEntry(navController: NavController){

    val fusedLocationProviderClient = FusedLocationProviderClient(navController.context)

    val token = remember {mutableStateOf("")}
    Scaffold(
        topBar = { AppBar(navController = navController, text = "Mark Entry")}
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(20.dp)
                    .fillMaxSize()
            )
            Image(
                painter = painterResource(id = R.drawable.fiiitdback),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                OutlinedTextField(
                    value = token.value,
                    onValueChange = {token.value = it},
                    singleLine = true,
                    placeholder = {Text(text = "Enter token")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(corner = CornerSize(20.dp)))
                        .background(color = Color(0xFFF7F7F7)),
                    shape = RoundedCornerShape(corner = CornerSize(20.dp))
                )
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(100.dp)
                            .width(150.dp)
                            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
                            .background(color = Color.White)
                            .padding(all = 10.dp)
                    ) {
                        Text(
                            text = "Your Location:",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500,
                                color = Color(0xFF115A11)
                            )
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Latitude: ${latitude} ",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W400,
                                color = Color.Black
                            )
                        )
                        Text(
                            text = "Longitude: ${longitude}",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W400,
                                color = Color.Black
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(40.dp))
                    IconButton(
                        onClick = { getLocation(navController.context,fusedLocationProviderClient) },
                        modifier = Modifier
                            .size(width = 30.dp, height = 30.dp)
                            .clip(shape = CircleShape)
                            .background(color = Color(0xFF115A11))
                    ) {
                        Icon(imageVector = Icons.Filled.GpsFixed, contentDescription = "Get GPS location", tint = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(150.dp))
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .width(160.dp)
                        .clip(shape = RoundedCornerShape(corner = CornerSize(size = 15.dp)))
                        .background(color = Color(0xFF08C8B6))
                        .clickable { /* Todo */ },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Mark Entry",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF33004A)
                        )
                    )
                }
            }
        }
    }
}

fun getLocation(context: Context, fusedLocationProviderClient: FusedLocationProviderClient) {
    if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
    {
        ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), 100)
        return
    }

    val location = fusedLocationProviderClient.lastLocation
    location.addOnSuccessListener {
        if(it!=null) {
            latitude = it.latitude.toString()
            longitude = it.longitude.toString()
        }
    }
}
