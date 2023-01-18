package com.bitflyer.testapp

import io.mockk.MockKAnnotations
import org.junit.Before

abstract class BaseTest {

    @Before
    open fun setUp() {
        MockKAnnotations.init(this)
    }
}