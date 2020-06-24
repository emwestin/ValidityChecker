package validator.validitycheck;

import validator.util.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * A simple validity check for swedish national identity numbers.
 */
public class IsPIN<T> implements ValidityCheck<T> {
    private final int SHORT_VAR = 10;
    private final int LONG_VAR = 12;

    @Override
    public boolean validate(Data<T> data) {
        String pin = data.getData();
        return pin != null && isCorrectLength(pin) &&
                containsOnlyNumbers(pin) && validBirthday(pin) &&
                validChecksum(pin);
    }

    private boolean isCorrectLength(String pin) {
        return pin.length() == SHORT_VAR || pin.length() == LONG_VAR;
    }

    private boolean containsOnlyNumbers(String pin) {
        try {
            Long.parseLong(pin);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validBirthday(String pin) {
        String format = pin.length() == SHORT_VAR ? "yyMMdd" : "yyyyMMdd";

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(pin.substring(0, pin.length() - 4));
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean validChecksum(String pin) {
        String number = pin.length() == SHORT_VAR ? pin : pin.substring(2);
        String value = "212121212";

        int sum = 0;
        int temp;
        for (int i = 0; i < 9; i++) {
            int first = Integer.parseInt(number.charAt(i) + "");
            int second = Integer.parseInt(value.charAt(i) + "");

            temp = first * second;
            sum += (temp / 10) + (temp % 10);
        }

        int checksum = (10 - (sum % 10)) % 10;
        return checksum + '0' == pin.charAt(pin.length() - 1);
    }
}
