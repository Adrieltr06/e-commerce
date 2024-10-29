package id.co.bca.intra.e_commerce.util

import id.co.bca.intra.e_commerce.dto.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import reactor.core.publisher.Mono

@ControllerAdvice
class GlobalErrorHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): Mono<ResponseEntity<ApiResponse>> {
        val errorResponse = ApiResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Unexpected error occured",
            data = "Internal Server Error"
        )
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse))
    }

}