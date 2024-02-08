package cz.mendelu.pef.pokeprojekt.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoveDetailApi(
    val name: String?,
    val accuracy: Int?,
    val power: Int?,
    @Json(name = "flavor_text_entries")
    private val flavorTextEntries: List<FlavorTextEntry>?
) {
    val englishFlavorText: String
        get() = flavorTextEntries
            ?.firstOrNull { it.language?.name == "en" }
            ?.flavorText.orEmpty()
            .replace("\n", " ")
            .replace("\u000c", " ")
}

@JsonClass(generateAdapter = true)
data class FlavorTextEntry(
    @Json(name = "flavor_text")
    val flavorText: String?,
    val language: Language?
)

@JsonClass(generateAdapter = true)
data class Language(
    val name: String?
)
