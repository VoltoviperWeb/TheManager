package de.voltoviper.web;

import org.hibernate.Session;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javafx.beans.property.SimpleDoubleProperty;

public class Dashboard extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Dashboard() {
		setCaption("TheManager - Dashboard");

		initPerson();
		initGeraete();
	}

	private void initGeraete() {
		// TODO Auto-generated method stub
		Session session = DBManager.getFactory().openSession();
		try {
			long q = (long) session.createQuery("select count(*) FROM Device").uniqueResult();
			VerticalLayout v = new VerticalLayout();
			Label kunde = new Label("Geräte");
			kunde.setWidth(null);
			v.addComponent(kunde);
			v.setComponentAlignment(kunde, Alignment.TOP_CENTER);
			Label kunde_anzahl = new Label(String.valueOf(q));
			kunde_anzahl.setWidth(null);
			v.addComponent(kunde_anzahl);
			v.setComponentAlignment(kunde_anzahl, Alignment.MIDDLE_CENTER);
			this.addComponent(v);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	private void initPerson() {
		// TODO Auto-generated method stub
		Session session = DBManager.getFactory().openSession();
		try {
			long q = (long) session.createQuery("select count(*) FROM Benutzer WHERE USER_TYPE='Kunde'").uniqueResult();
			VerticalLayout v = new VerticalLayout();
			Label kunde = new Label("Kunden");
			kunde.setWidth(null);
			v.addComponent(kunde);
			v.setComponentAlignment(kunde, Alignment.TOP_CENTER);
			Label kunde_anzahl = new Label(String.valueOf(q));
			kunde_anzahl.setWidth(null);
			v.addComponent(kunde_anzahl);
			v.setComponentAlignment(kunde_anzahl, Alignment.MIDDLE_CENTER);
			this.addComponent(v);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}
}
