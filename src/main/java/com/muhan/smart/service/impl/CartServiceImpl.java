package com.muhan.smart.service.impl;

import com.google.gson.Gson;
import com.muhan.smart.dao.ProductMapper;
import com.muhan.smart.enums.ProductStatusEnum;
import com.muhan.smart.enums.ResponseEnum;
import com.muhan.smart.form.CartAddForm;
import com.muhan.smart.form.CartUpdataForm;
import com.muhan.smart.pojo.Cart;
import com.muhan.smart.pojo.Product;
import com.muhan.smart.service.ICartService;
import com.muhan.smart.view.CartProductView;
import com.muhan.smart.view.CartView;
import com.muhan.smart.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Muhan.Zhou
 * @Description 购物车实现类
 * @Date 2022/2/9 21:14
 */
@Service
public class CartServiceImpl implements ICartService {

    public static final String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    @Autowired
    private ProductMapper productMapper;

    //redis
    @Autowired
    private StringRedisTemplate redisTemplate;

    //json序列化
    private Gson gson = new Gson();

    /**
     * 添加商品到购物车
     * @param cartAddForm
     * @return
     */
    @Override
    public ResponseView<CartView> add(Integer uid,CartAddForm cartAddForm) {

        //针对此接口，购物车数量永远是1
        Integer quntity = 1;

        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());

        //判断商品是否存在
        if (product == null){
            return ResponseView.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }

