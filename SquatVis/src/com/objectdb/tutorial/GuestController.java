package com.objectdb.tutorial;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class GuestController {
	    private Guest guest;
	    private List<Guest> guests;
	    int i = 0;

	    @EJB
	    private GuestDao service;

	    @PostConstruct
	    public void init() {
	    	guest = new Guest();
	    	guests = service.list();
	    }
	    
	    public void add() {
	    	guest.setName("Dieter" + i);
	    	guest.setDate();
	        service.save(guest);
	        init();
	        i++;
	        }

	    public Guest getGuest() { 
	        return guest;
	    }

	    public List<Guest> getGuests() {
	        return guests;
	    }

}
