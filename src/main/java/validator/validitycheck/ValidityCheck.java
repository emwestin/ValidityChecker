package validator.validitycheck;

import validator.util.Data;

public interface ValidityCheck<T> {
    boolean validate(Data<T> data);
}
