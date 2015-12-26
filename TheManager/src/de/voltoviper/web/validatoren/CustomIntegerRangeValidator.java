package de.voltoviper.web.validatoren;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.IntegerRangeValidator;

public class CustomIntegerRangeValidator extends AbstractValidator<String> {

    private static final long serialVersionUID = 1L;

    private IntegerRangeValidator integerRangeValidator;

    public CustomIntegerRangeValidator(String errorMessage, Integer minValue, Integer maxValue) {
        super(errorMessage);
        this.integerRangeValidator = new IntegerRangeValidator(errorMessage, minValue, maxValue);
    }

    @Override
    protected boolean isValidValue(String value) {
    	if(value==""){
    		return true;
    	}
        try {
            Integer result = Integer.parseInt(value);
            integerRangeValidator.validate(result);
            return true;
        } catch (NumberFormatException nfe) {
            // TODO: handle exception
            System.out.println("Cannot be parsed as Integer: " + nfe);
            return false;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

}
