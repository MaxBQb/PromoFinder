package edu.mirea_ikbo0619.promofinder

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
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
import edu.mirea_ikbo0619.promofinder.databinding.ActivityMainBinding
import edu.mirea_ikbo0619.promofinder.ui.auth.AuthViewModel
import edu.mirea_ikbo0619.promofinder.utils.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.data = viewModel
        binding.auth = authViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarToggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.appBarMain.toolbar, 0, 0)
        binding.drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.isDrawerIndicatorEnabled = true
        actionBarToggle.syncState()

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow),
            binding.drawerLayout
        )
        authViewModel.isAuthorized.observe(this) {
            binding.drawerLayout.setDrawerLockMode(
                if (it) LOCK_MODE_UNLOCKED
                else LOCK_MODE_LOCKED_CLOSED
            )
            binding.appBarMain.toolbar.isVisible = it
        }

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

    fun setHomeIndicator() {
        actionBarToggle.isDrawerIndicatorEnabled = true
        actionBarToggle.syncState()
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