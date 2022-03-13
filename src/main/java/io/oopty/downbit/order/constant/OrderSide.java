package io.oopty.downbit.order.constant;

public enum OrderSide {
    BID("bid"), ASK("ask");
    private String side;
    OrderSide(String side) {
        this.side = side;
    }
    public String getSide() {
        return side;
    }
}