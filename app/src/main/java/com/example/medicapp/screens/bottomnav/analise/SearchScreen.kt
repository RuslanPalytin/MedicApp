package com.example.medicapp.screens.bottomnav.analise

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicapp.R
import com.example.medicapp.models.CatalogModel
import com.example.medicapp.screens.bottomnav.analise.uiitems.SearchOneItem
import com.example.medicapp.ui.theme.*

@Composable
fun SearchScreen(navController: NavController) {

    var searchText by remember { mutableStateOf("") }
    var filteredText: List<CatalogModel> = listOf()
    val context = LocalContext.current
    val getCatalog = remember { mutableStateOf<List<CatalogModel>?>(null) }
    getCatalog(context = context, result = getCatalog)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .clip(shape = RoundedCornerShape(10.dp)),
                value = searchText,
                onValueChange = { newText -> searchText = newText },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                searchText = ""
                            },
                        painter = painterResource(id = R.drawable.cancel_icon),
                        contentDescription = null
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = GrayTextOnBoarding,
                    backgroundColor = BackgroundTextField,
                    placeholderColor = GrayTextOnBoarding,
                    focusedIndicatorColor = BorderColorTextField,
                    unfocusedIndicatorColor = BorderColorTextField
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.popBackStack() },
                text = "Отменить",
                textAlign = TextAlign.Center,
                fontFamily = LatoRegular,
                color = BlueTextOnBoarding,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(thickness = 1.dp, color = StrokeItemColor)
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
        ) {
            if(getCatalog.value != null) {
                items(count = getCatalog.value!!.size) { index ->
                    SearchOneItem(item = getCatalog.value!![index])
                }
            }
        }
    }
}