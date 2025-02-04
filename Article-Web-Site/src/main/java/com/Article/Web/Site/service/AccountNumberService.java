package com.Article.Web.Site.service;

import com.Article.Web.Site.dto.request.AccountNumberRequestDto;
import com.Article.Web.Site.model.AccountNumberEntity;
import com.Article.Web.Site.repo.AccountNumberRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AccountNumberService {

    private final AccountNumberRepository repository;
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    public AccountNumberService(AccountNumberRepository repository) {
        this.repository = repository;
    }

    public String generateAccountNumber() {
        List<String> existingNumbers = repository.findAll().stream()
                .map(AccountNumberEntity::getNumberOfAccount)
                .toList();

        String number;
        do {
            number = IntStream.range(0, 9)
                    .mapToObj(i -> String.valueOf(CHARS.charAt(RANDOM.nextInt(CHARS.length()))))
                    .collect(Collectors.joining());
        } while (existingNumbers.contains(number));

        return number;
    }

    protected void saveNumber(AccountNumberRequestDto requestDto) {

    }
}
