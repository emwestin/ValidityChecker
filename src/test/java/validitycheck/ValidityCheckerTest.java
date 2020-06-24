package validitycheck;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validator.util.Data;
import validator.util.StringData;
import validator.util.ValidityResult;
import validator.validitycheck.IsLicencePlate;
import validator.validitycheck.IsPIN;
import validator.validitycheck.NotNull;
import validator.validitychecker.ValidityChecker;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidityCheckerTest {
    private static ValidityChecker<String> checker;

    @BeforeAll
    public static void setup() {
        checker = new ValidityChecker<>();
    }

    @BeforeEach
    public void reset() {
        checker.reset();
    }

    @Test
    void testValidDataNotNull() {
        checker.addValidityCheck(new NotNull<>());
        ValidityResult<String> res;

        res = checker.validate(new StringData("data"));
        assertTrue(res.getResult());
    }

    @Test
    void testInvalidDataNotNull() {
        checker.addValidityCheck(new NotNull<>());
        ValidityResult<String> res;

        res = checker.validate(new StringData(null));
        assertFalse(res.getResult());

        res = checker.validate((Data<String>) null);
        assertFalse(res.getResult());
    }

    @Test
    void testValidPin() {
        checker.addValidityCheck(new IsPIN<>());
        ValidityResult<String> res;

        res = checker.validate(new StringData("197802022389"));
        assertTrue(res.getResult());

        res = checker.validate(new StringData("8204112380"));
        assertTrue(res.getResult());
    }

    @Test
    void testValidPins() {
        checker.addValidityCheck(new IsPIN<>());
        Collection<ValidityResult<String>> res;

        Collection<Data<String>> pins = new ArrayList<>();
        pins.add(new StringData("197802022389"));
        pins.add(new StringData("8204112380"));

        res = checker.validate(pins);
        res.forEach(r -> assertTrue(r.getResult()));
    }

    @Test
    void testInvalidPinBirthday() {
        checker.addValidityCheck(new IsPIN<>());
        ValidityResult<String> res;

        res = checker.validate(new StringData("197822022389"));
        assertFalse(res.getResult());

        res = checker.validate(new StringData("197802322389"));
        assertFalse(res.getResult());
    }

    @Test
    void testInvalidPinChecksum() {
        checker.addValidityCheck(new IsPIN<>());
        ValidityResult<String> res;

        res = checker.validate(new StringData("197802022388"));
        assertFalse(res.getResult());

        res = checker.validate(new StringData("197802022385"));
        assertFalse(res.getResult());
    }

    @Test
    void testInvalidPinCharacters() {
        checker.addValidityCheck(new IsPIN<>());
        ValidityResult<String> res;

        res = checker.validate(new StringData("19780202ABCD"));
        assertFalse(res.getResult());
    }

    @Test
    void testInvalidPinLength() {
        checker.addValidityCheck(new IsPIN<>());
        ValidityResult<String> res;

        res = checker.validate(new StringData("19780202"));
        assertFalse(res.getResult());

        res = checker.validate(new StringData("1978020212345"));
        assertFalse(res.getResult());
    }

    @Test
    void testValidLicencePlate() {
        checker.addValidityCheck(new IsLicencePlate<>());
        ValidityResult<String> res;

        res = checker.validate(new StringData("abc123"));
        assertTrue(res.getResult());

        res = checker.validate(new StringData("XYZ98A"));
        assertTrue(res.getResult());
    }

    @Test
    void testInvalidLicencePlate() {
        checker.addValidityCheck(new IsLicencePlate<>());
        ValidityResult<String> res;

        res = checker.validate(new StringData("123abc"));
        assertFalse(res.getResult());

        res = checker.validate(new StringData("abcd23"));
        assertFalse(res.getResult());
    }
}