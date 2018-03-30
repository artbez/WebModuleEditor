import com.se.wmeditor.home.HomeComponent
import com.se.wmeditor.wrappers.reactRouter
import kotlinext.js.invoke
import react.dom.render
import kotlin.browser.document

fun main(args: Array<String>) {
    kotlinext.js.require.invoke("storm-react-diagrams/dist/style.min.css")
    kotlinext.js.require.invoke("styles/home-diagram-styles.scss")
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
