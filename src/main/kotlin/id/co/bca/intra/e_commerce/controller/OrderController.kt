package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.dto.ApiResponse
import id.co.bca.intra.e_commerce.model.Order
import id.co.bca.intra.e_commerce.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/order")
class OrderController(private val orderService: OrderService) {

    //TODO MASIH PERLU LOGIC
    @PostMapping
    suspend fun createOrder(order: Order) : Mono<ResponseEntity<ApiResponse>> {
        return Mono.just(
            ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse(
                    status = HttpStatus.OK.value(),
                    message = "Create Order Success",
                )
            )
        )
    }

    //TODO MASIH PERLU LOGIC
    @PutMapping
    suspend fun completeOrder(order: Order) : Mono<ResponseEntity<ApiResponse>> {
        return Mono.just(
            ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse(
                    status = HttpStatus.OK.value(),
                    message = "Complete Order Success",
                )
            )
        )
    }
}