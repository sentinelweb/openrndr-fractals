import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.extra.gui.GUI
import org.openrndr.extra.parameters.ActionParameter
import org.openrndr.extra.parameters.DoubleParameter
import org.openrndr.extra.parameters.IntParameter
import org.openrndr.extra.parameters.XYParameter
import org.openrndr.math.Vector2


data class Tree(val root: Branch) {
    private val branches = mutableListOf<Branch>()
    fun draw(drawer: Drawer) {
        root.draw(drawer)
        branches.forEach { it.draw(drawer) }
    }

    fun buildTree(branch: Branch, angle: Double, depth: Int = 0) {
        if (depth < 10) {
            branch.buildBranch(angle, depth)
                .apply { branches.add(this) }
                .apply { buildTree(this, angle, depth + 1) }

            branch.buildBranch(-angle, depth)
                .apply { branches.add(this) }
                .apply { buildTree(this, angle, depth + 1) }
        }
    }
}

data class Branch(val begin: Vector2, val end: Vector2, val depth: Int) {
    fun draw(drawer: Drawer) {
        drawer.lineSegment(begin, end)
    }

    fun buildBranch(angle: Double, depth: Int): Branch {
        val dir = end - begin
        val rotated = dir.rotate(angle) * 0.67
        return Branch(end, end + rotated, depth)
    }
}

fun main() = application {
    configure {
        width = 2060
        height = (width * 9.0 / 16.0).toInt()
    }

    program {
        val gui = GUI()
        val settings = object {

            @DoubleParameter("angle", 0.0, 360.0)
            var angle: Double = 26.7
        }
        gui.add(settings, "Settings")
        extend(gui)

        extend {

            drawer.fill = ColorRGBa.BLACK
            drawer.stroke = ColorRGBa.WHITE
            val root = Branch(Vector2(width / 2.0, height.toDouble()), Vector2(width / 2.0, height - 350.0), 0)
            val tree = Tree(root)
            tree.buildTree(root, settings.angle)
            tree.draw(drawer)
        }
    }

}
