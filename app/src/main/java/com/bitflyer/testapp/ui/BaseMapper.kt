package com.bitflyer.testapp.ui

interface BaseMapper<A, B> {

    fun map(dto: A): B
}