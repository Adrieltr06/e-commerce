package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.repository.AccountRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service

@Service
class AccountService(private val accountRepository: AccountRepository) {

    suspend fun topUp(accountId: Long, amount: Double) {
        val account = accountRepository.findById(accountId).awaitFirstOrNull()
        if (account != null) {
            account.balance += amount
            accountRepository.save(account).awaitFirstOrNull()
        }
    }

    suspend fun createUser(account: Account): Account {
        return accountRepository.save(account).awaitFirstOrNull() ?: throw Exception("Failed to create user")
    }

    suspend fun updateUserName(id: Long, name: String): Mono<Account> {
        return accountRepository.findById(id)
            .flatMap {
                val updatedAccount = it.copy(name = name, address = it.address, balance = it.balance, isAdmin = it.isAdmin)
                accountRepository.save(updatedAccount)
            }
    }

    suspend fun updateUserAddress(id: Long, address: String): Mono<Account> {
        return accountRepository.findById(id)
            .flatMap {
                val updatedAccount = it.copy(name = it.name, address = address, balance = it.balance, isAdmin = it.isAdmin)
                accountRepository.save(updatedAccount)
            }
    }

    suspend fun deleteUser(id: Long): Mono<Void> = accountRepository.deleteById(id)
}
