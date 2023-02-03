@file:Suppress("unused")

package com.bitflyer.testapp.di

import android.content.Context
import androidx.room.Room
import com.bitflyer.testapp.data.local.AppDatabase
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.data.userdetails.UserDetailsRepositoryImpl
import com.bitflyer.testapp.data.userdetails.dto.UserDetails
import com.bitflyer.testapp.data.userlist.UserListRepositoryImpl
import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.domain.repository.UserDetailsRepository
import com.bitflyer.testapp.domain.repository.UserListRepository
import com.bitflyer.testapp.data.local.UserBriefEntity
import com.bitflyer.testapp.data.local.UserBriefToUserBriefEntityMapper
import com.bitflyer.testapp.ui.BaseMapper
import com.bitflyer.testapp.ui.userdetails.mapper.UserDetailsToUserDetailsModelMapper
import com.bitflyer.testapp.ui.userdetails.model.UserDetailsModel
import com.bitflyer.testapp.ui.userlist.mapper.UserBriefEntityToUserBriefModelMapper
import com.bitflyer.testapp.ui.userlist.model.UserBriefModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class MapperModule {
    @Binds
    abstract fun provideUserListModelMapper(mapper: UserBriefEntityToUserBriefModelMapper): BaseMapper<UserBriefEntity, UserBriefModel>

    @Binds
    abstract fun provideUserListEntityMapper(mapper: UserBriefToUserBriefEntityMapper): BaseMapper<UserBrief, UserBriefEntity>

    @Binds
    abstract fun provideUserDetailsModelMapper(
        mapper: UserDetailsToUserDetailsModelMapper
    ): BaseMapper<UserDetails, UserDetailsModel>
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideUserListRepo(userListRepository: UserListRepositoryImpl): UserListRepository

    @Binds
    abstract fun provideUserDetailsRepo(userDetailsRepository: UserDetailsRepositoryImpl): UserDetailsRepository
}


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Suppress("JSON_FORMAT_REDUNDANT")
    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideRetrofitClient(): Retrofit {
        val converterFactory = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }.asConverterFactory("application/json".toMediaType())
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun provideGithubApi(retrofit: Retrofit): GithubNetworkApi = GithubNetworkApi.create(retrofit)
}

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext applicationContext: Context) = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "app_database"
    ).build()

    @Provides
    fun provideUserDao(db: AppDatabase) = db.userListDao()
}