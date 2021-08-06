package com.semicolon.highball.ui.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.semicolon.highball.local.FavoriteWhiskyRoomData

@Composable
fun FavoriteWhiskyItem(
    favoriteWhisky: FavoriteWhiskyRoomData,
    onItemClick: (id: Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable { onItemClick(favoriteWhisky.id) }
    ) {
        Image(
            painter = rememberImagePainter(favoriteWhisky.imageUrl),
            contentDescription = "whisky image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(65.dp)
                .width(65.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color(0x33000000))
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
        ) {
            Text(text = favoriteWhisky.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "Price : ${favoriteWhisky.price}￡")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteWhiskyItemPreview() {
    FavoriteWhiskyItem(
        favoriteWhisky = FavoriteWhiskyRoomData(
            330,
            "Johnnie Walker Blue Label",
            157,
            "https://res.cloudinary.com/dt4sawqjx/image/upload/v1463683021/eymnv9ef7lqya5nwjqa3.jpg"
        )
    ) {

    }
}