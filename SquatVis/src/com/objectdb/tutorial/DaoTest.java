package com.objectdb.tutorial;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("daotest")
@ApplicationScoped
public class DaoTest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private GuestDao dao;

	public void init() {
		Guest g = new Guest("Dieter");
		// GuestDao dao = new GuestDao();
		dao.persist(g);
	}

	public String getGuestData() {
		// GuestDao dao = new GuestDao();
		String result = "";
		for (Guest guest : dao.getAllGuests()) {
			result = result + guest.toString();
		}
		return result;
	}

}
