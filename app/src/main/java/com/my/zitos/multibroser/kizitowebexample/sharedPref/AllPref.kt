package com.my.zitos.multibroser.kizitowebexample.sharedPref

import androidx.datastore.core.DataStore
import kotlinx.serialization.Serializable


@Serializable
data class AllPref(

    val web1AutoRefreshUrl : String = "",

    val isPcModeActiveForWeb1: Boolean =  false,

    val isIncognitoActiveForWeb1: Boolean =  false,

    val isAdBlockerActiveForWeb1: Boolean =  false,



)


suspend fun setWebView1UrlPref(url: String, dataStore: DataStore<AllPref>) {
    dataStore.updateData { currentData ->
        currentData.copy(
            web1AutoRefreshUrl = url
        )
    }
}

suspend fun setPcModeForWeb1Sp(pcModeValue: Boolean, dataStore: DataStore<AllPref>) {
    dataStore.updateData { currentData ->
        currentData.copy(
            isPcModeActiveForWeb1 = pcModeValue
        )
    }
}

suspend fun setIncognitoModeForWeb1Sp(incognitoModeValue: Boolean, dataStore: DataStore<AllPref>) {
    dataStore.updateData { currentData ->
        currentData.copy(
            isIncognitoActiveForWeb1 = incognitoModeValue
        )
    }
}


suspend fun setAdBlockerModeForWeb1Sp(adBlockerModeValue: Boolean, dataStore: DataStore<AllPref>) {
    dataStore.updateData { currentData ->
        currentData.copy(
            isAdBlockerActiveForWeb1 = adBlockerModeValue
        )
    }
}
