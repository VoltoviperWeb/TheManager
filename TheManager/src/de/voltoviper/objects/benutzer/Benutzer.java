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
	
	Boolean login;
	
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
	int id;
    
  
    /*
     * Absctract Methods
     */
    public abstract void save(Benutzer kunde);
    public abstract void update();
    
    
/*
 * Getter and Setter
 */
    
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
	public Boolean getLogin() {
		return login;
	}

	public void setLogin(Boolean login) {
		this.login = login;
	}
	@Override
	public String toString() {
		return firstname +" "+ lastname ;
	}
	
	
	
	
}
