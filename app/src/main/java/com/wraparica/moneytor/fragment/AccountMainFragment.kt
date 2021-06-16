package com.wraparica.moneytor.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wraparica.moneytor.R
import com.wraparica.moneytor.activity.AccountActivity
import com.wraparica.moneytor.activity.AddAccountsActivity
import com.wraparica.moneytor.activity.AddExpensesActivity
import com.wraparica.moneytor.adapter.AccountsAdapter
import com.wraparica.moneytor.databinding.FragmentAccountMainBinding
import com.wraparica.moneytor.datalayer.entity.Accounts
import com.wraparica.moneytor.helper.SwipeHelper
import com.wraparica.moneytor.viewmodel.AccountsListViewModel

class AccountMainFragment : Fragment(), AccountsAdapter.OnItemClickedListener {

    private lateinit var binding : FragmentAccountMainBinding
    private val viewModel: AccountsListViewModel by viewModels()
    private lateinit var adapter: AccountsAdapter
    private lateinit var mFab1 : FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = SwipeHelper.UnderlayButton(
            requireContext(),
            R.drawable.ic_remove_trash,
            ContextCompat.getColor(requireContext(), R.color.md_red_700),
            SwipeHelper.UnderlayButtonClickListener { _: SwipeHelper.UnderlayButton?, pos: Int -> deleteItem(pos) }
        )
        binding.mFab.setOnClickListener {
            viewTransaction()
        }
        SwipeHelper.newSwipeHelper(binding.recycler, button)
        adapter = AccountsAdapter(this)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
        binding.llEmptyState.ivEmptyState.setImageResource(R.drawable.ic_accounts)
        binding.llEmptyState.tvEmptyState.text = getString(R.string.no_accounts)


//        binding.recycler.visibility = View.GONE
//        binding.llEmptyState.constraintEmptyState.visibility = View.VISIBLE
        viewModel.getLiveSoSHeader().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            if (it.isNullOrEmpty()) {
                binding.recycler.visibility = View.GONE
                binding.llEmptyState.constraintEmptyState.visibility = View.VISIBLE
            } else {
                binding.recycler.visibility = View.VISIBLE
                binding.llEmptyState.constraintEmptyState.visibility = View.GONE
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }

    private fun deleteItem(pos: Int) {
        val item: Accounts = adapter.getItemData(pos) ?: return
        //showDeleteConfirmation(item.id)

    }

    private fun viewTransaction(){
        val intent: Intent? = AddAccountsActivity.getActivityIntent(requireContext())
        intent?.let { startActivity(it) }

    }

    override fun onItemClicked(account: Accounts) {
        val intent: Intent? = AccountActivity.getActivityIntent(requireContext(), account.id)
        intent?.let { startActivity(it) }
    }

}