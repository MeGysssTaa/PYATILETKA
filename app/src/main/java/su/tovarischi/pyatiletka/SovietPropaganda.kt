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
                SovietPropaganda(R.string.prop1_title, R.string.prop1_details, R.drawable.prop1_banner),
                SovietPropaganda(R.string.prop2_title, R.string.prop2_details, R.drawable.prop2_banner),
                SovietPropaganda(R.string.prop3_title, R.string.prop3_details, R.drawable.prop3_banner),
                SovietPropaganda(R.string.prop4_title, R.string.prop4_details, R.drawable.prop4_banner),
            )
        }
    }
}