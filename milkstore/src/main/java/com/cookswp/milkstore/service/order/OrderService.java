package com.cookswp.milkstore.service.order;

import com.cookswp.milkstore.enums.Status;
import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.dtos.OrderModel.CreateOrderRequest;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderDTO;
import com.cookswp.milkstore.pojo.dtos.OrderModel.OrderItemDTO;
import com.cookswp.milkstore.pojo.entities.*;
import com.cookswp.milkstore.repository.order.OrderRepository;
import com.cookswp.milkstore.repository.orderItem.OrderItemRepository;
import com.cookswp.milkstore.repository.product.ProductRepository;
import com.cookswp.milkstore.repository.shoppingCart.ShoppingCartRepository;
import com.cookswp.milkstore.repository.shoppingCartItem.ShoppingCartItemRepository;
import com.cookswp.milkstore.repository.transactionLog.TransactionLogRepository;
import com.cookswp.milkstore.repository.user.UserRepository;
import com.cookswp.milkstore.service.firebase.FirebaseService;
import com.cookswp.milkstore.service.product.ProductService;
import com.cookswp.milkstore.service.shoppingcart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    private final ShoppingCartItemRepository shoppingCartItemRepository;

    private final TransactionLogRepository transactionLogRepository;

    private final UserRepository userRepository;

    private final OrderItemRepository orderItemRepository;


    private final FirebaseService firebaseService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(ProductRepository productRepository, OrderRepository orderRepository, ProductService productService, ShoppingCartItemRepository shoppingCartItemRepository, TransactionLogRepository transactionLogRepository, UserRepository userRepository, OrderItemRepository orderItemRepository, FirebaseService firebaseService, ShoppingCartRepository shoppingCartRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.transactionLogRepository = transactionLogRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.firebaseService = firebaseService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
    }


    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().peek(order -> {
            if (Status.PRE_ORDERED.equals(order.getOrderStatus()) && productRepository.getProductById(orderItemRepository.findByOrderId(order.getId()).get(0).getProductId()).getPublishDate().isBefore(LocalDate.now())){
                order.setOrderStatus(Status.PREORDERED_ORDER_IN_DELIVERY);
                orderRepository.save(order);
            }
        }).toList();
    }


    //Tạo order dựa trên userID của người dùng
    @Override
    @Transactional
    public Order createOrder(int userID, CreateOrderRequest orderDTO) {
        User user = userRepository.findByUserId(userID);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        validateCheckOut(orderDTO);
        Order order = new Order();
        UUID id = UUID.randomUUID();
        order.setId(id.toString());
        order.setUserId(userID);
        //order.setCarID(orderDTO.getCartID());
        order.setReceiverName(orderDTO.getReceiverName());
        order.setReceiverPhone(orderDTO.getReceiverPhoneNumber());
        order.setOrderStatus(Status.IN_CART);
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(orderDTO.getShippingAddress());
        // order.setCart(orderDTO.);

        //Save Cart Information before clear Cart
        if (orderDTO.getItems() != null) {
            saveOrderItems(order, orderDTO.getItems());
        }

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order createPreOrder(int userID, CreateOrderRequest orderDTO) {
        User user = userRepository.findByUserId(userID);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        validateCheckOut(orderDTO);
        Order order = new Order();
        UUID id = UUID.randomUUID();
        order.setId(id.toString());
        order.setUserId(userID);
        //order.setCarID(orderDTO.getCartID());
        order.setReceiverName(orderDTO.getReceiverName());
        order.setReceiverPhone(orderDTO.getReceiverPhoneNumber());
        order.setOrderStatus(Status.IN_PREORDER_PROGRESS);
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(orderDTO.getShippingAddress());
        // order.setCart(orderDTO.);

        //Save Cart Information before clear Cart
        if (orderDTO.getItems() != null) {
            saveOrderItems(order, orderDTO.getItems());
        }

        return orderRepository.save(order);
    }

    private void validateCheckOut(CreateOrderRequest orderRequest) {
        if (orderRequest.getReceiverName().isEmpty()) {
            throw new AppException(ErrorCode.RECEIVER_NAME_EMPTY);
        }
        if (!isValidVietnameseName(orderRequest.getReceiverName().toLowerCase())) {
            throw new AppException(ErrorCode.RECEIVER_NAME_INVALID);
        }
        if (!hasSpecialCharacters(orderRequest.getReceiverName())) {
            throw new AppException(ErrorCode.RECEIVER_NAME_INVALID);
        }
        if (orderRequest.getShippingAddress().trim().equals(" ")) {
            throw new AppException(ErrorCode.SHIPPING_ADDRESS_EMPTY);
        }
        if (!orderRequest.getShippingAddress().contains("Quận")) {
            throw new AppException(ErrorCode.SHIPPING_ADDRESS_NOT_ENOUGH_FIELD);
        }
        String[] arrays = orderRequest.getShippingAddress().split("Quận");
        boolean isValid = true;
        for (int i = 0; i < arrays.length; i++) {
            if (i == 0) {
                if (arrays[i].trim().isEmpty() || arrays[i].trim().equals(" ")) {
                    isValid = false;
                    break;
                }
            }
        }
        if (!isValid) {
            throw new AppException(ErrorCode.SHIPPING_ADDRESS_EMPTY);
        }
        if (orderRequest.getReceiverPhoneNumber().isEmpty()) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EMPTY);
        }
        if (orderRequest.getReceiverPhoneNumber().length() != 10) {
            throw new AppException(ErrorCode.INVALID_PHONE_NUMBER);
        }
        if (!orderRequest.getReceiverPhoneNumber().startsWith("0")) {
            throw new AppException(ErrorCode.INVALID_PHONE_NUMBER_START_WITH_ZERO);
        }
        if (!checkNumberInPhoneNumbers(orderRequest.getReceiverPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_MUST_CONTAIN_NUMBERS);
        }

    }

    private boolean hasSpecialCharacters(String input) {
//        String regex = "[!@#$%^&*()_+=-`~]";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(input);
//        return matcher.find();
        return true;
    }

    private boolean isValidVietnameseName(String input) {
//        String regex = "^[a-zàáâãèéêìíòóôõùúăđĩũơưăạảấầẩẫậắằẳẵặẹẻẽềềểễệỉịọỏốồổỗộớờởỡợụủứừửữựỳỵỷỹ\\s]+$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(input);
//        return matcher.matches();
        return true;
    }


    private boolean checkNumberInPhoneNumbers(String input) {
        String regex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    @Override
    @Transactional
    public Order updateOrder(String orderId, OrderDTO orderDTO) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        findOrder.setUserId(orderDTO.getUserId());
        findOrder.setOrderDate(orderDTO.getOrderDate());

        findOrder.setTotalPrice(orderDTO.getTotalPrice());
        findOrder.setShippingAddress(orderDTO.getShippingAddress());
        return orderRepository.save(findOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order updateOrderStatus(String orderID) {
        Optional<Order> findOrder = orderRepository.findById(orderID);
        if (findOrder.isEmpty()) {
            return null;
        }
        Order order = findOrder.get();
        String statusCode = transactionLogRepository.findTransactionNoByTxnRef(orderID);
        if ("00".equals(statusCode)) {
            order.setOrderStatus(Status.PAID);
            ShoppingCart shoppingCart = shoppingCartRepository.findCartsByUserId(order.getUserId()).get(0);

            if (shoppingCart != null) {
                reduceProductQuantity(shoppingCart);
                // Lưu các mục giỏ hàng vào bảng OrderItem
                List<OrderItem> orderItems = new ArrayList<>();
                for (ShoppingCartItem cartItem : shoppingCart.getItems()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderId(order.getId());
                    orderItem.setProductId(cartItem.getProduct().getProductID());
                    orderItem.setProductName(cartItem.getProduct().getProductName());
                    orderItem.setProductImage(cartItem.getProduct().getProductImage());//Set order_image
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getProduct().getPrice());
                    orderItems.add(orderItem);
                }
                orderItemRepository.saveAll(orderItems);

                // Xóa các mục giỏ hàng trong ShoppingCart
                shoppingCart.getItems().clear();
                shoppingCartRepository.save(shoppingCart);
            }
        }
        return orderRepository.save(order);
    }

    @Override
    public Order updatePreOrderStatus(String orderID) {
        Optional<Order> findOrder = orderRepository.findById(orderID);
        if (findOrder.isEmpty()) {
            return null;
        }
        Order order = findOrder.get();
        String statusCode = transactionLogRepository.findTransactionNoByTxnRef(orderID);
        if ("00".equals(statusCode)) {
            order.setOrderStatus(Status.PRE_ORDERED);
            ShoppingCart cart = shoppingCartRepository.findCartsByUserId(order.getUserId()).get(1);

            if (cart != null) {
                reduceProductQuantity(cart);
                // Lưu các mục giỏ hàng vào bảng OrderItem
                List<OrderItem> orderItems = new ArrayList<>();
                for (ShoppingCartItem cartItem : cart.getItems()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderId(order.getId());
                    orderItem.setProductId(cartItem.getProduct().getProductID());
                    orderItem.setProductName(cartItem.getProduct().getProductName());
                    orderItem.setProductImage(cartItem.getProduct().getProductImage());//Set order_image
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getProduct().getPrice());
                    orderItems.add(orderItem);
                }
                orderItemRepository.saveAll(orderItems);

                shoppingCartRepository.deleteById(cart.getId());
            }
        }
        return orderRepository.save(order);
    }

