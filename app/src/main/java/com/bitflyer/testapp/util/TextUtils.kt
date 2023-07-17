package com.bitflyer.testapp.util

fun checkWebLink(s: String): String {
    return if (s.startsWith("http://") || s.startsWith("https://")) {
        s
    } else {
        return """http://$s"""
    }
}