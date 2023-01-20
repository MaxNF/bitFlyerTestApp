package com.bitflyer.testapp.di

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.bitflyer.testapp.data.local.AppDatabase
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.data.userlist.UserListPagingSource
import com.bitflyer.testapp.data.userlist.UserListRepositoryImpl
import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.domain.userlist.entity.UserBriefEntity
import com.bitflyer.testapp.domain.userlist.UserListRepository
import com.bitflyer.testapp.domain.userlist.mapper.UserBriefToUserBriefEntityMapper
import com.bitflyer.testapp.ui.BaseMapper
import com.bitflyer.testapp.ui.userlist.mapper.UserBriefEntityToUserBriefModelMapper
import com.bitflyer.testapp.ui.userlist.model.UserBriefModel
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
    abstract fun provideUserListModelMapper(mapper: UserBriefEntityToUserBriefModelMapper): BaseMapper<UserBriefEntity, UserBriefModel>

    @Binds
    abstract fun provideUserListEntityMapper(mapper: UserBriefToUserBriefEntityMapper): BaseMapper<UserBrief, UserBriefEntity>
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

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideAppDatabase(applicationContext: Context) = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "app_database"
    ).build()

    @Provides
    fun provideUserDao(db: AppDatabase) = db.userListDao()
}