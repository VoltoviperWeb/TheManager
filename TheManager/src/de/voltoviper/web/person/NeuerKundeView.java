package de.voltoviper.web.person;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;

import de.voltoviper.objects.benutzer.Kunde;
import de.voltoviper.web.validatoren.CustomIntegerRangeValidator;

public class NeuerKundeView extends FormLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NeuerKundeView() {

		setCaption("Einen neuen Kunden anlegen");
		TextField tf1 = new TextField("Vorname");
		tf1.setIcon(FontAwesome.USER);
		tf1.setRequired(true);
		tf1.addValidator(new NullValidator("Pflichtfeld", false));
		this.addComponent(tf1);

		TextField tf2 = new TextField("Nachname");
		tf2.setIcon(FontAwesome.USER);
		tf2.setRequired(true);
		tf2.addValidator(new NullValidator("Pflichtfeld", false));
		this.addComponent(tf2);

		TextField email = new TextField("E-Mail");
		email.setIcon(FontAwesome.ENVELOPE_O);
		email.addValidator(new EmailValidator("Dies ist keine E-Mail Adresse"));
		addComponent(email);

		TextField telefon = new TextField("Telefon");
		telefon.setIcon(FontAwesome.PHONE);
		addComponent(telefon);

		TextField tf3 = new TextField("PLZ");
		tf3.setIcon(FontAwesome.ENVELOPE);
		tf3.addValidator(new CustomIntegerRangeValidator("Keine PLZ", 10000, 99999));
		this.addComponent(tf3);

		CheckBox login = new CheckBox("- Login");
		login.setIcon(FontAwesome.LOCK);
		this.addComponent(login);

		Button neuerKunde = new Button("Eintragen");
		neuerKunde.setIcon(FontAwesome.CHECK);
		neuerKunde.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("Neuer Kunde " + tf2.getValue() + " wurde angelegt");
				Kunde kunde = new Kunde(tf1.getValue(), tf2.getValue(), email.getValue(), telefon.getValue(), login.getValue());
			}
		});
		addComponent(neuerKunde);

		Button reset = new Button("Reset");
		reset.setIcon(FontAwesome.ARROW_CIRCLE_DOWN);
		addComponent(reset);
	}


}
