package de.voltoviper.web.person;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;

import de.voltoviper.objects.benutzer.Kunde;
import de.voltoviper.web.validatoren.CustomIntegerRangeValidator;

public class NeuerKundeView extends FormLayout {
	NeuerKundeView view;
	
	TextField tf1;
	TextField tf2;
	TextField email;
	TextField telefon;
	TextField tf3;
	CheckBox login;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NeuerKundeView() {
		this.view = this;
		setCaption("Einen neuen Kunden anlegen");
		tf1 = new TextField("Vorname");
		tf1.setIcon(FontAwesome.USER);
		tf1.setRequired(true);
		tf1.addValidator(new NullValidator("Pflichtfeld", false));
		this.addComponent(tf1);

		tf2 = new TextField("Nachname");
		tf2.setIcon(FontAwesome.USER);
		tf2.setRequired(true);
		tf2.addValidator(new NullValidator("Pflichtfeld", false));
		this.addComponent(tf2);

		email = new TextField("E-Mail");
		email.setIcon(FontAwesome.ENVELOPE_O);
		email.addValidator(new EmailValidator("Dies ist keine E-Mail Adresse"));
		addComponent(email);

		telefon = new TextField("Telefon");
		telefon.setIcon(FontAwesome.PHONE);
		addComponent(telefon);

		tf3 = new TextField("PLZ");
		tf3.setIcon(FontAwesome.ENVELOPE);
		tf3.addValidator(new CustomIntegerRangeValidator("Keine PLZ", 10000, 99999));
		this.addComponent(tf3);

		login = new CheckBox("- Login");
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
				Kunde kunde = new Kunde(tf1.getValue(), tf2.getValue(), email.getValue(), telefon.getValue(),
						login.getValue());
				Notification.show("Kunde hinzugefügt", tf2.getValue(), Notification.Type.TRAY_NOTIFICATION);
				formreset();
			}
		});
		addComponent(neuerKunde);

		Button reset = new Button("Reset");
		reset.setIcon(FontAwesome.ARROW_CIRCLE_DOWN);
		addComponent(reset);
	}

	private void formreset() {
		tf1.setValue("");
		tf2.setValue("");
		email.setValue("");
		telefon.setValue("");
		tf3.setValue("");
		login.setValue(false);
	}
}
