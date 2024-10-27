package id.co.bca.intra.e_commerce.repository

import id.co.bca.intra.e_commerce.model.Account
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface AccountRepository : ReactiveCrudRepository<Account, Long>