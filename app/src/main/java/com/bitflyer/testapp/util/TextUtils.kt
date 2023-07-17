package com.bitflyer.testapp.util

fun getWebLink(s: String): String =
    if (s.startsWith("http://") || s.startsWith("https://")) s
    else """http://$s"""


fun getTwitterLink(s: String): String = """https://twitter.com/$s"""