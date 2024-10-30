package id.co.bca.intra.e_commerce.service

import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.model.Product
import id.co.bca.intra.e_commerce.repository.AccountRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
class AccountServiceTest {

    private val accountRepository = mock(AccountRepository::class.java)
    private val accountService = AccountService(accountRepository)

    @Test
    fun `test getListProduct return products`() {

        runBlocking {
            // Mock Data
            val accounts = listOf(
                Account(1, "Bayu", "Jakarta Barat", 1000.00, false),
                Account(2, "Agus", "Jakarta Timur", 2000.00, false),
                Account(3, "Budi", "Jakarta Selatam", 2000.00, false),
            )

            `when`(accountRepository.findAll()).thenReturn(Flux.fromIterable(accounts))

            //Verify behavior
            val result = accountService.getListAccounts()
            StepVerifier.create(result)
                .expectNext(accounts[0])
                .expectNext(accounts[1])
                .expectNext(accounts[2])
                .verifyComplete()
        }

    }

    @Test
    fun `test createProduct save product`() {

        runBlocking {
            //Mock Data
            val account = Account(1, "Bayu", "Jakarta Barat", 1000.00, false)

            `when`(accountRepository.save(account))
                .thenReturn(Mono.just(account.copy(id = 1)))

            //Verify Behavior
            val result = accountService.createUser(account)

            StepVerifier.create(result)
                .expectNextMatches {
                    it.id == 1L && it.name == "Bayu" && it.address == "Jakarta Barat" && it.balance == 1000.00 && !it.isAdmin
                }.verifyComplete()
        }

    }

    @Test
    fun `test deleteUser deletes user`() {
        runBlocking{
            // Mock behavior
            `when`(accountRepository.deleteById(1)).thenReturn(Mono.empty())

            // Verify behavior
            val result = accountService.deleteUser(1)
            StepVerifier.create(result).verifyComplete()
        }
    }

    @Test
    fun `test getAccount return account`() {

        runBlocking {
            // Mock Data
            val account = Account(1, "Bayu", "Jakarta Barat", 1000.00, false)

            // Mock Behavior
            `when`(accountRepository.findById(1)).thenReturn(Mono.just(account))

            //Verify behavior
            val result = accountRepository.findById(1)
            StepVerifier.create(result)
                .expectNext(account)
                .verifyComplete()
        }
    }
}