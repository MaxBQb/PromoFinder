package edu.mirea_ikbo0619.promofinder

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.wada811.databinding.dataBinding
import edu.mirea_ikbo0619.promofinder.databinding.ActivityMainBinding
import edu.mirea_ikbo0619.promofinder.ui.auth.AuthViewModel
import edu.mirea_ikbo0619.promofinder.utils.hideKeyboard
import edu.mirea_ikbo0619.promofinder.utils.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding: ActivityMainBinding by dataBinding()
    private val viewModel: MainViewModel by viewModel()
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.data = viewModel
        binding.auth = authViewModel
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, binding.appBarMain.toolbar, 0, 0)
        binding.drawerLayout.addDrawerListener(actionBarToggle)
        observe(viewModel.isHomePage) {
            actionBarToggle.isDrawerIndicatorEnabled = it
            actionBarToggle.syncState()
        }

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home),
            binding.drawerLayout
        )
        authViewModel.isAuthorized.observe(this) {
            binding.drawerLayout.setDrawerLockMode(
                if (it) LOCK_MODE_UNLOCKED
                else LOCK_MODE_LOCKED_CLOSED
            )
            binding.appBarMain.toolbar.isVisible = it
        }
        navController.navigate(
            MobileNavigationDirections.actionHomeFragmentToWelcomeFragment()
        )
        binding.signOut.setOnClickListener {
            authViewModel.signOut()
            navController.navigate(
                MobileNavigationDirections.actionHomeFragmentToWelcomeFragment()
            )
            hideKeyboard()
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        binding.navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        actionBarToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        actionBarToggle.onConfigurationChanged(newConfig)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        findNavController(R.id.nav_host_fragment_content_main).navigate(when (item.itemId) {
            R.id.nav_home -> R.id.homeFragment
            else -> R.id.homeFragment
        })
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}