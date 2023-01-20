package com.bitflyer.testapp.ui

interface BaseMapper<A, B> {

    fun map(item: A): B
}