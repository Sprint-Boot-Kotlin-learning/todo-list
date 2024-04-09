package belicfr.exercises.todolist

import belicfr.exercises.todolist.properties.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class TodolistApplication

fun main(args: Array<String>) {
	runApplication<TodolistApplication>(*args)
}
