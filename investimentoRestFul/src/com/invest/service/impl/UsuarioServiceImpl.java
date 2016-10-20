package com.invest.service.impl;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invest.entidade.Usuario;
import com.invest.service.UsuarioService;

@Service(value = "appUserService")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	@Transactional
	public Usuario loadUserByUsername(String username) {
//		sessionFactory.getCurrentSession().createCriteria(AppUser.class).add(Restrictions.eq("username", username));
		return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class).add(Restrictions.eq("username", username))
				.uniqueResult();
	}

	@Transactional
	@Override
	public long post(Usuario appUser) {
		return (long) sessionFactory.getCurrentSession().save(appUser);
	}

	@Transactional
	@Override
	public Usuario get(long id) {
		return (Usuario) sessionFactory.getCurrentSession().get(Usuario.class, id);
	}

	@Transactional
	@Override
	public Usuario patch(Usuario appUser) {
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
