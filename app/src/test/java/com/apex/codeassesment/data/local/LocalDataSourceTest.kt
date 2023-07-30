package com.apex.codeassesment.data.local


import com.apex.codeassesment.data.model.User
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LocalDataSourceTest {

    @Test
    fun `test loadUser when data is available`() {
        // Mock PreferencesManager
        val preferencesManager: PreferencesManager = mockk()
        val moshi : Moshi = mockk()
        val localDataSource = LocalDataSource(preferencesManager , moshi)

        // Mock serialized user data
        val serializedUser = """{"name":{"first":"John","last":"Doe"},"email":"john.doe@example.com"}"""

        // Define the behavior of PreferencesManager
        coEvery { preferencesManager.loadUser() } returns serializedUser


        // Call the function under test
        val user = localDataSource.loadUser()

        // Assert the result
        assertEquals("John", user.name?.first)
        assertEquals("Doe", user.name?.last)
        assertEquals("john.doe@example.com", user.email)
    }

    @Test
    fun `test loadUser when data is not available`() {
        // Mock PreferencesManager
        val preferencesManager: PreferencesManager = mockk()
        val moshi : Moshi = mockk()

        val localDataSource = LocalDataSource(preferencesManager , moshi)

        // Define the behavior of PreferencesManager to return null

        coEvery{preferencesManager.loadUser()} returns null

        // Call the function under test
        val user = localDataSource.loadUser()

        // Assert the result
        assertTrue(user.name?.first?.isNotBlank() ?: false)
        assertTrue(user.name?.last?.isNotBlank() ?: false)
    }

    // Your test function
//    @Test
//    fun `test saveUser`() {
//        val preferencesManagerMock = mockk<PreferencesManager>()
//
//        val argumentCaptor = slot<String>()
//        coEvery { preferencesManagerMock.saveUser(capture(argumentCaptor)) } coAnswers { null }
//
//
//        // Assertions
//        val serializedUser = argumentCaptor.captured
//        val expectedUserJson = """{"name":{"first":"John","last":"Doe"},"email":"john.doe@example.com"}"""
//
//        // Parse the JSON strings and compare the parsed objects
//        val expectedUser = Gson().fromJson(expectedUserJson, User::class.java)
//        val actualUser = Gson().fromJson(serializedUser, User::class.java)
//
//        assertEquals(expectedUser, actualUser)
//    }
}
