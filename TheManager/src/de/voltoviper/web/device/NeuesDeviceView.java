package de.voltoviper.web.device;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.vaadin.addon.borderlayout.BorderLayout;
import org.vaadin.addon.borderlayout.BorderLayout.Constraint;

import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;

import de.voltoviper.objects.benutzer.Kunde;
import de.voltoviper.objects.device.Device;
import de.voltoviper.objects.standards.Device_Typ;
import de.voltoviper.objects.standards.Hersteller;
import de.voltoviper.web.DBManager;
import de.voltoviper.web.validatoren.CustomIntegerRangeValidator;
import de.voltoviper.web.validatoren.CustomPasswordvalidator;

public class NeuesDeviceView extends FormLayout implements KundenAuswahlInterface {
	Kunde k;
	ComboBox typ;
	ComboBox hersteller;
	TextField bezeichnung;
	TextField lan;
	TextField wlan;
	CheckBox wlansender;
	TextField ssid;
	PasswordField wlanpasswd1, wlanpasswd2;
	Button eintragen;
	private static final Logger logger = LogManager.getLogger(NeuesDeviceView.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NeuesDeviceView(BorderLayout layout) {
		init(layout);
	}

	@SuppressWarnings("unchecked")
	private void init(BorderLayout layout) {

		typ = new ComboBox("Typ");
		typ.setRequired(true);

		for (Device_Typ device_typ : Device_Typ.values()) {
			typ.addItem(device_typ);
		}
		this.addComponent(typ);

		hersteller = new ComboBox("Hersteller");
		hersteller.setRequired(true);
		Session session = DBManager.getFactory().openSession();
		try {
			List<Hersteller> hersteller_list = session.createCriteria(Hersteller.class).list();
			for (Hersteller k : hersteller_list) {
				hersteller.addItem(k);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		addComponent(hersteller);

		bezeichnung = new TextField("Bezeichnung");
		bezeichnung.setRequired(true);
		addComponent(bezeichnung);

		lan = new TextField("Anzahl LAN Schnittstellen");
		lan.addValidator(new CustomIntegerRangeValidator("Keine gültige Zahl", 0, null));
		addComponent(lan);

		wlan = new TextField("Anzahl W-LAN Schnittstellen");
		wlan.addValidator(new CustomIntegerRangeValidator("Keine gültige Zahl", 0, null));
		addComponent(wlan);
		wlan.setImmediate(true);

		wlansender = new CheckBox("W-Lan Sender?");
		addComponent(wlansender);
		wlansender.setImmediate(true);
		wlansender.setEnabled(false);

		wlan.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				try {
					if (wlan.getValue() != null) {
						if (Integer.parseInt(wlan.getValue()) > 0) {
							wlansender.setEnabled(true);
							logger.trace("W-Lan eingeschaltet");
						} else {
							wlansender.setEnabled(false);
							logger.trace("W-Lan ausgeschaltet");
						}
					}

				} catch (Exception e) {
					Notification.show("Error", "not an Integer", Notification.Type.ERROR_MESSAGE);
					logger.error("Not an Integer");
					wlansender.setEnabled(false);
				}
			}
		});

		ssid = new TextField("SSID:");
		addComponent(ssid);
		ssid.setEnabled(false);

		wlanpasswd1 = new PasswordField("W-Lan Kennwort:");
		addComponent(wlanpasswd1);
		wlanpasswd1.setEnabled(false);

		wlanpasswd2 = new PasswordField("W-Lan Kennwort:");
		addComponent(wlanpasswd2);
		wlanpasswd2.setEnabled(false);
		wlanpasswd2.addValidator(new CustomPasswordvalidator("Kennwörter nicht identisch", wlanpasswd1));

		// Action Listener für Wlan Abfragen
		wlansender.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				ssid.setEnabled((Boolean) event.getProperty().getValue());
				wlanpasswd1.setEnabled((Boolean) event.getProperty().getValue());
				wlanpasswd2.setEnabled((Boolean) event.getProperty().getValue());
				if ((Boolean) event.getProperty().getValue()) {
					logger.trace("W-Lan als Basistation eingestellt");
				} else {
					logger.trace("W-Lan als Clientstation eingestellt");
				}
			}
		});

		eintragen = new Button("Eintragen");
		if (k == null) {
			eintragen.setEnabled(false);
		} else {
			eintragen.setEnabled(true);
		}
		eintragen.setIcon(FontAwesome.CHECK);
		eintragen.addClickListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Device device = null;
				if (!wlansender.getValue()) {
					device = new Device((Device_Typ) typ.getValue(), k, (Hersteller) hersteller.getValue(),
							Integer.parseInt(lan.getValue()), Integer.parseInt(wlan.getValue()), wlansender.getValue(),
							null, null, bezeichnung.getValue());
				} else {
					device = new Device((Device_Typ) typ.getValue(), k, (Hersteller) hersteller.getValue(),
							Integer.parseInt(lan.getValue()), Integer.parseInt(wlan.getValue()), wlansender.getValue(),
							ssid.getValue(), wlanpasswd1.getValue(), bezeichnung.getValue());
				}
				device.setBezeichnung(bezeichnung.getValue());
				Notification.show("Gerät hinzugefügt", device.toString(), Notification.Type.TRAY_NOTIFICATION);
				resetform();
			}

		});

		this.addComponent(eintragen);

		KundenAuswahlView k_auswahl = new KundenAuswahlView(layout, this);
		layout.addComponent(k_auswahl, Constraint.EAST);
	}

	private void resetform() {
		typ.setValue("");
		hersteller.setValue("");
		bezeichnung.setValue("");
		lan.setValue("");
		wlan.setValue("");
		wlansender.setValue(false);
		ssid.setValue("");
		wlanpasswd1.setValue("");
		wlanpasswd2.setValue("");

	}

	@Override
	public void setKunde(Kunde k) {
		this.k = k;
		if (k == null) {
			eintragen.setEnabled(false);
		} else {
			eintragen.setEnabled(true);
		}
	}
}
