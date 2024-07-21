package com.cookswp.milkstore.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    //Post Error Code Exception:
    TITLE_NULL(1, "Tiêu đề không được để trống"),
    TITLE_BLANK(2, "Tiêu đề không được để trống"),
    TITLE_EMPTY(3, "Tiêu đề không được để trống"),
    CONTENT_NULL(4, "Nội dung không được để trống"),
    CONTENT_BLANK(5, "Nội dung không được để trống"),
    CONTENT_EMPTY(6, "Nội dung không được để trống"),
    POST_ID_NOT_FOUND(7, "Bài đăng phải tồn tại trong hệ thống"),
    POST_TITLE_ERROR(8, "Tiêu đề bài đăng không được để trống"),
    POST_CONTENT_ERROR(9, "Nội dung bài đăng không được để trống"),
    ALL_POST_EMPTY(10, "Chưa có bài đăng nào"),
    POST_EXISTS(11, "Bài đăng đã tồn tại"),
    POST_TITLE_EXISTS(12, "Tiêu đề bài đăng phải là duy nhất"),
    POST_CONTENT_OFFENSIVE_WORD(13, "Nội dung bài đăng chứa từ không phù hợp"),
    CATEGORY_NOT_EXISTED(14, "Danh mục phải tồn tại"),
    INVALID_PRICE(15, "Giá không được nhỏ hơn 0"),
    PRODUCT_DESCRIPTION_IS_NULL(16, "Mô tả sản phẩm là bắt buộc"),
    PRODUCT_NAME_EXISTS(17, "Tên sản phẩm đã tồn tại trong hệ thống"),
    PRODUCT_IMAGE_INVALID(18, "Ảnh sản phẩm không hợp lệ"),
    PRODUCT_QUANTITY_INVALID(19, "Số lượng không được nhỏ hơn 0"),
    PRODUCT_NOT_FOUND(20, "Không tìm thấy sản phẩm"),
    PRODUCT_LIST_NOT_FOUND(21, "Danh sách sản phẩm trống"),
    PRODUCT_ID_NOT_FOUND(22, "ID sản phẩm không tồn tại"),
    MANU_DATE_CAN_NOT_BEFORE_EXPI_DATE(23, "Ngày sản xuất không được trước ngày hết hạn"),
    CART_NOT_FOUND(24, "Giỏ hàng không tìm thấy"),
    PRODUCT_NOT_FOUND_IN_CART(25, "Không tìm thấy sản phẩm trong giỏ hàng"),
    INSUFFICIENT_STOCK(26, "Số lượng hàng tồn không đủ"),
    INVALID_QUANTITY(27, "Số lượng phải lớn hơn 0"),
    PRODUCT_NOT_AVAILABLE(28, "Sản phẩm không đủ số lượng"),
    ORDER_NOT_FOUND(29, "Đơn hàng không tìm thấy"),
    INVALID_ORDER_STATUS(30, "Đơn hàng không có trạng thái đúng để xác định"),
    FEEDBACK_NOT_FOUND(31, "Không tìm thấy bài đánh giá"),
    FEEDBACK_RATING_ERROR(32, "Đánh giá nên từ 1 đến 5 sao");



    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
