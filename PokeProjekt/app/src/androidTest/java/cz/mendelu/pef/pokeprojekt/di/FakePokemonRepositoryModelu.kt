package cz.mendelu.pef.pokeprojekt.di

import cz.mendelu.pef.pokeprojekt.database.IPokemonRepository
import cz.mendelu.pef.pokeprojekt.fake.FakePokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class FakePokemonRepositoryModelu {

    @Binds
    abstract fun providePlacesRepository(service: FakePokemonRepository): IPokemonRepository

}