package de.voltoviper.web.device;

import java.util.List;

import org.hibernate.Session;
import org.vaadin.addon.borderlayout.BorderLayout;
import org.vaadin.addon.borderlayout.BorderLayout.Constraint;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

import de.voltoviper.objects.benutzer.Kunde;
import de.voltoviper.objects.device.Device;
import de.voltoviper.objects.standards.Device_Typ;
import de.voltoviper.objects.standards.Hersteller;
import de.voltoviper.web.DBManager;
import de.voltoviper.web.validatoren.CustomIntegerRangeValidator;

public class NeuesDeviceView extends FormLayout implements KundenAuswahlInterface {
	Kunde k;
	ComboBox typ;
	Button eintragen;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NeuesDeviceView(BorderLayout layout) {
		init(layout);
	}

	private void init(BorderLayout layout) {

		typ = new ComboBox("Typ");
		
		for(Device_Typ device_typ:Device_Typ.values()){
			typ.addItem(device_typ);
		}
		this.addComponent(typ);
		
		ComboBox hersteller = new ComboBox("Hersteller");
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
		
		TextField bezeichnung = new TextField("Bezeichnung");
		addComponent(bezeichnung);
		
		TextField lan = new TextField("Anzahl LAN Schnittstellen");
		lan.addValidator(new CustomIntegerRangeValidator("Keine gültige Zahl", 0, null));
		addComponent(lan);
		
		TextField wlan = new TextField("Anzahl W-LAN Schnittstellen");
		wlan.addValidator(new CustomIntegerRangeValidator("Keine gültige Zahl", 0, null));
		addComponent(wlan);
		
		eintragen = new Button("Eintragen");
		if (k == null) {
			eintragen.setEnabled(false);
		}else{
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
				Device device = new Device((Device_Typ)typ.getValue(), k, (Hersteller)hersteller.getValue(),Integer.parseInt(lan.getValue()),Integer.parseInt(wlan.getValue()));
			}
		});
		
		this.addComponent(eintragen);
		
		
		
		
		
		
		
		
		
		
		KundenAuswahlView k_auswahl = new KundenAuswahlView(layout, this);
		layout.addComponent(k_auswahl, Constraint.EAST);
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
