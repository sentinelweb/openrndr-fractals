import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.extra.gui.GUI
import org.openrndr.extra.parameters.DoubleParameter
import kotlin.math.cos
import kotlin.math.sin


// also : Juilia sets
// https://www.shadertoy.com/view/NdSGRG
// https://www.shadertoy.com/view/3s33W4
// https://www.shadertoy.com/view/lsdBWs
// l-systems
// https://www.shadertoy.com/view/XtyGR1
// https://www.shadertoy.com/view/XtyGzh
// https://www.shadertoy.com/view/llXfRr
// edge of universe
// https://www.shadertoy.com/view/MljSDy
// more koch
// https://www.shadertoy.com/view/WtGfR1

fun main() = application {
    configure {
        width = 768
        height = 576
    }

    program {
        val gui = GUI()
        val settings = object {
            @DoubleParameter("tex_x", 0.0, 1.0)
            var x: Double = 0.5

            @DoubleParameter("tex_y", 0.0, 1.0)
            var y: Double = 0.5

            @DoubleParameter("scale", 1.0, 10.0)
            var scale: Double = 2.0
        }
        gui.add(settings, "Settings")
        extend(gui)

        val image = loadImage("data/images/spidy_liberty_2_512.png")
        image.filter(MinifyingFilter.LINEAR_MIPMAP_NEAREST, MagnifyingFilter.LINEAR)

        val kochFrags = KochFrags()

        extend {
            drawer.shadeStyle = shadeStyle {
                fragmentPreamble = kochFrags.funs
                fragmentTransform = kochFrags.shader
                parameter("image", image)
                parameter("time", seconds)
                parameter("tex_x", settings.x)
                parameter("tex_y", settings.y)
                parameter("scale", settings.scale.toInt())
            }

            drawer.fill = ColorRGBa.PINK
            drawer.stroke = null
            //drawer.rectangle(0.0, 0.0, width.toDouble(), height.toDouble())
            drawer.rectangle(width / 2.0 - 100.0, height / 2.0 - 200.0, 400.0, 400.00)
        }
    }
}
