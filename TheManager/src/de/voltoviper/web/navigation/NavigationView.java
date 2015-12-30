package de.voltoviper.web.navigation;

import org.vaadin.addon.borderlayout.BorderLayout;
import org.vaadin.addon.borderlayout.BorderLayout.Constraint;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

import de.voltoviper.web.Dashboard;
import de.voltoviper.web.device.DeviceUebersicht;
import de.voltoviper.web.device.NeuesDeviceView;
import de.voltoviper.web.konfiguration.HerstellerUebersichtView;
import de.voltoviper.web.person.KundeUebersicht;
import de.voltoviper.web.person.NeuerKundeView;

public class NavigationView extends VerticalLayout {
	BorderLayout layout;
	NavigationView navigation;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NavigationView(BorderLayout layout) {
		this.layout=layout;
		this.navigation = this;
		init(layout);
	}

	private void init(BorderLayout layout) {

		setWidth("230");

		final Object[][] planets = new Object[][] { 
			new String[] { "Dashboard" },
			new String[] { "Kunden", "Kundenübersicht", "Neuer Kunde", "Kunde bearbeiten" },
			new String[] { "Ticket", "Ticket Übersicht", "Neues Ticket"},
			new String[] { "Geräte", "Geräteübersicht", "Neues Gerät"},
			new String[] { "Konfiguration","Hersteller"}
		};
			
			
		Tree tree = new Tree("Hauptmenü");

		for (int i = 0; i < planets.length; i++) {
			String item = (String) (planets[i][0]);
			tree.addItem(item);

			if (planets[i].length == 1) {
				tree.setChildrenAllowed(item, false);
			} else {
				for (int j = 1; j < planets[i].length; j++) {
					String subitem = (String) planets[i][j];
					tree.addItem(subitem);
					tree.setParent(subitem, item);
					tree.setChildrenAllowed(subitem, false);
				}

				tree.expandItemsRecursively(item);
			}
		}
		tree.setImmediate(true);
		tree.addValueChangeListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				String id = (String) event.getProperty().getValue();
				if(id!=null){
				switch(id){
				case "Kunde bearbeiten":
					resetView();
					layout.addComponent(new NeuerKundeView(layout, false), Constraint.CENTER);
					break;
				case "Neuer Kunde":
					resetView();
					layout.addComponent(new NeuerKundeView(layout, true), Constraint.CENTER);
					break;
				case "Kundenübersicht":
					resetView();
					layout.addComponent(new KundeUebersicht(), Constraint.CENTER);
					break;
				case "Geräteübersicht":
					resetView();
					layout.addComponent(new DeviceUebersicht(layout), Constraint.CENTER);
					break;
				case "Neues Gerät":
					resetView();
					layout.addComponent(new NeuesDeviceView(layout), Constraint.CENTER);
					break;
				case "Hersteller":
					resetView();
					layout.addComponent(new HerstellerUebersichtView(navigation), Constraint.CENTER);
					break;
				default:
					resetView();
					layout.addComponent(new Dashboard(), Constraint.CENTER);
				}
				}else{
					resetView();
					layout.addComponent(new Dashboard(), Constraint.CENTER);
				}
				
			}
		});
		addComponent(tree);

	}
	
	private void resetView(){
		layout.removeComponent(Constraint.CENTER);
		layout.removeComponent(Constraint.EAST);
	}
	
	public void setView(AbstractLayout inside_layout){
		resetView();
		layout.addComponent(inside_layout, Constraint.CENTER);
	}

}
