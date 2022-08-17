package com.example.bankcalculator

data class Payment(
    var id : Int,
    var paymentAmount: Double,
    var principalAmount: Double,
    var interestAmount: Double,
    var balanceOwned: Double
)