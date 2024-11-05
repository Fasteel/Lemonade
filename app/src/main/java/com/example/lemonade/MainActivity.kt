package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text(
                                    text = "Lemonade",
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        )
                    }
                ) { innerPadding ->
                    LemonadeApp(innerPadding)
                }
            }
        }
    }
}

@Composable
fun LemonadeApp(paddingValues: PaddingValues, modifier: Modifier = Modifier) {
    var step by remember { mutableIntStateOf(1) }
    var currentRetry by remember { mutableIntStateOf(0) }
    var objectiveRetry by remember { mutableIntStateOf(0) }
    val currentStepData = when (step) {
        1 -> Triple(R.string.step_1_title, R.drawable.lemon_tree, R.string.step_1_description)
        2 -> Triple(R.string.step_2_title, R.drawable.lemon_squeeze, R.string.step_2_description)
        3 -> Triple(R.string.step_3_title, R.drawable.lemon_drink, R.string.step_3_description)
        4 -> Triple(R.string.step_4_title, R.drawable.lemon_restart, R.string.step_4_description)
        else -> Triple(R.string.step_1_title, R.drawable.lemon_restart, R.string.step_1_description)
    }

    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(32.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable {
                    if (step == 2) {
                        if (currentRetry == objectiveRetry - 1) {
                            objectiveRetry = 0
                            currentRetry = 0
                            step++
                        } else if (currentRetry == 0 && objectiveRetry == 0) {
                            objectiveRetry = (2..4).random()
                            currentRetry++
                        } else if (currentRetry < objectiveRetry) {
                            assert(objectiveRetry > 0)
                            currentRetry++
                        }
                    } else {
                        step = step % 4 + 1
                    }
                },
        ) {
            Image(
                painter = painterResource(currentStepData.second),
                modifier = Modifier.padding(32.dp, 8.dp),
                contentDescription = stringResource(currentStepData.third),
            )
        }
        Spacer(modifier = modifier.height(24.dp))
        Text(
            stringResource(currentStepData.first),
            color = Color.DarkGray,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth()
        )
    }
}
