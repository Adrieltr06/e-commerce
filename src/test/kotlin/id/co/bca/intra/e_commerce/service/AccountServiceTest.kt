package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.repository.AccountRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
class AccountServiceTest {

    private val accountRepository = mock(AccountRepository::class.java)
    private val accountService = AccountService(accountRepository)

    @Test
    fun `test topUp increases account balance`() {
        // Mock data
        val accountId = 1L
        val initialBalance = 100.0
        val topUpAmount = 50.0
        val account = Account(id = accountId, name = "Test User", address = "Test Address", balance = initialBalance, isAdmin = false)
        val updatedAccount = account.copy(balance = initialBalance + topUpAmount)

        `when`(accountRepository.findById(accountId)).thenReturn(Mono.just(account))
        `when`(accountRepository.save(updatedAccount)).thenReturn(Mono.just(updatedAccount))

        // Verify behavior
        val result = accountService.topUp(accountId, topUpAmount)
        StepVerifier.create(result)
            .expectNextMatches { it.balance == initialBalance + topUpAmount }
            .verifyComplete()
    }

    @Test
    fun `test createUser saves new account`() {
        // Mock data
        val account = Account(id = 1L, name = "Test User", address = "Test Address", balance = 100.0, isAdmin = false)

        `when`(accountRepository.save(account)).thenReturn(Mono.just(account))

        // Verify behavior
        val result = accountService.createUser(account)
        StepVerifier.create(result)
            .expectNextMatches { it == account }
            .verifyComplete()
    }

    @Test
    fun `test updateAccount updates existing account`() {
        // Mock data
        val accountId = 1L
        val account = Account(id = accountId, name = "Old Name", address = "Old Address", balance = 100.0, isAdmin = false)
        val updatedAccount = account.copy(name = "New Name", address = "New Address")

        `when`(accountRepository.findById(accountId)).thenReturn(Mono.just(account))
        `when`(accountRepository.save(updatedAccount)).thenReturn(Mono.just(updatedAccount))

        // Verify behavior
        val result = accountService.updateAccount(accountId, updatedAccount)
        StepVerifier.create(result)
            .expectNextMatches { it.name == "New Name" && it.address == "New Address" }
            .verifyComplete()
    }

    @Test
    fun `test deleteUser deletes account`() {
        // Mock behavior
        `when`(accountRepository.deleteById(1L)).thenReturn(Mono.empty())

        // Verify behavior
        val result = accountService.deleteUser(1L)
        StepVerifier.create(result).verifyComplete()
    }

    @Test
    fun `test getListAccounts returns all accounts`() {
        // Mock data
        val accounts = listOf(
            Account(id = 1L, name = "User 1", address = "Address 1", balance = 100.0, isAdmin = false),
            Account(id = 2L, name = "User 2", address = "Address 2", balance = 200.0, isAdmin = true)
        )

        `when`(accountRepository.findAll()).thenReturn(Flux.fromIterable(accounts))

        // Verify behavior
        val result = accountService.getListAccounts()
        StepVerifier.create(result)
            .expectNext(accounts[0])
            .expectNext(accounts[1])
            .verifyComplete()
    }

    @Test
    fun `test getAccountById returns account`() {
        // Mock data
        val accountId = 1L
        val account = Account(id = accountId, name = "Test User", address = "Test Address", balance = 100.0, isAdmin = false)

        `when`(accountRepository.findById(accountId)).thenReturn(Mono.just(account))

        // Verify behavior
        val result = accountService.getAccountById(accountId)
        StepVerifier.create(result)
            .expectNext(account)
            .verifyComplete()
    }
}
