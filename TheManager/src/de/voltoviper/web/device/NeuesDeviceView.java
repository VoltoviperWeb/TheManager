package de.voltoviper.web.device;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;

public class NeuesDeviceView extends FormLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public NeuesDeviceView(){
			init();
		}
		private void init() {
			// TODO Auto-generated method stub
			ComboBox typ = new ComboBox("Typ");
			typ.addItem(new String("Computer"));
			typ.addItem(new String("Laptop"));
			typ.addItem(new String("Switch"));
			typ.addItem(new String("Router"));
			typ.addItem(new String("WLAN-Router"));
			this.addComponent(typ);
		}
}
