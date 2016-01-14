package de.voltoviper.web.validatoren;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
/**
 * IntegerRangeValidator der vor der �berpr�fung einen String in ein Integer parsed. Sollten hierbei fehler auftreten, ist die Validit�t nicht gew�hleistet
 * @author Christoph Nebendahl
 *
 */
public class CustomIntegerRangeValidator extends AbstractValidator<String> {

    private static final long serialVersionUID = 1L;

    private IntegerRangeValidator integerRangeValidator;
/**
 * 
 * @param errorMessage String der im Falle der Negativit�t dem user angezeigt wird.
 * @param minValue Kleinster Wert der vorhanden sein darf. Wenn null kann der Wert beliebig klein werden.
 * @param maxValue Gr��ter Wert der vorhanden sein darf. Wenn null kann der Wert beliebig gro� werden.
 */
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
