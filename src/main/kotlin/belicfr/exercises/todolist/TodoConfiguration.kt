package belicfr.exercises.todolist

import belicfr.exercises.todolist.entities.Task
import belicfr.exercises.todolist.repositories.TaskRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

@Configuration
class TodoConfiguration {

    @Bean
    fun databaseInitializer(taskRepository: TaskRepository)
        = ApplicationRunner {

        for (i in 1..5) {
            taskRepository.save(Task(
                title = "Buy GTA VI",
                description = "Do not forget to buy GTA VI when it will be released.",
                endDate = LocalDate.now(),
                id = i.toLong()))
        }
    }

}