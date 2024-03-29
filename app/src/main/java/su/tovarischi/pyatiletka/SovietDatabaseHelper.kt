package su.tovarischi.pyatiletka

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class SovietDatabaseHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    private companion object {
        const val DATABASE_NAME = "pyatiletka.db"
        const val DATABASE_VERSION = 2

        val DATETIME_FORMATTER: DateTimeFormatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

        private fun Boolean.toInt(): Int = if (this) 1 else 0
        private fun Int.toBoolean(): Boolean = this != 0
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            create table tasks (
                task_id text not null,
                created text not null,
                category integer not null,
                title text not null,
                details text not null,
                is_completed integer not null
            );
        """.trimIndent())

        db.execSQL("""
            create table task_delete_counter (
                    counter integer default 0,
                    category integer not null
            );
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists tasks")
        db.execSQL("drop table if exists task_delete_counter")
        onCreate(db)
    }

    fun createTask(task: SovietTask) {
        val values = ContentValues().apply {
            put("task_id", task.taskId.toString())
            put("created", task.created.format(DATETIME_FORMATTER))
            put("category", task.category.ordinal)
            put("title", task.title)
            put("details", task.details)
            put("is_completed", task.isCompleted.toInt())
        }

        writableDatabase.use { db ->
            db.insert("tasks", null, values)
        }
    }

    fun getTasks(category: SovietTask.Category): List<SovietTask> {
        return readableDatabase.use { db ->
            db.query(
                false,
                "tasks",
                null,
                "category = ?",
                 arrayOf(category.ordinal.toString()),
                null,
                null,
                null,
                null,
                null,
            ).use { cursor ->
                val tasks = mutableListOf<SovietTask>()

                while (cursor.moveToNext()) {
                    tasks += SovietTask(
                        taskId = UUID.fromString(
                            cursor.getString(
                                cursor.getColumnIndexOrThrow("task_id")
                            )
                        ),
                        created = LocalDateTime.parse(
                            cursor.getString(
                                cursor.getColumnIndexOrThrow("created")
                            ),
                            DATETIME_FORMATTER
                        ),
                        category = category,
                        title = cursor.getString(
                            cursor.getColumnIndexOrThrow("title")
                        ),
                        details = cursor.getString(
                            cursor.getColumnIndexOrThrow("details")
                        ),
                        isCompleted = cursor.getInt(
                            cursor.getColumnIndexOrThrow("is_completed")
                        ).toBoolean(),
                    )
                }

                tasks.sortedByDescending { it.created }.sortedBy { it.isCompleted }
            }
        }
    }

    fun updateIsCompleted(taskId: UUID, isCompleted: Boolean): Boolean {
        val values = ContentValues().apply {
            put("is_completed", isCompleted.toInt())
        }

        return writableDatabase.use { db ->
            db.update(
                "tasks",
                values,
                "task_id = ?",
                arrayOf(taskId.toString()),
            )
        }.toBoolean()
    }

    fun deleteTask(taskId: UUID): Boolean {
        return writableDatabase.use { db ->
            db.delete(
                "tasks",
                "task_id = ?",
                arrayOf(taskId.toString()),
            )
        }.toBoolean()
    }

    fun countTasks(isCompleted: Boolean?): Int {
        val whereClause: String = isCompleted?.let { "where is_completed = $it" } ?: ""

        return writableDatabase.use { db ->
            db.rawQuery(
                "select count(*) from tasks $whereClause",
                arrayOf(),
            ).use { cursor ->
                check(cursor.moveToFirst()) { "No results???" }
                cursor.getInt(0)
            }
        }
    }


    fun addTaskDelete(category: SovietTask.Category){
        val currentCount = getTaskDeleteCount(category)
        val values = ContentValues().apply {
            put("counter", currentCount + 1)
        }
        writableDatabase.use { db ->
            db.update(
                "task_delete_counter",
                values,
                "category = ?",
                arrayOf(category.ordinal.toString())
            )
        }
    }

    fun insertTaskDelete(category: SovietTask.Category){
        val values = ContentValues().apply {
            put("category", category.ordinal)
            put("counter", 0)
        }

        writableDatabase.use { db ->
            db.insert("task_delete_counter", null, values)
        }
    }

    fun getTaskDeleteCount(category: SovietTask.Category): Int{
        return writableDatabase.use { db ->
            db.rawQuery(
                "select counter from task_delete_counter where category = ?",
                arrayOf(category.ordinal.toString()),
            ).use { cursor ->
                if (cursor.moveToFirst()) {
                    cursor.getInt(0)
                } else {
                    insertTaskDelete(category)
                    0
                }
            }
        }
    }

    fun dropDatabase(context: Context) {
        context.deleteDatabase(DATABASE_NAME)
    }
}
