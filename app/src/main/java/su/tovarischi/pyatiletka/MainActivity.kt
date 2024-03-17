package su.tovarischi.pyatiletka

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startService(Intent(this, BackgroundMusicService::class.java))
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
