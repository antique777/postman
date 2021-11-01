package cn.microvideo.postman.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 文件夹
 *
 * @author xab
 * @date 2021-10-31 17:56
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private String name;
    private List<Item> item;
    private Request request;
    private List<Response> response;

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }


    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", item=" + item +
                ", request=" + request +
                '}';
    }
}
