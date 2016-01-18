package de.voltoviper.web.konfiguration;

import java.util.List;

import org.hibernate.Session;
import org.vaadin.addon.borderlayout.BorderLayout;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
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
				table.setItemCaption(table.addItem(new Object[] { h.getId(), h.getName()}, i), 
						String.valueOf(h.getId()));
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();

		}
		table.addItemClickListener(new ItemClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					Session session = DBManager.getFactory().openSession();
					try {
						Hersteller hersteller = (Hersteller) session.get(Hersteller.class,
								Integer.parseInt(table.getItemCaption(event.getItemId())));
						navigation.setView(new HerstellerHinzufuegen(hersteller));
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						session.close();

					}
				}

			}
		});
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
				navigation.setView(new HerstellerHinzufuegen(null));
			}
		});
		v.addComponent(neu);
		
		this.addComponent(v, Constraint.NORTH);
	}
}
