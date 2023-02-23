package com.bvbsaha.fitnesskursach.model.repository

import androidx.lifecycle.LiveData

interface LeaderBoardRepo {

    fun addScoreToLeaderBoardList(player: PlayerModel)

    fun getLeaderBoardList(): LiveData<List<PlayerModel>>

}