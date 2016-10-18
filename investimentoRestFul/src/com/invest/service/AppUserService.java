package com.invest.service;

import com.invest.entidade.AppUser;

/**
 * Created by LynAs on 20-Mar-16
 */
public interface AppUserService {
    AppUser loadUserByUsername(String username);

    long post(AppUser appUser);

    AppUser get(long id);

    AppUser patch(AppUser appUser);

    boolean delete(long id);
}
