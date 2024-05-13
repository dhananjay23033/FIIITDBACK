package com.example.hostelite.presentation.student_screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AddAlert
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hostelite.R
import com.example.hostelite.shared.widgets.BottomDrawer
import com.example.hostelite.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StudentHome(navController: NavController){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val firestore = FirebaseFirestore.getInstance()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

// Retrieve student userId
    val userId = auth.currentUser?.uid
    val userDocRef = userId?.let { firestore.collection(Constants.COLLECTION_NAME_STUDENTS).document(it) }

    Log.d("user", userId.toString())
    Log.d("userDocRef", userDocRef.toString())

// State to hold student details
    val studentName = remember { mutableStateOf("") }
    val roomNumber = remember { mutableStateOf("") }

    if (userDocRef != null) {
        userDocRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val data = document.data
                studentName.value = data?.get("userName").toString()
                roomNumber.value = data?.get("roomNo").toString()

                // Log inside the onSuccessListener
                Log.d("studentName", studentName.value)
                Log.d("roomNo", roomNumber.value)
            } else {
                Log.d("Firestore", "No such document")
            }
        }.addOnFailureListener { e ->
            Log.w("Firestore", "Error getting documents: ", e)
        }
    } else {
        Log.d("Firestore", "userDocRef is null")
    }

    ModalDrawer(
        drawerContent = {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                IconButton(onClick = {
                    coroutineScope.launch { drawerState.close() }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 20.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                    )
                }
                Text(
                    text = "App Version 1.0",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 15.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF544D4D)
                    )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(
                        painter = painterResource(id = R.drawable.dhananjay),
                        contentDescription = "dp",
                        modifier = Modifier
                            .size(140.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = studentName.value,
                        style = TextStyle(
                            color = Color(0xFF636363),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.W700
                        )
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = roomNumber.value,
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color(0xFF535353),
                            fontWeight = FontWeight.W400
                        )
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Divider(
                        modifier = Modifier.fillMaxWidth(0.70f),
                        thickness = 1.dp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(45.dp))
                    Column(){
                        drawerContent(text = "Edit Profile", icon = Icons.Filled.Edit)
                        Spacer(modifier = Modifier.height(25.dp))
                        drawerContent(text = "Notifications", icon = Icons.Filled.Notifications)
                        Spacer(modifier = Modifier.height(25.dp))
                        drawerContent(text = "Report Bug", icon = Icons.Filled.Dangerous)
                        Spacer(modifier = Modifier.height(25.dp))
                        drawerContent(text = "App Updates", icon = Icons.Filled.Download)
                        Spacer(modifier = Modifier.height(25.dp))
                        drawerContent(text = "Contact Us", icon = Icons.Filled.Contacts)
                        Spacer(modifier = Modifier.height(25.dp))
                        drawerContent(text = "Log Out", icon = Icons.Filled.Logout)
                        Spacer(modifier = Modifier.height(25.dp))
                    }
                }
            }
        },
        drawerState = drawerState,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        gesturesEnabled = true,
        drawerBackgroundColor = Color(0xFFFFBCF4),
        drawerContentColor = Color(0xFF641F65),
        content = { Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomDrawer(navController, isStudent = true) }
        ) {
            Box(modifier = Modifier.fillMaxSize()){
                Column(
                    modifier = Modifier.align(Alignment.TopCenter)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                        }
                        Image(
                            painter = painterResource(id = R.drawable.fiiitdback ),
                            contentDescription = null
                        )
                        IconButton(onClick = {
                            navController.navigate(route = "studentalerts")
                        }) {
                            Icon(imageVector = Icons.Outlined.AddAlert, contentDescription = "Alert")
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .scrollable(
                                state = rememberScrollState(),
                                orientation = Orientation.Vertical
                            )
                    ) {
                        Text(
                            text = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM")),
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color(0xFFA7A7A7),
                                fontWeight = FontWeight.W600
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Hello, ${studentName.value}",
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.W700
                            )
                        )
                        Text(
                            text = "Let’s manage your Hostel Things",
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color(0xFF858585),
                                fontWeight = FontWeight.W700
                            )
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(
                            text = "Quick Access",
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.W700
                            )
                        )
                        Spacer(modifier = Modifier.height(30.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.65f)
                        ){
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(shape = RoundedCornerShape(corner = CornerSize(30.dp)))
                                    .border(width = 2.dp, color = Color(0xAA000000))
                                    .fillMaxHeight()
                                    .padding(horizontal = 10.dp)
                                    .clickable { navController.navigate(route = "studentreportissue") },
                                verticalArrangement = Arrangement.Center
                            ){
                                Image(
                                    painter = painterResource(id = R.drawable.complaints),
                                    contentDescription = "Post A Complaint",
                                    modifier = Modifier.size(size = 150.dp)
                                )
                                Text(
                                    text = "Post a",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W400
                                    ),
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                                Text(
                                    text = "Complaint",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.W700
                                    ),
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(width = 7.dp))
                            Column(
                                modifier = Modifier.weight(1f)
                            ){
                                Row(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(corner = CornerSize(20.dp)))
                                        .background(Color(0xFF08C8B6))
                                        .weight(1f)
                                        .clickable { navController.navigate(route = "markentry") },
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Image(
                                        painter = painterResource(id = R.drawable.entry),
                                        contentDescription = "Mark Entry",
                                        modifier = Modifier.size(100.dp)
                                    )
                                    Text(
                                        text = "Mark Entry",
                                        Modifier.padding(horizontal = 5.dp),
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W700,
                                            color = Color(0xFF7E1D7B)
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(height= 7.dp))
                                Row(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(corner = CornerSize(20.dp)))
                                        .background(Color(0xFFFFEB3B))
                                        .weight(1f)
                                        .clickable { navController.navigate(route = "markexit")},
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Image(
                                        painter = painterResource(id = R.drawable.exit),
                                        contentDescription = "Mark Exit",
                                        modifier = Modifier.size(100.dp)
                                    )
                                    Text(
                                        text = "Mark Exit",
                                        Modifier.padding(horizontal = 5.dp),
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W700,
                                            color = Color.Black
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
            }
        }
    )
}

@Composable
fun drawerContent(text: String, icon: ImageVector){
    Row(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = icon,
            contentDescription = "Edit Profile",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xFF641F65)
            )
        )
    }

}

@Preview
@Composable
fun StudentHomePreview(){
//    StudentHome(navController = rememberNavController())
}