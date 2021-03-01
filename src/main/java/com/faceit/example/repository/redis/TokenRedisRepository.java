package com.faceit.example.repository.redis;

import com.faceit.example.model.redis.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRedisRepository extends CrudRepository<ConfirmationToken, String> {

}
