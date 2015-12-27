package de.voltoviper.web.person;

import java.util.List;

import org.hibernate.Session;
import org.vaadin.addon.borderlayout.BorderLayout;

import com.vaadin.ui.Table;
import de.voltoviper.objects.benutzer.Kunde;
import de.voltoviper.web.DBManager;

public class KundeUebersicht extends BorderLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KundeUebersicht() {
		init();
	}

	@SuppressWarnings("unchecked")
	private void init() {
		Session session = DBManager.getFactory().openSession();
		Table table = new Table("Kundenübersicht");
		table.addContainerProperty("Vorname", String.class, null);
		table.addContainerProperty("Nachname", String.class, null);
		table.addContainerProperty("E-Mail", String.class, null);
		table.addContainerProperty("Login", Boolean.class, null);
		table.setSelectable(true);
		table.setImmediate(true);
		try {

			List<Kunde> kunden = session.createCriteria(Kunde.class).list();
			int i = 0;
			for (Kunde k : kunden) {
				table.addItem(new Object[] { k.getFirstname(), k.getLastname(), k.getEmail(), k.getLogin() }, i);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();

		}
		this.addComponent(table, Constraint.CENTER);
		
		
		
	}
}
