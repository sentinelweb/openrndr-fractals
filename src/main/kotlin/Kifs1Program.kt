import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.extra.gui.GUI
import org.openrndr.extra.parameters.DoubleParameter
import org.openrndr.extra.parameters.XYParameter
import org.openrndr.math.Vector2

//https://www.shadertoy.com/view/ldyyRm

fun main() = application {
    configure {
        width = 2060
        height = (width*9.0/16.0).toInt()
    }

    program {
        val gui = GUI()
        val settings = object {
            @DoubleParameter("ifs_rot", 1.0, 10.0)
            var ifs_rot: Double = 2.0
        }
        gui.add(settings, "Settings")
        extend(gui)

        //val kochFrags = KochFrags()
        val kifsFrags = GlslLoader().load("data/glsl/kifs1.glsl")
        extend {
            drawer.shadeStyle = shadeStyle {
                fragmentPreamble = kifsFrags.first
                fragmentTransform = kifsFrags.second
                parameter("time", seconds)
                parameter("ifs_rot", settings.ifs_rot)
            }

            drawer.fill = ColorRGBa.PINK
            drawer.stroke = null
            drawer.rectangle(0.0, 0.0, width.toDouble(), height.toDouble())
            //drawer.rectangle(width / 2.0 - 100.0, height / 2.0 - 200.0, 400.0, 400.00)
        }
    }
}
