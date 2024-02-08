package cz.mendelu.pef.pokeprojekt.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Pokemon(
    @Json(name = "height")
    val height: Int,

    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String?,

    @Json(name = "sprites")
    val sprites: Sprites,

    @Json(name = "types")
    val types: List<Type>,

    @Json(name = "weight")
    val weight: Int,

    @Json(name = "moves")
    val moves: List<Move>
)








@JsonClass(generateAdapter = true)
data class Sprites(
    @Json(name = "back_default")
    val backDefault: String?,

    @Json(name = "front_default")
    val frontDefault: String?

)

@JsonClass(generateAdapter = true)
data class Type(
    @Json(name = "slot")
    val slot: Int,

    @Json(name = "type")
    val type: TypeName
)

@JsonClass(generateAdapter = true)
data class TypeName(
    @Json(name = "name")
    val name: String,

    @Json(name = "url")
    val url: String
)

@JsonClass(generateAdapter = true)
data class Move(
    @Json(name = "move")
    val move: MoveDetail
)

@JsonClass(generateAdapter = true)
data class MoveDetail(
    @Json(name = "name")
    val name: String,

    @Json(name = "url")
    val url: String
)
