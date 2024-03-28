package su.tovarischi.pyatiletka

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class BackgroundMusicService : Service() {
    private companion object {
        private const val VOLUME = 0.05f
    }

    private lateinit var player: MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.anthem)
        player.isLooping = true
        player.setVolume(VOLUME, VOLUME)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "su.tovarischi.pyatiletka.intent.action.PAUSE_ANTHEM" -> player.pause()
            "su.tovarischi.pyatiletka.intent.action.RESUME_ANTHEM" -> player.start()
            else -> player.start()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
    }
}
