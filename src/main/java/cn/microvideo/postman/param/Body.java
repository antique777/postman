package cn.microvideo.postman.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * request body
 *
 * @author xab
 * @date 2021-11-01 11:23
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Body {
    private String mode;
    private String raw;
    private List<Formdata> formdata;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public List<Formdata> getFormdata() {
        return formdata;
    }

    public void setFormdata(List<Formdata> formdata) {
        this.formdata = formdata;
    }

    @Override
    public String toString() {
        return "Body{" +
                "mode='" + mode + '\'' +
                ", raw='" + raw + '\'' +
                ", formdata=" + formdata +
                '}';
    }
}
