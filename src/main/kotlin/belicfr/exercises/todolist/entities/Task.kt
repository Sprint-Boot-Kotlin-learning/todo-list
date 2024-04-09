package belicfr.exercises.todolist.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
class Task(
    var title: String,
    var description: String,
    var endDate: LocalDate,
    var status: Boolean = true,
    @Id @GeneratedValue var id: Long? = null) {

    fun getStatusLabel(): String
        = if (status) "In course" else "Closed"

    fun getStatusLabelCssClass(): String
        = if (status) "in-course" else "closed"
}