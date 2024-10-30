package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.dto.ApiResponse
import id.co.bca.intra.e_commerce.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/order")
class OrderController(private val orderService: OrderService) {

    @PostMapping("/{cartId}")
    suspend fun createOrder(@PathVariable cartId: Long, ) : Mono<ResponseEntity<ApiResponse>> {
        return orderService.createOrder(cartId).map {
            ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse(
                    status = HttpStatus.OK.value(),
                    message = "Create Order Success",
                )
            )
        }.defaultIfEmpty(
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse(
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    message = "Create Order Failed",
                )
            )
        )
    }

    @PutMapping("/{orderId}")
    suspend fun completeOrder(@PathVariable orderId: Long) : Mono<ResponseEntity<ApiResponse>> {
        return orderService.completeOrder(orderId).map {
            ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse(
                    status = HttpStatus.OK.value(),
                    message = "Create Order Success",
                )
            )
        }.defaultIfEmpty(
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse(
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    message = "Create Order Failed",
                )
            )
        )
    }


}