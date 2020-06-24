package validator.validitycheck;

import validator.util.Data;

/**
 * A simple validity check for swedish licence plates.
 * <p>
 * The validation does not take into consideration any foul words in the licence
 * plate, nor does it consider special variants, e.g. military plates.
 */
public class IsLicencePlate<T> implements ValidityCheck<T> {
    private final int LICENCE_LEN = 6;

    @Override
    public boolean validate(Data<T> data) {
        String plate = data.getData();
        return plate != null  && isCorrectLength(plate) &&
                containsCorrectSymbols(plate);
    }

    private boolean isCorrectLength(String plate) {
        return plate.length() == LICENCE_LEN;
    }

    private boolean containsCorrectSymbols(String plate) {
        boolean valid;
        String symbols = plate.toUpperCase();

        // First three symbols must be letters between A-Z
        valid = symbols.charAt(0) >= 'A' && symbols.charAt(0) <= 'Z';
        valid &= symbols.charAt(1) >= 'A' && symbols.charAt(1) <= 'Z';
        valid &= symbols.charAt(2) >= 'A' && symbols.charAt(2) <= 'Z';

        // The following two symbols must be numerical
        valid &= symbols.charAt(3) >= '0' && symbols.charAt(3) <= '9';
        valid &= symbols.charAt(4) >= '0' && symbols.charAt(4) <= '9';

        // The last symbol may be either a letter between A-Z or numerical
        valid &= symbols.charAt(5) >= '0' && symbols.charAt(5) <= '9' ||
                symbols.charAt(5) >= 'A' && symbols.charAt(5) <= 'Z';

        return valid;
    }
}
