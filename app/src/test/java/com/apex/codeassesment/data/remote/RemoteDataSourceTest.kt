import com.apex.codeassesment.ApiClient
import com.apex.codeassesment.ApiResponse
import com.apex.codeassesment.data.model.Info
import com.apex.codeassesment.data.model.LoadUserApiResponse
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.RemoteDataSource
import com.apex.codeassesment.interfaces.ApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.lang.Exception
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class RemoteDataSourceTest {
    @Test
    fun `test loadUser() success response`()= runBlocking {
        val apiService: ApiClient = mockk()
        val remoteDataSource = RemoteDataSource(apiService)

        val user = User.createRandom()
        val response =
            Response.success( LoadUserApiResponse(info = Info(0,0,"","")
                ,results = listOf(user)))
        coEvery { apiService.service.loadUser() } returns response


        val result = remoteDataSource.loadUser()


        assert(response.body()?.results?.isNotEmpty() == true)

    }
    @Test
    fun `test loadUser failure`() = runBlocking {

        val apiService: ApiClient = mockk()
        val remoteDataSource = RemoteDataSource(apiService)

        val errorResponse = Response.error<LoadUserApiResponse>(404, mock())

        coEvery { apiService.service.loadUser() } returns errorResponse


        val result = remoteDataSource.loadUser()


        assertEquals(errorResponse.body(), result.results)
    }

    @Test
    fun `test loadUser exception`() = runBlocking {

        val apiService: ApiClient = mockk()
        val remoteDataSource = RemoteDataSource(apiService)


        coEvery { apiService.service.loadUser() } throws Exception("API call failed")


        val result = remoteDataSource.loadUser()


        assertNull(result.results)
        assertEquals("API call failed", result.error)
    }


/*    @Test
    fun `test loadUser success`() = runBlocking {

        val apiService: ApiClient = mockk()
        val remoteDataSource = RemoteDataSource(apiService)

        val user = User.createRandom()
        val response =
            LoadUserApiResponse(info = Info(0,0,"","")
                ,results = listOf(user))
        coEvery { apiService.service.loadUser() } returns response


        val result = remoteDataSource.loadUser()


        assertEquals(response, result)
    }

*/

}
