package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Order
import id.co.bca.intra.e_commerce.repository.AccountRepository
import id.co.bca.intra.e_commerce.repository.OrderRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val accountRepository: AccountRepository
) {

    suspend fun completeOrder(order: Order) {
    }
}
