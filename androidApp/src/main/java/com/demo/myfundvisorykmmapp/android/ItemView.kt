package com.demo.myfundvisorykmmapp.android

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.myfundvisorykmmapp.Controller
import com.demo.myfundvisorykmmapp.HNItem
import com.demo.myfvapp.ui.theme.MyFVAppTheme


@Composable
fun ItemView(item: HNItem) {
    Text(
        text = item.title,
        fontSize = 30.sp,
        modifier = Modifier.padding(8.dp, 12.dp)
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ItemViewPreview() {
    MyFVAppTheme {
        ItemView(
            HNItem(1, "story", 0, null, emptyList(), null, "Ceci est un titre", 0),
        )
    }
}
