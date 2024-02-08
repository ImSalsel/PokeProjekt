package cz.mendelu.pef.pokeprojekt.di

import cz.mendelu.pef.pokeprojekt.database.IPokemonRepository
import cz.mendelu.pef.pokeprojekt.database.PokemonRepositoryImpl
import cz.mendelu.pef.pokeprojekt.database.PokemonsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalPlacesRepository(dao: PokemonsDao): IPokemonRepository {
        return PokemonRepositoryImpl(dao)
    }

}
