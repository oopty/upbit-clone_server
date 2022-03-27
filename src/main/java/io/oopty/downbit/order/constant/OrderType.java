package io.oopty.downbit.order.constant;

public enum OrderType {
    LIMIT("limit"), MARKET("market");
    private String type;
    OrderType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
