package com.fazliddin.armebel.presentation.ui.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.fazliddin.armebel.R
import com.fazliddin.armebel.databinding.ActivityMainBinding
import android.widget.TextView
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var textCartItemCount: TextView? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    private val onDestinationChangedListener: NavController.OnDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.visibility =
                if (destination.id == R.id.furnitureDetailsFragment
                    || destination.id == R.id.furnitureArFragment
                    || destination.id == R.id.cartFragment
                )
                    View.GONE
                else
                    View.VISIBLE

            binding.topAppBar.visibility = if (destination.id == R.id.furnitureArFragment)
                View.GONE
            else
                View.VISIBLE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        viewModel.cartItemCount.observe(this) {
            try {
                setupCartBadge()
            } catch (e: Exception) {
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onBackPressed() {
        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        val menuItem = menu.findItem(R.id.cartFragment)

        val actionView = menuItem.actionView
        textCartItemCount = actionView?.findViewById(R.id.cart_badge)

        actionView?.setOnClickListener { onOptionsItemSelected(menuItem) }

        setupCartBadge()

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.cartFragment) {
            if (navController.currentDestination?.id != R.id.cartFragment)
                navController.navigate(R.id.cartFragment)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupCartBadge() {
        if (viewModel.cartItemCount.value == null || viewModel.cartItemCount.value == 0) {
            if (textCartItemCount?.visibility != View.GONE) {
                textCartItemCount?.visibility = View.GONE
            }
        } else {
            textCartItemCount?.text = viewModel.cartItemCount.value?.coerceAtMost(99).toString()
            if (textCartItemCount?.visibility != View.VISIBLE) {
                textCartItemCount?.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        navController.addOnDestinationChangedListener(onDestinationChangedListener)
        super.onResume()
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(onDestinationChangedListener)
        super.onPause()
    }

}