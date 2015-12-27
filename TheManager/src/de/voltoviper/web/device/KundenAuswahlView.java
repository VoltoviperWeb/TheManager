package de.voltoviper.web.device;

import java.util.List;

import org.hibernate.Session;
import org.vaadin.addon.borderlayout.BorderLayout;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;

import de.voltoviper.objects.benutzer.Kunde;
import de.voltoviper.web.DBManager;

public class KundenAuswahlView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KundenAuswahlView(BorderLayout layout, KundenAuswahlInterface maincontainer) {
		init(layout, maincontainer);
	}

	@SuppressWarnings("unchecked")
	private void init(BorderLayout layout,KundenAuswahlInterface maincontainer) {
		setWidth("250");

		ComboBox auswahl = new ComboBox("Kundenauswahl");

		Session session = DBManager.getFactory().openSession();
		try {
			List<Kunde> kunde = session.createCriteria(Kunde.class).list();
			for (Kunde k : kunde) {
				auswahl.addItem(k);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		auswahl.addValueChangeListener(new ComboBox.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				maincontainer.setKunde((Kunde)event.getProperty().getValue());
				
			}
		});

		addComponent(auswahl);
	}

}
