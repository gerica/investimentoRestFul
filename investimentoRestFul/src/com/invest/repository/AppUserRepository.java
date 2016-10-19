package com.invest.repository;

import org.springframework.data.repository.CrudRepository;

import com.invest.entidade.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

	AppUser findByUsername(String username);

	AppUser findByUsernameAndPassword(String username, String password);

}
