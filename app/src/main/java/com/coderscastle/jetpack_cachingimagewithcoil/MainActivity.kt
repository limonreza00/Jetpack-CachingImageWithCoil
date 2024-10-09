package com.coderscastle.jetpack_cachingimagewithcoil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import com.coderscastle.jetpack_cachingimagewithcoil.ui.theme.JetpackCachingImageWithCoilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackCachingImageWithCoilTheme {


                LoadImg()

            }
        }
    }
}


@Composable
fun LoadImg (){

    val imageUrl = listOf<MyHeroDetails>(
        MyHeroDetails("Wolverine","https://i.pinimg.com/736x/f3/53/ca/f353ca6dc3a99679a1ea6e3c06ebf8a2.jpg"),
        MyHeroDetails("Captain America","https://seeklogo.com/images/C/Captain_America-logo-8F6417E732-seeklogo.com.png"),
        MyHeroDetails("Superman","https://seeklogo.com/images/S/superman-logo-E555F48FD9-seeklogo.com.png"),
        MyHeroDetails("Black Panther","https://seeklogo.com/images/B/black-panther-logo-7F7DE09CE3-seeklogo.com.png"),
        MyHeroDetails("Hulk","https://londondecal.com/cdn/shop/products/Comiccon_Decals_Square_for_Shopify-48.png?v=1541578692&width=960"),
        MyHeroDetails("Thor","https://londondecal.com/cdn/shop/products/Comiccon_Decals_Square_for_Shopify-27.png?v=1541578710&width=960"),
        MyHeroDetails("Deadpool","https://londondecal.com/cdn/shop/products/Comiccon_Decals_Square_for_Shopify-28.png?v=1541516216&width=960"),
        MyHeroDetails("Wolverine","https://i.pinimg.com/736x/f3/53/ca/f353ca6dc3a99679a1ea6e3c06ebf8a2.jpg"),
        MyHeroDetails("Captain America","https://seeklogo.com/images/C/Captain_America-logo-8F6417E732-seeklogo.com.png"),
        MyHeroDetails("Superman","https://seeklogo.com/images/S/superman-logo-E555F48FD9-seeklogo.com.png"),



    )

    MyImgGrid(imageUrl)
}




@Composable
fun MyImgGrid (imageUrls : List<MyHeroDetails>){

    Text(
        text = "Loading Image With Coil",
        modifier = Modifier
            .padding(top =70.dp,start =100.dp),
        fontWeight = FontWeight.Bold)

    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp , start = 10.dp , end = 10.dp),

        content = {

            items(imageUrls){ imageUrl->
                ImageCardDesign(imageUrl.imgUrl,imageUrl.name)
            }}
    )

}


@Composable
fun ImageCardDesign(imgUrl : String ,name: String){

    Card (
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize(),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(10.dp)
    ){
        Column (
            modifier = Modifier
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val model= ImageRequest
                .Builder(LocalContext.current)
                .data(imgUrl)
                .crossfade(1000)
                .size(Size.ORIGINAL)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .build()

            val imgState = rememberAsyncImagePainter(model).state

            when(imgState){
                is AsyncImagePainter.State.Loading ->{
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp)
                    )
                }

                is AsyncImagePainter.State.Empty -> {Text(text = "Empty${imgState.javaClass.simpleName}")}
                is AsyncImagePainter.State.Error -> {Text(text = "Error is ${imgState.javaClass.simpleName}")}
                is AsyncImagePainter.State.Success -> {
                    Image(
                        painter = rememberAsyncImagePainter(model),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 10.dp,top=2.dp,bottom=2.dp)
                            .size(150.dp)
                            .clip(CircleShape)

                    )
                }
            }

            Text(text = "I am $name ")
        }

    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    JetpackCachingImageWithCoilTheme {
        LoadImg()
    }
}

data class MyHeroDetails (
    val name : String,
    val imgUrl : String
)

