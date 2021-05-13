package com.example.githubprofilesearch.di

import com.example.githubprofilesearch.repository.ISearchRepository
import com.example.githubprofilesearch.repository.ProfileSearchRepository
import com.example.githubprofilesearch.services.GithubServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideGithubSearchRepository(
        githubServices: GithubServices
    ) = ProfileSearchRepository(githubServices) as ISearchRepository
}