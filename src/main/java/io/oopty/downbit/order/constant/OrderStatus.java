package io.oopty.downbit.order.constant;

public enum OrderStatus {
    OPENED("opened");
    private final String value;

    public String getValue() {
        return value;
    }

    OrderStatus(String value) {
        this.value = value;
    }
}