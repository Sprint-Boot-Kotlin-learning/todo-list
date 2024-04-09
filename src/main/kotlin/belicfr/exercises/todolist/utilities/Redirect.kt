package belicfr.exercises.todolist.utilities

import org.springframework.web.servlet.view.RedirectView

object Redirect {
    fun to(map: String): RedirectView {
        return RedirectView(map)
    }
}