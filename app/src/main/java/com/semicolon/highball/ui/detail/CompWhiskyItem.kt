package com.semicolon.highball.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.semicolon.highball.remote.CompWhiskyData

@Composable
fun CompWhiskyItem(
    whiskyData: CompWhiskyData,
    onItemClick: (itemId: Int) -> Unit
) {

    Column(
        modifier = Modifier
            .clickable {
                onItemClick(whiskyData.id)
            }
            .width(100.dp)
            .height(160.dp)
            .padding(horizontal = 5.dp)
            .padding(horizontal = 0.dp)
            .background(Color(0xFFF0F0F0), RoundedCornerShape(10.dp))
    ) {
        Image(
            painter = rememberImagePainter(whiskyData.imageUrl),
            contentDescription = "whisky image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
        )

        Text(
            text = whiskyData.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 8.dp)
        )

        Text(
            text = "Price : ${whiskyData.price}￡",
            fontSize = 12.sp,
            maxLines = 1,
            modifier = Modifier
                .padding(start = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CompWhiskyItemPreview() {
    CompWhiskyItem(
        whiskyData = CompWhiskyData(
            329,
            "Johnnie Walker Black Label",
            30,
            "https://res.cloudinary.com/dt4sawqjx/image/upload/v1463689070/qva69o4glqlqutkpzfdg.jpg"
        )
    ) { }
}