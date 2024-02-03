import org.arthrp.PngManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertFailsWith

internal class PngManagerTest {
    @Test
    fun loadingTestFileAsPngFails(){
        val resourceUrl = javaClass.classLoader.getResource("testFile.txt")
        val file = File(resourceUrl!!.toURI())

        val p = PngManager()
        val exception = assertFailsWith<IllegalArgumentException> {
            p.parse(file.absolutePath)
        }
        assertEquals(exception.message, "Not a valid png file!")
    }
}