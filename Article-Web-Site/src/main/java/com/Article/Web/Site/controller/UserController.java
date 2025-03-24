package com.Article.Web.Site.controller;

import com.Article.Web.Site.dto.request.CreateUserRequestDto;
import com.Article.Web.Site.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-api")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody CreateUserRequestDto requestDto) {
        service.createUser(requestDto);
    }

}
