package cn.microvideo.postman.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author xab
 * @date 2021-10-31 18:10
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Url {
    private String raw;
    private List<String> host;
    private List<String> path;
    private List<Query> query;

    public List<String> getHost() {
        return host;
    }

    public void setHost(List<String> host) {
        this.host = host;
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public List<Query> getQuery() {
        return query;
    }

    public void setQuery(List<Query> query) {
        this.query = query;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    @Override
    public String toString() {
        return "Url{" +
                "raw='" + raw + '\'' +
                ", host=" + host +
                ", path=" + path +
                ", query=" + query +
                '}';
    }
}
