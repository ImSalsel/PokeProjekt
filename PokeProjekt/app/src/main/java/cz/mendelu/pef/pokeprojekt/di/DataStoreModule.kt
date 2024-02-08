package cz.mendelu.pef.pokeprojekt.di

import android.content.Context
import cz.mendelu.pef.pokeprojekt.datastore.DataStoreRepositoryImpl
import cz.mendelu.pef.pokeprojekt.datastore.IDataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePokemonDataStore(@ApplicationContext context: Context): IDataStoreRepository {
        return DataStoreRepositoryImpl(context)
    }

}
