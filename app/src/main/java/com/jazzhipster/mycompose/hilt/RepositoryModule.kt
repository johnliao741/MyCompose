package com.jazzhipster.mycompose.hilt

import com.jazzhipster.mycompose.remote.repository.HomeArticleRepository
import com.jazzhipster.mycompose.remote.service.HomePageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    fun provideHomeArticleRepository(homePageService: HomePageService): HomeArticleRepository =
        HomeArticleRepository(homePageService)
}