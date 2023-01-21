package com.bitflyer.testapp.ui.userdetails.mapper

import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.userDetailsMock
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UserDetailsToUserDetailsModelMapperTest : BaseTest() {

    private lateinit var mapper: UserDetailsToUserDetailsModelMapper
    private val dto = userDetailsMock

    override fun setUp() {
        super.setUp()
        mapper = UserDetailsToUserDetailsModelMapper()
    }

    @Test
    fun `id is mapped correctly`() {
        val result = mapper.map(dto)
        assertThat(result.login).isEqualTo(dto.login)
    }

    @Test
    fun `avatarUrl is mapped correctly`() {
        val result = mapper.map(dto)
        assertThat(result.avatarUrl).isEqualTo(dto.avatarUrl)
    }

    @Test
    fun `name is mapped correctly`() {
        val result = mapper.map(dto)
        assertThat(result.name).isEqualTo(dto.name)
    }

    @Test
    fun `company is mapped correctly`() {
        val result = mapper.map(dto)
        assertThat(result.company).isEqualTo(dto.company)
    }

    @Test
    fun `blogUrl is mapped correctly`() {
        val result = mapper.map(dto)
        assertThat(result.blogUrl).isEqualTo(dto.blogUrl)
    }

    @Test
    fun `location is mapped correctly`() {
        val result = mapper.map(dto)
        assertThat(result.location).isEqualTo(dto.location)
    }

    @Test
    fun `email is mapped correctly`() {
        val result = mapper.map(dto)
        assertThat(result.email).isEqualTo(dto.email)
    }

    @Test
    fun `twitter is mapped correctly`() {
        val result = mapper.map(dto)
        assertThat(result.twitter).isEqualTo(dto.twitter)
    }

    @Test
    fun `followers is mapped correctly`() {
        val result = mapper.map(dto)
        assertThat(result.followers).isEqualTo(dto.followers.toString())
    }

    @Test
    fun `following is mapped correctly`() {
        val result = mapper.map(dto)
        assertThat(result.following).isEqualTo(dto.following.toString())
    }

    @Test
    fun `repos is mapped correctly`() {
        val result = mapper.map(dto)
        assertThat(result.repos).isEqualTo(dto.repos.toString())
    }
}