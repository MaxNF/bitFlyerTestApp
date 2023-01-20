package com.bitflyer.testapp.domain.userlist.mapper

import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.userBriefMock
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UserBriefToUserBriefEntityMapperTest : BaseTest() {

    private lateinit var mapper: UserBriefToUserBriefEntityMapper
    private val dto = userBriefMock

    override fun setUp() {
        super.setUp()
        mapper = UserBriefToUserBriefEntityMapper()
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