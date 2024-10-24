package com.example.demo.repository;

import com.example.demo.entity.TokenBlackList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenBlackListRepository extends MongoRepository<TokenBlackList, String> {
    void insert(String token);

    boolean existsByToken(String token);
}
