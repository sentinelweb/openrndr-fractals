import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.extra.gui.GUI
import org.openrndr.extra.parameters.ActionParameter
import org.openrndr.extra.parameters.DoubleParameter
import org.openrndr.extra.parameters.IntParameter
import org.openrndr.extra.parameters.XYParameter
import org.openrndr.math.Vector2


// https://www.shadertoy.com/view/XtyGzh

fun main() = application {
    configure {
        width = 2060
        height = (width*9.0/16.0).toInt()
    }

    program {
        val gui = GUI()
        val settings = object {

            @DoubleParameter("lsys rot", 0.0,360.0)
            var l_sys_rot: Double = 26.7
//            @IntParameter("lsys depth", 1,10)
//            var l_sys_depth: Int = 7
//            @IntParameter("lsys rot", 1,10)
//            var l_sys_branches: Int = 3
        }
        gui.add(settings, "Settings")
        extend(gui)

        val lsysTreeFrags = GlslLoader().load("data/glsl/l-sys-tree.glsl")
        extend {
            drawer.shadeStyle = shadeStyle {
                fragmentPreamble = lsysTreeFrags.first
                fragmentTransform = lsysTreeFrags.second
                parameter("time", seconds)
                parameter("l_sys_rot", settings.l_sys_rot)
//                parameter("l_sys_depth", settings.l_sys_depth)
//                parameter("l_sys_branches", settings.l_sys_branches)
//                parameter("x", settings.xy.x)
//                parameter("y", settings.xy.y)
            }

            drawer.fill = ColorRGBa.PINK
            drawer.stroke = null
            drawer.rectangle(0.0, 0.0, width.toDouble(), height.toDouble())
        }
    }
}
