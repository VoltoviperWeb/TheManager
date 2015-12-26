package de.voltoviper.web.navigation;

import org.vaadin.addon.borderlayout.BorderLayout;
import org.vaadin.addon.borderlayout.BorderLayout.Constraint;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

import de.voltoviper.web.Dashboard;
import de.voltoviper.web.person.KundeUebersicht;
import de.voltoviper.web.person.NeuerKundeView;

public class NavigationView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NavigationView(BorderLayout layout) {
		init(layout);
	}

	private void init(BorderLayout layout) {

		setWidth("220");

		final Object[][] planets = new Object[][] { 
			new String[] { "Dashboard" },
			new String[] { "Kunden", "Kundenübersicht", "Neuer Kunde", "Kunde bearbeiten" },
			new String[] { "Ticket", "Ticket Übersicht", "Neues Ticket"}
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
				case "Neuer Kunde":
					layout.removeComponent(Constraint.CENTER);
					layout.addComponent(new NeuerKundeView(), Constraint.CENTER);
					break;
				case "Kundenübersicht":
					layout.removeComponent(Constraint.CENTER);
					layout.addComponent(new KundeUebersicht(), Constraint.CENTER);
					break;
				default:
					layout.removeComponent(Constraint.CENTER);
					layout.addComponent(new Dashboard(), Constraint.CENTER);
				}
				}else{
					layout.removeComponent(Constraint.CENTER);
					layout.addComponent(new Dashboard(), Constraint.CENTER);
				}
				
			}
		});
		addComponent(tree);

	}

}
