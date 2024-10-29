package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.dto.ApiResponse
import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.service.AccountService
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/api/accounts")
class AccountController(private val accountService: AccountService) {

    @GetMapping
    suspend fun getListAccount(): Mono<ResponseEntity<ApiResponse>> {
        return accountService.getListAccounts().collectList().flatMap { list ->
            if(list.isEmpty()){
                Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse(
                        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        message = "Account not found"
                    )
                ))
            } else {
                Mono.just(ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse(
                        status = HttpStatus.OK.value(),
                        message = "Success Get List of Accounts",
                        data = list
                    )
                ))
            }
        }
    }

    @GetMapping("/{accountId}")
    suspend fun getOneAccount(@PathVariable accountId: Long): Mono<ResponseEntity<ApiResponse>> {

        val account = accountService.getAccountById(accountId)

        return account.hasElement().flatMap { hasAccount ->
            if(hasAccount){
                Mono.just(ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse(
                        status = HttpStatus.OK.value(),
                        message = "Account found",
                        data = account
                    )
                ))
            } else {
                Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse(
                        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        message = "Account not found",
                        data = null
                    )
                ))
            }
        }
    }

    @PostMapping("/topup/{accountId}/{amount}")
    suspend fun topUp(@PathVariable accountId: Long, @PathVariable amount: Double) : Mono<ResponseEntity<ApiResponse>> {
        return accountService.topUp(accountId, amount).map {
            ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse(
                    status = HttpStatus.OK.value(),
                    message = "Topup Success",
                )
            )
        }.defaultIfEmpty(
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse(
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    message = "Topup Account Failed",
                )
            )
        )
    }

    @PostMapping("/create")
    suspend fun createUser(@RequestBody account: Account) : Mono<ResponseEntity<ApiResponse>>  {
        return accountService.createUser(account).map {
            ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse(
                    status = HttpStatus.OK.value(),
                    message = "Account Created"
                )
            )
        }.defaultIfEmpty(
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse(
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    message = "Create Account Failed",
                )
            )
        )
    }

    @PutMapping("/{accountId}/name/{name}")
    suspend fun updateUserName(@PathVariable accountId: Long, @RequestBody account: Account): Mono<ResponseEntity<ApiResponse>> {
        return accountService.updateAccount(accountId, account).map {
            ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse(
                    status = HttpStatus.OK.value(),
                    message = "Account Created"
                )
            )
        }.defaultIfEmpty(
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse(
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    message = "Create Account Failed",
                )
            )
        )
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    suspend fun deleteUser(@PathVariable accountId: Long) : Mono<ResponseEntity<ApiResponse>> {
        return accountService.deleteUser(accountId).map {
            ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse(
                    status = HttpStatus.OK.value(),
                    message = "Account Deleted Successfully",
                )
            )
        }
    }
}
