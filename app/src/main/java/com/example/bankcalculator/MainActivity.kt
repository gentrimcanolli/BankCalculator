package com.example.bankcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var sumEt: EditText
    private lateinit var interesEt: EditText
    private lateinit var timeInMonthsEt: EditText
    private lateinit var kestTv: TextView
    private lateinit var totalTv: TextView
    private lateinit var btnCalculate: Button
    private lateinit var btnClear: Button
    private lateinit var tableRecyclerView: RecyclerView
    private var paymentList = ArrayList<Payment>()
    private lateinit var tableRowAdapter: TableRowAdapter
    private lateinit var payment: Payment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()


        btnCalculate.setOnClickListener {

            if (sumEt.text.isEmpty() || interesEt.text.isEmpty() || timeInMonthsEt.text.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
            } else {
                kestTv.text = "Payment every month: ${calculateLoan()}"
                totalTv.text = "Total: ${calculateTotal()}"

                tableData()

                tableRowAdapter = TableRowAdapter(paymentList)
                tableRecyclerView = findViewById(R.id.recycler_view)

                tableRecyclerView.layoutManager = LinearLayoutManager(this)
                tableRecyclerView.adapter = tableRowAdapter
            }

        }

    }


    private fun initUI() {
        sumEt = findViewById(R.id.sum_et)
        interesEt = findViewById(R.id.interes_et)
        timeInMonthsEt = findViewById(R.id.months_et)
        kestTv = findViewById(R.id.kesti_tv)
        totalTv = findViewById(R.id.total_tv)
        btnCalculate = findViewById(R.id.calculate_btn)
        btnClear = findViewById(R.id.clear_btn)


    }

    private fun getSum(): Double {
        return sumEt.text.toString().toDouble()
    }

    private fun getInterest(): Double {
        return (interesEt.text.toString().toDouble()) / 1200
    }

    private fun getTimeInMonths(): Double {
        return timeInMonthsEt.text.toString().toDouble()
    }

    private fun decimalFormat(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN

        return df.format(number).toDouble()

    }

    private fun calculateLoan(): Double {

//        PV - loan amount
//        PMT - monthly payment
//        i - interest rate per month
//        n - number of months

        val pv = getSum()
        val i = getInterest()
        val n = getTimeInMonths()

        val pmt = (pv * i * (1 + i).pow(n)) / (((1 + i).pow(
            n
        )) - 1)

        return decimalFormat(pmt)

    }

    private fun calculateTotal(): Double {
        val total = calculateLoan() * getTimeInMonths()
        return decimalFormat(total)
    }

    private fun tableData() {

//        PMT - monthly payment
//        i - interest rate per month
//        n - number of months

        val i = getInterest()
        val n = getTimeInMonths()

        var paymentAmount = calculateLoan()
        var principalAmount: Double
        var interestAmount: Double
        var balanceOwned = getSum()
        var id = 1

        while (balanceOwned > 0.0) {

            interestAmount = balanceOwned * (i / 365) * n * 10
            principalAmount = paymentAmount - interestAmount
            if (balanceOwned - principalAmount > 0) {
                balanceOwned -= principalAmount
            } else {
                val temp = balanceOwned - principalAmount
                interestAmount = abs(temp)
                paymentAmount = decimalFormat(principalAmount + interestAmount)
                balanceOwned = paymentAmount - (principalAmount + interestAmount)
            }



            paymentList.add(
                Payment(
                    id,
                    paymentAmount,
                    decimalFormat(principalAmount),
                    decimalFormat(interestAmount),
                    decimalFormat(abs(balanceOwned))
                )
            )

            id++
        }
    }
}
