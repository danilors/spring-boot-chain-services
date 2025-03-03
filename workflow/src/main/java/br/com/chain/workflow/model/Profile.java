package br.com.chain.workflow.model;

import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "Profile", timeToLive = 60)
public record Profile(String id, String name, String email, int addressId, int occupationId) {
}
