package cn.microvideo.postman.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author xab
 * @date 2021-10-31 18:14
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private String name;
    private String body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Response{" +
                "name='" + name + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
