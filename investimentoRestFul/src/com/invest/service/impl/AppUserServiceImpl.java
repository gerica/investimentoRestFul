package com.invest.service.impl;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invest.entidade.AppUser;
import com.invest.repository.AppUserRepository;
import com.invest.service.AppUserService;

@Service(value = "appUserService")
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	@Transactional
	public AppUser loadUserByUsername(String username) {
//		sessionFactory.getCurrentSession().createCriteria(AppUser.class).add(Restrictions.eq("username", username));
		return (AppUser) sessionFactory.getCurrentSession().createCriteria(AppUser.class).add(Restrictions.eq("username", username))
				.uniqueResult();
	}

	@Transactional
	@Override
	public long post(AppUser appUser) {
		return (long) sessionFactory.getCurrentSession().save(appUser);
	}

	@Transactional
	@Override
	public AppUser get(long id) {
		return (AppUser) sessionFactory.getCurrentSession().get(AppUser.class, id);
	}

	@Transactional
	@Override
	public AppUser patch(AppUser appUser) {
		sessionFactory.getCurrentSession().update(appUser);
		return get(appUser.getId());
	}

	@Transactional
	@Override
	public boolean delete(long id) {
		sessionFactory.getCurrentSession().delete(get(id));
		return true;
	}
}
