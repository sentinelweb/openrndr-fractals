import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.extra.gui.GUI
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
        val gui = GUI()
        val settings = object {
            @XYParameter("tex coordinate")
            var xy = Vector2(0.5, 0.5)

            @DoubleParameter("scale", 1.0, 10.0)
            var scale: Double = 2.0

//            @DoubleParameter("kockAngle", 0.0, 2.0)
//            var kochAngle: Double = 2.0/3.0
//
//            @DoubleParameter("reflectAngle", 0.0, 2.0)
//            var reflectAngle: Double = 5.0/6.0
        }
        gui.add(settings, "Settings")
        extend(gui)

        val image = loadImage("data/images/spidy_liberty_2_512.png")
        //val image = loadImage("data/images/spidy_liberty.png")
        image.filter(MinifyingFilter.LINEAR_MIPMAP_NEAREST, MagnifyingFilter.LINEAR)

        //val kochFrags = KochFrags()
        val kochFrags = GlslLoader().load("data/glsl/koch.glsl")
        extend {
            drawer.shadeStyle = shadeStyle {
                fragmentPreamble = kochFrags.first
                fragmentTransform = kochFrags.second
                parameter("image", image)
                parameter("time", seconds)
                parameter("tex_x", settings.xy.x)
                parameter("tex_y", settings.xy.y)
                parameter("scale", settings.scale.toInt())
//                parameter("kochAngle", settings.kochAngle)
//                parameter("reflectAngle", settings.reflectAngle)
            }

            drawer.fill = ColorRGBa.PINK
            drawer.stroke = null
            drawer.rectangle(0.0, 0.0, width.toDouble(), height.toDouble())
            //drawer.rectangle(width / 2.0 - 100.0, height / 2.0 - 200.0, 400.0, 400.00)
        }
    }
}
