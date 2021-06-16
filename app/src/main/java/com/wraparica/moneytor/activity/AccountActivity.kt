package com.wraparica.moneytor.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wraparica.moneytor.R
import com.wraparica.moneytor.adapter.AccountsActivityAdapter
import com.wraparica.moneytor.databinding.ActivityAccountBinding
import com.wraparica.moneytor.datalayer.database.MoneytorDatabase
import com.wraparica.moneytor.viewmodel.AccountActivityListViewModel

class AccountActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAccountBinding
    private lateinit var adapter: AccountsActivityAdapter
    private val viewModel: AccountActivityListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account)

        adapter = AccountsActivityAdapter(this)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
        binding.recycler.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.llEmptyState.ivEmptyState.setImageResource(R.drawable.ic_expenses)
        binding.llEmptyState.tvEmptyState.text = getString(R.string.no_expenses)

        viewModel.postAccountId(getAccountId())
        viewModel.getLiveAccountActivity().observe(this, Observer {
            adapter.submitList(it)
            if (it.isNullOrEmpty()) {
                binding.recycler.visibility = View.GONE
                binding.llEmptyState.constraintEmptyState.visibility = View.VISIBLE
            } else {
                binding.recycler.visibility = View.VISIBLE
                binding.llEmptyState.constraintEmptyState.visibility = View.GONE
            }
        })
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        supportActionBar?.title = MoneytorDatabase.get(this).accountsDao().getAccountNameById(getAccountId())
    }
    private fun getAccountId() = intent.getLongExtra(PARAM_ACCOUNT_ID, -1)
    override fun onSupportNavigateUp() = super.onSupportNavigateUp().also { onBackPressed() }
    companion object {
        const val PARAM_ACCOUNT_ID = "PARAM_ACCOUNT_ID"
        fun getActivityIntent(context: Context, accountId : Long): Intent {
            return Intent(context, AccountActivity::class.java)
                .putExtra(PARAM_ACCOUNT_ID, accountId)
        }
    }
}