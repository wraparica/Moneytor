package com.wraparica.moneytor.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.wraparica.moneytor.R
import com.wraparica.moneytor.databinding.ActivityAddAccountsBinding
import com.wraparica.moneytor.datalayer.database.MoneytorDatabase
import com.wraparica.moneytor.datalayer.entity.Accounts
import com.wraparica.moneytor.datalayer.entity.Expenses
import com.wraparica.moneytor.datalayer.entity.Income
import com.wraparica.moneytor.utils.DateUtils

class AddAccountsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddAccountsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_accounts)
        binding.bCancel.setOnClickListener{
            onBackPressed()
        }
        binding.bSave.setOnClickListener{
            if(checkCanSave()){
                saveAccount()
                onBackPressed()
            }

        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        supportActionBar?.subtitle = getString(R.string.accounts)
    }

    override fun onSupportNavigateUp() = super.onSupportNavigateUp().also { onBackPressed() }
    companion object {
        fun getActivityIntent(context: Context): Intent {
            return Intent(context, AddAccountsActivity::class.java)
        }
    }

    private fun checkCanSave() : Boolean{
        if (binding.txtAccountName.length() == 0){
            Toast.makeText(this,"Kindly add account name", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
    private fun saveAccount(){
        val db = MoneytorDatabase.get(this)
        val accountId = db.accountsDao().getNextId()
        db.accountsDao().insertUpdateAccounts(Accounts(accountId, binding.txtAccountName.text.toString(), 0.0, 1))
        db.incomeDao().insertUpdateIncome(Income(db.incomeDao().getNextId(), "Initial Amount", DateUtils.getLocalTimestamp(DateUtils.DATE), accountId, binding.txtInitialAccount.text.toString().toDouble()))
        db.accountsDao().updateAccountAddIncome(accountId, binding.txtInitialAccount.text.toString().toDouble())
    }
}