package com.example.khelo

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Notifications
//import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khelo.ui.theme.PrimaryGreen
import com.example.khelo.ui.theme.SecondaryGreen

@Composable
fun MatchDisplaySummeryCard(
    @DrawableRes team1Image : Int,
    @DrawableRes team2Image : Int,
    team1Name: String = "Team 1",
    team2Name: String = "Team 2",
    team1Score: String = "0/0",
    team2Score: String = "0/0",
    overs: String = "0.0",
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick, 
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .size(300.dp, 185.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .background(SecondaryGreen.copy(0.2f))
                .fillMaxSize()
        ) {

            var bellIconPressed by rememberSaveable { mutableStateOf(false) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        "$team1Name vs $team2Name",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        "Overs: $overs",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light
                    )
                }
                Icon(
//                    imageVector = Icons.Filled.SportsCricket,
                    imageVector = if(bellIconPressed) Icons.Filled.Notifications else Icons.Outlined.Notifications ,
                    contentDescription = "Notification Icon",
                    tint = PrimaryGreen,
                    modifier = Modifier.clickable(onClick = {bellIconPressed = !bellIconPressed})
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Image(
                    painter = painterResource(team1Image),
                    contentDescription = "",
                    modifier = Modifier.clip(shape = RectangleShape).size(50.dp)

                )
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(
                        team1Name,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        team1Score,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )
                }
            }
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Image(
                    painter = painterResource(team2Image),
                    contentDescription = "",
                    modifier = Modifier.clip(shape = RectangleShape).size(50.dp)

                    )
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(
                        team2Name,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        team2Score,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )
                }
            }
//
//            HorizontalDivider()
//
//            Row {
//                Text(
//                    "Live Match",
//                    modifier = Modifier.padding(10.dp),
//                    color = PrimaryGreen,
//                    fontWeight = FontWeight.Bold
//                )
//            }
        }
    }
}

@Preview
@Composable
fun MatchDisplaySummeryCardPreview() {
    MatchDisplaySummeryCard(
        team1Name = "Mumbai Indians",
        team2Name = "Chennai Super Kings",
        team1Score = "120/4",
        team2Score = "0/0",
        overs = "15.2",
        team1Image = R.drawable.default_profile_image,
        team2Image = R.drawable.default_profile_image
    )
}
