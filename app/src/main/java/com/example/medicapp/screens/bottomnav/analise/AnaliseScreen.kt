package com.example.medicapp.screens.bottomnav.analise

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medicapp.R
import com.example.medicapp.api.ApiService
import com.example.medicapp.models.CatalogModel
import com.example.medicapp.models.StockAndNewsModel
import com.example.medicapp.screens.bottomnav.analise.uiitems.CatalogItem
import com.example.medicapp.screens.bottomnav.analise.uiitems.StockAndNewsItem
import com.example.medicapp.storage.SharedPreference
import com.example.medicapp.ui.theme.*
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale.Category

@Composable
fun AnaliseScreen(navController: NavController) {

    val context = LocalContext.current

    val getNews = remember { mutableStateOf<List<StockAndNewsModel>?>(null) }
    val getCatalog = remember { mutableStateOf<List<CatalogModel>?>(null) }
    getNews(context = context, result = getNews)
    getCatalog(context = context, result = getCatalog)
    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
            .padding(top = 40.dp)
    ) {

        Search()

        Spacer(modifier = Modifier.height(32.dp))

        StockAndNews(getNews = getNews)

        Spacer(modifier = Modifier.height(32.dp))

        if (getCatalog.value != null) {
            Catalog(getCatalog = getCatalog)
        }
    }
}

@Composable
fun Search() {

    var search by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp)),
        value = search,
        onValueChange = { newText -> search = newText },
        leadingIcon = {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = null
            )
        },
        placeholder = {
            Text(
                text = "Искать анализы",
                fontFamily = LatoRegular,
                fontSize = 16.sp
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
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun StockAndNews(getNews: MutableState<List<StockAndNewsModel>?>) {

    val pagerState = rememberPagerState()

    Text(
        text = "Акции и новости",
        fontSize = 17.sp,
        fontFamily = LatoRegular,
        color = GrayTextOnBoarding
    )
    Spacer(modifier = Modifier.height(16.dp))

    if (getNews.value != null) {

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(count = getNews.value!!.size) { index ->
                StockAndNewsItem(item = getNews.value!![index], count = index)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Catalog(getCatalog: MutableState<List<CatalogModel>?>) {

    val pagerState = rememberPagerState()

    var categories: List<CatalogModel> = listOf()

    if (getCatalog.value != null) {
        categories = getCatalog.value!!.distinctBy { it.category }
    }

    Text(
        text = "Каталог анализов",
        fontSize = 17.sp,
        fontFamily = LatoRegular,
        color = GrayTextOnBoarding
    )
    Spacer(modifier = Modifier.height(16.dp))

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        Column(modifier = Modifier
            .padding(paddingValues = contentPadding)
            .padding(bottom = 16.dp)) {
            Tabs(categories = categories, pagerState = pagerState)
            Spacer(modifier = Modifier.height(24.dp))
            TabsContent(tabs = categories, response = getCatalog, pagerState = pagerState)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(categories: List<CatalogModel>, pagerState: PagerState) {

    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .pagerTabIndicatorOffset(
                        pagerState = pagerState,
                        tabPositions = tabPositions
                    )
                    .height(0.dp)
            )
        },
        divider = { Divider(thickness = 0.dp, color = Color.White) }
    ) {
        categories.forEachIndexed { index, tab ->

            val selected = pagerState.currentPage == index

            Tab(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = if (selected) ButtonEnabledColor else BackgroundTextField),
                selected = selected,
                selectedContentColor = Color.White,
                unselectedContentColor = UnselectedTabTextColor,
                text = {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(vertical = 14.dp),
                        text = tab.category,
                        fontFamily = LatoRegular,
                        fontSize = 15.sp
                    )
                },
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                })
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(
    tabs: List<CatalogModel>,
    response: MutableState<List<CatalogModel>?>,
    pagerState: PagerState,
) {
    HorizontalPager(
        count = tabs.size,
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) { page ->

        val list: MutableList<CatalogModel> = mutableListOf()

        for (i in 0 until response.value!!.size) {
            if (response.value!![i].category == tabs[page].category) {
                list.add(response.value!![i])
            }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(count = list.size) { index ->
                CatalogItem(item = list[index])
            }
        }
    }
}

fun getNews(context: Context, result: MutableState<List<StockAndNewsModel>?>) {

    val token = SharedPreference(context).readToken()

    ApiService.retrofit.getStockAndNews("Bearer $token")
        .enqueue(object : Callback<List<StockAndNewsModel>?> {
            override fun onResponse(
                call: Call<List<StockAndNewsModel>?>,
                response: Response<List<StockAndNewsModel>?>
            ) {
                if (response.isSuccessful) {
                    val resp = response.body()!!
                    result.value = resp
                } else {
                    Toast.makeText(context, "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<StockAndNewsModel>?>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
}

fun getCatalog(context: Context, result: MutableState<List<CatalogModel>?>) {

    val token = SharedPreference(context).readToken()

    ApiService.retrofit.getCatalog("Bearer $token").enqueue(object : Callback<List<CatalogModel>?> {
        override fun onResponse(
            call: Call<List<CatalogModel>?>,
            response: Response<List<CatalogModel>?>
        ) {
            if (response.isSuccessful) {
                val resp = response.body()!!
                result.value = resp
            } else {
                Toast.makeText(context, "Error ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<List<CatalogModel>?>, t: Throwable) {
            Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
        }
    })
}