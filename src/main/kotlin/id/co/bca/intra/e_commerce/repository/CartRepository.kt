package id.co.bca.intra.e_commerce.repository

import id.co.bca.intra.e_commerce.model.Cart
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface CartRepository: ReactiveCrudRepository<Cart, Long> {
    fun findByAccountId(accountId: Long): Mono<Cart>
}


