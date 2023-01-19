package com.bitflyer.testapp.di

import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.data.userlist.UserListRepositoryImpl
import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.domain.userlist.UserListRepository
import com.bitflyer.testapp.ui.BaseMapper
import com.bitflyer.testapp.ui.userlist.mapper.UserListModelMapper
import com.bitflyer.testapp.ui.userlist.model.UserListModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
abstract class MapperModule {
    @Binds
    abstract fun provideUserListModelMapper(mapper: UserListModelMapper): BaseMapper<UserBrief, UserListModel>

}

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideUserListRepo(userListRepository: UserListRepositoryImpl): UserListRepository
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    fun provideGithubApi(retrofit: Retrofit) = GithubNetworkApi.create(retrofit)
}