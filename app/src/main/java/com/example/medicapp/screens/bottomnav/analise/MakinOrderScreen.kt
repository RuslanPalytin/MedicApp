package com.example.medicapp.screens.bottomnav.analise

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicapp.R
import com.example.medicapp.graphs.Graph
import com.example.medicapp.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MakingOrderScreen(navController: NavController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.White, darkIcons = true)

    val scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val selectedEdit = remember { mutableStateOf("") }
    val analiseNumber = remember { mutableStateOf(0) }
    val analiseSum = remember { mutableStateOf(0) }

    ModalBottomSheetLayout(
        sheetContent = {
            when (selectedEdit.value) {
                "Введите ваш адрес" -> SheetContentAddress(sheetState = sheetState, scope = scope)
                "Выберите дату и время" -> SheetContentDateAndTime(
                    sheetState = sheetState,
                    scope = scope
                )
                "Пациент" -> SheetContentPatient(sheetState = sheetState, scope = scope)
                else -> SheetContentAddress(sheetState = sheetState, scope = scope)
            }
        },
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetBackgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 88.dp)
                .padding(horizontal = 20.dp)
                .verticalScroll(state = scrollState, enabled = true)
        ) {
            Text(
                text = "Оформление заказа",
                fontSize = 24.sp,
                fontFamily = LatoRegular,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Адрес*",
                color = TextColorMakingOrder,
                fontSize = 14.sp,
                fontFamily = LatoRegular
            )
            Spacer(modifier = Modifier.height(4.dp))
            Edit(
                placeholder = "Введите ваш адрес",
                modalSheet = sheetState,
                scope = scope,
                selectedText = selectedEdit
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Дата и время*",
                color = TextColorMakingOrder,
                fontSize = 14.sp,
                fontFamily = LatoRegular
            )
            Spacer(modifier = Modifier.height(4.dp))
            Edit(
                placeholder = "Выберите дату и время",
                modalSheet = sheetState,
                scope = scope,
                selectedText = selectedEdit
            )
            Spacer(modifier = Modifier.height(32.dp))

            Row {
                Text(
                    text = "Кто будет сдавать анализы?",
                    color = TextColorMakingOrder,
                    fontSize = 14.sp,
                    fontFamily = LatoRegular
                )
                Text(text = " *", color = Color.Red, fontSize = 14.sp, fontFamily = LatoRegular)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Image(
            painter = painterResource(id = R.drawable.back_button_icon),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 42.dp, start = 26.dp)
                .size(32.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetContentAddress(sheetState: ModalBottomSheetState, scope: CoroutineScope) {

    val address = remember { mutableStateOf("") }
    val longitude = remember { mutableStateOf("") }
    val latitude = remember { mutableStateOf("") }
    val altitude = remember { mutableStateOf("") }
    val flat = remember { mutableStateOf("") }
    val entrance = remember { mutableStateOf("") }
    val floor = remember { mutableStateOf("") }
    val intercom = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 24.dp, bottom = 32.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Адрес сдачи анализов",
                fontSize = 20.sp,
                fontFamily = LatoRegular,
                fontWeight = FontWeight.Bold
            )
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(id = R.drawable.close_address),
                contentDescription = null,
                tint = TextColorMakingOrder
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        EditText(label = "Ваш адрес", enterText = address, outlineSize = 1f)
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            EditText(label = "Долгота", enterText = longitude, outlineSize = 0.3f)
            Spacer(modifier = Modifier.width(12.dp))
            EditText(label = "Широта", enterText = latitude, outlineSize = 0.6f)
            Spacer(modifier = Modifier.width(12.dp))
            EditText(label = "Высота", enterText = altitude, outlineSize = 1f)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetContentDateAndTime(sheetState: ModalBottomSheetState, scope: CoroutineScope) {

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetContentPatient(sheetState: ModalBottomSheetState, scope: CoroutineScope) {

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Edit(
    placeholder: String,
    @DrawableRes icon: Int? = null,
    modalSheet: ModalBottomSheetState,
    scope: CoroutineScope,
    selectedText: MutableState<String>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = BackgroundTextField)
            .border(width = 1.dp, color = BorderColorTextField)
            .clickable {
                selectedText.value = placeholder
                scope.launch {
                    modalSheet.show()
                }
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = placeholder,
                fontFamily = LatoRegular,
                fontSize = 16.sp,
                color = GrayTextOnBoarding
            )
        }
    }
}

@Composable
private fun EditText(
    placeholder: String = "",
    label: String,
    enterText: MutableState<String>,
    @DrawableRes icon: Int? = null,
    outlineSize: Float
) {

    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth(outlineSize)) {
        Text(text = label, color = GrayTextOnBoarding, fontFamily = LatoRegular, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp)),
            value = enterText.value,
            onValueChange = { newText -> enterText.value = newText },
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = LatoRegular,
                    fontSize = 14.sp
                )
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = GrayTextOnBoarding,
                backgroundColor = BackgroundTextField,
                placeholderColor = GrayTextOnBoarding,
                focusedIndicatorColor = BorderColorTextField,
                unfocusedIndicatorColor = BorderColorTextField
            ),
            enabled = icon == null,
            trailingIcon = {
                if (icon != null) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { expanded = true },
                        painter = painterResource(id = icon),
                        contentDescription = null
                    )
                }
            }
        )
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 20.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            Text(
                text = "Мужской",
                fontSize = 18.sp,
                fontFamily = LatoRegular,
                modifier = Modifier.clickable {
                    enterText.value = "Мужской"
                    expanded = false
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Женский",
                fontSize = 18.sp,
                fontFamily = LatoRegular,
                modifier = Modifier.clickable {
                    enterText.value = "Женский"
                    expanded = false
                }
            )
        }
    }
}