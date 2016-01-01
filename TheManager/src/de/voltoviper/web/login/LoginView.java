package de.voltoviper.web.login;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.vaadin.addon.borderlayout.BorderLayout;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import de.voltoviper.ThemanagerWEB;
import de.voltoviper.objects.benutzer.Benutzer;
import de.voltoviper.objects.benutzer.Kunde;
import de.voltoviper.objects.device.Device;
import de.voltoviper.system.EncryptPassword;
import de.voltoviper.web.DBManager;

import com.vaadin.ui.Button.ClickEvent;

public class LoginView extends FormLayout{

	TextField username;
	PasswordField password;
	private static final Logger logger = LogManager.getLogger(LoginView.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public LoginView(ThemanagerWEB parent) {
		username = new TextField("Benutzername:");
		addComponent(username);
		
		password = new PasswordField("Passwort:");
		addComponent(password);
		
		Button login = new Button("Login");
		login.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				String passwd;
				Session session = DBManager.getFactory().openSession();
				try {
					passwd = EncryptPassword.SHA512(password.getValue());
					
					Criteria cr = session.createCriteria(Benutzer.class);
					cr.add(Restrictions.eq("login", true));
					cr.add(Restrictions.eq("username", username.getValue()));
					List<Benutzer> user = cr.list();
					for(Benutzer b:user){
						if(username.getValue().toLowerCase().equals(b.getUsername())&& passwd.equals(b.getPassword())){
							logger.trace("Welcome, "+b.toString()+"!");
							parent.initmain();
							break;
						}
					}
					
					
					
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			}

			
		});
		addComponent(login);
	}
	
	
	private void formreset() {
		username.setValue("");
		password.setValue("");
		
	}
}
