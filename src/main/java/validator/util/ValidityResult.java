package validator.util;

import java.util.HashMap;
import java.util.Map;

public class ValidityResult<T> {
    private final Data<T> data;
    private boolean result = true;
    private final Map<String, Boolean> results;

    public ValidityResult(Data<T> data) {
        this.data = data;
        results = new HashMap<>();
    }

    public void setResult(String checkName, boolean result) {
        this.result &= result;
        results.put(checkName, result);
    }

    public boolean getResult() {
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("---------------------").append("\n");
        builder.append("Data:  ").append(data.getData()).append("\n");
        builder.append("Valid: ").append(result).append("\n");
        builder.append("---------------------").append("\n");
        results.keySet().forEach(k -> {
            builder.append(k);
            builder.append(": ");
            builder.append(results.get(k) ? "Passed" : "Failed");
            builder.append("\n");
        });
        builder.append("---------------------").append("\n");

        return builder.toString();
    }
}
