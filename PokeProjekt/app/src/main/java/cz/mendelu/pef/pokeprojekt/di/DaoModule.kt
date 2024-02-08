package cz.mendelu.pef.pokeprojekt.di

import cz.mendelu.pef.pokeprojekt.database.PokemonsDao
import cz.mendelu.pef.pokeprojekt.database.PokemonsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideDao(database: PokemonsDatabase): PokemonsDao {
        return database.pokemonsDao()
    }

}