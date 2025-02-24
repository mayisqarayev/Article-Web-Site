package com.Article.Web.Site.controller;

import com.Article.Web.Site.dto.request.CreateAccountRequestDto;
import com.Article.Web.Site.dto.request.TransferAccountRequestDto;
import com.Article.Web.Site.dto.request.UpdateAccountRequestDto;
import com.Article.Web.Site.dto.response.AccountInfoResponseDto;
import com.Article.Web.Site.dto.response.AccountResponseDto;
import com.Article.Web.Site.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/account/v1")
public class AccountController {

    private final AccountService service;
    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponseDto getAccountById(@PathVariable String id) {
        return service.getAccountById(id);
    }

    @GetMapping("/account-info/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountInfoResponseDto getAccountInfoById(@PathVariable String id) {
        return service.getAccountInfoById(id);
    }

    @GetMapping("/accounts-by-userid/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponseDto> getAccountsByUserId(@PathVariable String userId) {
        return service.getAccountsByUserId(userId);
    }

    @GetMapping("/most-popular-accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponseDto> getMostPopularAccounts() {
        return service.getMostPopularAccounts();
    }

    @PutMapping("/transfer-account")
    @ResponseStatus(HttpStatus.OK)
    public void transferAccount(@RequestBody TransferAccountRequestDto requestDto) {
        service.transferAccount(requestDto);
    }

    @GetMapping("/caculate-popularity-score/{id}")
    public Double calculatePopularityScore(@PathVariable String id) {
        return service.calculatePopularityScore(id);
    }

    @DeleteMapping("/delete-account/{id}")
    public void deleteAccountById(@PathVariable String id) {
        service.deleteAccountById(id);
    }

    @DeleteMapping("/delete-acounts")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<Void> batchDeleteAccountsByIds(@RequestBody List<String> ids) {
        return service.batchDeleteAccountsByIds(ids);
    }

    @GetMapping("/follower-accounts/{id}")
    public List<AccountResponseDto> getFollowerAccountsById(@PathVariable String id) {
        return service.getFollowerAccountsById(id);
    }

    @GetMapping("/followed-accounts/{id}")
    public List<AccountResponseDto> getFollowedAccountsById(@PathVariable String id) {
        return service.getFollowedAccountsById(id);
    }

    @PostMapping("/create-account")
    public void createAccount(@RequestBody CreateAccountRequestDto requestDto) {
        service.createAccount(requestDto);
    }

    @PutMapping("/update-account")
    public void updateAccount(@RequestBody UpdateAccountRequestDto requestDto) {
        service.updateAccount(requestDto);
    }
}
