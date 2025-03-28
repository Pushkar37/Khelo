package com.example.khelo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khelo.ui.theme.SecondaryGreen

@Preview
@Composable
fun MatchDisplaySummeryCard(){
    Card(onClick = {}, modifier = Modifier.padding(horizontal = 10.dp).size(300.dp,185.dp) , elevation = CardDefaults.cardElevation(defaultElevation = 20.dp , pressedElevation = 0.dp)) {
        Column (modifier = Modifier.background(SecondaryGreen.copy(0.5f)).fillMaxSize()){
            Row (verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.SpaceBetween , modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()){
                Column {
                    Text("Match Name", fontSize = 15.sp , fontWeight = FontWeight.Light)
                    Text("Location", fontSize = 15.sp , fontWeight = FontWeight.Light)
                }
                Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "" , modifier = Modifier.clickable(onClick = {}))
            }

            Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)){
                Icon(Icons.Default.AccountCircle, contentDescription = "")
                Text("Team 1" , modifier = Modifier.padding(horizontal = 10.dp))
            }
            Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)){
                Icon(Icons.Default.AccountCircle, contentDescription = "")
                Text("Team 1" , modifier = Modifier.padding(horizontal = 10.dp))
            }
            HorizontalDivider()
            Row {
                Text("Highlight / Summary" , modifier = Modifier.padding(10.dp))
            }
        }
    }
}
