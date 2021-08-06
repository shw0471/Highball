package com.semicolon.highball.ui.all

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
import com.semicolon.highball.remote.WhiskyData

@Composable
fun WhiskyItem(
    whisky: WhiskyData,
    onItemClick: (id: Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable { onItemClick(whisky.id) }
    ) {
        Image(
            painter = rememberImagePainter(whisky.imageUrl),
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
            Text(text = whisky.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "Price : ${whisky.price}￡")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WhiskyItemPreview() {
    val whiskyData = WhiskyData(
        330,
        "Johnnie Walker Blue Label",
        "https://res.cloudinary.com/dt4sawqjx/image/upload/v1463683021/eymnv9ef7lqya5nwjqa3.jpg",
        "Other",
        157,
        78,
        "This is an excellent whiskey with notes of fruit and a strong finish. Accesible, but also complex enough for a seasoned drinker.",
        ArrayList(),
        ArrayList()
    )

    WhiskyItem(whisky = whiskyData) { }
}