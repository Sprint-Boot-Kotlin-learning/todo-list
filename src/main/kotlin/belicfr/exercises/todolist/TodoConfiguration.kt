package belicfr.exercises.todolist

import belicfr.exercises.todolist.entities.Task
import belicfr.exercises.todolist.repositories.TaskRepository
import io.bloco.faker.Faker
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

@Configuration
class TodoConfiguration {

    @Bean
    fun databaseInitializer(taskRepository: TaskRepository)
        = ApplicationRunner {

        val faker: Faker = Faker()

        for (i in 1..5) {
            taskRepository.save(Task(
                title = faker.lorem.sentence(),
                description = faker.lorem.paragraph(),
                endDate = LocalDate.now(),
                id = i.toLong()))
        }
    }

}