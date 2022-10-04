package com.example.assignment.data.repository

import android.accounts.NetworkErrorException
import com.example.assignment.data.api.ApiService
import com.example.assignment.data.model.PullRequest
import com.example.assignment.data.model.User
import com.example.assignment.ui.utils.Status
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class MainRepositoryTest : TestCase() {
    private lateinit var systemUnderTest: MainRepository

    private val dispatcher = TestCoroutineDispatcher()
    private lateinit var apiService: ApiService

    @Test
    fun testGetClosedPullRequestsSuccessCase() {
        apiService = SuccessDataService()
        systemUnderTest = MainRepository(apiService)
        val page = 1
        runBlocking {
            val result = systemUnderTest.getClosedPullRequests(page, dispatcher)
            assertEquals(result.status, Status.SUCCESS)
        }
    }

    @Test
    fun testGetClosedPullRequestsNetworkErrorCase() {
        apiService = NetworkErrorDataService()
        systemUnderTest = MainRepository(apiService)
        val page = 1
        runBlocking {
            val result = systemUnderTest.getClosedPullRequests(page, dispatcher)
            assertEquals(result.status, Status.NETWORK_ERROR)
        }
    }

    @Test
    fun testGetClosedPullRequestsAPIErrorCase() {
        apiService = APIErrorDataService()
        systemUnderTest = MainRepository(apiService)
        val page = 1
        runBlocking {
            val result = systemUnderTest.getClosedPullRequests(page, dispatcher)
            assertEquals(result.status, Status.API_ERROR)
        }
    }

    class SuccessDataService:ApiService{
        private val mockedPullRequestsData = listOf(PullRequest(title = "Pull request 1",
                                                user = User(user_name = "Sunil", avatar = ""),
                                                created_at = "08-08-2022", closed_at = "08-08-2022"),
                                            PullRequest(title = "Pull request 2",
                                                user = User(user_name = "Sunil", avatar = ""),
                                                created_at = "08-08-2022", closed_at = "08-08-2022") )
        override suspend fun getClosedPullRequests(page: Int): List<PullRequest> {
            return mockedPullRequestsData
        }
    }

    class NetworkErrorDataService:ApiService{
        override suspend fun getClosedPullRequests(page: Int): List<PullRequest> {
            throw NetworkErrorException()
        }
    }

    class APIErrorDataService:ApiService{
        override suspend fun getClosedPullRequests(page: Int): List<PullRequest> {
            throw IOException()
        }
    }
}

