package com.apex.codeassesment.data.local


import android.content.SharedPreferences
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PreferencesManagerTest {

    @Mock
    private lateinit var sharedPreferencesMock: SharedPreferences

    @Mock
    private lateinit var editorMock: SharedPreferences.Editor

    private lateinit var preferencesManager: PreferencesManager

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(sharedPreferencesMock.edit()).thenReturn(editorMock)
        preferencesManager = PreferencesManager(sharedPreferencesMock)
    }

    @Test
    fun testSaveUser() {
        // Given
        val userJson = """{"name":{"first":"John","last":"Doe"},"email":"john.doe@example.com"}"""

        // When
        preferencesManager.saveUser(userJson)

        // Then
        verify(editorMock).putString("saved-user", userJson)
        verify(editorMock).apply()
    }

    @Test
    fun testLoadUser() {
        // Given
        val userJson = """{"name":{"first":"John","last":"Doe"},"email":"john.doe@example.com"}"""
        `when`(sharedPreferencesMock.getString("saved-user", null)).thenReturn(userJson)

        // When
        val loadedUser = preferencesManager.loadUser()

        // Then
        assertEquals(userJson, loadedUser)
    }

    @Test
    fun testLoadUserNull() {
        // Given
        `when`(sharedPreferencesMock.getString("saved-user", null)).thenReturn(null)

        // When
        val loadedUser = preferencesManager.loadUser()

        // Then
        assertEquals(null, loadedUser)
    }
}
