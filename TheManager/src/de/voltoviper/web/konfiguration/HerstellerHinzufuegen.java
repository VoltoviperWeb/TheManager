package de.voltoviper.web.konfiguration;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

import de.voltoviper.objects.standards.Hersteller;

import com.vaadin.ui.Button.ClickEvent;

public class HerstellerHinzufuegen extends FormLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HerstellerHinzufuegen(){
		init();
	}
	
	private void init() {
		TextField name = new TextField("Name");
		addComponent(name);
		
		Button eintragen = new Button("Eintragen");
		eintragen.setIcon(FontAwesome.CHECK);
		eintragen.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Hersteller hersteller = new Hersteller(name.getValue());
				
			}
		});
		addComponent(eintragen);
		
	}


}