        //商品是否正常在售
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())){
            return ResponseView.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        //商品库存是否足够
        //商品数量不用传，添加商品永远是以1累加，把一件商品加到购物车，只需要判断是否是大于0
        if (product.getStock() <= 0){
            return ResponseView.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }

        //写入到redis
        //key: cart_1
        //set(k,v) 第一个是名字，第二个是对象
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();

        //将redis的key提出来
        String redisKey =String.format(CART_REDIS_KEY_TEMPLATE,uid);

        Cart cart;

        //将value提出来
        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));

        //如果value是空的
        if (StringUtils.isEmpty(value)){
            //redis里面没有该商品，新增
            cart = new Cart(product.getId(), quntity, cartAddForm.getSelected());
        }else {
            //如果读出来已经有该商品，数量+1
            //拿出value，并反序列化
            //第一个是string,第二个是要转成哪个对象
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quntity);  //+1
        }

        //写入到redis
        opsForHash.put(redisKey,
                String.valueOf(product.getId()),
                gson.toJson(cart));

        return list(uid);
    }

    /**
     * 获取购物车列表
     * @param uid
     * @return
     */
    @Override
    public ResponseView<CartView> list(Integer uid) {

        //从redis获取购物车列表
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey =String.format(CART_REDIS_KEY_TEMPLATE,uid);
        //通过key获取所有的键值对
        Map<String, String> entries = opsForHash.entries(redisKey);

        //是否全选标记,默认true
        boolean selectedAll = true;

        //总数初始化为0
        Integer cartTotalQuantity = 0;

        //购物车总价
        BigDecimal cartTotalPrice =  BigDecimal.ZERO;


        CartView cartView = new CartView();
        List<CartProductView> cartProductViewList = new ArrayList<>();

        //遍历
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            //获取redis里面的key，也就是商品id
            Integer productId = Integer.valueOf(entry.getKey());
            //获取redis的value，并将其序列化
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);

            //使用peoductId从数据库查数据
            //TODO 需要优化，不要再for里面查询数据库，使用musql里面的in
            Product product = productMapper.selectByPrimaryKey(productId);

            if (product != null){
                CartProductView cartProductView = new CartProductView(productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),  //总价=单价*数量
                        product.getStock(),
                        cart.getProductSelected()
                        );

                //将对象加入list
                cartProductViewList.add(cartProductView);

                //判断是否选中,如果未选中，
                if (!cart.getProductSelected()){
                    selectedAll = false;
                }

                //商品数量
                cartTotalQuantity += cart.getQuantity();

                //购物车总价 = 购物车里面所有商品的价格*商品的数量的和
                //只计算选中的
                if (cart.getProductSelected()) {
                    cartTotalPrice = cartTotalPrice.add(cartProductView.getProductTotalPrice());
                }

            }
        }

        //设置返回的数据
        //有一个没有选中，就不叫全选，
        cartView.setSelectAll(selectedAll);
        cartView.setCartTotalQuantity(cartTotalQuantity);  //总数
        cartView.setCartTotalPrice(cartTotalPrice);  //购物车总价
        
        
        //将list存入cartView对象
        cartView.setCartProductViewList(cartProductViewList);

        return ResponseView.success(cartView);
    }

    /**
     * 购物车更新
     * @param uid
     * @param productId  商品id
     * @param form  参数
     * @return
     */
    @Override
    public ResponseView<CartView> update(Integer uid, Integer productId, CartUpdataForm form) {

        //先从redis里面读取数据出来
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();

        //将redis的key提出来
        String redisKey =String.format(CART_REDIS_KEY_TEMPLATE,uid);

        //将value提出来
        String value = opsForHash.get(redisKey, String.valueOf(productId));

        //如果value是空的
        if (StringUtils.isEmpty(value)){
            //redis里面没有该商品，报错
            return ResponseView.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }

        //如果读出来已经有该商品，修改内容
        // 数量不等于空并且大于等于0
        Cart cart = gson.fromJson(value, Cart.class);
        if (form.getQuantity() != null && form.getQuantity() >= 0){
            cart.setQuantity(form.getQuantity());
        }
        //更新选中
        if (form.getSelected() != null){
            cart.setProductSelected(form.getSelected());
        }

        //将更新后的数据设置进购物车
        opsForHash.put(redisKey,String.valueOf(productId),gson.toJson(cart));

        return list(uid);
    }


    /**
     * 删除购物车商品
     * @param uid
     * @param productId
     * @return
     */
    @Override
    public ResponseView<CartView> delete(Integer uid, Integer productId) {

        //先从redis里面读取数据出来
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();

        //将redis的key提出来
        String redisKey =String.format(CART_REDIS_KEY_TEMPLATE,uid);

        //将value提出来
        String value = opsForHash.get(redisKey, String.valueOf(productId));

        //如果redis里面没有该商品，报错
        if (StringUtils.isEmpty(value)){
            return ResponseView.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }

        //删除购物车商品
        opsForHash.delete(redisKey,String.valueOf(productId));

        return list(uid);

    }

    /**
     * 购物车全选
     * @param uid
     * @return
     */
    @Override
    public ResponseView<CartView> selectAll(Integer uid) {
        //从redis获取购物车列表
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey =String.format(CART_REDIS_KEY_TEMPLATE,uid);

        //遍历购物车，将所有属性设置为true
        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(true);

            //set到redis
            opsForHash.put(redisKey,
                    String.valueOf(cart.getProductId()),
                    gson.toJson(cart));
        }

        return list(uid);
    }

    /**
     * 购物车全不选
     * @param uid
     * @return
     */
    @Override
    public ResponseView<CartView> unSelectAll(Integer uid) {
        //从redis获取购物车列表
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey =String.format(CART_REDIS_KEY_TEMPLATE,uid);

        //遍历购物车，将所有属性设置为true
        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(false);

            //set到redis
            opsForHash.put(redisKey,
                    String.valueOf(cart.getProductId()),
                    gson.toJson(cart));
        }

        return list(uid);
    }

    /**
     * 购物车商品总和
     * @param uid
     * @return
     */
    @Override
    public ResponseView<Integer> sum(Integer uid) {
        //从0开始累加
        Integer sum = listForCart(uid).stream()
                .map(Cart::getQuantity)
                .reduce(0, Integer::sum);
        return ResponseView.success(sum);
    }


    /**
     * 遍历购物车的方法
     * @return
     */
    private List<Cart> listForCart(Integer uid){
        //从redis获取购物车列表
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey =String.format(CART_REDIS_KEY_TEMPLATE,uid);
        //通过key获取所有的键值对
        Map<String, String> entries = opsForHash.entries(redisKey);

        //遍历购物车，
        List<Cart> cartsList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            cartsList.add(gson.fromJson(entry.getValue(),Cart.class));
        }

        return cartsList;
    }
}
