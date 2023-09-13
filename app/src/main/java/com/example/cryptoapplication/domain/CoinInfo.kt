package com.example.cryptoapplication.domain

data class CoinInfo(
    var fromSymbol: String,
    var toSymbol: String? = null,
    var price: Double? = null,
    var lastUpdate: String? = null,
    var highDay: Double? = null,
    var lowDay: Double? = null,
    var lastMarket: String? = null,
    var imageUrl: String
)