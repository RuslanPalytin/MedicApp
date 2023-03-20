package com.example.medicapp.screens.bottomnav.analise

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
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
    val enterAddress = remember { mutableStateOf("Введите ваш адрес") }
    val enterFlat = remember { mutableStateOf("") }
    val enterDateAndTime = remember { mutableStateOf("Выберите дату и время") }

    ModalBottomSheetLayout(
        sheetContent = {
            when (selectedEdit.value) {
                "Введите ваш адрес" -> SheetContentAddress(
                    sheetState = sheetState,
                    scope = scope,
                    placeholder = enterAddress
                )
                "Выберите дату и время" -> SheetContentDateAndTime(
                    sheetState = sheetState,
                    scope = scope,
                    placeholder = enterDateAndTime
                )
                "Пациент" -> SheetContentPatient(sheetState = sheetState, scope = scope)
                else -> SheetContentAddress(
                    sheetState = sheetState,
                    scope = scope,
                    placeholder = enterAddress
                )
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
            SelectedField(
                label = "Введите ваш адрес",
                placeholder = enterAddress,
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
            SelectedField(
                label = "Выберите дату и время",
                placeholder = enterDateAndTime,
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
fun SheetContentAddress(
    sheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    placeholder: MutableState<String>
) {

    val address = remember { mutableStateOf("") }
    val longitude = remember { mutableStateOf("") }
    val latitude = remember { mutableStateOf("") }
    val altitude = remember { mutableStateOf("") }
    val flat = remember { mutableStateOf("") }
    val entrance = remember { mutableStateOf("") }
    val floor = remember { mutableStateOf("") }
    val intercom = remember { mutableStateOf("") }
    val enableButton = remember { mutableStateOf(false) }
    val isSaveAddress = remember { mutableStateOf(false) }

    enableButton.value =
        address.value.isNotEmpty() &&
                longitude.value.isNotEmpty() &&
                latitude.value.isNotEmpty() &&
                altitude.value.isNotEmpty() &&
                flat.value.isNotEmpty() &&
                entrance.value.isNotEmpty() &&
                floor.value.isNotEmpty() &&
                intercom.value.isNotEmpty()

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            EditText(label = "Долгота", enterText = longitude, outlineSize = 0.3f)
            Spacer(modifier = Modifier.width(12.dp))
            EditText(label = "Широта", enterText = latitude, outlineSize = 0.6f)
            Spacer(modifier = Modifier.width(12.dp))
            EditText(label = "Высота", enterText = altitude, outlineSize = 1f)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            EditText(label = "Квартира", enterText = flat, outlineSize = 0.2f)
            Spacer(modifier = Modifier.width(16.dp))
            EditText(label = "Подъезд", enterText = entrance, outlineSize = 0.5f)
            Spacer(modifier = Modifier.width(16.dp))
            EditText(label = "Этаж", enterText = floor, outlineSize = 1f)
        }
        Spacer(modifier = Modifier.height(16.dp))
        EditText(label = "Домофон", enterText = intercom, outlineSize = 1f)
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = "Сохранить этот адрес?",
                fontSize = 16.sp,
                fontFamily = LatoRegular,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = enableButton.value,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ButtonEnabledColor,
                contentColor = Color.White
            ),
            onClick = {
                placeholder.value = address.value + ", кв. " + flat.value
                scope.launch {
                    sheetState.hide()
                }
            }
        ) {
            Text(text = "Подтвердить", fontFamily = LatoRegular, fontSize = 17.sp)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun SheetContentDateAndTime(
    sheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    placeholder: MutableState<String>
) {

    val date = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val enableButton = remember { mutableStateOf(false) }
    val times = listOf("10:00", "13:00", "14:00", "15:00", "16:00", "18:00", "19:00")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(times[0]) }

    enableButton.value = date.value.isNotEmpty()

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
                text = "Дата и время",
                fontSize = 20.sp,
                fontFamily = LatoRegular,
                fontWeight = FontWeight.Bold
            )
            Icon(
                modifier = Modifier
                    .size(22.dp)
                    .clickable {
                        scope.launch {
                            sheetState.hide()
                        }
                    },
                painter = painterResource(id = R.drawable.close_icon),
                contentDescription = null,
                tint = TextColorMakingOrder
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        EditText(label = "Выберите дату", enterText = date, outlineSize = 1f)
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Выберите время",
            color = GrayTextOnBoarding,
            fontFamily = LatoRegular,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup(),
            cells = GridCells.Adaptive(minSize = 76.dp)
        ) {
            items(times.size) { index ->
                Card(
                    modifier = Modifier
                        .padding(end = 16.dp, bottom = 16.dp)
                        .clickable {
                            onOptionSelected(times[index])
                            time.value = times[index]
                        },
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = if (times[index] == selectedOption) ButtonEnabledColor else SelectedDateAndTimeTextColor,
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        text = times[index],
                        color = if (times[index] == selectedOption) Color.White else GrayTextOnBoarding
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(10.dp),
            enabled = enableButton.value,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ButtonEnabledColor,
                contentColor = Color.White
            ),
            onClick = {
                placeholder.value = date.value + " " + time.value
                scope.launch {
                    sheetState.hide()
                }
            }
        ) {
            Text(text = "Подтвердить", fontFamily = LatoRegular, fontSize = 17.sp)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetContentPatient(sheetState: ModalBottomSheetState, scope: CoroutineScope) {

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SelectedField(
    label: String,
    placeholder: MutableState<String>,
    modalSheet: ModalBottomSheetState,
    scope: CoroutineScope,
    selectedText: MutableState<String>
) {
    val placeholders: MutableList<String> =
        mutableListOf("Введите ваш адрес", "Выберите дату и время", "Пациент")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = BackgroundTextField)
            .border(width = 1.dp, color = BorderColorTextField)
            .clickable {
                selectedText.value = label
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
                text = placeholder.value,
                fontFamily = LatoRegular,
                fontSize = 16.sp,
                color = if (!placeholders.contains(placeholder.value)) Color.Black else GrayTextOnBoarding
            )
        }
    }
}

@Composable
private fun EditText(
    label: String,
    enterText: MutableState<String>,
    outlineSize: Float
) {
    Column(modifier = Modifier.fillMaxWidth(outlineSize)) {
        Text(text = label, color = GrayTextOnBoarding, fontFamily = LatoRegular, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp)),
            value = enterText.value,
            onValueChange = { newText -> enterText.value = newText },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = GrayTextOnBoarding,
                backgroundColor = BackgroundTextField,
                placeholderColor = GrayTextOnBoarding,
                focusedIndicatorColor = BorderColorTextField,
                unfocusedIndicatorColor = BorderColorTextField
            ),
        )
    }
}