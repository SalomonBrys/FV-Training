package com.demo.myfundvisorykmmapp.android

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.demo.myfundvisorykmmapp.Controller
import com.demo.myfundvisorykmmapp.HNItem
import com.demo.myfvapp.ui.theme.MyFVAppTheme

@Composable
fun ItemListView(
    model: Controller.Model,
    postIntent: (Controller.Intent) -> Unit,
    navController: NavHostController?
) {
    when (model) {
        is Controller.Model.Loading -> {
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is Controller.Model.Items -> {
            LazyColumn {
                model.items.forEach {
                    item {
                        Text(
                            text = it.title,
                            modifier = Modifier
                                .clickable {
                                    navController?.navigate("item/${it.id}")
                                }
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 12.dp)
                        )
                        Divider()
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ItemListViewPreview() {
    MyFVAppTheme {
        ItemListView(
            model = Controller.Model.Items(
                listOf(
                    HNItem(1, "story", 0, null, emptyList(), null, "Ceci est un titre", 0),
                    HNItem(1, "story", 0, null, emptyList(), null, "Un autre message", 0),
                )
            ),
            postIntent = { println(it) },
            navController = null
        )
    }
}
