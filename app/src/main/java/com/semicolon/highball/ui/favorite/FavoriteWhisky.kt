package com.semicolon.highball.ui.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.semicolon.highball.local.FavoriteWhiskyRoomData
import com.semicolon.highball.ui.Screen
import com.semicolon.highball.ui.component.AppBar
import com.semicolon.highball.ui.component.SearchBar
import com.semicolon.highball.viewmodel.WhiskyViewModel


@Composable
fun FavoriteWhisky(
    navController: NavController,
    whiskyViewModel: WhiskyViewModel
) {
    val favoriteWhisky by whiskyViewModel.favoriteWhiskyList.observeAsState()
    FavoriteWhisky(favoriteWhisky = favoriteWhisky) {
        navController.navigate(Screen.Detail.createRoute(it))
    }
    whiskyViewModel.getFavoriteWhiskyList()
}

@Composable
fun FavoriteWhisky(
    favoriteWhisky: List<FavoriteWhiskyRoomData>?,
    onItemClick: (whiskyId: Int) -> Unit
) {
    val listState = rememberLazyListState()
    val isTop by remember { derivedStateOf { listState.firstVisibleItemIndex < 1 } }
    var whiskyList by remember { mutableStateOf(favoriteWhisky) }

    Scaffold(
        topBar = { AppBar("Favorite❤️", isTop) },
        modifier = Modifier
            .padding(bottom = 60.dp)
    ) {
        Column {
            SearchBar {
                if (favoriteWhisky != null) {
                    whiskyList = if (it.isEmpty()) favoriteWhisky
                    else {
                        val searchResult = ArrayList<FavoriteWhiskyRoomData>()
                        for (whisky in favoriteWhisky)
                            if (whisky.title.contains(it)) searchResult.add(whisky)
                        searchResult
                    }
                }
            }

            LazyColumn(state = listState) {

                if (whiskyList != null) items(whiskyList!!) { whisky ->
                    FavoriteWhiskyItem(whisky) { onItemClick(it) }
                    Divider(color = Color(0x11000000))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteWhiskyPreview() {
    val favoriteWhiskyList = ArrayList<FavoriteWhiskyRoomData>()
    val favoriteWhisky = FavoriteWhiskyRoomData(
        330,
        "Johnnie Walker Blue Label",
        157,
        "https://res.cloudinary.com/dt4sawqjx/image/upload/v1463683021/eymnv9ef7lqya5nwjqa3.jpg"
    )
    favoriteWhiskyList.add(favoriteWhisky)
    favoriteWhiskyList.add(favoriteWhisky)
    favoriteWhiskyList.add(favoriteWhisky)
    favoriteWhiskyList.add(favoriteWhisky)
    favoriteWhiskyList.add(favoriteWhisky)
    favoriteWhiskyList.add(favoriteWhisky)
    favoriteWhiskyList.add(favoriteWhisky)
    favoriteWhiskyList.add(favoriteWhisky)
    favoriteWhiskyList.add(favoriteWhisky)
    favoriteWhiskyList.add(favoriteWhisky)

    FavoriteWhisky(favoriteWhisky = favoriteWhiskyList) { }
}