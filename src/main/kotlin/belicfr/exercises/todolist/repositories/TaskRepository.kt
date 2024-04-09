package belicfr.exercises.todolist.repositories

import belicfr.exercises.todolist.entities.Task
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface TaskRepository: CrudRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {
    fun findAllByOrderByIdDesc(pageable: Pageable): List<Task>
}