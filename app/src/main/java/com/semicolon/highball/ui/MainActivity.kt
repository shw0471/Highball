package com.semicolon.highball.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import coil.annotation.ExperimentalCoilApi
import com.semicolon.highball.ui.theme.HighballTheme
import com.semicolon.highball.viewmodel.WhiskyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val whiskyViewModel: WhiskyViewModel by viewModel()

    @ExperimentalCoilApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HighballTheme {
                Surface(color = MaterialTheme.colors.background) {
                    HighballApp(whiskyViewModel = whiskyViewModel)
                }

            }
        }
    }
}
