package su.tovarischi.pyatiletka

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.navigationIcon = AppCompatResources.getDrawable(applicationContext, R.drawable.ic_launcher_fg)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        startService(Intent(this, BackgroundMusicService::class.java))
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
            R.id.nav_groceries -> {
                // Handle Groceries navigation
                println("groceries")
            }
            R.id.nav_work -> {
                // Handle Work Tasks navigation
                println("work")
            }
            R.id.nav_home -> {
                // Handle Home Tasks navigation
                println("home")
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPause() {
        super.onPause()
        val pauseMusicIntent = Intent(this, BackgroundMusicService::class.java)
        pauseMusicIntent.action = "PAUSE"
        startService(pauseMusicIntent)
    }

    override fun onResume() {
        super.onResume()
        val resumeMusicIntent = Intent(this, BackgroundMusicService::class.java)
        resumeMusicIntent.action = "RESUME"
        startService(resumeMusicIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, BackgroundMusicService::class.java))
    }
}
