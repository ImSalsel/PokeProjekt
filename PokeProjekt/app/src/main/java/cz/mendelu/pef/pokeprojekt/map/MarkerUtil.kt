package cz.mendelu.pef.pokeprojekt.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import cz.mendelu.pef.pokeprojekt.R
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarkerUtil(private val context: Context) {

    companion object {
        private const val DEFAULT_MARKER_SIZE = 300

        suspend fun createBitmapMarker(context: Context, pokemon: Pokemon): Bitmap {
            val imageLoader = ImageLoader.Builder(context)
                .crossfade(true)
                .build()

            return getImageBitmap(imageLoader, pokemon.sprites.frontDefault ?: "", context)
                ?: getDefaultMarkerBitmap()
        }


        private suspend fun getImageBitmap(
            imageLoader: ImageLoader,
            imageUrl: String,
            context: Context
        ): Bitmap? {
            return withContext(Dispatchers.IO) {
                val request = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .size(DEFAULT_MARKER_SIZE, DEFAULT_MARKER_SIZE)
                    .build()

                val result = (imageLoader.execute(request) as? SuccessResult)
                result?.drawable?.toBitmap()
            }
        }

        private fun getDefaultMarkerBitmap(): Bitmap {
            // Replace with your default marker bitmap creation logic
            // This is a placeholder; you can create a default marker or use a placeholder image
            // Example:
            // val defaultDrawable = ContextCompat.getDrawable(context, R.drawable.ic_marker)
            // return defaultDrawable?.toBitmap(DEFAULT_MARKER_SIZE, DEFAULT_MARKER_SIZE) ?: throw IllegalArgumentException("Default marker not found")
            return Bitmap.createBitmap(
                DEFAULT_MARKER_SIZE,
                DEFAULT_MARKER_SIZE,
                Bitmap.Config.ARGB_8888
            )
        }

        fun resizeMapIcons(context: Context, iconId: Int, width: Int, height: Int): Bitmap {
            val drawable = ContextCompat.getDrawable(context, iconId) ?: return Bitmap.createBitmap(0, 0, Bitmap.Config.ARGB_8888)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, width, height)
            //DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.your_color))  // Optional: if you want to change the color of the drawable
            drawable.draw(canvas)
            return bitmap
        }
    }


}
