package com.example.narutoapp.screens.narutodetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.narutoapp.data.remote.responses.Character
import com.example.narutoapp.data.remote.responses.Debut
import com.example.narutoapp.data.remote.responses.Family
import com.example.narutoapp.screens.AppScreen
import com.example.narutoapp.ui.theme.LightBlue
import com.example.narutoapp.util.Resourse
import com.example.narutoapp.util.parseTypeToColor
import java.util.Locale

@Composable
fun NarutoDetailScreen(
    characterId: String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    viewModel: NarutoDetailViewModel = hiltViewModel()
) {
    val characterInfo = produceState<Resourse<Character>>(initialValue = Resourse.Loading()) {
        value = viewModel.getCharacterInfo(characterId)
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
            .padding(16.dp)
    ) {
        Column() {
            TopSection(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f)
            )
            Box(modifier = Modifier.weight(1f)) {
                CharacterDetailStateWrapper(
                    characterInfo = characterInfo,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                        .shadow(10.dp, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp),
                    loadingModifier = Modifier
                        .size(100.dp)
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                )
            }
        }
    }
}

@Composable
fun CharacterDetailStateWrapper(
    characterInfo: Resourse<Character>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (characterInfo) {
        is Resourse.Error -> {
            Text(
                text = characterInfo.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }

        is Resourse.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = loadingModifier
            )
        }

        is Resourse.Success -> {
            CharacterDetailSection(characterInfo = characterInfo.data!!)
        }
    }
}

@Composable
fun TopSection(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.navigate(AppScreen.ListScreen.route)
                }
        )
    }
}

@Composable
fun CharacterDetailSection(
    characterInfo: Character,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        characterInfo.images.firstOrNull()?.let {
            CharacterImage(
                imageUrl = it,
                contentDescription = characterInfo.name
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = characterInfo.name,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 26.sp
        )
        Spacer(modifier = Modifier.height(15.dp))
        if (characterInfo.debut != null) {
            CharacterDebutSection(debut = characterInfo.debut)
        }
        Spacer(modifier = Modifier.height(15.dp))
        if (characterInfo.natureType != null) {
            CharacterTypeSection(types = characterInfo.natureType)
        }
        Spacer(modifier = Modifier.height(15.dp))
        if (characterInfo.family != null) {
            CharacterFamilySection(family = characterInfo.family, name = characterInfo.name)
        }
        Spacer(modifier = Modifier.height(15.dp))
        if (characterInfo.jutsu != null) {
            CharacterJutsuSection(jutsu = characterInfo.jutsu)
        }
    }
}

@Composable
fun CharacterImage(
    imageUrl: String,
    contentDescription: String?
) {
    AsyncImage(model = imageUrl, contentDescription = contentDescription)
}

@Composable
fun CharacterTypeSection(types: List<String>) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold,
        text = "Natures Type:",
        fontSize = 20.sp
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .height(35.dp)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = type.capitalize(Locale.ROOT),
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun CharacterDebutSection(debut: Debut) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        text = "Debut:",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        if (debut.manga != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Manga: ${debut.manga}",
                fontSize = 18.sp
            )
        }
        if (debut.anime != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Anime: ${debut.anime}",
                fontSize = 18.sp
            )
        }
        if (debut.game != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Game: ${debut.game}",
                fontSize = 18.sp
            )
        }
        if (debut.movie != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Movie: ${debut.movie}",
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun CharacterFamilySection(family: Family, name: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        text = "$name family:",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        if (family.father != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Father: ${family.father}",
                fontSize = 18.sp
            )
        }
        if (family.brother != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Brother: ${family.brother}",
                fontSize = 18.sp
            )
        }
        if (family.husband != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Husband: ${family.husband}",
                fontSize = 18.sp
            )
        }
        if (family.cousin != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Cousin: ${family.cousin}",
                fontSize = 18.sp
            )
        }
        if (family.son != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Son: ${family.son}",
                fontSize = 18.sp
            )
        }
        if (family.adoptive_brother != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Adoptive brother: ${family.adoptive_brother}",
                fontSize = 18.sp
            )
        }
        if (family.adoptive_father != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Adoptive father: ${family.adoptive_father}",
                fontSize = 18.sp
            )
        }
        if (family.depowered_form != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Depowered form: ${family.depowered_form}",
                fontSize = 18.sp
            )
        }
        if (family.nephew != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Nephew: ${family.nephew}",
                fontSize = 18.sp
            )
        }
        if (family.`incarnation with the god tree` != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Incarnation with the god tree: ${family.`incarnation with the god tree`}",
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun CharacterJutsuSection(jutsu: List<String>) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        text = "Jutsu:",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        for (i in jutsu) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "№${jutsu.indexOf(i)} $i",
                fontSize = 18.sp
            )
        }
    }
}