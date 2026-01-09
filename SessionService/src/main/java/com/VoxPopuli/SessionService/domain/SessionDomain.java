package com.VoxPopuli.SessionService.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@RedisHash("SessionToken")
@AllArgsConstructor
@NoArgsConstructor
public class SessionDomain implements Serializable {

    @Id
    private String sessionId;
    private String userId;
    @TimeToLive
    private Long expiryInSeconds;

}
