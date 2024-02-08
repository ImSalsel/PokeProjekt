package cz.mendelu.pef.pokeprojekt.ui.screens.qrShare

import com.google.mlkit.vision.text.Text
import java.lang.Exception

interface OnTextFoundListener {

    fun onTextFound(text: Text)
    fun onFailure(exception: Exception)


}