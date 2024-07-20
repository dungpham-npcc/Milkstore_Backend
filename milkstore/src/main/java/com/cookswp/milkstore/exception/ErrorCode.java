package com.cookswp.milkstore.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // Lỗi liên quan đến nội dung bài đăng
    CONTENT_NULL(4, "Nội dung không được để trống"),
    CONTENT_BLANK(5, "Nội dung không được để trống"),
    CONTENT_EMPTY(6, "Nội dung không được để trống"),
    POST_CONTENT_ERROR(9, "Nội dung bài đăng không được để trống"),
    POST_CONTENT_OFFENSIVE_WORD(13, "Nội dung bài đăng chứa từ không phù hợp"),

    // Lỗi liên quan đến bài đăng
    POST_ID_NOT_FOUND(7, "Bài đăng phải tồn tại trong hệ thống"),
    POST_TITLE_ERROR(8, "Tiêu đề bài đăng không được để trống"),
    ALL_POST_EMPTY(10, "Chưa có bài đăng nào"),
    POST_EXISTS(11, "Bài đăng đã tồn tại"),
    POST_TITLE_EXISTS(12, "Tiêu đề bài đăng phải là duy nhất"),

    // Lỗi liên quan đến danh mục
    CATEGORY_NOT_EXISTED(14, "Danh mục phải tồn tại"),

    // Lỗi liên quan đến sản phẩm
    INVALID_PRICE(15, "Giá không được nhỏ hơn 0"),
    PRODUCT_DESCRIPTION_IS_NULL(16, "Mô tả sản phẩm là bắt buộc"),
    PRODUCT_NAME_EXISTS(17, "Tên sản phẩm đã tồn tại trong hệ thống"),
    PRODUCT_IMAGE_INVALID(18, "Ảnh sản phẩm không hợp lệ"),
    PRODUCT_QUANTITY_INVALID(19, "Số lượng không được nhỏ hơn 0"),
    PRODUCT_NOT_FOUND(20, "Không tìm thấy sản phẩm"),
    PRODUCT_LIST_NOT_FOUND(21, "Danh sách sản phẩm trống"),
    PRODUCT_ID_NOT_FOUND(22, "ID sản phẩm không tồn tại"),
    MANU_DATE_CAN_NOT_BEFORE_EXPI_DATE(23, "Ngày sản xuất không được trước ngày hết hạn"),

    // Lỗi liên quan đến giỏ hàng
    CART_NOT_FOUND(24, "Giỏ hàng không tìm thấy"),
    PRODUCT_NOT_FOUND_IN_CART(25, "Không tìm thấy sản phẩm trong giỏ hàng"),
    INSUFFICIENT_STOCK(26, "Số lượng hàng tồn không đủ"),
    INVALID_QUANTITY(27, "Số lượng phải lớn hơn 0"),
    PRODUCT_NOT_AVAILABLE(28, "Sản phẩm không đủ số lượng"),

    // Lỗi liên quan đến đơn hàng
    ORDER_NOT_FOUND(29, "Đơn hàng không tìm thấy"),
    INVALID_ORDER_STATUS(30, "Đơn hàng không có trạng thái đúng để xác định"),

    // Lỗi liên quan đến đánh giá
    FEEDBACK_NOT_FOUND(31, "Không tìm thấy bài đánh giá"),
    FEEDBACK_RATING_ERROR(32, "Đánh giá nên từ 1 đến 5 sao"),

    // Lỗi liên quan đến yêu cầu hoàn trả
    REFUND_REQUEST_NEED_CUSTOMER_IMAGE(33, "Yêu cầu hoàn trã cần phải có ảnh từ khách hàng để cửa hàng có thể kiểm chứng"),
    REFUND_REQUEST_NEED_CUSTOMER_REASON(34, "Yêu cầu hoàn trã cần lí do từ khách hàng để kiểm tra kết hợp với ảnh được cung cấp"),
    DENY_REFUND_REQUEST_NEED_REASON(35, "Yêu cầu từ chối hoàn trả cần lí do"),
    DENY_REFUND_REQUEST_NEED_IMAGE(36, "Yêu cầu từ chối hoàn trả cần ảnh"),
    DENY_TAKE_REFUND_ORDER_NEED_REASON(37, "Yêu cầu từ chối nhận đơn hoàn trả cần lí do"),
    CONFIRM_REFUND_ORDER_TAKING_NEED_IMAGE(38, "Xác nhận nhận đơn hoàn trả cần ảnh từ shipper"),
    CANNOT_RECALL_REQUEST_ORDER_NEED_REASON(39, "Không thể thu hồi yêu cầu đơn hàng cần lí do từ shipper"),
    CANNOT_RETURN_REFUND_ORDER_NEED_REASON(40, "Không thể trả lại đơn hàng hoàn trả cần lí do từ shipper"),
    COMPLETE_RETURN_REFUND_ORDER_NEED_IMAGE(41, "Hoàn thành trả lại đơn hàng hoàn trả cần ảnh từ shipper"),
    NO_REFUND_REQUEST_FOUND(42, "Không có yêu cầu hoàn trã nào được tìm thấy"),
    INVALID_REFUND_REQUEST(43, "Yêu cầu hoàn trã không hợp lệ"),
    INVALID_REFUND_REQUEST_STATUS(44, "Trạng thái của yêu cầu hoàn trã không hợp lệ"),
    REFUND_NOT_FOUND(45, "KHông tìm thấy đơn hoàn trả"),
    REFUND_IMAGE_NOT_FOUND(46, "Yêu cầu hình ảnh"),
    CUSTOMER_REFUND_REASON_EMPTY(47, "Lý do hoàn trả của khách hàng trống"),
    CUSTOMER_PHONE_EMPTY(48, "Số điện thoại của khách hàng không được để trống"),
    CUSTOMER_ADDRESS_EMPTY(49, "Địa chỉ khác hàng không được để trống"),
    PRODUCT_NAME_EMPTY(50, "Tên sản phẩm không được để trống"),
    CUSTOMER_NAME_EMPTY(51, "Tên khách hàng không được để trống");







    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
