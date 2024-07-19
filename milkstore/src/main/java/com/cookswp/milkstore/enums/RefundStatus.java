package com.cookswp.milkstore.enums;

public enum RefundStatus {

    IN_PROGRESSING(1, "Yêu cầu hoàn trả đang trong giai đoạn xử lý"),
    CONFIRM_REFUND_MONEY(2, "Cửa hàng đã hoàn tiền thành công cho khách hàng có nhu cầu trã hàng"),
    CANNOT_CONFIRM_REQUEST(3, "Nhân viên không thể xác nhận yêu cầu hoàn trả với lý do"),
    TAKING_PRODUCT_PROGRESSING(4, "Nhân viên giao hàng đang đến lấy sản phẩm hoàn trả"),
    CANNOT_TAKE_AWAY_PRODUCT(5, "Nhân viên giao hàng không thể lấy sản phẩm hoàn trả từ khách hàng"),
    CONFIRM_TAKING(6, "Nhân viên giao hàng hoàn tất việc lấy sản phẩm hoàn trả từ khách hàng với bằng chứng ảnh"),
    SHOP_PROCESS(7, "Cửa hàng xử lý sản phẩm hoàn trả bao gồm vận chuyển và kiểm tra sản phẩm"),
    CANNOT_ACCEPT_REFUND_REQUEST(8, "Cửa hàng không thể chấp nhận yêu cầu hoàn trả từ khách hàng sau khi kiểm tra lại sản phẩm"),
    CANCEL_REFUND_REQUEST(9,"Yêu cầu hoàn trã đơn hàng sẽ bị hủy"),
    DELIVERY_TO_TURN_BACK(10, "Nhân viên ship hàng trở lại cho khách nếu như hàng không đúng thực tế hoặc muốn đổi mới bảo hành cho khách"),
    COMPLETE_TURN_BACK(11, "Hoàn thành giao lại hàng cho khách hàng");


    private final int code;
    private final String msg;

    RefundStatus (int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
