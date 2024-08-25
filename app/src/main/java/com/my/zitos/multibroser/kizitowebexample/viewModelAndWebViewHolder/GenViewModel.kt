package com.my.zitos.multibroser.kizitowebexample.viewModelAndWebViewHolder

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.my.zitos.multibroser.kizitowebexample.ValuesHolderForAutoRefreshActivityMainScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class GenViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GenViewModel::class.java)) {
            return GenViewModel(SavedStateHandle(),context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}




class GenViewModel(
    savedStateHandle: SavedStateHandle,
    context: Context
) : ViewModel()
{



    private val _passedValuesForInteraction  = MutableStateFlow(savedStateHandle["passed_value_class_inside_ViewModel"]
        ?: ValuesHolderForAutoRefreshActivityMainScreen()
    )
    val passedValuesForInteraction: StateFlow<ValuesHolderForAutoRefreshActivityMainScreen> =
        _passedValuesForInteraction.asStateFlow()




    //for web1

    private val _webViewHolder1 = MutableStateFlow(savedStateHandle["webViewHolder1_class__inside_ViewModel"]
        ?: WebViewHolder(context)
    )
    val webViewHolder1: StateFlow<WebViewHolder> = _webViewHolder1.asStateFlow()




}

