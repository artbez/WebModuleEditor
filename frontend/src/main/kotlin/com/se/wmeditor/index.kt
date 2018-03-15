import com.se.wmeditor.home.HomeComponent
import com.se.wmeditor.wrappers.reactRouter
import kotlinext.js.invoke
import react.dom.render
import kotlin.browser.document

fun main(args: Array<String>) {
    kotlinext.js.require.invoke("storm-react-diagrams/dist/style.min.css")
    kotlinext.js.require.invoke("css/com/se/wmeditor/home/home-diagram-styles.css")
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
