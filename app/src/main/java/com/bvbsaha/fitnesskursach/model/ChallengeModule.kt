package com.bvbsaha.fitnesskursach.model

import com.bvbsaha.fitnesskursach.model.repository.LeaderBoardFirebaseRepoImpl
import com.bvbsaha.fitnesskursach.model.repository.LeaderBoardRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class ChallengeModule {

    @Binds
    abstract fun getLeaderBoardSource(repo: LeaderBoardFirebaseRepoImpl): LeaderBoardRepo
}