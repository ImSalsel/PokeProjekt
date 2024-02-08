package cz.mendelu.pef.pokeprojekt.di


import cz.mendelu.pef.pokeprojekt.communication.pokemon.IPokemonRemoteRepository
import cz.mendelu.pef.pokeprojekt.communication.pokemon.PokemonAPI
import cz.mendelu.pef.pokeprojekt.communication.pokemon.PokemonRemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteRepositoryModule {

    @Provides
    @Singleton
    fun providePetsRemoteRepository(pokemonAPI: PokemonAPI): IPokemonRemoteRepository
        = PokemonRemoteRepositoryImpl(pokemonAPI)


}