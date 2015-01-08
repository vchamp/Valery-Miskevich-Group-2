package com.epam.trn.model;

public class UserRole {
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object other) {
	    if(other == null) return false;
	    if(other == this) return true;
	    if(!(other instanceof UserRole))return false;
	    UserRole otherUserRole = (UserRole)other;
	    if(this.id != null && this.id.equals(otherUserRole.getId())) return true;
	    if(this.name != null && this.name.equals(otherUserRole.getName())) return true;
	    
	    return false;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
