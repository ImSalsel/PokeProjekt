package cz.mendelu.pef.pokeprojekt.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonSpecies(
    @Json(name = "genera") val genera: List<Genera>,
    @Json(name = "is_legendary") val isLegendary: Boolean,
    @Json(name = "is_mythical") val isMythical: Boolean,
    @Json(name = "flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>
) {
    val englishGenera: String
        get() = genera.firstOrNull { it.language.name == "en" }?.genus.orEmpty()

    val englishFlavorText: String
        get() = flavorTextEntries.firstOrNull { it.language?.name == "en" }?.flavorText.orEmpty()
            .replace("\n", " ")
            .replace("\u000c", " ")
}

@JsonClass(generateAdapter = true)
data class Genera(
    @Json(name = "genus") val genus: String,
    @Json(name = "language") val language: Language
)


