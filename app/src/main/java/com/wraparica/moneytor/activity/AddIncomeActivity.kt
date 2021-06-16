package com.wraparica.moneytor.activity

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wraparica.moneytor.R
import com.wraparica.moneytor.databinding.ActivityAddIncomeBinding
import com.wraparica.moneytor.datalayer.database.MoneytorDatabase
import com.wraparica.moneytor.datalayer.entity.Income
import com.wraparica.moneytor.utils.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AddIncomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddIncomeBinding
    private var dateFrom: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_income)
        binding.bCancel.setOnClickListener{
            onBackPressed()
        }
        binding.bSave.setOnClickListener{
            if(checkCanSave()){
                saveIncome()
                onBackPressed()
            }
        }
        binding.bDate.setOnClickListener{ setDate(binding.tvDate)}

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        supportActionBar?.subtitle = getString(R.string.income)
        setupLeaveTypeSelection()
    }

    override fun onSupportNavigateUp() = super.onSupportNavigateUp().also { onBackPressed() }
    companion object {
        fun getActivityIntent(context: Context): Intent {
            return Intent(context, AddIncomeActivity::class.java)
        }
    }

    private fun checkCanSave() : Boolean{
//        if (binding.txtAccountName.length() == 0){
//            Toast.makeText(this,"Kindly add account name", Toast.LENGTH_LONG).show()
//            return false
//        }
        return true
    }

    private fun setupLeaveTypeSelection() {
        val leaveTypeEntries: MutableList<String> =
            ArrayList()
        leaveTypeEntries.add("Select Account")
        leaveTypeEntries.addAll(MoneytorDatabase.get(this).accountsDao().getAccountName())
        val adapter = ArrayAdapter(
            this, R.layout.spinner_default, leaveTypeEntries
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown)
        binding.spAccount.adapter = adapter
    }
    private fun setDate(dateText: TextView) {
        val dpd: DatePickerDialog
        if (dateFrom.isEmpty()) {
            val timestamp: String = DateUtils.getLocalTimestamp()
            dpd = DatePickerDialog(
                this,
                OnDateSetListener { _: DatePicker?, year: Int, month: Int, day: Int ->
                    dateFrom = String.format(
                        Locale.getDefault(), "%04d-%02d-%02d ",
                        year, month + 1, day
                    )
                    dateText.text =
                        DateUtils.convert(
                            DateUtils.TIMESTAMP_DATE,
                            DateUtils.DATE,
                            dateFrom
                        )

                }, timestamp.substring(0, 4).toInt(),
                timestamp.substring(5, 7).toInt() - 1, timestamp.substring(8, 10).toInt()
            )
        } else {
            dpd = DatePickerDialog(
                this,
                OnDateSetListener { _: DatePicker?, year: Int, month: Int, day: Int ->
                    dateFrom = String.format(
                        Locale.getDefault(), "%04d-%02d-%02d ",
                        year, month + 1, day
                    )
                    dateText.text = DateUtils.convert(
                        DateUtils.TIMESTAMP_DATE,
                        DateUtils.DATE,
                        dateFrom
                    )
                }, dateFrom.substring(0, 4).toInt(),
                dateFrom.substring(5, 7).toInt() - 1, dateFrom.substring(8, 10).toInt()
            )
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        var dateObj: Date? = null
        try {
            dateObj = sdf.parse(DateUtils.getLocalTimestamp(DateUtils.TIMESTAMP))
            dpd.datePicker.maxDate = dateObj.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        dpd.show()
    }
    private fun saveIncome(){
        val accountId = MoneytorDatabase.get(this).accountsDao()
            .getAccountID(binding.spAccount.selectedItem.toString())
        val db = MoneytorDatabase.get(this)
        db.incomeDao().insertUpdateIncome(Income(db.incomeDao().getNextId(), binding.txtDetails.text.toString(), binding.tvDate.text.toString(), accountId, binding.txtAmount.text.toString().toDouble()))
        db.accountsDao().updateAccountAddIncome(accountId, binding.txtAmount.text.toString().toDouble())
    }
}