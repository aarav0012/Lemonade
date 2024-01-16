package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonApp()
            }
        }
    }
}

@Composable
fun LemonTextAndImage(
    image: Int,
    text: Int,
    currentStepIncrease: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "lemon Tree",
            modifier = Modifier
                .size(240.dp)
                .border(
                    width = 2.dp, color = Color(105, 216, 181, 220),
                    RoundedCornerShape(24.dp)
                )
                .background(
                    color = Color(128, 228, 196, 126),
                    RoundedCornerShape(24.dp)
                )
                .clickable {
                    currentStepIncrease()
                }

        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(text),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalMaterial3Api :: class)
@Composable
fun LemonApp() {
    var currentStep by remember { mutableIntStateOf(1) }
    var tapCount by remember { mutableIntStateOf((2..4).random()) }
    var count = 1

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lemonade",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) {innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentStep) {
                1 -> LemonTextAndImage(
                    image = R.drawable.lemon_tree,
                    text = R.string.lemon_tree
                ) {
                    currentStep = 2
                    count = 1
                    tapCount = (2..4).random()
                }

                2 -> if (count == tapCount) {
                    LemonTextAndImage(
                        image = R.drawable.lemon_squeeze,
                        text = R.string.lemon
                    ) { currentStep = 3 }
                } else {

                    LemonTextAndImage(
                        image = R.drawable.lemon_squeeze,
                        text = R.string.lemon
                    ) {
                        currentStep = if (count < 4 && count != tapCount) {
                            count++
                            2
                        } else {
                            3
                        }
                    }
                }

                3 -> LemonTextAndImage(
                    image = R.drawable.lemon_drink,
                    text = R.string.glass_of_lemonade
                ) { currentStep = 4 }

                4 -> LemonTextAndImage(
                    image = R.drawable.lemon_restart,
                    text = R.string.empty_glass
                ) { currentStep = 1 }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeTheme {
        LemonApp()
    }
}