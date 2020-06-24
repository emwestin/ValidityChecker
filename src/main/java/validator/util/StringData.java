package validator.util;

public class StringData implements Data<String> {
    private String data;

    public StringData(String data) {
        this.data = data;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }
}
