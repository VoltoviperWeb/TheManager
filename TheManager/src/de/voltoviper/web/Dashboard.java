package de.voltoviper.web;

import org.hibernate.Query;
import org.hibernate.Session;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Dashboard extends HorizontalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Dashboard(){
		setCaption("TheManager - Dashboard");
		
		initPerson();
	}

	private void initPerson() {
		// TODO Auto-generated method stub
		Session session = DBManager.getFactory().openSession();
		try{
		long q = (long)session.createQuery("select count(*) FROM Benutzer WHERE USER_TYPE='Kunde'").uniqueResult();
		VerticalLayout v =new VerticalLayout();
		Label kunde = new Label("Kunden");
		kunde.setWidth(null);
		v.addComponent(kunde);
		v.setComponentAlignment(kunde, Alignment.TOP_CENTER);
		Label kunde_anzahl = new Label(String.valueOf(q));
		kunde_anzahl.setWidth(null);
		v.addComponent(kunde_anzahl);
		v.setComponentAlignment(kunde_anzahl, Alignment.MIDDLE_CENTER);
		this.addComponent(v);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		
	}
}
