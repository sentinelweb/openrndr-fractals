import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.extra.gui.GUI
import org.openrndr.extra.parameters.ActionParameter
import org.openrndr.extra.parameters.DoubleParameter
import org.openrndr.extra.parameters.XYParameter
import org.openrndr.math.Vector2


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
        width = 2060
        height = (width*9.0/16.0).toInt()
    }

    program {
        var time = 0.0
        val gui = GUI()
        val settings = object {
            @XYParameter("julia coordinate")
            var xy = Vector2(-0.345, 0.654)

            @DoubleParameter("speed", 0.001, 0.1)
            var speed: Double = 0.005

            @ActionParameter("Reset time", order = 7)
            fun actionFunction() {
                time = seconds
            }
        }
        gui.add(settings, "Settings")
        extend(gui)

        val juliaFrags = GlslLoader().load("data/glsl/julia.glsl")
        extend {
            drawer.shadeStyle = shadeStyle {
                fragmentPreamble = juliaFrags.first
                fragmentTransform = juliaFrags.second
                parameter("time", seconds-time)
                parameter("speed", settings.speed)
                parameter("x", settings.xy.x)
                parameter("y", settings.xy.y)
            }

            drawer.fill = ColorRGBa.PINK
            drawer.stroke = null
            drawer.rectangle(0.0, 0.0, width.toDouble(), height.toDouble())
        }
    }
}
