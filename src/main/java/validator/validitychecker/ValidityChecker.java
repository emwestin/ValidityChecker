package validator.validitychecker;

import validator.util.Data;
import validator.util.ValidityResult;
import validator.validitycheck.ValidityCheck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ValidityChecker<T> {
    private List<ValidityCheck<T>> checks;

    public ValidityChecker() {
        reset();
    }

    public ValidityResult<T> validate(Data<T> data) {
        ValidityResult<T> result = new ValidityResult<T>(data);

        for (ValidityCheck<T> check : checks) {
            String name = check.getClass().getSimpleName();
            boolean valid = check.validate(data);
            result.setResult(name, valid);
        }

        return result;
    }

    public Collection<ValidityResult<T>> validate(Collection<Data<T>> data) {
        return data.stream().map(this::validate).collect(Collectors.toList());
    }

    public void reset() {
        checks = new ArrayList<>();
    }

    public boolean addValidityCheck(ValidityCheck<T> check) {
        if (check == null) return false;
        return checks.add(check);
    }

    public boolean addValidityCheck(Collection<ValidityCheck<T>> check) {
        return check.stream()
                .map(this::addValidityCheck)
                .noneMatch(e -> e.equals(false));
    }
}
