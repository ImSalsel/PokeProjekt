package cz.mendelu.pef.pokeprojekt.mock

import cz.mendelu.pef.pokeprojekt.model.FlavorTextEntry
import cz.mendelu.pef.pokeprojekt.model.Genera
import cz.mendelu.pef.pokeprojekt.model.Language
import cz.mendelu.pef.pokeprojekt.model.Move
import cz.mendelu.pef.pokeprojekt.model.MoveDetail
import cz.mendelu.pef.pokeprojekt.model.MoveDetailApi
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import cz.mendelu.pef.pokeprojekt.model.PokemonSpecies
import cz.mendelu.pef.pokeprojekt.model.Sprites
import cz.mendelu.pef.pokeprojekt.model.Type
import cz.mendelu.pef.pokeprojekt.model.TypeName

object ServerMock {


    val ditto = Pokemon(
        height = 3,
        id = 132,
        name = "ditto",
        sprites = Sprites(
            backDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png",
            frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"
        ),
        types = listOf(
            Type(
                slot = 1,
                type = TypeName(
                    name = "normal",
                    url = "https://pokeapi.co/api/v2/type/1/"
                )
            )
        ),
        weight = 40,
        moves = listOf(
            Move(
                move = MoveDetail(
                    name = "cut",
                    url = "https://pokeapi.co/api/v2/move/15/"
                )
            ),
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            ),
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            ), Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            )
        )
    )

    val ditto2 = Pokemon(
        height = 3,
        id = 132,
        name = "ditto",
        sprites = Sprites(
            backDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png",
            frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"
        ),
        types = listOf(
            Type(
                slot = 1,
                type = TypeName(
                    name = "normal",
                    url = "https://pokeapi.co/api/v2/type/1/"
                )
            )
        ),
        weight = 40,
        moves = listOf(
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/15/"
                )
            )
        )
    )

    val ditto3 = Pokemon(
        height = 3,
        id = 132,
        name = "pes",
        sprites = Sprites(
            backDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png",
            frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"
        ),
        types = listOf(
            Type(
                slot = 1,
                type = TypeName(
                    name = "normal",
                    url = "https://pokeapi.co/api/v2/move/15/"
                )
            )
        ),
        weight = 40,
        moves = listOf(
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/15/"
                )
            )
        )
    )

    val ditto4 = Pokemon(
        height = 3,
        id = 132,
        name = "ditto4",
        sprites = Sprites(
            backDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png",
            frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"
        ),
        types = listOf(
            Type(
                slot = 1,
                type = TypeName(
                    name = "normal",
                    url = "https://pokeapi.co/api/v2/type/1/"
                )
            )
        ),
        weight = 40,
        moves = listOf(
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/15/"
                )
            ),
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            ),
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            ), Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/15/"
                )
            )
        )
    )

    val ditto6 = Pokemon(
        height = 3,
        id = 132,
        name = "ditto6",
        sprites = Sprites(
            backDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png",
            frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"
        ),
        types = listOf(
            Type(
                slot = 1,
                type = TypeName(
                    name = "normal",
                    url = "https://pokeapi.co/api/v2/type/1/"
                )
            )
        ),
        weight = 40,
        moves = listOf(
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/15/"
                )
            ),
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            ),
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            ), Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            )
        )
    )

    val ditto5 = Pokemon(
        height = 3,
        id = 132,
        name = "ditto5",
        sprites = Sprites(
            backDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png",
            frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"
        ),
        types = listOf(
            Type(
                slot = 1,
                type = TypeName(
                    name = "normal",
                    url = "https://pokeapi.co/api/v2/type/1/"
                )
            )
        ),
        weight = 40,
        moves = listOf(
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            ),
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            ),
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            ), Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            )
        )
    )
    val ditto7 = Pokemon(
        height = 7,
        id = 132,
        name = "ditto4",
        sprites = Sprites(
            backDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png",
            frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"
        ),
        types = listOf(
            Type(
                slot = 1,
                type = TypeName(
                    name = "normal",
                    url = "https://pokeapi.co/api/v2/type/1/"
                )
            )
        ),
        weight = 40,
        moves = listOf(
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            ),
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            ),
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            ), Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            )
        )
    )
    val ditto8 = Pokemon(
        height = 7,
        id = 132,
        name = "ditto8",
        sprites = Sprites(
            backDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/132.png",
            frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"
        ),
        types = listOf(
            Type(
                slot = 1,
                type = TypeName(
                    name = "normal",
                    url = "https://pokeapi.co/api/v2/type/1/"
                )
            )
        ),
        weight = 40,
        moves = listOf(
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            ),
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            ),
            Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            ), Move(
                move = MoveDetail(
                    name = "transform",
                    url = "https://pokeapi.co/api/v2/move/144/"
                )
            )
        )
    )

    val moveDetailApi =
        MoveDetailApi(name = "ohen", accuracy = 100, power = 100, flavorTextEntries = null)

    val pokemonSpecies =
        PokemonSpecies(
            genera = listOf(Genera("tohle je", language = Language("en"))),
            isLegendary = false,
            isMythical = false,
            flavorTextEntries = listOf(
                FlavorTextEntry("ma nohy", Language("en"))
            )
        )


    val starters = listOf(ditto, ditto2, ditto3, ditto4, ditto6, ditto5, ditto7, ditto8)
}