package de.voltoviper.web.konfiguration;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import de.voltoviper.objects.standards.Hersteller;

import com.vaadin.ui.Button.ClickEvent;

public class HerstellerHinzufuegen extends FormLayout {
	TextField name;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HerstellerHinzufuegen(Hersteller hersteller) {
		init(hersteller);
	}

	private void init(Hersteller hersteller) {

		name = new TextField("Name");
		addComponent(name);
		if (hersteller != null) {
			name.setValue(hersteller.getName());
		}
		Button eintragen = new Button("Eintragen");
		eintragen.setIcon(FontAwesome.CHECK);
		eintragen.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (hersteller != null) {
					hersteller.setName(name.getValue());
					hersteller.saveHersteller(hersteller);
					Notification.show("Hersteller geupdated", hersteller.toString(),
							Notification.Type.TRAY_NOTIFICATION);
				} else {
					Hersteller hersteller = new Hersteller(name.getValue());
					Notification.show("Hersteller hinzugefügt", hersteller.toString(),
							Notification.Type.TRAY_NOTIFICATION);
				}
				resetform();
			}

		});
		addComponent(eintragen);

	}

	private void resetform() {
		// TODO Auto-generated method stub
		name.setValue("");
	}
}
