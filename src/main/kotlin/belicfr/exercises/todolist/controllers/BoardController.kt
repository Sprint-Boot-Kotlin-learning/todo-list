package belicfr.exercises.todolist.controllers

import belicfr.exercises.todolist.entities.Task
import belicfr.exercises.todolist.properties.AppProperties
import belicfr.exercises.todolist.repositories.TaskRepository
import belicfr.exercises.todolist.utilities.Redirect
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.view.RedirectView
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Optional

@Controller
class BoardController(private val appProperties: AppProperties,
                      private val taskRepository: TaskRepository) {

    private val MAX_TITLE_LENGTH: Int = 150

    private val MAX_ROWS_PER_PAGE: Int = 10

    @GetMapping("", "/")
    fun root(): RedirectView {
        return Redirect.to("/board")
    }

    @GetMapping("/board", "/board/")
    fun board(model: Model): String {
        return this.board(1, model)
    }

    @GetMapping("/board/{page}", "/board/{page}/")
    fun board(@PathVariable("page") page: Int, model: Model): String {
        val pageable: Pageable = PageRequest.of(page - 1, MAX_ROWS_PER_PAGE)
        val pageTasks: List<Task> = taskRepository.findAllByOrderByIdDesc(pageable)

        if (page > 1 && pageTasks.isEmpty()) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND,
                                          "There is not a page $page.")
        }

        val previousPageNumber: Int = page - 2
        val isPreviousPageExisting: Boolean

        if (previousPageNumber < 0) {
            isPreviousPageExisting = false
        } else {
            val previousPage: Pageable = PageRequest.of(previousPageNumber, MAX_ROWS_PER_PAGE)
            val previousPageContent: List<Task> = taskRepository.findAllByOrderByIdDesc(previousPage)
            isPreviousPageExisting = previousPageContent.isNotEmpty()
        }

        val nextPage: Pageable = PageRequest.of(page, MAX_ROWS_PER_PAGE)
        val nextPageContent: List<Task> = taskRepository.findAllByOrderByIdDesc(nextPage)

        model["app"] = appProperties
        model["tasks"] = pageTasks
        model["isPreviousPageExisting"] = isPreviousPageExisting
        model["isNextPageExisting"] = nextPageContent.isNotEmpty()

        return "Board"
    }

    @PostMapping("/board/task/create", "/board/task/create/")
    fun createTask(@RequestParam title: String,
                   @RequestParam description: String,
                   @RequestParam endDate: String): RedirectView {

        if (title.length > MAX_TITLE_LENGTH) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,
                                          "Title field length must be less than 150 characters.")
        }

        val endDateObject: LocalDate = LocalDate.parse(endDate)

        if (endDateObject.isBefore(LocalDate.now())) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,
                                          "End date cannot be in the past.")
        }

        taskRepository.save(Task(
            title,
            description,
            endDateObject))

        return Redirect.to("/board")
    }

    @GetMapping("/board/task-{id}", "/board/task-{id}/")
    fun task(@PathVariable("id") id: Long, model: Model): String {
        val task: Optional<Task> = taskRepository.findById(id)

        if (!task.isPresent) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND,
                                          "The task does not longer exist.")
        }

        model["app"] = appProperties
        model["task"] = task

        return "EditTask"
    }

    @PostMapping("/board/task/save", "/board/task/save")
    fun saveTask(@RequestParam id: Long,
                 @RequestParam title: String,
                 @RequestParam description: String,
                 @RequestParam endDate: String): RedirectView {

        val concernedTask: Optional<Task> = taskRepository.findById(id)

        if (!concernedTask.isPresent) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND,
                                          "The task does not longer exist.")
        }

        val task: Task = concernedTask.get()
        task.title = title
        task.description = description
        task.endDate = LocalDate.parse(endDate)

        taskRepository.save(task)

        return Redirect.to("/board")
    }

    @PostMapping("/board/task/delete", "/board/task/delete/")
    fun deleteTask(@RequestParam id: Long): RedirectView {
        val task: Optional<Task> = taskRepository.findById(id)

        if (!task.isPresent) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND,
                                          "The task does not longer exist.")
        }

        taskRepository.delete(task.get())

        return Redirect.to("/board")
    }

    @PostMapping("/board/task/toggle-status", "/board/task/toggle-status/")
    fun toggleTaskStatus(@RequestParam id: Long): RedirectView {
        val concernedTask: Optional<Task> = taskRepository.findById(id)

        if (!concernedTask.isPresent) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND,
                                          "The task does not longer exist.")
        }

        val task: Task = concernedTask.get()
        task.status = !task.status
        taskRepository.save(task)

        return Redirect.to("/board")
    }

}