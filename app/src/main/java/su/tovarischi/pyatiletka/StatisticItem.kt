package su.tovarischi.pyatiletka

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class StatisticItem(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val value: Int,
)
