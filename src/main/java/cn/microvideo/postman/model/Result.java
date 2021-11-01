package cn.microvideo.postman.model;

/**
 * @author xab
 * @date 2021-10-31 18:17
 */
public class Result {
    private Object object;

    public Result(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "Result{" +
                "object=" + object +
                ", msg=hello'" +
                '}';
    }
}
