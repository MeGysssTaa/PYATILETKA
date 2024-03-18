package su.tovarischi.pyatiletka

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var databaseHelper: SovietDatabaseHelper

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        navView.setNavigationItemSelectedListener(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon = AppCompatResources.getDrawable(this, R.drawable.ic_outline_menu_white_24dp)

        startService(Intent(this, BackgroundMusicService::class.java))

        databaseHelper = SovietDatabaseHelper(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_category_party_tasks -> {
                navController.navigate(R.id.nav_party_tasks)
            }
            R.id.nav_category_home_tasks -> {
                navController.navigate(R.id.nav_home_tasks)
            }
            R.id.nav_category_tickets -> {
                navController.navigate(R.id.nav_tickets)
            }
            R.id.nav_stats -> {
                navController.navigate(R.id.nav_stats)
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    override fun onPause() {
        super.onPause()

        val pauseMusicIntent = Intent(this, BackgroundMusicService::class.java)
        pauseMusicIntent.action = "su.tovarischi.pyatiletka.intent.action.PAUSE_ANTHEM"
        startService(pauseMusicIntent)
    }

    override fun onResume() {
        super.onResume()

        val resumeMusicIntent = Intent(this, BackgroundMusicService::class.java)
        resumeMusicIntent.action = "su.tovarischi.pyatiletka.intent.action.RESUME_ANTHEM"
        startService(resumeMusicIntent)
    }

    override fun onDestroy() {
        super.onDestroy()

        stopService(Intent(this, BackgroundMusicService::class.java))
    }
}
