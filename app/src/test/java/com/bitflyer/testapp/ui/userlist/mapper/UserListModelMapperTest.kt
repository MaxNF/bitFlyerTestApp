package com.bitflyer.testapp.ui.userlist.mapper

import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.userBriefEntityMock
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UserListModelMapperTest : BaseTest() {

    private lateinit var mapper: UserListModelMapper
    private val entity = userBriefEntityMock

    override fun setUp() {
        super.setUp()
        mapper = UserListModelMapper()
    }

    @Test
    fun `id is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.id).isEqualTo(entity.id)
    }

    @Test
    fun `login is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.login).isEqualTo(entity.login)
    }

    @Test
    fun `avatarUrl is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.avatarUrl).isEqualTo(entity.avatarUrl)
    }
}