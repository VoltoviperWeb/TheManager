package de.voltoviper.web;

import com.thoughtworks.selenium.webdriven.commands.AltKeyUp;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;

public class TopLeiste extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TopLeiste(){
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		Label logo = new Label("TheManager");
		addComponent(logo);
		setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
		setSpacing(true);
		
		
		MenuBar menuBar = new MenuBar();
		menuBar.addItem("", FontAwesome.ENVELOPE_O, null);
		addComponent(menuBar);
		setComponentAlignment(menuBar, Alignment.MIDDLE_CENTER);
	}
}
