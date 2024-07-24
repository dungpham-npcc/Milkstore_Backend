package com.cookswp.milkstore.enums;


public enum Status {
    IN_CART(1,"Product is currently in the cart"),
    IN_CHECKOUT(2,"Customer is in the checkout process"),
    PAID(3,"Payment has been successfully processed"),
    ORDER_TRANSFER(4,"Order has been transferred for confirmation"),
    IN_DELIVERY(5,"Product is being delivered"),
    CANNOT_DELIVER(6,"Delivery cannot be completed"),
    DELIVERED(7,"Product has been delivered"),
    COMPLETE_EXCHANGE(8,"Completed all flow of buying"),
    CANNOT_CONFRIRM(9, "Can not confirm with some reason"),
    IS_FEEDBACK(10, "User had been feedback"),
    PRE_ORDERED(11, "Product is pre-ordered and paid"),
    IN_PREORDER_PROGRESS(12, "Product is in preorder process"),
    PREORDERED_ORDER_IN_DELIVERY(13, "Preordered order is in delivery");

    private final int code;
    private final String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
