package com.uney.android.mls.mlswrappertestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uney.android.mls.mlswrapper.GroupType
import com.uney.android.mls.mlswrapper.GroupVisibility
import com.uney.android.mls.mlswrapper.MLSWrapper
import com.uney.android.mls.mlswrappertestapp.ui.theme.AndroidMLSLibraryTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mlsWrapper: MLSWrapper


    suspend fun startMLS() {
        mlsWrapper.initlaize()// initialize

        mlsWrapper.methods.tempRegisterUserOnCAS(
            clientId = getDecodedJwtValue("uniqueDeviceId"),
            userId = getDecodedJwtValue("sub")
        )

        mlsWrapper.methods.uploadKeyBundles()

        //create a group
        mlsWrapper.methods.createGroup(
            groupName = "sample group",
            type = GroupType.MULTI,
            visibility = GroupVisibility.PUBLIC,
            users = listOf("93a885ef-fb5a-480b-9d32-1a793868459c")
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidMLSLibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "MLS",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Button(
                enabled = true,
                onClick = {

                    // Launch a coroutine to call the suspend function
                    CoroutineScope(Dispatchers.Main).launch {
                        startMLS()
                    }
                },
                modifier = Modifier.padding(16.dp) // Optional: Add padding for better spacing
            ) {
                Text(text = "Hello, $name!") // Set button title
            }

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        AndroidMLSLibraryTheme {
            Greeting("Android")
        }
    }
}

