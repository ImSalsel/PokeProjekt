package cz.mendelu.pef.pokeprojekt.ui.screens.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import android.location.Location
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapUiSettings
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.pokeprojekt.R
import cz.mendelu.pef.pokeprojekt.map.MarkerUtil
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import cz.mendelu.pef.pokeprojekt.model.UiState
import cz.mendelu.pef.pokeprojekt.ui.elements.BaseScreen
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.ListOfPokemonDestination
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.PokemonBattleScreenDestination

@com.ramcosta.composedestinations.annotation.Destination(start = true, route = "map")
@SuppressLint("MissingPermission")
@Composable
fun MapScreen(navigator: DestinationsNavigator) {

    val viewModel = hiltViewModel<MapViewModel>()
    val uiState: MutableState<UiState<Pokemon, MapErrors>> =
        rememberSaveable { mutableStateOf(UiState()) }

    viewModel.pokemonUIState.value.let {
        uiState.value = it
    }
    var hasCheckedFavoritePokemon by rememberSaveable { mutableStateOf(false) }
    val hasFavoritePokemon by viewModel.hasFavoritePokemon.observeAsState(initial = true)


    BaseScreen(
        topBarText = null,
        showLoading = uiState.value.loading,
        placeholderScreenContent = null,
        drawFullScreenContent = true,
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = { navigator.navigate(ListOfPokemonDestination()) },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp, start = 32.dp)
                        .testTag("ButtonToList")
                        .size(64.dp),
                    content = {
                        Image(
                            painter = painterResource(id = R.drawable.pokemon),
                            contentDescription = "Button Image",
                            modifier = Modifier.background(Color.Transparent)
                        )
                    }
                )
            }
        }
    ) {
        LaunchedEffect(Unit) {
            if (!hasCheckedFavoritePokemon) {
                viewModel.checkForFavoritePokemon()
                hasCheckedFavoritePokemon = true
            }
        }

        Log.d("StartViewModel", "has favoritePokemon:${hasFavoritePokemon}")
        LaunchedEffect(hasFavoritePokemon) {
            if (!hasFavoritePokemon) {
                navigator.navigate("start")
            }
        }
        MapScreenContent(
            paddingValues = it,
            navigator = navigator,
            uiState = uiState,
            viewModel = viewModel
        )
    }
}


@SuppressLint("MissingPermission", "PotentialBehaviorOverride")
@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun MapScreenContent(
    paddingValues: PaddingValues,
    navigator: DestinationsNavigator,
    uiState: MutableState<UiState<Pokemon, MapErrors>>,
    viewModel: MapViewModel
) {

    var currentLocationMarker by remember { mutableStateOf<Marker?>(null) }


    val context = LocalContext.current


    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    var location by remember { mutableStateOf<LatLng?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(49.195061, 16.606836), 18f)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                Log.d("MapScreen", "Location permission granted")
                fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
                    loc?.let {
                        location = LatLng(it.latitude, it.longitude)
                        Log.d("MapScreen", "Last known location: $location")
                    }
                }
                startLocationUpdates(context, fusedLocationClient) { loc: Location ->
                    location = LatLng(loc.latitude, loc.longitude)
                    Log.d("MapScreen", "Updated location: $location")
                }
            } else {
                Log.d("MapScreen", "Location permission denied")
            }
        }
    )

    LaunchedEffect(key1 = Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    location?.let {
        LaunchedEffect(it) {
            cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(it, 15f))
        }
    }


    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                rotationGesturesEnabled = true,
                scrollGesturesEnabled = false,
                zoomControlsEnabled = false,
                mapToolbarEnabled = false,
                zoomGesturesEnabled = false,
                tiltGesturesEnabled = false
            )
        )
    }



    Box(modifier = Modifier.fillMaxSize()) {
        location?.let {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("MapTag"),
                uiSettings = mapUiSettings,
                cameraPositionState = cameraPositionState
            ) {
                MapEffect { map ->
                    Log.d("MapScreen", "Pokemon Data MapEffect ${uiState.value.data}")
                    Log.d("MapScreen", "Pokemon Data MapEffect ${viewModel.wildPokemonList}")

                    currentLocationMarker = map.addMarker(
                        MarkerOptions().position(
                            LatLng(
                                location!!.latitude, location!!.longitude
                            )
                        ).icon(
                            BitmapDescriptorFactory.fromBitmap(
                                MarkerUtil.resizeMapIcons(
                                    context,
                                    R.drawable.person,
                                    200,
                                    200
                                )
                            )
                        ).zIndex(1F)
                    )


                    for (pokemon in viewModel.wildPokemonList) {

                        val marker = map.addMarker(
                            MarkerOptions()
                                .position(
                                    viewModel.generateRandomLocationNearby(
                                        location!!.latitude, location!!.longitude
                                    )
                                )
                                .icon(
                                    BitmapDescriptorFactory.fromBitmap(
                                        MarkerUtil.createBitmapMarker(
                                            context,
                                            pokemon
                                        )
                                    )
                                )
                                .title(pokemon.name)
                        )
                        if (marker != null) {
                            marker.tag = pokemon
                        }
                    }
                    map.setOnMarkerClickListener { marker ->
                        Log.d("MapScreenMarkerTag", "MapScreenContent: ${marker.tag}")
                        //
                        if (marker.tag != null) {
                            viewModel.savePokemonToDataStore(marker.tag as Pokemon)
                            navigator.navigate(PokemonBattleScreenDestination())
                            viewModel.wildPokemonList.remove(marker.tag)
                            marker.remove()
                        }
                        true
                    }
                }
            }
        }




        if (uiState.value.errors != null) {
            Text(
                text = uiState.value.errors.toString(),
                modifier = Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                color = Color.White,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}


@SuppressLint("MissingPermission")
fun startLocationUpdates(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onLocationReceived: (Location) -> Unit
) {

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.firstOrNull()?.let { location ->
                    onLocationReceived(location)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }
}