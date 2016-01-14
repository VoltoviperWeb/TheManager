package de.voltoviper.web.validatoren;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.PasswordField;

public class CustomPasswordvalidator extends AbstractValidator<String> {
	
	PasswordField passwd1;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomPasswordvalidator(String errorMessage, PasswordField passwd1) {
		super(errorMessage);
		this.passwd1 = passwd1;

	}

	@Override
	protected boolean isValidValue(String value) {
		if(passwd1.getValue().equals(value)){
			return true;
		}
		return false;
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

}
