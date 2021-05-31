package com.example.bwtools.etc

import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

object HashUtil {
    fun getSHA512(input: String): String? {
        var toReturn: String? = null
        try {
            val digest =
                MessageDigest.getInstance("SHA-512")
            digest.reset()
            digest.update(input.toByteArray(StandardCharsets.UTF_8))
            toReturn = String.format("%040x", BigInteger(1, digest.digest()))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return toReturn
    }

    fun getSHA256(input: String): String? {
        var toReturn: String? = null
        try {
            val digest =
                MessageDigest.getInstance("SHA-256")
            digest.reset()
            digest.update(input.toByteArray(StandardCharsets.UTF_8))
            toReturn = String.format("%040x", BigInteger(1, digest.digest()))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return toReturn
    }

}