package id.co.bca.intra.e_commerce.repository

import id.co.bca.intra.e_commerce.model.Order
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface OrderRepository: ReactiveCrudRepository<Order, Long>