package validator;

import validator.util.Data;
import validator.util.StringData;
import validator.util.ValidityResult;
import validator.validitycheck.IsPIN;
import validator.validitycheck.NotNull;
import validator.validitychecker.ValidityChecker;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        new Main();
    }

    public Main() throws IOException {
        ValidityChecker<String> checker = new ValidityChecker<>();
        checker.addValidityCheck(new NotNull<>());
        checker.addValidityCheck(new IsPIN<>());

        Collection<Data<String>> data = openTestCSV()
                .stream()
                .map(StringData::new)
                .collect(Collectors.toList());

        Collection<ValidityResult<String>> result = checker.validate(data);
        boolean res = result.stream().anyMatch(e -> !e.getResult());
        if(res) {
            long count = result.stream().filter(e-> !e.getResult()).count();
            System.out.println("Found " + count + " false positives out of " + data.size());
        } else {
            System.out.println("All " + data.size() + " valid PINs passed");
        }
    }

    public Collection<String> openTestCSV() throws IOException {
        List<String> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/testfil.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line);
            }
        }
        return records;
    }
}
