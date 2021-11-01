package cn.microvideo.postman.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * postman参数 info
 *
 * @author xab
 * @date 2021-10-31 17:52
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Info {

    private String _postman_id;
    private String name;
    private String schema;

    public String get_postman_id() {
        return _postman_id;
    }

    public void set_postman_id(String _postman_id) {
        this._postman_id = _postman_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public String toString() {
        return "Info{" +
                "_postman_id='" + _postman_id + '\'' +
                ", name='" + name + '\'' +
                ", schema='" + schema + '\'' +
                '}';
    }
}
