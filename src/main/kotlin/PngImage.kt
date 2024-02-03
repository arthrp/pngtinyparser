package org.arthrp

data class PngImage(val chunks: MutableList<PngChunk>, val width: Int, val height: Int)

data class PngChunk(val length: Int, val chunkType: String, val data: ByteArray, val crc: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PngChunk

        if (length != other.length) return false
        if (chunkType != other.chunkType) return false
        if (!data.contentEquals(other.data)) return false
        if (!crc.contentEquals(other.crc)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = length
        result = 31 * result + chunkType.hashCode()
        result = 31 * result + data.contentHashCode()
        result = 31 * result + crc.contentHashCode()
        return result
    }
}