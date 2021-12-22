package com.demo.myfundvisorykmmapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.demo.myfundvisorykmmapp.Controller
import com.demo.myfvapp.ui.theme.MyFVAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyFVAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ContentView()
                }
            }
        }
    }
}

@Composable
fun ContentView() {
    val controller = remember { Controller() }
    val model by controller.model.collectAsState()

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            ItemListView(
                model = model,
                postIntent = { controller.process(it) },
                navController = navController
            )
        }
        composable(
            route = "item/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments!!.getLong("id")
            val m = model
            if (m is Controller.Model.Items) {
                val item = m.items.first { it.id == id }
                ItemView(item)
            } else {
                error("Items were not fetched!")
            }
        }
    }

//    ItemListView(model) { controller.process(it) }
}


//@Composable
//fun ScreenView(
//    model: Controller.Model,
//    postIntent: (Controller.Intent) -> Unit
//) {
//    Box(
//        contentAlignment = Alignment.Center
//    ) {
//        when (model) {
//            is Controller.Model.Form -> {
//                LapForm(
//                    postIntent,
//                    modifier = Modifier
//                        .padding(21.dp)
//                ) {
//                    if (model.errorMessage != null) {
//                        Text(
//                            text = model.errorMessage!!,
//                            color = Color.Red
//                        )
//                    }
//                }
//            }
//            is Controller.Model.LoggingIn -> {
//                CircularProgressIndicator()
//            }
//            is Controller.Model.LoggedIn -> {
//                Text("Hello, ${model.username}!")
//            }
//        }
//    }
//}
//
//@Composable
//fun LapForm(
//    postIntent: (Controller.Intent) -> Unit,
//    modifier: Modifier = Modifier,
//    content: @Composable () -> Unit
//) {
//    var username by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//
//    Column(modifier) {
//        UsernameAndPassword(
//            username, password,
//            { username = it },
//            { password = it }
//        )
//
//        Box(modifier = Modifier.padding(10.dp)) {
//            content()
//        }
//
//        Button(
//            onClick = {
//                postIntent(Controller.Intent.LogIn(username, password))
//            },
//            modifier = Modifier
//                .padding(10.dp)
//                .align(Alignment.End)
//        ) {
//            Text("Go!")
//        }
//    }
//}
//
//@Composable
//private fun UsernameAndPassword(
//    login: String,
//    password: String,
//    onLoginChange: (String) -> Unit,
//    onPasswordChange: (String) -> Unit,
//) {
//    OutlinedTextField(
//        value = login,
//        onValueChange = onLoginChange,
//        modifier = Modifier
//            .padding(10.dp)
//            .fillMaxWidth(),
//        label = { Text("Username") }
//    )
//    OutlinedTextField(
//        value = password,
//        onValueChange = onPasswordChange,
//        modifier = Modifier
//            .padding(10.dp)
//            .fillMaxWidth(),
//        label = { Text("password") },
//        visualTransformation = PasswordVisualTransformation()
//    )
//}
//
//@Preview(
//    showBackground = true,
//    showSystemUi = true
//)
//@Composable
//fun ContentViewPreview() {
//    MyFVAppTheme {
//        ScreenView(
//            model = Controller.Model.Form("Bad username un très très long username que j'ai mis juste pour te rendre la vie compliquée"),
//            postIntent = { println(it) }
//        )
//    }
//}
