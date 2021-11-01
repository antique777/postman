package cn.microvideo.postman.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author xab
 * @date 2021-10-31 18:09
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {
    private String method;
    private List<Header> header;
    private Url url;
    private Body body;

    public List<Header> getHeader() {
        return header;
    }

    public void setHeader(List<Header> header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "request{" +
                "method='" + method + '\'' +
                ", header=" + header +
                ", url=" + url +
                '}';
    }
}
