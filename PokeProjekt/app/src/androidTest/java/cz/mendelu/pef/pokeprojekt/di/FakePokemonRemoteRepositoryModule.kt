package cz.mendelu.pef.pokeprojekt.di

import cz.mendelu.pef.pokeprojekt.communication.pokemon.IPokemonRemoteRepository
import cz.mendelu.pef.pokeprojekt.fake.FakePokemonRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RemoteRepositoryModule::class],
)
abstract class FakePokemonRemoteRepositoryModule {
    @Binds
    abstract fun providePokemonsRemoteRepository(service: FakePokemonRemoteRepositoryImpl): IPokemonRemoteRepository
}