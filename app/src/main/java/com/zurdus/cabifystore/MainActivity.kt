package com.zurdus.cabifystore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.zurdus.base.ui.theme.CabifyTheme
import com.zurdus.cabifystore.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CabifyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CabifyTheme.color.main.background,
                ) {
                    AppNavHost()
                }
            }
        }
    }
}
