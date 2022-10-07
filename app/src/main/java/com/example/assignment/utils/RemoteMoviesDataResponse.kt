package com.example.assignment.utils

import com.example.assignment.data.model.Status
import com.example.assignment.data.model.entity.Movie

sealed class RemoteMoviesDataResponse {
    abstract val status: Status
    class SuccessResponse(override val status: Status = Status.SUCCESS, val data:List<Movie>): RemoteMoviesDataResponse()
    class ErrorResponse(override var status: Status = Status.API_ERROR): RemoteMoviesDataResponse()
}