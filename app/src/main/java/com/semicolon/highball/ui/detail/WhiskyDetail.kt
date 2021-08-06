package com.semicolon.highball.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.semicolon.highball.remote.CompWhiskyData
import com.semicolon.highball.remote.TagData
import com.semicolon.highball.remote.WhiskyData
import com.semicolon.highball.ui.Screen
import com.semicolon.highball.viewmodel.WhiskyViewModel

@ExperimentalCoilApi
@Composable
fun WhiskyDetail(
    id: Int,
    whiskyViewModel: WhiskyViewModel,
    navController: NavController
) {
    val whiskyInfo by whiskyViewModel.whiskyInfo.observeAsState()
    val isFavorite by whiskyViewModel.favoriteWhiskyStateEvent.observeAsState()
    WhiskyDetail(
        whiskyData = whiskyInfo,
        isFavorite = isFavorite,
        onBackClick = { navController.popBackStack() },
        onFavoriteClick = {
            if (isFavorite!!) whiskyViewModel.deleteFavoriteWhisky(id)
            else whiskyViewModel.addFavoriteWhisky(it.id, it.title, it.price, it.imageUrl)
        },
        onItemClick = {
            navController.popBackStack()
            navController.navigate(Screen.Detail.createRoute(it)) }
    )
    whiskyViewModel.getWhiskyInfo(id)
    whiskyViewModel.isFavoriteWhisky(id)
}

@ExperimentalCoilApi
@Composable
fun WhiskyDetail(
    whiskyData: WhiskyData?,
    isFavorite: Boolean?,
    onBackClick: () -> Unit,
    onFavoriteClick: (item: WhiskyData) -> Unit,
    onItemClick: (itemId: Int) -> Unit
) {
    val scrollState = rememberScrollState()
    val compWhiskyListState = rememberLazyListState()
    val whiskyImage =
        if (whiskyData != null) rememberImagePainter(whiskyData.imageUrl) else rememberImagePainter(
            "https://i.ibb.co/xYHxkmB/noimage-LS-300x450-9aa08d8b1f9798e48e994716e17c760b.png"
        )

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            Image(
                painter = whiskyImage,
                contentDescription = "whisky image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0x33000000))
            )

            IconButton(
                onClick = { onBackClick() },
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Favorite"
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = whiskyData?.title ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .width(300.dp)
            )
            IconButton(
                onClick = { if(whiskyData != null) onFavoriteClick(whiskyData) },
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
            ) {
                val favoriteIcon =
                    when {
                        isFavorite == null -> Icons.Filled.FavoriteBorder
                        isFavorite -> Icons.Filled.Favorite
                        else -> Icons.Filled.FavoriteBorder
                    }

                Icon(
                    imageVector = favoriteIcon,
                    tint = Color(0xFFFF0000),
                    contentDescription = "Favorite",
                )
            }
        }

        Text(
            text = if (whiskyData != null) "Price : ${whiskyData.price}￡" else "Price : ",
            fontSize = 15.sp,
            modifier = Modifier
                .padding(start = 20.dp, bottom = 5.dp)
        )
        Text(
            text = if (whiskyData != null) "Region : ${whiskyData.region}" else "Region : ",
            fontSize = 15.sp,
            modifier = Modifier
                .padding(start = 20.dp)
        )

        Divider(
            color = Color(0x11000000),
            modifier = Modifier
                .padding(vertical = 20.dp)
        )

        Text(
            text = "Description",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 20.dp)
        )
        Text(
            text = whiskyData?.description ?: "",
            fontSize = 15.sp,
            modifier = Modifier
                .padding(top = 5.dp, start = 20.dp, end = 20.dp)
        )

        Divider(
            color = Color(0x11000000),
            modifier = Modifier
                .padding(vertical = 20.dp)
        )

        Text(
            text = "Whisky tag",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 5.dp, start = 20.dp)
        )
        if (whiskyData != null) HashTag(tags = whiskyData.tags)

        Divider(
            color = Color(0x11000000),
            modifier = Modifier
                .padding(vertical = 20.dp)
        )

        Text(
            text = "Comparable",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 20.dp, bottom = 5.dp)
        )

        LazyRow(
            state = compWhiskyListState,
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
        ) {
            if (whiskyData != null) items(whiskyData.comparable) { compWhisky ->
                CompWhiskyItem(whiskyData = compWhisky) {
                    onItemClick(it)
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Preview(showBackground = true)
@Composable
fun WhiskyDetailPreview() {
    val tagList: MutableList<TagData> = ArrayList()
    tagList.add(TagData("brown"))
    tagList.add(TagData("spicy"))
    tagList.add(TagData("salty"))
    tagList.add(TagData("bitter"))
    tagList.add(TagData("sugar"))
    tagList.add(TagData("cocoa"))
    tagList.add(TagData("mild"))
    tagList.add(TagData("complex"))
    tagList.add(TagData("orange"))
    tagList.add(TagData("fruity"))
    tagList.add(TagData("citrus"))

    val comparableList: MutableList<CompWhiskyData> = ArrayList()
    comparableList.add(
        CompWhiskyData(
            329,
            "Johnnie Walker Black Label",
            30,
            "https://res.cloudinary.com/dt4sawqjx/image/upload/v1463689070/qva69o4glqlqutkpzfdg.jpg"
        )
    )
    comparableList.add(
        CompWhiskyData(
            503,
            "Wild Turkey 101",
            19,
            "https://res.cloudinary.com/dt4sawqjx/image/upload/v1463689218/ydyp4tuufumgx6pcvy1v.jpg"
        )
    )
    comparableList.add(
        CompWhiskyData(
            55,
            "Ardbeg Corryvreckan",
            66,
            "https://res.cloudinary.com/dt4sawqjx/image/upload/v1463689119/b9c19mrjwesamr1vxtt8.jpg"
        )
    )

    val whiskyData = WhiskyData(
        330,
        "Johnnie Walker Blue Label",
        "https://res.cloudinary.com/dt4sawqjx/image/upload/v1463683021/eymnv9ef7lqya5nwjqa3.jpg",
        "Other",
        157,
        78,
        "This is an excellent whiskey with notes of fruit and a strong finish. Accesible, but also complex enough for a seasoned drinker.",
        tagList,
        comparableList
    )

    WhiskyDetail(
        whiskyData = whiskyData,
        isFavorite = false,
        onBackClick = {},
        onFavoriteClick = {},
        onItemClick = {}
    )
}