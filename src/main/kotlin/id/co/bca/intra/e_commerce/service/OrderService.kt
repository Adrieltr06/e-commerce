package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Cart
import id.co.bca.intra.e_commerce.model.Order
import id.co.bca.intra.e_commerce.repository.AccountRepository
import id.co.bca.intra.e_commerce.repository.CartRepository
import id.co.bca.intra.e_commerce.repository.OrderRepository
import id.co.bca.intra.e_commerce.util.DatabaseException
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val accountRepository: AccountRepository,
    private val cartRepository: CartRepository
) {

    suspend fun createOrder(cartId: Long) : Mono<Order> {
        return cartRepository.findById(cartId).flatMap { cart ->
            var totalQuantity = 0
            var totalAmount = 0.00

            for ((key, value) in cart.products) {
                totalQuantity+=value
                totalAmount+=key.price * value
            }

            val newOrder = Order(id = null, account = cart.account, products = cart.products, status = "Outstanding", totalQuantity = totalQuantity, totalAmount = totalAmount)
            orderRepository.save(newOrder)

        }.onErrorResume { e ->
            Mono.error(DatabaseException("Failed to create order", e))
        }
    }

    suspend fun completeOrder(orderId: Long) : Mono<Order> {
        return orderRepository.findById(orderId).flatMap {
            var completeOrder = it.copy(status = "Complete", products = it.products, account = it.account, id = it.id, totalQuantity = it.totalQuantity, totalAmount = it.totalAmount)
            orderRepository.save(completeOrder)
        }.onErrorResume {
            e -> Mono.error(DatabaseException("Failed to complete order", e))
        }
    }
}
