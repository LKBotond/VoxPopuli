package com.VoxPopuli.SessionService.repositories;

import com.VoxPopuli.SessionService.domain.SessionDomain;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepo extends CrudRepository<SessionDomain, String> {

}
