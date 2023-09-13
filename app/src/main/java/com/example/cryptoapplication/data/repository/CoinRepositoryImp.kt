package com.example.cryptoapplication.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.cryptoapplication.data.database.AppDatabase
import com.example.cryptoapplication.data.mapper.CoinMapper
import com.example.cryptoapplication.data.network.ApiFactory
import com.example.cryptoapplication.domain.CoinInfo
import com.example.cryptoapplication.domain.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImp(private val application: Application) : CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinInfoDao()
    private val apiService = ApiFactory.apiService
    private val mapper = CoinMapper()

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return coinInfoDao.getPriceList().map {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return coinInfoDao.getPriceInfoAboutCoin(fromSymbol).map {
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val fSymbols = mapper.mapNamesListToString(topCoins)
                val jsonContainerDto = apiService.getFullPriceList(fSyms = fSymbols)
                val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainerDto)
                val dbModelList = coinInfoDtoList.map {mapper.mapDtoToDbModel(it)}
                coinInfoDao.insertPriceList(dbModelList)
            } catch (e: Exception) {
                Log.d("CoinRepositoryImp", "Error: ${e.message}")
            }
            delay(10000)
        }
    }
}