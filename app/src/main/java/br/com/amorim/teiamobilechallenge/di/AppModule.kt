package br.com.amorim.teiamobilechallenge.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import br.com.amorim.teiamobilechallenge.feature_nickname.data.data_source.local.NicknameDatabase
import br.com.amorim.teiamobilechallenge.feature_nickname.data.repository.NicknameRepositoryImpl
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.repository.NicknameRepository
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.use_case.AddNickName
import br.com.amorim.teiamobilechallenge.feature_nickname.domain.use_case.NicknameUseCases
import br.com.amorim.teiamobilechallenge.feature_posts.data.local.PostDatabase
import br.com.amorim.teiamobilechallenge.feature_posts.data.local.PostEntity
import br.com.amorim.teiamobilechallenge.feature_posts.data.remote.PostApi
import br.com.amorim.teiamobilechallenge.feature_posts.data.remote.PostRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerPostDatabase(app: Application): PostDatabase {
        return Room.databaseBuilder(
            app,
            PostDatabase::class.java,
            PostDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePostApi(): PostApi {
        return Retrofit.Builder()
            .baseUrl(PostApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PostApi::class.java)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providePostPager(postDb: PostDatabase, postApi: PostApi): Pager<Int, PostEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PostRemoteMediator(
                postDb = postDb,
                postApi = postApi
            ),
            pagingSourceFactory = { postDb.postDao.paggingSource() }
        )
    }

    @Provides
    @Singleton
    fun provideNicknameDatabase(app: Application): NicknameDatabase {
        return Room.databaseBuilder(
            app,
            NicknameDatabase::class.java,
            NicknameDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNicknameRepository(db: NicknameDatabase): NicknameRepository {
        return NicknameRepositoryImpl(db.nicknameDao)
    }

    @Provides
    @Singleton
    fun provideNicknameUseCases(repository: NicknameRepository): NicknameUseCases {
        return NicknameUseCases(
            addNickName = AddNickName(repository),
        )
    }
}
