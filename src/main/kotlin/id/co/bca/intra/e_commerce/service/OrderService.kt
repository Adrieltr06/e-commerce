package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Order
import id.co.bca.intra.e_commerce.repository.AccountRepository
import id.co.bca.intra.e_commerce.repository.OrderRepository
import id.co.bca.intra.e_commerce.util.DatabaseException
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {

    fun createOrder(order: Order) : Mono<Order> {
        return orderRepository.save(order).onErrorResume {
            e -> Mono.error(DatabaseException("Failed to create order", e))
        }
    }

    fun completeOrder(orderId: Long) : Mono<Order> {
        return orderRepository.findById(orderId).flatMap {
            var completeOrder = it.copy(status = "Complete", products = it.products, account = it.account, id = it.id, totalQuantity = it.totalQuantity, totalAmount = it.totalAmount)
            orderRepository.save(completeOrder)
        }.onErrorResume {
            e -> Mono.error(DatabaseException("Failed to complete order", e))
        }
    }
}
