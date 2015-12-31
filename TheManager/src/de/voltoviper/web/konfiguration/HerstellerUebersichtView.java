package de.voltoviper.web.konfiguration;

import java.util.List;

import org.hibernate.Session;
import org.vaadin.addon.borderlayout.BorderLayout;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import de.voltoviper.objects.standards.Hersteller;
import de.voltoviper.web.DBManager;
import de.voltoviper.web.navigation.NavigationView;

public class HerstellerUebersichtView extends BorderLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HerstellerUebersichtView(NavigationView navigation){
		Session session = DBManager.getFactory().openSession();
		Table table = new Table("Herstellerübersicht");
		table.addContainerProperty("id", Integer.class, null);
		table.addContainerProperty("Name", String.class, null);
		table.setSelectable(true);
		table.setImmediate(true);
		try {

			@SuppressWarnings("unchecked")
			List<Hersteller> hersteller=session.createCriteria(Hersteller.class).list();
			int i = 0;
			for (Hersteller h : hersteller){
				table.addItem(new Object[] { h.getId(), h.getName()}, i);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();

		}
		this.addComponent(table, Constraint.CENTER);
		
		VerticalLayout v = new VerticalLayout();
		Button neu  = new Button();
		neu.setIcon(FontAwesome.PLUS);
		neu.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				navigation.setView(new HerstellerHinzufuegen());
			}
		});
		v.addComponent(neu);
		
		this.addComponent(v, Constraint.NORTH);
	}
}
