package com.my.zitos.multibroser.kizitowebexample

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CustomSwitch(
    modifier: Modifier,
    isActive: Boolean = false,
    borderWidth: Dp = 1.dp,
    toggleWidth: Dp = 50.dp,
    toggleHeight: Dp = 21.dp,
    toggleShape: Shape = CircleShape,
    toggleColor: Color = Color.LightGray,
    borderColor: Color = Color.DarkGray,
    painter: Painter? = null,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    onClick: () -> Unit
){



    val offsetAnimation by animateDpAsState(
        targetValue = if (isActive) toggleWidth + (borderWidth*2) else{0.dp-(borderWidth*2)},
        animationSpec = animationSpec, label = "null"
    )

    Box(modifier = modifier
        .wrapContentWidth()
        .wrapContentHeight()
        .background(Color.Transparent),
        contentAlignment = Alignment.CenterStart
    ){


        Box (modifier = Modifier
            .width(toggleWidth)
            .height(toggleHeight)
            .background(color = toggleColor, shape = toggleShape)
            .clip(shape = toggleShape)
            .clickable {
                onClick()
            }
            .border(width = borderWidth, shape = toggleShape, color = borderColor)
        )


        Image(
            painter = painter!!,
            contentDescription = null,
            modifier = Modifier
                .width(
                    if (isActive) {
                        toggleWidth / 2
                    } else {
                        toggleWidth / 3
                    }
                )
                .height(
                    if (isActive) {
                        toggleHeight * 2
                    } else {
                        toggleHeight
                    }
                )
                .offset(x = offsetAnimation / 2)
                .clip(shape = toggleShape)

        )



    }




}
