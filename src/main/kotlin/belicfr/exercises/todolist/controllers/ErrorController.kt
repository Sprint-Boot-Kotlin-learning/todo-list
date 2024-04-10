package belicfr.exercises.todolist.controllers

import belicfr.exercises.todolist.properties.AppProperties
import belicfr.exercises.todolist.utilities.Redirect
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.view.RedirectView

@Controller
class IErrorController(private val appProperties: AppProperties,
                       private val errorAttributes: ErrorAttributes) {

    @GetMapping("/error", "/error/")
    fun back(model: Model): RedirectView {
        return Redirect.to("/board")
    }

    @PostMapping("/error", "/error/")
    fun page(model: Model,
             webRequest: WebRequest): String {

        val errorAttributes = this
            .errorAttributes
            .getErrorAttributes(webRequest,
                                ErrorAttributeOptions.defaults())

        model["app"] = appProperties
        model["error"] = errorAttributes["error"]

        return "Error"
    }

}