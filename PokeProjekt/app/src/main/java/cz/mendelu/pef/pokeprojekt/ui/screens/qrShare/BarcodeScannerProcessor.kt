package cz.mendelu.pef.pokeprojekt.ui.screens.qrShare

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions.ZoomCallback
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import cz.mendelu.pef.pokeprojekt.datastore.IDataStoreRepository
import cz.mendelu.pef.pokeprojekt.ui.activities.MainActivity
import cz.mendelu.pef.pokeprojekt.ui.screens.qrShare.GraphicOverlay
import cz.mendelu.pef.pokeprojekt.ui.screens.qrShare.VisionProcessorBase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Barcode Detector Demo. */
class BarcodeScannerProcessor @Inject constructor(
    context: Context,
    zoomCallback: ZoomCallback?,
    private val pokemonDataStore: IDataStoreRepository
) : VisionProcessorBase<List<Barcode>>(context) {

    val context = context
    private var barcodeScanner: BarcodeScanner

    init {
        // Note that if you know which format of barcode your app is dealing with, detection will be
        // faster to specify the supported barcode formats one by one, e.g.
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build();
        barcodeScanner =
            if (zoomCallback != null) {
                val options =
                    BarcodeScannerOptions.Builder()
                        .setZoomSuggestionOptions(
                            ZoomSuggestionOptions.Builder(zoomCallback).build()
                        )
                        .build()
                BarcodeScanning.getClient(options)
            } else {
                BarcodeScanning.getClient()
            }
    }

    override fun stop() {
        super.stop()
        barcodeScanner.close()
    }

    override fun detectInImage(image: InputImage): Task<List<Barcode>> {
        return barcodeScanner.process(image)
    }

    override fun onSuccess(barcodes: List<Barcode>, graphicOverlay: GraphicOverlay) {
        if (barcodes.isEmpty()) {
            Log.v(MANUAL_TESTING_LOG, "No barcode has been detected")
        }
        for (i in barcodes.indices) {
            val barcode = barcodes[i]
            graphicOverlay.add(BarcodeGraphic(graphicOverlay, barcode))
            logExtrasForTesting(barcode)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    barcode.rawValue?.let { pokemonDataStore.setSharedPokemon(it) }
                } catch (e: Exception) {
                    Log.e(TAG, "Error saving barcode value to DataStore", e)
                }
            }


            context.startActivity(
                Intent(
                    context,
                    MainActivity::class.java
                )
            )
        }
    }

    override fun onFailure(e: Exception) {
        Log.e(TAG, "Barcode detection failed $e")
    }

    companion object {
        private const val TAG = "BarcodeProcessor"

        private fun logExtrasForTesting(barcode: Barcode?) {
            if (barcode != null) {
                Log.v(
                    MANUAL_TESTING_LOG,
                    String.format(
                        "Detected barcode's bounding box: %s",
                        barcode.boundingBox!!.flattenToString()
                    )
                )
                Log.v(
                    MANUAL_TESTING_LOG,
                    String.format(
                        "Expected corner point size is 4, get %d",
                        barcode.cornerPoints!!.size
                    )
                )
                for (point in barcode.cornerPoints!!) {
                    Log.v(
                        MANUAL_TESTING_LOG,
                        String.format(
                            "Corner point is located at: x = %d, y = %d",
                            point.x,
                            point.y
                        )
                    )
                }


            }
        }
    }
}