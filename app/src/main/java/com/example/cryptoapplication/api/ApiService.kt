package com.example.cryptoapplication.api

import com.example.cryptoapplication.pojo.CoinInfo
import com.example.cryptoapplication.pojo.CoinInfoListOfData
import com.example.cryptoapplication.pojo.CoinPriceInfoRawData
import com.example.cryptoapplication.pojo.Datum
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top/totalvolfull")
    fun getTopCoinsInfo (
        @Query(QUERY_PARAM_API_KEY) apiKey:String = "e184f0d66b0a7987b8db28e1e6013f06697c9ca184bd251a62e7100b02bde7d2",
        @Query(QUERY_PARAM_LIMIT) limit:Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym:String = CURRENCY,
    ): Single<CoinInfoListOfData>
@GET("pricemultifull")
    fun getFullPriceList(
    @Query(QUERY_PARAM_API_KEY) apiKey:String = "e184f0d66b0a7987b8db28e1e6013f06697c9ca184bd251a62e7100b02bde7d2",
    @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms:String,
    @Query(QUERY_PARAM_TO_SYMBOLS) tSyms:String = CURRENCY
    ):Single<CoinPriceInfoRawData>

companion object {
    private const val QUERY_PARAM_API_KEY = "api_key"
    private const val QUERY_PARAM_LIMIT = "limit"
    private const val QUERY_PARAM_TO_SYMBOL = "tsym"
    private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
    private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
    private const val CURRENCY = "USD"
}
}