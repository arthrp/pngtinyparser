import org.arthrp.PngManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
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

    @Test
    fun loadingSmallByteArrAsPngFails(){
        val arr = byteArrayOf(0x89.toByte(), 0x50.toByte(), 0x4E.toByte(), 0x47.toByte(), 0x00.toByte())
        val inputStream = ByteArrayInputStream(arr)

        val p = PngManager()
        val exception = assertFailsWith<IllegalArgumentException> {
            p.parse(inputStream)
        }
        assertEquals(exception.message, "File too small, not a valid png")
    }
}