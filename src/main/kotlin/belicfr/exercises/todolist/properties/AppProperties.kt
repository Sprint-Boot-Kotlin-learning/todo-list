package belicfr.exercises.todolist.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("app")
data class AppProperties(var name: String,
                         var description: String)
