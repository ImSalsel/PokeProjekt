package cz.mendelu.pef.pokeprojekt.ui.screens.pokemonDetail

import cz.mendelu.pef.pokeprojekt.database.PokemonRoom


class PokemonDetailData {
    var pokemon: PokemonRoom = PokemonRoom(
        id = null,
        pokemonId = null,
        name = null,
        height = null,
        weight = null,
        moves = null,
        frontDefaultSprite = null,
        types = null,
        hp = 0,
        level = 0,
        xp = 0f
    )
    var loading: Boolean = true
    var itemTextError: Int? = null


}
