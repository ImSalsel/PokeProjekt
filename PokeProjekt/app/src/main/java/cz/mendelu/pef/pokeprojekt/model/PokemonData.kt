package cz.mendelu.pef.pokeprojekt.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable


@JsonClass(generateAdapter = true)
data class PokemonData(
    @Json(name = "count")
    var count: Int? = null,

    @Json(name = "next")
    var next: String? = null,

    @Json(name = "previous")
    var previous: String? = null,

    @Json(name = "results")
    var results: List<PokemonResult>? = null
) : Serializable

@JsonClass(generateAdapter = true)
data class PokemonResult(
    @Json(name = "name")
    var name: String? = null,

    @Json(name = "url")
    var url: String? = null
) : Serializable
