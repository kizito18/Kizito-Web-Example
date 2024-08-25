package com.my.zitos.multibroser.kizitowebexample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.my.zitos.multibroser.kizitowebexample.sharedPref.setAdBlockerModeForWeb1Sp
import com.my.zitos.multibroser.kizitowebexample.sharedPref.setIncognitoModeForWeb1Sp
import com.my.zitos.multibroser.kizitowebexample.sharedPref.setPcModeForWeb1Sp
import com.my.zitos.multibroser.kizitowebexample.sharedPref.setWebView1UrlPref
import com.my.zitos.multibroser.kizitowebexample.viewModelAndWebViewHolder.GenViewModel
import com.my.zitos.multibroser.kizitowebexample.viewModelAndWebViewHolder.GenViewModelFactory
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun AutoRefreshWebViewSetupLayout(
    url: String,
    initPcMode: Boolean,
    initIncognitoMode: Boolean,
    initAdBlockerMode: Boolean,
    onDismiss: () -> Unit

) {



    val context = LocalContext.current
    val scope = rememberCoroutineScope()



    var urlTextValue by remember { mutableStateOf(url) }

    var borderColor by remember { mutableStateOf(Color.Transparent)}


    var isPcModeEnabled by remember { mutableStateOf(initPcMode) }
    var isIncognitoModeEnabled by remember { mutableStateOf(initIncognitoMode)}
    var isAdBlockerEnabled by remember { mutableStateOf(initAdBlockerMode)}


    val genViewModel: GenViewModel = viewModel(
        factory = GenViewModelFactory(context)
    )






    val whiteColor by remember { mutableStateOf(Color.LightGray) }

    val lightGray = Color.LightGray




        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(
                        red = lightGray.red,
                        green = lightGray.green,
                        blue = lightGray.blue,
                        alpha = 0.3f
                    )
                )
                .pointerInput(Unit) {
                    detectTapGestures {
                        onDismiss()
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp)
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                    )
                    .pointerInput(Unit) {
                        detectTapGestures {

                        }
                    }
                    .clip(RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp)),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {



                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.1f)
                            .height(5.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(whiteColor, shape = RoundedCornerShape(5.dp))
                    )






                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {

                    val platinumColor = Color(0xffCFC7D4)
                    val taupeGrayColor = Color(0xffADA5B2)


                    val colors = TextFieldDefaults.colors(
                        focusedContainerColor = platinumColor,
                        unfocusedContainerColor = platinumColor,
                        disabledContainerColor = platinumColor,

                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,


                        focusedTextColor = Color.DarkGray,
                        unfocusedTextColor = Color.DarkGray,
                        disabledTextColor = Color.DarkGray,

                        cursorColor = Color.Black,

                        selectionColors = TextSelectionColors(
                            handleColor = taupeGrayColor,
                            backgroundColor = taupeGrayColor
                        )
                    )

                    TextField(
                        value = urlTextValue,
                        onValueChange = {
                            urlTextValue = it

                            if (it.isNotBlank() && borderColor == Color.Red) {
                                borderColor = Color.Transparent
                            }
                        },
                        singleLine = true,
                        maxLines = 1,
                        colors = colors,
                        placeholder = {
                            Text(
                                text = "https://",
                                fontWeight = FontWeight.Normal,
                                color = Color.White
                            )
                        },
                        shape = RoundedCornerShape(100),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .border(
                                width = 1.dp,
                                color = borderColor,
                                shape = RoundedCornerShape(100)
                            )

                    )


                }







                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    val toggleHeight by remember { mutableStateOf(21.dp) }
                    val iconHeight by remember { mutableStateOf(toggleHeight *2) }

                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                    ) {



                        Row(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Text(text = "AdBlocker:")

                            Spacer(modifier = Modifier.width(10.dp))

                            Box(modifier = Modifier
                                .wrapContentWidth()
                                .height(iconHeight)
                                .align(Alignment.CenterVertically),
                                contentAlignment = Alignment.Center
                            ) {
                                CustomSwitch(
                                    modifier = Modifier,
                                    isActive = isAdBlockerEnabled,
                                    toggleHeight = toggleHeight,
                                    painter = if (isAdBlockerEnabled) {
                                        painterResource(id = R.drawable.icon_ads)
                                    } else {
                                        painterResource(id = R.drawable.icon_ads)
                                    },
                                    onClick = {
                                        isAdBlockerEnabled = if (isAdBlockerEnabled) {
                                            false
                                        } else {
                                            true
                                        }
                                    }
                                )
                            }
                        }




                        Row(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Text(text = "Incognito:")

                            Spacer(modifier = Modifier.width(10.dp))

                            Box(modifier = Modifier
                                .wrapContentWidth()
                                .height(iconHeight)
                                .align(Alignment.CenterVertically),
                                contentAlignment = Alignment.Center
                            ) {
                                CustomSwitch(
                                    modifier = Modifier,
                                    isActive = isIncognitoModeEnabled,
                                    toggleHeight = toggleHeight,
                                    painter = if (isIncognitoModeEnabled) {
                                        painterResource(id = R.drawable.icon_privacy)
                                    } else {
                                        painterResource(id = R.drawable.icon_privacy)
                                    },
                                    onClick = {
                                        isIncognitoModeEnabled = if (isIncognitoModeEnabled) {
                                            false
                                        } else {
                                            true
                                        }
                                    }
                                )
                            }
                        }



                        Row(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Text(text = "Desktop:")

                            Spacer(modifier = Modifier.width(10.dp))

                            Box(modifier = Modifier
                                .wrapContentWidth()
                                .height(iconHeight)
                                .align(Alignment.CenterVertically),
                                contentAlignment = Alignment.Center
                            ) {
                                CustomSwitch(
                                    modifier = Modifier,
                                    isActive = isPcModeEnabled,
                                    toggleHeight = toggleHeight,
                                    painter = if (isPcModeEnabled) {
                                        painterResource(id = R.drawable.icon_pc)
                                    } else {
                                        painterResource(id = R.drawable.icon_pc)
                                    },
                                    onClick = {
                                        isPcModeEnabled = if (isPcModeEnabled) {
                                            false
                                        } else {
                                            true
                                        }
                                    }
                                )
                            }
                        }



                    }



                }



                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {


                    //Start for Button for use for this web
                    TextButton(
                        onClick = {

                            if (urlTextValue.isBlank()) {
                                borderColor = Color.Red
                            } else {


                                val textFieldValueInLowercase = urlTextValue.lowercase(
                                    Locale.ROOT
                                )

                                val reconfiguredUrl =
                                    if (textFieldValueInLowercase.startsWith("https://")) {
                                        urlTextValue

                                    } else {
                                        if (textFieldValueInLowercase.startsWith("http://")) {

                                            val theLetterAfterHttp =
                                                urlTextValue.substring(7) // this remove the first 7 letters which is "http://"
                                            val newValue = "https://$theLetterAfterHttp"
                                            newValue

                                        } else {
                                            if (textFieldValueInLowercase.startsWith("www.")) {

                                                val newValue = "https://$urlTextValue"
                                                newValue

                                            } else {
                                                "https://www.google.com/search?q=${urlTextValue}"

                                            }

                                        }

                                    }

                                scope.launch {

                                    //change Url Value In SharedPref
                                    setWebView1UrlPref(
                                        url = reconfiguredUrl,
                                        dataStore = context.dataStoreAll
                                    )

                                    //  changePcModeInSharedPrefForWebRefresh
                                    setPcModeForWeb1Sp(
                                        pcModeValue = isPcModeEnabled,
                                        dataStore = context.dataStoreAll
                                    )

                                    // change IncognitoMode InS haredPref
                                    setIncognitoModeForWeb1Sp(
                                        incognitoModeValue = isIncognitoModeEnabled,
                                        dataStore = context.dataStoreAll
                                    )

                                    // change AdBlockerMode In SharedPref
                                    setAdBlockerModeForWeb1Sp(
                                        adBlockerModeValue = isAdBlockerEnabled,
                                        dataStore = context.dataStoreAll
                                    )


                                    //change WebView ViewModel Values
                                    genViewModel.webViewHolder1.value.webViewUrl = reconfiguredUrl
                                    genViewModel.webViewHolder1.value.webController =
                                        WebControllerType.LOAD_URL
                                    genViewModel.webViewHolder1.value.isPcModeActive =
                                        isPcModeEnabled
                                    genViewModel.webViewHolder1.value.isIncognitoActive =
                                        isIncognitoModeEnabled
                                    genViewModel.webViewHolder1.value.isAdBlockerActive =
                                        isAdBlockerEnabled
                                    //stop


                                    onDismiss()


                                }


                            }


                        },
                        modifier = Modifier
                            .weight(1f)
                            .scale(0.75f)
                            .background(whiteColor, shape = RoundedCornerShape(20.dp))
                            .border(
                                width = 1.dp,
                                Color.LightGray,
                                shape = RoundedCornerShape(20.dp)
                            )
                    ) {
                        Text(text = "USE FOR THIS WEB")
                    }
                    //Stop for Button for use for this web


                }



                val snowColor by remember { mutableStateOf(Color(0xffFFFBFE)) }

                // Start WebView Navigation controller
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 5.dp, end = 5.dp)
                        .background(snowColor, shape = RoundedCornerShape(20.dp))
                        .border(width = 1.dp, Color.LightGray, shape = RoundedCornerShape(20.dp))
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        contentAlignment = Alignment.Center
                    ) {

                        IconButton(onClick = {

                            genViewModel.webViewHolder1.value.webController= WebControllerType.GO_BACK

                        }) {

                            Icon(
                                painter = painterResource(id = R.drawable.icon_back),
                                contentDescription = "web view back button"
                            )

                        }

                    }


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        contentAlignment = Alignment.Center
                    ) {

                        IconButton(onClick = {

                            genViewModel.webViewHolder1.value.webController= WebControllerType.RELOAD_URL

                        }) {

                            Icon(
                                painter = painterResource(id = R.drawable.icon_refresh),
                                contentDescription = "web view refresh button"
                            )

                        }

                    }



                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        contentAlignment = Alignment.Center
                    ) {

                        IconButton(onClick = {

                            genViewModel.webViewHolder1.value.webController= WebControllerType.GO_FORWARD

                        }) {

                            Icon(
                                painter = painterResource(id = R.drawable.icon_forward),
                                contentDescription = "web view forward button"
                            )

                        }


                    }


                }

                // Stop WebView Navigation controller


            }


        }



}



