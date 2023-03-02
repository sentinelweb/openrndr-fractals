import java.io.File

class GlslLoader() {

    fun load(path: String): Pair<String?, String> {
        val split = File(path).readText()
            .split("//----- main -------------")
        return split
            .takeIf { it.size == 2 }
            ?.let { Pair(it[0].trim(), it[1].trim()) }
            ?: let { Pair(null as String?, split[0]) }
    }
}
