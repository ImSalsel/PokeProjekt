package cz.mendelu.pef.pokeprojekt.di


import cz.mendelu.pef.pokeprojekt.communication.pokemon.PokemonAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun providePetsAPI(retrofit: Retrofit): PokemonAPI
        = retrofit.create(PokemonAPI::class.java)


}