package com.example.cryptoapplication.data.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptoapplication.data.database.AppDatabase
import com.example.cryptoapplication.data.mapper.CoinMapper
import com.example.cryptoapplication.data.network.ApiFactory
import kotlinx.coroutines.delay

class RefreshDataWorker(context: Context, workerParameters: WorkerParameters):CoroutineWorker(
    context,
    workerParameters
) {
    private val coinInfoDao = AppDatabase.getInstance(context).coinInfoDao()
    private val apiService = ApiFactory.apiService
    private val mapper = CoinMapper()
    override suspend fun doWork(): Result {
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
    companion object {
        const val NAME = "RefreshDataWorker"
        fun makeRequest():OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }
}