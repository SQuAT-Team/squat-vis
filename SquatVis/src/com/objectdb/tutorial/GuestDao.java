package com.objectdb.tutorial;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class GuestDao implements Serializable {
	private static final long serialVersionUID = -3735053544541491231L;

	// Injected database connection:
	@PersistenceContext
	private EntityManager em;

	// Stores a new guest:
	public void persist(Guest guest) {
		em.persist(guest);
	}

	// Retrieves all the guests:
	public List<Guest> getAllGuests() {
		TypedQuery<Guest> query = em.createQuery("SELECT g FROM Guest g ORDER BY g.id", Guest.class);
		return query.getResultList();
	}

	public String test() {
		return "test";
	}

	public List<Guest> list() {
		return em.createQuery("SELECT g FROM Guest g", Guest.class).getResultList();
		}

	public Guest find(Integer id) {
		return em.find(Guest.class, id);
	}

	public void save(Guest book) {
		em.persist(book);
	}

	public void update(Guest book) {
		em.merge(book);
	}

	public void delete(Guest book) {
		em.remove(em.contains(book) ? book : em.merge(book));
	}

}