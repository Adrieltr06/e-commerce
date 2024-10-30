package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.repository.AccountRepository
import id.co.bca.intra.e_commerce.util.DatabaseException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AccountService(private val accountRepository: AccountRepository) {

    suspend fun topUp(accountId: Long, amount: Double) : Mono<Account> {
        return accountRepository.findById(accountId).flatMap {
            val updatedAccount = it.copy(balance = it.balance + amount)
            accountRepository.save(updatedAccount)
        }.onErrorResume{
            e -> Mono.error(DatabaseException("Failed to topup account", e))
        }
    }

    suspend fun createUser(account: Account): Mono<Account> {
        return accountRepository.save(account).onErrorResume {
            e -> Mono.error(DatabaseException("Failed to create account", e))
        }
    }

    suspend fun updateAccount(id: Long, account: Account): Mono<Account> {
        return accountRepository.findById(id)
            .flatMap {
                val updatedAccount = it.copy(name = it.name, address = it.address, balance = it.balance, isAdmin = it.isAdmin)
                accountRepository.save(updatedAccount)
            }
    }

    suspend fun deleteUser(id: Long): Mono<Void> {
        return accountRepository.deleteById(id).onErrorResume{
            e -> Mono.error(DatabaseException("Failed to delete account", e))
        }
    }

    suspend fun getListAccounts(): Flux<Account> = accountRepository.findAll()

    suspend fun getAccountById(id: Long): Mono<Account> = accountRepository.findById(id)
}
