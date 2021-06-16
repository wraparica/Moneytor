package com.wraparica.moneytor.activity

import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.wraparica.moneytor.R
import com.wraparica.moneytor.datalayer.database.MoneytorDatabase
import com.wraparica.moneytor.fragment.AccountMainFragment
import com.wraparica.moneytor.fragment.ExpenseMainFragment
import com.wraparica.moneytor.fragment.IncomeMainFragment


open class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {



    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var moduleName : TextView
    private lateinit var drawerLayout: DrawerLayout
    private var fragment: Fragment? = null
    lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        moduleName = findViewById(R.id.moduleName)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,0,0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        setFragment(R.id.nav_accounts)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_accounts -> {
                //Toast.makeText(this, "Accounts Clicked", Toast.LENGTH_SHORT).show()
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                moduleName.text = getString(R.string.accounts)
                setFragment(item.itemId)

            }
            R.id.nav_income -> {
                if (MoneytorDatabase.get(this).accountsDao().checkHasAccounts()) {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    moduleName.text = getString(R.string.income)
                    setFragment(item.itemId)
                } else {
                    showDialog(getString(R.string.no_accounts), getString(R.string.kindly_add_account_before_adding_income))
                }
            }
            R.id.nav_expenses -> {
                if (MoneytorDatabase.get(this).accountsDao().checkHasAccounts()) {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    moduleName.text = getString(R.string.expenses)
                    setFragment(item.itemId)
                } else {
                    showDialog(getString(R.string.no_accounts), getString(R.string.kindly_add_account_before_adding_expenses))
                }
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showDialog(title : String, message : String){
        val dialog: AlertDialog =
            AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(getString(android.R.string.ok),null)
                .create()
        showDialog(this, dialog)
    }

    open fun showDialog(
        activity: Activity?,
        dialog: AlertDialog
    ) {
        if (activity == null) {
            return
        }
        try {
            if (!activity.isFinishing) {
                dialog.show()
            }
        } catch (e: Exception) {
        }
    }


    private fun setFragment(id: Int) {
        setFragment(id, null)
    }
    private fun setFragment(id: Int, args: Bundle?) {
        var tag = ""
        when (id) {
            R.id.nav_accounts -> {
                fragment = AccountMainFragment()
                tag = "" + R.id.nav_accounts
            }
            R.id.nav_income -> {
                fragment = IncomeMainFragment()
                tag = "" + R.id.nav_income
            }
            R.id.nav_expenses -> {
                fragment = ExpenseMainFragment()
                tag = "" + R.id.nav_expenses
            }
            else -> return
        }
        fragment!!.arguments = args
        switchFragment(fragment!!, tag)
    }
    private fun switchFragment(
        fragment: Fragment,
        fragmentTag: String
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayoutMainActivity, fragment, fragmentTag)
            .disallowAddToBackStack()
            .commitNow()
       // Utilities.trackScreen(this@MainActivity, fragment)
    }
}