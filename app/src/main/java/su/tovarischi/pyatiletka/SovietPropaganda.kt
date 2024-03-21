package su.tovarischi.pyatiletka

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SovietPropaganda(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int) {

    companion object {
        fun getAllSovietPropaganda(): List<SovietPropaganda> {
            return listOf(
                SovietPropaganda(R.string.a, R.string.lorem_ipsum, R.drawable.replace_with_capy),
                SovietPropaganda(R.string.b, R.string.lorem_ipsum, R.drawable.replace_with_capy),
                SovietPropaganda(R.string.c, R.string.lorem_ipsum, R.drawable.replace_with_capy),
                SovietPropaganda(R.string.d, R.string.lorem_ipsum, R.drawable.replace_with_capy),
                SovietPropaganda(R.string.e, R.string.lorem_ipsum, R.drawable.replace_with_capy),
            )
        }
    }
}