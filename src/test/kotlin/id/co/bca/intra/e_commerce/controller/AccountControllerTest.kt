package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.service.AccountService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@WebFluxTest(controllers = [AccountController::class])
class AccountControllerTest {

    @MockBean
    private lateinit var accountService: AccountService

    @Test
    fun `test getListProduct return products`() {

        runBlocking {
            // Mock Data
            val accounts = listOf(
                Account(1, "Bayu", "Jakarta Barat", 1000.00, false),
                Account(2, "Agus", "Jakarta Timur", 2000.00, false),
                Account(3, "Budi", "Jakarta Selatam", 2000.00, false),
            )

            `when`(accountService.getListAccounts()).thenReturn(Flux.fromIterable(accounts))

            val client = WebTestClient.bindToController(AccountController(accountService)).build()

            client.get().uri("/api/accounts")
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Account::class.java)
                .hasSize(3)
                .contains(accounts[0], accounts[1], accounts[2])
        }

    }

    @Test
    fun `test createProduct save product`() {

        runBlocking {
            // Mock Data
            val account = Account(1, "Bayu", "Jakarta Barat", 1000.00, false)

            // Mock Behavior
            `when`(accountService.createUser(account)).thenReturn(Mono.just(account.copy(id = 1)))

            // Verify Behavior
            val client = WebTestClient.bindToController(AccountController(accountService)).build()

            client.post().uri("/api/accounts")
                .bodyValue(account)
                .exchange()
                .expectStatus().isCreated
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Bayu")
                .jsonPath("$.address").isEqualTo("Jakarta Barat")
                .jsonPath("$.balance").isEqualTo(1000.00)
                .jsonPath("$.isAdmin").isEqualTo(false)
        }

    }

    @Test
    fun `test getAccount return account`() {

        runBlocking {

            val account = Account(1, "Bayu", "Jakarta Barat", 1000.00, false)

            `when`(accountService.getAccountById(1)).thenReturn(Mono.just(account.copy(id = 1)))

            // Verify Behavior
            val client = WebTestClient.bindToController(AccountController(accountService)).build()

            client.post().uri("/api/accounts/1")
                .exchange()
                .expectStatus().isCreated
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Bayu")
                .jsonPath("$.address").isEqualTo("Jakarta Barat")
                .jsonPath("$.balance").isEqualTo(1000.00)
                .jsonPath("$.isAdmin").isEqualTo(false)
        }
    }

}