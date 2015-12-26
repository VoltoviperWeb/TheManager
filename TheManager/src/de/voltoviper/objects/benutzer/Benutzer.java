package de.voltoviper.objects.benutzer;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;

import de.voltoviper.system.EncryptPassword;

@Entity
@Inheritance
@DiscriminatorColumn(name="USER_TYPE")
@Table(name="USER")
public abstract class Benutzer {
	@Column(name = "username")
	
	String username;
	@Column (name = "password")
	String password;
	
	@Column (name= "firstname")
	String firstname;
	
	@Column (name ="lastname")
	String lastname;
	
	@Column (name="berechtigung")
	String berechtigung;
	
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
	int id;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		
		try {
			this.password = EncryptPassword.SHA512(password);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	
}
