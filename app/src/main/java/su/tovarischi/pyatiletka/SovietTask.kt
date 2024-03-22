package su.tovarischi.pyatiletka

import androidx.annotation.StringRes
import java.time.LocalDateTime
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

data class SovietTask(
    val taskId: UUID,
    val created: LocalDateTime,
    val category: Category,
    val title: String,
    val details: String,
    var isCompleted: Boolean,
) {
    companion object {
        fun random() = SovietTask(
            taskId = UUID.randomUUID(),
            created = LocalDateTime.now() + ((1)..(365 * 24 * 60)).random().minutes.toJavaDuration(),
            category = Category.entries.random(),
            title = "Lorem ipsum ${UUID.randomUUID()}",
            details = "Lorem ipsum ${UUID.randomUUID()}",
            isCompleted = ThreadLocalRandom.current().nextBoolean(),
        )
    }

    enum class Category(@StringRes val label: Int) {
        PartyTask(R.string.category_party_tasks),
        HomeTask(R.string.category_home_tasks),
        Ticket(R.string.category_tickets),
    }
}
