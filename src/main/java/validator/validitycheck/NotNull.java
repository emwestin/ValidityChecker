package validator.validitycheck;

import validator.util.Data;

public class NotNull<T> implements ValidityCheck<T> {
    @Override
    public boolean validate(Data<T> data) {
        return data != null && data.getData() != null;
    }
}
