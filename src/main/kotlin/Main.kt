import org.arthrp.PngManager

fun main(args: Array<String>) {
    val p = PngManager()

    val img = p.parse(args[0])
    println("Image width:"+img.width+" height:"+img.height)
}