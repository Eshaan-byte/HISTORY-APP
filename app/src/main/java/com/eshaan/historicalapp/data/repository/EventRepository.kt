package com.eshaan.historicalapp.data.repository

import com.eshaan.historicalapp.data.model.ApiService
import com.eshaan.historicalapp.data.model.HistoricalEvent
import com.eshaan.historicalapp.data.model.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun login(username: String, password: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.login(LoginRequest(username, password))
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.keypass)
                } else {
                    Result.failure(Exception("Login failed: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getDashboard(keypass: String): Result<List<HistoricalEvent>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getDashboard(keypass)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.entities)
                } else {
                    Result.failure(Exception("Failed to load dashboard: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}