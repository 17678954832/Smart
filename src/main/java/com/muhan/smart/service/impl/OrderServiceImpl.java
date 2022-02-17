package com.muhan.smart.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.muhan.smart.dao.OrderItemMapper;
import com.muhan.smart.dao.OrderMapper;
import com.muhan.smart.dao.ProductMapper;
import com.muhan.smart.dao.ShippingMapper;
import com.muhan.smart.enums.OrderStatusEnum;
import com.muhan.smart.enums.PaymentTypeEnum;
import com.muhan.smart.enums.ProductStatusEnum;
import com.muhan.smart.enums.ResponseEnum;
import com.muhan.smart.pojo.*;
import com.muhan.smart.service.ICartService;
import com.muhan.smart.service.IOrderService;
import com.muhan.smart.vo.OrderItemVo;
import com.muhan.smart.vo.OrderVo;
import com.muhan.smart.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Muhan.Zhou
 * @Description
 * @Date 2022/2/13 13:19
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;



    /**
     * 创建订单
     * @param uid        用户id
     * @param shippingId 收货地址id
     * @return
     */
    @Override
    @Transactional
    public ResponseVo<OrderVo> createOrder(Integer uid, Integer shippingId) {
        //1.收货地址校验（查出来收货地址）
        //先根据当前用户的id属于当前用户的收货地址查询出来，
        Shipping shipping = shippingMapper.selectByUidAndShippingId(uid, shippingId);
        if (shipping ==null){
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }


        //2.获取购物车，判断商品是否选中，校验库存，商品是否存在,针对选中的商品
        //从redis获取购物车,此方法已经在购物车写过，可以直接调用
        List<Cart> carts = cartService.listForCart(uid).stream()
                .filter(Cart::getProductSelected)
                .collect(Collectors.toList());
        //判断选中的商品是否为空
        if(CollectionUtils.isEmpty(carts)){
            return ResponseVo.error(ResponseEnum.CART_SELECT_IS_EMPTY);
        }
        //获取carts里的productIds
        Set<Integer> productIdSet = carts.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toSet());
        //通过id去数据库查商品
        List<Product> productList = productMapper.selectByProductIdSet(productIdSet);

        //把productList变成map集合，可以省略在for循环里面方便获取
        Map<Integer, Product> map = productList.stream().collect(Collectors.toMap(Product::getId,product -> product));

        List<OrderItem> orderItemList = new ArrayList<>();
        Long orderNo = generateOrderNo();  //TODO 订单号 优化

        for (Cart cart : carts) {
            //根据productId查询数据库
            Product product = map.get(cart.getProductId());
            //商品是否存在
            if (product == null){
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST, "商品不存在,productId = " + cart.getProductId());
            }
            //商品的上下架状态校验
            if (!ProductStatusEnum.ON_SALE.getCode().equals(product.getStatus())){
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE,"商品下架或已删除，商品名称：" + product.getName());
            }
            //校验库存是否充足
            if (product.getStock() < cart.getQuantity()) {
                return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR,"商品库存不足：" + product.getName());
            }

            //单独写一个orderItem方法，将orderItem的数据set进去
            OrderItem orderItem = buildOrderItem(uid, orderNo, product, cart.getQuantity());
            orderItemList.add(orderItem);

            //5.成功后，减库存，针对选中的商品  数据库库存-购买的数量
            product.setStock(product.getStock() - cart.getQuantity());
            int i = productMapper.updateByPrimaryKeySelective(product);
            if (i <= 0 ){
                return ResponseVo.error(ResponseEnum.ERROR);
            }

        }

        //3.计算总价，只计算被选中的商品(已经在方法里面做了)
        Order order = bulidOrder(uid, orderNo, shippingId, orderItemList);

        //4.生成订单入库：order，orderItem，使用事务保证数据一致性，针对选中的商品(@Transactional)
        int rowForOrder = orderMapper.insertSelective(order);
        if (rowForOrder <= 0){
            //TODO 增加短信通知接口
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        int rowForOrderItem = orderItemMapper.batchInsert(orderItemList);
        if (rowForOrderItem <= 0 ){
            return ResponseVo.error(ResponseEnum.ERROR);
        }


        //6.更新购物车（删除选中的商品），针对选中的商品,reids是原子性的，事务回滚会不起作用
        //再次遍历购物车,删除已经生成订单的商品
        for (Cart cart : carts) {
            cartService.delete(uid,cart.getProductId());
        }


        //7.构造orderVo对象并返回返回给前端，
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.success(orderVo);
    }

    /**
     * 查询订单列表
     * @param uid      用户id
     * @param pageNum  分页
     * @param pageSize
     * @return 分页数据
     */
    @Override
    public ResponseVo<PageInfo> orderList(Integer uid, Integer pageNum, Integer pageSize) {
        //分页设置，通过id查询出订单列表
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.selectByUid(uid);

        //查询出来orderNoSet集合，再用orderNoSet集合去orderItem里面查询数据
        Set<Long> orderNoSet = orderList.stream()
                .map(Order::getOrderNo)
                .collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);
        //将orderItemList转换成想要的格式的map,k:orderNo,v:OrderItem
        //返回value为list时，使用groupingBy，返回对象用toMap();
        Map<Long,List<OrderItem>> orderItemMap = orderItemList.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderNo));


        //查出来收货地址集合，再通过集合去查询当前用户的收货地址
        Set<Integer> shippidSet = orderList.stream()
                .map(Order::getShippingId)
                .collect(Collectors.toSet());
        List<Shipping> shippingList = shippingMapper.selectByIdSet(shippidSet);
        //将收收货地址转换为map,k:shippingId,v:shipping
        Map<Integer, Shipping> shippingMap = shippingList.stream()
                .collect(Collectors.toMap(Shipping::getId,shipping -> shipping));


        List<OrderVo> orderVoList = new ArrayList<>();

        //遍历订单列表
        for (Order order : orderList) {
            //获取order对应的orderItem
            OrderVo orderVo = buildOrderVo(order, orderItemMap.get(order.getOrderNo()), shippingMap.get(order.getShippingId()));
            orderVoList.add(orderVo);
        }

        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);

        return ResponseVo.success(pageInfo);
    }

    /**
     * 订单详情
     * @param uid
     * @param orderNo
     * @return
     */
    @Override
    public ResponseVo<OrderVo> orderDetails(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);

        //判断订单是否存在以及是否属于此用户的订单
        //如果不存在
        if (order == null || !order.getUserId().equals(uid)){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }

        //存在
        Set<Long> orderNoSet = new HashSet();
        orderNoSet.add(order.getOrderNo());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);

        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());

        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);

        return ResponseVo.success(orderVo);
    }

    /**
     * 通过uid和orderNo取消订单
     *
     * @param uid
     * @param orderNo
     * @return
     */
    @Override
    public ResponseVo cancelOrder(Integer uid, Long orderNo) {
        //判断此订单是否属于此用户
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || !order.getUserId().equals(uid)){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }

        //判断：什么情况下是可以取消的
        //设定 ：只有未付款的才可以取消
        if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())){
            return ResponseVo.error(ResponseEnum.ORDER_STATUS_ERROR);
        }

        order.setStatus(OrderStatusEnum.CANCELED.getCode());
        order.setCloseTime(new Date());
        int i = orderMapper.updateByPrimaryKeySelective(order);
        if (i <= 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        return ResponseVo.success();
    }

    /**
     * pay通过mq通知修改订单状态
     * @param orderNo
     */
    @Override
    public void paid(Long orderNo) {
        //判断此订单是否为空
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null){
            throw new RuntimeException(ResponseEnum.ORDER_NOT_EXIST.getDesc() + "订单编号：" + orderNo);
        }

        //判断：正常情况下数据库应该是未付款，只有未付款才可以变成已付款
        if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())){
            throw new RuntimeException(ResponseEnum.ORDER_STATUS_ERROR.getDesc() + "订单编号：" + orderNo);
        }

        //校验通过，修改为已支付
        order.setStatus(OrderStatusEnum.PAID.getCode());
        order.setPaymentTime(new Date());
        int i = orderMapper.updateByPrimaryKeySelective(order);
        if (i <= 0){
            throw new RuntimeException("将订单更新为【已支付】状态失败,订单编号：" + orderNo);
        }

    }


    /**
     * 构造orderVo对象
     * @param order  订单对象
     * @param orderItemList  orderItem对象
     * @param shipping  收货地址
     */
    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItemList, Shipping shipping) {
        //将order复制给ordervo
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order,orderVo);

        //将orderItemList由list转换为orderItemVo
        List<OrderItemVo> orderItemVoList = orderItemList.stream().map(e -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(e, orderItemVo);
            return orderItemVo;
        }).collect(Collectors.toList());

        //将转换后的orderItemVoset进orderVo里面的OrderItemVoList里
        orderVo.setOrderItemVoList(orderItemVoList);

        //地址id
        if (shipping != null) {
            orderVo.setShippingId(shipping.getId());
            orderVo.setShippingVo(shipping);
        }


        return orderVo;
    }


    /**
     * 生成订单编号的方法
     * @return
     */
    private Long generateOrderNo() {

        //TODO  优化
        return System.currentTimeMillis() + new Random().nextInt(999);

    }


    /**
     * 构造order对象
     */
    private Order bulidOrder(Integer uid,
                            Long orderNo,
                            Integer shippingId,
                            List<OrderItem> orderItemList) {

        //对购物车价格从零开始做累加
        BigDecimal totalPrice = orderItemList.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        order.setPayment(totalPrice);  //购物车支付总价
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());  //1-在线支付
        order.setPostage(0);  //运费
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());  //订单状态，刚下单是未支付
        return order;
    }

    /**
     * 构造Item对象
     * @return
     */
    private OrderItem buildOrderItem(Integer uid,Long orderNo,Product product,Integer quantity) {
        OrderItem item = new OrderItem();
        item.setUserId(uid);
        item.setOrderNo(orderNo);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductImage(product.getMainImage());
        item.setCurrentUnitPrice(product.getPrice());  //单价
        item.setQuantity(quantity);
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));  //单价*数量

        return item;
    }
}
