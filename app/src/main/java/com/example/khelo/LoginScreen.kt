//package com.example.khelo
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.sp
//import com.example.khelo.ui.theme.LoginBackGround
//
//@Preview
//@Composable
//fun LoginScreen(){
//    Box (modifier = Modifier.fillMaxSize().background(color = LoginBackGround)){
//        Image(painter = painterResource(R.drawable.girl), contentDescription = "Girl Image")
//        Column (verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){
//            Image(painter = painterResource(R.drawable.bottom_white), contentDescription = "Bottom White" , Modifier.fillMaxWidth())
//        }
//        Column (verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxSize()){
//            Row {
//                Text("Welcome to Khelo" , fontSize = 35.sp , fontWeight = FontWeight.SemiBold)
//            }
//            Row {
//
//            }
//        }
//
//    }
//}