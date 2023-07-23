package com.bitflyer.testapp.di

import com.bitflyer.testapp.data.userdetails.UserDetailsRepositoryImplTest
import com.bitflyer.testapp.data.userlist.UserListRepositoryImplTest
import com.bitflyer.testapp.domain.repository.UserDetailsRepository
import com.bitflyer.testapp.domain.repository.UserListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn


@Module
@TestInstallIn(components = [ViewModelComponent::class], replaces = [RepositoryModule::class])
abstract class TestRepositoryModule {
    @Binds
    abstract fun provideUserListRepo(userListRepository: UserListRepositoryImplTest): UserListRepository

    @Binds
    abstract fun provideUserDetailsRepo(userDetailsRepository: UserDetailsRepositoryImplTest): UserDetailsRepository
}