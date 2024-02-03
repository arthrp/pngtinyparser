package org.arthrp

import java.io.File
import java.io.InputStream
import java.nio.ByteBuffer

class PngManager {
    fun parse(filePath: String): PngImage {
        File(filePath).inputStream().use { input ->
            return parse(input)
        }
    }

    fun parse(input: InputStream): PngImage {
        if(!hasPngHeader(input)) throw IllegalArgumentException("Not a valid png file!")
        val list = emptyList<PngChunk>().toMutableList()
        var chunk = readChunk(input)

        list.add(chunk)
        while(chunk.chunkType != "IEND"){
            chunk = readChunk(input)
            list.add(chunk)
        }

        val (width,height) = parseIhdr(list[0])
        val pngImg = PngImage(list, width, height)
        return pngImg
    }

    private fun readChunk(stream: InputStream): PngChunk{
        val buff = ByteArray(4)

        //Chunk length
        stream.read(buff)
        val len = getInt(buff)
        val dataBuff = ByteArray(len)

        //Chunk type
        stream.read(buff)
        val chunkType = String(buff)

        //Data
        stream.read(dataBuff)

        //CRC
        stream.read(buff)
        return PngChunk(len, chunkType, dataBuff, buff)
    }

    private fun parseIhdr(ihdr: PngChunk) : Pair<Int,Int> {
        val widthArr = ihdr.data.slice(0..3).toByteArray()
        val width = getInt(widthArr)
        val heightArr = ihdr.data.slice(4..7).toByteArray()
        val height = getInt(heightArr)

        return Pair(width,height)
    }

    private fun getInt(arr: ByteArray): Int {
        return ByteBuffer.wrap(arr).int
    }

    private fun hasPngHeader(stream: InputStream): Boolean {
        val headerBytes = ByteArray(8)
        val pngHeader = byteArrayOf(0x89.toByte(), 0x50.toByte(), 0x4E.toByte(), 0x47.toByte(), 0x0D.toByte(), 0x0A.toByte(),
            0x1A.toByte(), 0x0A.toByte())

        val readCount = stream.read(headerBytes)
        if(readCount < 8) throw IllegalArgumentException("File too small, not a valid png")

        for ((i,b) in pngHeader.withIndex()) {
            if(headerBytes[i] != b) return false
        }

        return true
    }
}