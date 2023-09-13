package com.example.cryptoapplication.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cryptoapplication.data.repository.CoinRepositoryImp
import com.example.cryptoapplication.domain.GetCoinInfoListUseCase
import com.example.cryptoapplication.domain.GetCoinInfoUseCase
import com.example.cryptoapplication.domain.LoadDataUseCase

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImp(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()
    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    init {
            loadDataUseCase()
    }

}