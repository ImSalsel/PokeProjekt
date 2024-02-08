package cz.mendelu.pef.pokeprojekt.di

import android.content.Context
import cz.mendelu.pef.pokeprojekt.PokeProjektApplication
import cz.mendelu.pef.pokeprojekt.database.PokemonsDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(): PokemonsDatabase =
        PokemonsDatabase.getDatabase(PokeProjektApplication.appContext)

}