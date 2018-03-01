import com.infowings.catalog.wrappers.reactRouter
import com.se.wmeditor.home.HomeComponent
import kotlinext.js.requireAll
import react.dom.render
import kotlin.browser.document

fun main(args: Array<String>) {
    requireAll(kotlinext.js.require.context("", true, js("/\\.css$/")))
    render(document.getElementById("root")) {
        reactRouter.BrowserRouter {
            reactRouter.Route {
                attrs {
                    path = "/"
                    component = ::HomeComponent
                }
            }
        }
    }
}
