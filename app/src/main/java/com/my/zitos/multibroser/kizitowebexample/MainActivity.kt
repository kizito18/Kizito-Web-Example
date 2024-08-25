package com.my.zitos.multibroser.kizitowebexample

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.dataStore
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.my.zitos.multibroser.kizitowebexample.sharedPref.AllPref
import com.my.zitos.multibroser.kizitowebexample.sharedPref.AllSerializer
import com.my.zitos.multibroser.kizitowebexample.viewModelAndWebViewHolder.GenViewModel
import com.my.zitos.multibroser.kizitowebexample.viewModelAndWebViewHolder.GenViewModelFactory


class ValuesHolderForAutoRefreshActivityMainScreen{

    var isSettingsLayoutOpen by mutableStateOf(false)

    var urlPassed by mutableStateOf("")

    var pcModePassed by mutableStateOf<Boolean?>(null)
    var adBlockerModePassed by mutableStateOf<Boolean?>(null)
    var incognitoModePassed by mutableStateOf<Boolean?>(null)


}

val Context.dataStoreAll by dataStore("app-sp.json", AllSerializer)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            val context = LocalContext.current


            val dataStoreValues = context.dataStoreAll.data.collectAsStateWithLifecycle(
                initialValue = AllPref()
            )

            val genViewModel: GenViewModel = viewModel(
                factory = GenViewModelFactory(context)
            )

            val flowValueHolder by genViewModel.passedValuesForInteraction.collectAsStateWithLifecycle()




            val systemUiController = rememberSystemUiController()

            systemUiController.setNavigationBarColor(Color.Gray)
            systemUiController.setStatusBarColor(Color.Gray)







            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {


                BrowserAutoRefresh(

                    initUrl = dataStoreValues.value.web1AutoRefreshUrl,
                    isPcModeActiveInit = dataStoreValues.value.isPcModeActiveForWeb1,
                    isAdBlockerActiveInit = dataStoreValues.value.isAdBlockerActiveForWeb1,
                    isIncognitoActiveInit = dataStoreValues.value.isIncognitoActiveForWeb1,
                    showSettings = { url, pcMode,adMode, incognitoMode ->

                        flowValueHolder.urlPassed = url
                        flowValueHolder.isSettingsLayoutOpen = true
                        flowValueHolder.pcModePassed = pcMode
                        flowValueHolder.adBlockerModePassed = adMode
                        flowValueHolder.incognitoModePassed = incognitoMode

                    },
                    modifier = Modifier

                )







                if(flowValueHolder.isSettingsLayoutOpen){
                    AutoRefreshWebViewSetupLayout(
                        url = flowValueHolder.urlPassed,
                        initPcMode = flowValueHolder.pcModePassed!!,
                        initIncognitoMode = flowValueHolder.incognitoModePassed!!,
                        initAdBlockerMode = flowValueHolder.adBlockerModePassed!!,
                        onDismiss = {
                            flowValueHolder.urlPassed =""
                            flowValueHolder.pcModePassed = null
                            flowValueHolder.adBlockerModePassed = null
                            flowValueHolder.incognitoModePassed = null
                            flowValueHolder.isSettingsLayoutOpen = false

                        }
                    )

                }



            }



        }




    }



}






