package com.bvbsaha.fitnesskursach.model.repository

import androidx.lifecycle.LiveData
import com.bvbsaha.fitnesskursach.domain.PlayerModel

interface LeaderBoardRepo {

    fun addScoreToLeaderBoardList(player: PlayerModel)

    fun getLeaderBoardList(): LiveData<List<PlayerModel>>

}