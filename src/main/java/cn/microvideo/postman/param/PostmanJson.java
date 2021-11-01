package cn.microvideo.postman.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * postman json实体
 *
 * @author xab
 * @date 2021-10-31 17:52
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostmanJson {

    private Info info;
    private List<Item> item;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "PostmanJson{" +
                "info=" + info +
                ", item=" + item +
                '}';
    }
}