//    @Override
//    public List<Order> getAll() {
//        return orderRepository.findAll();
//    }

    private void reduceProductQuantity(ShoppingCart cart) {

        for (ShoppingCartItem item : cart.getItems()) {
            productService.reduceQuantityProduct(item.getProduct().getProductID(), item.getQuantity());
        }
    }

    @Override
    public Order getOrderById(String id) {
        Optional<Order> optionalOrder = orderRepository.findByIdWithCart(id);
        if (optionalOrder.isEmpty()) throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        System.out.println("Order fetched: " + optionalOrder.get().getId());
        System.out.println("Cart size: " + optionalOrder.get().getCart().size());
        for (OrderItem item : optionalOrder.get().getCart()) {
            System.out.println("Item: " + item.getProductName());
        }
        return optionalOrder.get();
    }

    public List<OrderItem> getOrderItemsByOrderId(String orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    //Method to Confirm Order
    @Transactional
    public Order confirmOrderToShipping(String OrderId) {
        Order order = getOrderById(OrderId);
        if (order.getOrderStatus() == Status.PAID) {
            order.setOrderStatus(Status.IN_DELIVERY);
            return orderRepository.save(order);
        } else {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }
    }

    //Method to Cancel Order with Reason
    @Transactional
    public Order cancelOrder(String OrderId, String reason) {
        Order order = getOrderById(OrderId);
        order.setFailureReasonNote(reason);
        String reasons = order.getFailureReasonNote();
        String[] token = reasons.split(";");
        List<String> reasonList = Arrays.asList(token);

        if (reasonList.isEmpty()) {
            order.setFailureReasonNote(reason + "|" + LocalDateTime.now());
            order.setOrderStatus(Status.CANNOT_DELIVER);
        } else if (reasonList.size() == 1) {
            order.setFailureReasonNote(reasonList.get(0)
                    + ";" + reason + "|" + LocalDateTime.now());
            order.setOrderStatus(Status.CANNOT_DELIVER);
        } else {
            throw new RuntimeException("Can not cancel order more than 2 times");
        }
        return orderRepository.save(order);
    }

    //Method to Transfer InDelivery to CompleteTransaction Status
    @Transactional
    public Order completeOrderTransaction(String OrderId, MultipartFile EvidenceImage) {
        Order order = getOrderById(OrderId);
        String imageURL = firebaseService.upload(EvidenceImage);
        if ((order.getOrderStatus() == Status.IN_DELIVERY || Status.PREORDERED_ORDER_IN_DELIVERY.equals(order.getOrderStatus())) && EvidenceImage != null) {
            order.setOrderStatus(Status.COMPLETE_EXCHANGE);
            order.setImage(imageURL);
        } else {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }
        return orderRepository.save(order);
    }

    public Order cannotOrderInDelivery(String OrderId) {
        Order order = getOrderById(OrderId);
        if (order.getOrderStatus() == Status.CANNOT_DELIVER) {
            order.setOrderStatus(Status.IN_DELIVERY);
            return orderRepository.save(order);
        } else {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }
    }

    //Method to get all order from an User
    public List<Order> getOrderByAnUserId(int userId) {
        return orderRepository.findByUserId(userId);
    }


    // Phương thức riêng để lưu thông tin sản phẩm từ giỏ hàng vào OrderItem
    private void saveOrderItems(Order order, List<OrderItemDTO> items) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO item : items) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(item.getProductId());
            orderItem.setProductName(item.getProductName());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());
            orderItems.add(orderItem);
        }
        orderItemRepository.saveAll(orderItems);
    }


    // Convert Order Entity to OrderDTO
    private OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(order.getUserId());
        orderDTO.setStatus(order.getOrderStatus());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setShippingAddress(order.getShippingAddress());
        return orderDTO;
    }

    // Convert OrderDTO to Order entity
    private Order toOrderEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setUserId(orderDTO.getUserId());
        order.setOrderStatus(orderDTO.getStatus());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setShippingAddress(orderDTO.getShippingAddress());
        return order;
    }

    @Override
    public Long getNumberOfOrdersByStatus(String status) throws IllegalArgumentException {
        return orderRepository.getNumberOfOrdersByStatus(Status.valueOf(status));
    }

    @Override
    public Long getTotalOrders() {
        return orderRepository.getTotalOrders();
    }

    @Override
    public Double getTotalRevenue() {
        return orderRepository.getTotalRevenue();
    }

    @Override
    public Map<Status, Long> getOrderStatusBreakdown() {
        List<Object[]> result = orderRepository.getOrderStatusBreakdown();
        return result.stream().collect(Collectors.toMap(
                row -> (Status) row[0],
                row -> (Long) row[1]
        ));
    }

    @Override
    public Double getAverageRevenuePerOrder() {
        return orderRepository.getAverageRevenuePerOrder();
    }

    @Override
    public Long getOrdersByMonth(int startMonth, int endMonth) {
        return orderRepository.getOrdersByMonth(startMonth, endMonth);
    }

    @Override
    public Map<Integer, Long> getOrderCountsForYear(int year) {
        List<Integer> months = IntStream.rangeClosed(1, 12).boxed().toList();
        List<Object[]> results = orderRepository.getOrderCountsByMonth(year);

        Map<Integer, Long> ordersByMonth = new HashMap<>();
        for (Integer month : months) {
            ordersByMonth.put(month, 0L);
        }

        for (Object[] result : results) {
            Integer month = (Integer) result[0];
            Long count = (Long) result[1];
            ordersByMonth.put(month, count);
        }

        return ordersByMonth;
    }
}
