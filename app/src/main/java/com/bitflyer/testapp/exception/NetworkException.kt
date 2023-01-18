package com.bitflyer.testapp.exception

class NetworkException(val code: Int, message: String) : Exception(message)