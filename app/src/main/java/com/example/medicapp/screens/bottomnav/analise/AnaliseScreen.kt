package com.example.medicapp.screens.bottomnav.analise

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medicapp.R
import com.example.medicapp.api.ApiService
import com.example.medicapp.graphs.Graph
import com.example.medicapp.models.CatalogModel
import com.example.medicapp.models.StockAndNewsModel
import com.example.medicapp.navigation.AnaliseScreenSealed
import com.example.medicapp.screens.bottomnav.analise.uiitems.CatalogItem
import com.example.medicapp.screens.bottomnav.analise.uiitems.StockAndNewsItem
import com.example.medicapp.storage.DbHandlerAnalise
import com.example.medicapp.storage.SharedPreference
import com.example.medicapp.ui.theme.*
import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnaliseScreen(navController: NavController) {

    val context = LocalContext.current
    val db = DbHandlerAnalise(context)
    val items = db.getItems()
    val getNews = remember { mutableStateOf<List<StockAndNewsModel>?>(null) }
    val getCatalog = remember { mutableStateOf<List<CatalogModel>?>(null) }
    getNews(context = context, result = getNews)
    getCatalog(context = context, result = getCatalog)
    val modalSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val price = remember { mutableStateOf(0) }
    val selectedItem = remember { mutableStateOf<CatalogModel?>(null) }

    if (getCatalog.value != null) {
        ModalBottomSheetLayout(
            sheetContent = {
                BottomSheetContent(
                    item = if (selectedItem.value != null) selectedItem.value!! else getCatalog.value!![0],
                    scope = scope,
                    state = modalSheetState
                )
            },
            sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            sheetState = modalSheetState,
            sheetBackgroundColor = Color.White,
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(horizontal = 20.dp)
                    .padding(top = 40.dp)
            ) {

                Search(navController = navController)

                Spacer(modifier = Modifier.height(32.dp))

                StockAndNews(getNews = getNews)

                Spacer(modifier = Modifier.height(32.dp))

                Catalog(
                    getCatalog = getCatalog,
                    scope = scope,
                    modalSheetState = modalSheetState,
                    selectedItem = selectedItem,
                    price = price
                )
            }

            if(price.value == 0) {
                items.forEach {
                    price.value += it.price.toInt()
                }
            }
            else {
                ShoppingCart(
                    prices = price,
                    navController = navController,
                )
            }
        }
    }
}

@Composable
fun Search(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = BackgroundTextField)
            .border(width = 1.dp, color = BorderColorTextField)
            .clickable {
                navController.navigate(Graph.ANALISE_GRAPH)
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Искать анализы",
                fontFamily = LatoRegular,
                fontSize = 16.sp,
                color = GrayTextOnBoarding
            )
        }
    }
}

@Composable
fun StockAndNews(getNews: MutableState<List<StockAndNewsModel>?>) {
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

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun Catalog(
    getCatalog: MutableState<List<CatalogModel>?>,
    scope: CoroutineScope,
    modalSheetState: ModalBottomSheetState,
    selectedItem: MutableState<CatalogModel?>,
    price: MutableState<Int>
) {
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
        modifier = Modifier
            .fillMaxSize()
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = contentPadding)
                .padding(bottom = 16.dp)
        ) {
            Tabs(categories = categories, pagerState = pagerState)
            Spacer(modifier = Modifier.height(24.dp))
            TabsContent(
                tabs = categories,
                response = getCatalog,
                pagerState = pagerState,
                scope = scope,
                modalSheetState = modalSheetState,
                selectedItem = selectedItem,
                price = price
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(
    categories: List<CatalogModel>,
    pagerState: PagerState
) {

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

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun TabsContent(
    tabs: List<CatalogModel>,
    response: MutableState<List<CatalogModel>?>,
    pagerState: PagerState,
    scope: CoroutineScope,
    modalSheetState: ModalBottomSheetState,
    selectedItem: MutableState<CatalogModel?>,
    price: MutableState<Int>,
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
                CatalogItem(
                    item = list[index],
                    scope = scope,
                    modalSheetState = modalSheetState,
                    selectedItem = selectedItem,
                    price = price
                )
            }
        }
    }
}

@Composable
fun ShoppingCart(
    prices: MutableState<Int>,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp),
            backgroundColor = Color.White,
            border = BorderStroke(1.dp, color = StrokeItemColor)
        ) {
            Button(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 24.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ButtonEnabledColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                onClick = { navController.navigate(AnaliseScreenSealed.ShoppingCardScreen.route) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(modifier = Modifier.fillMaxWidth(0.5f)) {
                        Image(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.shop_icon),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "В корзину",
                            fontSize = 17.sp,
                            fontFamily = LatoRegular,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "${prices.value} ₽",
                        fontSize = 17.sp,
                        fontFamily = LatoRegular,
                        fontWeight = FontWeight.Bold
                    )
                }
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