package org.squat_team.vis.util;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ClassReturner {

	public String check(String name, boolean check) {
		if(check) {
			return name;
		}else {
			return "";
		}
	}
	
	public String checkNot(String name, boolean check) {
		if(!check) {
			return name;
		}else {
			return "";
		}
	}
	
}
