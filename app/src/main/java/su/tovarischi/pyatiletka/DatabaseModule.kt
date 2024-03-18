package su.tovarischi.pyatiletka

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideSovietDatabaseHelper(@ApplicationContext appContext: Context): SovietDatabaseHelper {
        return SovietDatabaseHelper(appContext)
    }
}
