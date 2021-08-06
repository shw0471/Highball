package com.semicolon.highball.ui.all

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.semicolon.highball.remote.WhiskyData
import com.semicolon.highball.ui.Screen
import com.semicolon.highball.ui.component.AppBar
import com.semicolon.highball.ui.component.SearchBar
import com.semicolon.highball.viewmodel.WhiskyViewModel

@Composable
fun AllWhisky(
    navController: NavController,
    whiskyViewModel: WhiskyViewModel
) {
    val whisky by whiskyViewModel.whiskyList.observeAsState()
    val nextPage by whiskyViewModel.nextPage.observeAsState()
    AllWhisky(whiskys = whisky?.results) {
        navController.navigate(Screen.Detail.createRoute(it))
    }
    if(nextPage!! != 0) whiskyViewModel.getWhiskyList(nextPage!!)
}

@Composable
fun AllWhisky(
    whiskys: List<WhiskyData>?,
    onItemClick: (whiskyId: Int) -> Unit
) {
    val listState = rememberLazyListState()
    val isTop by remember { derivedStateOf { listState.firstVisibleItemIndex < 1 } }
    var whiskyList by remember { mutableStateOf(whiskys) }

    Scaffold(
        topBar = { AppBar("All Whisky🥃️", isTop) },
        modifier = Modifier
            .padding(bottom = 60.dp)
    ) {
        Column {
            SearchBar {
                if (whiskys != null) {
                    whiskyList = if (it.isEmpty()) whiskys
                    else {
                        val searchResult = ArrayList<WhiskyData>()
                        for (whisky in whiskys)
                            if (whisky.title.contains(it)) searchResult.add(whisky)
                        searchResult
                    }
                }
            }

            LazyColumn(state = listState) {

                if (whiskyList != null) items(whiskyList!!) { whisky ->
                    WhiskyItem(whisky) { onItemClick(it) }
                    Divider(color = Color(0x11000000))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllWhiskyPreview() {
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
    val whiskyList = ArrayList<WhiskyData>()
    whiskyList.add(whiskyData)
    whiskyList.add(whiskyData)
    whiskyList.add(whiskyData)
    whiskyList.add(whiskyData)
    whiskyList.add(whiskyData)
    whiskyList.add(whiskyData)
    whiskyList.add(whiskyData)
    whiskyList.add(whiskyData)
    whiskyList.add(whiskyData)
    whiskyList.add(whiskyData)
    AllWhisky(whiskys = whiskyList) {}
}