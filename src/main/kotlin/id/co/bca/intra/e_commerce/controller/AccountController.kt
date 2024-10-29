package id.co.bca.intra.e_commerce.controller

import id.co.bca.intra.e_commerce.model.Account
import id.co.bca.intra.e_commerce.service.AccountService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/accounts")
class AccountController(private val accountService: AccountService) {

    @PostMapping("/topup/{accountId}/{amount}")
    suspend fun topUp(@PathVariable accountId: Long, @PathVariable amount: Double) {
        accountService.topUp(accountId, amount)

    }

    @PostMapping("/create")
    suspend fun createUser(@RequestBody account: Account) : Account  {
        return accountService.createUser(account)
    }

    @PutMapping("/{id}/name/{name}")
    suspend fun updateUserName(@PathVariable id: Long, @PathVariable name: String): Mono<Account> =
        accountService.updateUserName(id, name).switchIfEmpty(
            Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))

    @PutMapping("/{id}/address/{address}")
    suspend fun updateUserAddress(@PathVariable id: Long, @PathVariable address: String): Mono<Account> =
        accountService.updateUserName(id, address).switchIfEmpty(
            Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteUser(@PathVariable id: Long) : Mono<Void> =
        accountService.deleteUser(id).switchIfEmpty(
            Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)))
}
