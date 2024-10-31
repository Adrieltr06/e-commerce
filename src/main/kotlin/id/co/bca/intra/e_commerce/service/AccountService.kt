package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.repository.AccountRepository
import id.co.bca.intra.e_commerce.util.DatabaseException
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class AccountService(private val accountRepository: AccountRepository) {

    fun topUp(accountId: Long, amount: Double) : Mono<Account> {
        return accountRepository.findById(accountId).flatMap {
            val updatedAccount = it.copy(balance = it.balance + amount)
            accountRepository.save(updatedAccount)
        }.onErrorResume{
            e -> Mono.error(DatabaseException("Failed to topup account", e))
        }
    }

    fun createUser(account: Account): Mono<Account> {
        return accountRepository.save(account).onErrorResume {
            e -> Mono.error(DatabaseException("Failed to create account", e))
        }
    }

    fun updateAccount(id: Long, account: Account): Mono<Account> {
        return accountRepository.findById(id)
            .flatMap {
                val updatedAccount = it.copy(name = it.name, address = it.address, balance = it.balance, isAdmin = it.isAdmin)
                accountRepository.save(updatedAccount)
            }
    }

    fun deleteUser(id: Long): Mono<Void> {
        return accountRepository.deleteById(id).onErrorResume{
            e -> Mono.error(DatabaseException("Failed to delete account", e))
        }
    }

    fun getListAccounts(): Flux<Account> = accountRepository.findAll()

    fun getAccountById(id: Long): Mono<Account> = accountRepository.findById(id)
}
