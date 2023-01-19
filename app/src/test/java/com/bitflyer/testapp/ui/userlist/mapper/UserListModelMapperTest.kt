package com.bitflyer.testapp.ui.userlist.mapper

import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UserListModelMapperTest : BaseTest() {

    private lateinit var mapper: UserListModelMapper
    private val dto = UserBrief(1,"login", "avatar")

    override fun setUp() {
        super.setUp()
        mapper = UserListModelMapper()
    }

    @Test
    fun `id is mapped correctly`() {
        val result = mapper.map(dto)
        assertThat(result.id).isEqualTo(dto.id)
    }

    @Test
    fun `login is mapped correctly`() {
        val result = mapper.map(dto)
        assertThat(result.login).isEqualTo(dto.login)
    }

    @Test
    fun `avatarUrl is mapped correctly`() {
        val result = mapper.map(dto)
        assertThat(result.avatarUrl).isEqualTo(dto.avatarUrl)
    }
}