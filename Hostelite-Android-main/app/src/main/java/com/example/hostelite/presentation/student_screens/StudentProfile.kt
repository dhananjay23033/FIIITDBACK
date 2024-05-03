package com.example.hostelite.presentation.student_screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hostelite.R
import com.example.hostelite.presentation.Authentication.AuthenticationViewModel
import com.example.hostelite.shared.widgets.BottomDrawer
import com.example.hostelite.shared.widgets.UserDetailField
import com.example.hostelite.util.Response

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StudentProfile(navController: NavController, viewModel: AuthenticationViewModel){
    val fieldValues: List<Pair<String, String>> = listOf(
        Pair(first = "E-mail", second = "josp.ug20.ece@nitp.ac.in"),
        Pair(first = "Roll No.", second = "2106037"),
        Pair(first = "Mobile", second = "9999999999"),
        Pair(first = "Room No.", second = "2-03"),
    )
    Scaffold(
        bottomBar = { BottomDrawer(navController = navController, isStudent = true)},
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(all = 10.dp)
                    .align(Alignment.TopStart)) {
                Icon(imageVector = Icons.Filled.ArrowBackIos, contentDescription = null)
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 50.dp, vertical = 20.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Surface(
                    shape = CircleShape,
                    elevation = 5.dp,
                    color = Color.Gray
                ){
                    Image(
                        painter = painterResource(id = R.drawable.profilepic),
                        contentDescription = "profile picture",
                        modifier = Modifier.clip(CircleShape),
                        contentScale = ContentScale.Fit
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "Jos Pritam",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W500
                    )
                )
                Text(
                    text = "B.Tech",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        color = Color(0xFF4D4D4D)
                    )
                )
                Text(
                    text = "CSE 2021",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF9B9B9B)
                    )
                )
                Spacer(modifier = Modifier.height(15.dp))
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color(0xFF9B9B9B)
                )
                Spacer(modifier = Modifier.height(10.dp))
                fieldValues.forEach {
                    field ->
                        UserDetailField(label = field.first, value = field.second)
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .width(200.dp)
                        .clip(shape = RoundedCornerShape(corner = CornerSize(size = 15.dp)))
                        .clickable {
                            viewModel.signOut()
                            val response = viewModel.signOutState.value
                            if (response is Response.Success){
                                navController.navigate(route = "login"){
                                    popUpTo(route = "studentprofile"){
                                        inclusive = true
                                    }
                                }
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(50.dp)
                            .background(color = Color(0xFF9C32A6)),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(imageVector = Icons.Filled.Logout, contentDescription = "Log Out", tint = Color.White)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(150.dp)
                            .background(color = Color(0xFFCA48D6)),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Log Out",
                            style = TextStyle(
                                fontSize = 17.sp,
                                color = Color.White,
                                fontWeight = FontWeight.W500
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}