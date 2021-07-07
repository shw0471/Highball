package com.semicolon.highball.di

import com.semicolon.highball.viewmodel.WhiskyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { WhiskyViewModel(get(), get()) }
}