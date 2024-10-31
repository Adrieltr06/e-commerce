package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.dto.ApiResponse
import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.service.AccountService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(controllers = [AccountController::class])
class AccountControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var accountService: AccountService

    @Test
    fun `test getListAccount returns list of accounts`() {
        // Mock data
        val accounts = listOf(
            Account(id = 1L, name = "User 1", address = "Address 1", balance = 100.0, isAdmin = false),
            Account(id = 2L, name = "User 2", address = "Address 2", balance = 200.0, isAdmin = true)
        )

        given(accountService.getListAccounts()).willReturn(Flux.fromIterable(accounts))

        // Verify response
        webTestClient.get()
            .uri("/api/accounts")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo(HttpStatus.OK.value())
            .jsonPath("$.message").isEqualTo("Success Get List of Accounts")
            .jsonPath("$.data").isArray
            .jsonPath("$.data[0].name").isEqualTo("User 1")
    }

    @Test
    fun `test getOneAccount returns account`() {
        // Mock data
        val accountId = 1L
        val account = Account(id = accountId, name = "User 1", address = "Address 1", balance = 100.0, isAdmin = false)

        given(accountService.getAccountById(accountId)).willReturn(Mono.just(account))

        // Verify response
        webTestClient.get()
            .uri("/api/accounts/$accountId")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo(HttpStatus.OK.value())
            .jsonPath("$.message").isEqualTo("Account found")
            .jsonPath("$.data.name").isEqualTo("User 1")
    }

    @Test
    fun `test topUp increases account balance`() {
        // Mock data
        val accountId = 1L
        val amount = 50.0
        val updatedAccount = Account(id = accountId, name = "User 1", address = "Address 1", balance = 150.0, isAdmin = false)

        given(accountService.topUp(accountId, amount)).willReturn(Mono.just(updatedAccount))

        // Verify response
        webTestClient.post()
            .uri("/api/accounts/topup/$accountId/$amount")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo(HttpStatus.OK.value())
            .jsonPath("$.message").isEqualTo("Topup Success")
    }

    @Test
    fun `test createUser creates a new account`() {
        // Mock data
        val account = Account(id = 1L, name = "New User", address = "New Address", balance = 100.0, isAdmin = false)

        given(accountService.createUser(account)).willReturn(Mono.just(account))

        // Verify response
        webTestClient.post()
            .uri("/api/accounts/create")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(account)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo(HttpStatus.OK.value())
            .jsonPath("$.message").isEqualTo("Account Created")
    }

    @Test
    fun `test updateUserName updates account name`() {
        // Mock data
        val accountId = 1L
        val updatedAccount = Account(id = accountId, name = "Updated Name", address = "Old Address", balance = 100.0, isAdmin = false)

        given(accountService.updateAccount(accountId, updatedAccount)).willReturn(Mono.just(updatedAccount))

        // Verify response
        webTestClient.put()
            .uri("/api/accounts/$accountId/name/Updated Name")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(updatedAccount)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo(HttpStatus.OK.value())
            .jsonPath("$.message").isEqualTo("Account Created")
    }

    @Test
    fun `test deleteUser deletes account`() {
        // Mock behavior
        val accountId = 1L
        given(accountService.deleteUser(accountId)).willReturn(Mono.empty())

        // Verify response
        webTestClient.delete()
            .uri("/api/accounts/$accountId")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo(HttpStatus.OK.value())
            .jsonPath("$.message").isEqualTo("Account Deleted Successfully")
    }
}
