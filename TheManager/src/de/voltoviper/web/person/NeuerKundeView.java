package de.voltoviper.web.person;

import org.vaadin.addon.borderlayout.BorderLayout;
import org.vaadin.addon.borderlayout.BorderLayout.Constraint;

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
import de.voltoviper.web.device.KundenAuswahlInterface;
import de.voltoviper.web.device.KundenAuswahlView;
import de.voltoviper.web.validatoren.CustomIntegerRangeValidator;

public class NeuerKundeView extends FormLayout implements KundenAuswahlInterface {
	NeuerKundeView view;
	Kunde k;
	TextField tf1;
	TextField tf2;
	TextField email;
	TextField telefon;
	TextField tf3;
	CheckBox login;

	EmailValidator emailvalidator = new EmailValidator("Dies ist keine E-Mail Adresse");
	CustomIntegerRangeValidator plzvalidator = new CustomIntegerRangeValidator("Keine PLZ", 10000, 99999);
	NullValidator nullvalidator = new NullValidator("Pflichtfeld", false);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NeuerKundeView(BorderLayout layout, boolean neu) {
		this.view = this;
		if (neu) {
			setCaption("Einen neuen Kunden anlegen");
		} else {
			setCaption("Einen Kunden bearbeiten");
		}
		tf1 = new TextField("Vorname");
		tf1.setIcon(FontAwesome.USER);
		tf1.setRequired(true);
		tf1.addValidator(nullvalidator);
		this.addComponent(tf1);

		tf2 = new TextField("Nachname");
		tf2.setIcon(FontAwesome.USER);
		tf2.setRequired(true);
		tf2.addValidator(nullvalidator);
		this.addComponent(tf2);

		email = new TextField("E-Mail");
		email.setIcon(FontAwesome.ENVELOPE_O);
		email.addValidator(emailvalidator);
		addComponent(email);

		telefon = new TextField("Telefon");
		telefon.setIcon(FontAwesome.PHONE);
		addComponent(telefon);

		tf3 = new TextField("PLZ");
		tf3.setIcon(FontAwesome.ENVELOPE);
		tf3.addValidator(plzvalidator);
		this.addComponent(tf3);

		login = new CheckBox("- Login");
		login.setIcon(FontAwesome.LOCK);
		this.addComponent(login);
		if (neu) {
			Button neuerKunde = new Button("Eintragen");
			neuerKunde.setIcon(FontAwesome.CHECK);
			neuerKunde.addClickListener(new Button.ClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					try {
						validateForm();

						Kunde kunde = new Kunde(tf1.getValue(), tf2.getValue(), email.getValue(), telefon.getValue(),
								login.getValue());
						Notification.show("Kunde hinzugefügt", kunde.toString(), Notification.Type.TRAY_NOTIFICATION);
						formreset();
					} catch (Exception e) {
						Notification.show("Formular Fehler", e.getMessage(), Notification.Type.ERROR_MESSAGE);
					}
				}
			});
			addComponent(neuerKunde);

			Button reset = new Button("Reset");
			reset.setIcon(FontAwesome.ARROW_CIRCLE_DOWN);
			reset.addClickListener(new Button.ClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					formreset();
				}
			});
			addComponent(reset);
		} else {
			Button neuerKunde = new Button("Update");
			neuerKunde.setIcon(FontAwesome.CHECK);
			neuerKunde.addClickListener(new Button.ClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					k.setFirstname(tf1.getValue());
					k.setLastname(tf2.getValue());
					k.setEmail(email.getValue());
					k.setTelefon(telefon.getValue());
					k.update();
					Notification.show("Kunde bearbeitet", k.toString(), Notification.Type.TRAY_NOTIFICATION);
					formreset();
				}
			});
			addComponent(neuerKunde);

			Button reset = new Button("Reset");
			reset.setIcon(FontAwesome.ARROW_CIRCLE_DOWN);
			reset.addClickListener(new Button.ClickListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					// TODO Auto-generated method stub
					formreset();
				}
			});
			addComponent(reset);

			KundenAuswahlView k_auswahl = new KundenAuswahlView(layout, this);
			layout.addComponent(k_auswahl, Constraint.EAST);
		}

	}

	private void validateForm() throws Exception {
		if (!emailvalidator.isValid(email.getValue())) {
			throw new Exception(email.getValue() + " ist keine E-Mail Adresse");
		}
		if(!plzvalidator.isValid(tf3.getValue())){
			throw new Exception(tf3.getValue() + " ist keine PLZ");
		}
		nullvalidator.validate(tf1);
		nullvalidator.validate(tf2);

	}

	private void formreset() {
		if (k == null) {
			tf1.setValue("");
			tf2.setValue("");
			email.setValue("");
			telefon.setValue("");
			tf3.setValue("");
			login.setValue(false);
		} else {
			tf1.setValue(k.getFirstname());
			tf2.setValue(k.getLastname());
			email.setValue(k.getEmail());
			telefon.setValue(k.getTelefon());
			tf3.setValue("");
			login.setValue(false);
		}
	}

	@Override
	public void setKunde(Kunde k) {
		// TODO Auto-generated method stub
		this.k = k;
		tf1.setValue(k.getFirstname());
		tf2.setValue(k.getLastname());
		email.setValue(k.getEmail());
		telefon.setValue(k.getTelefon());
	}
}
