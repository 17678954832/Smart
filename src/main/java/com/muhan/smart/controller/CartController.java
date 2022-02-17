package com.muhan.smart.controller;

import com.muhan.smart.form.CartAddForm;
import com.muhan.smart.form.CartUpdataForm;
import com.muhan.smart.pojo.User;
import com.muhan.smart.service.ICartService;
import com.muhan.smart.vo.CartVo;
import com.muhan.smart.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.muhan.smart.consts.SmartConst.CURRENT_USER;

/**
 * @Author: Muhan.Zhou
 * @Description 购物车控制层，添加商品进购物车
 * @Date 2022/2/9 20:50
 */
@RestController
public class CartController {

    @Autowired
    private ICartService cartService;


    /**
     * 获取购物车列表
     * @param session
     * @return
     */
    @GetMapping("/carts")
    public ResponseVo<CartVo> list(HttpSession session){
        User user = (User) session.getAttribute(CURRENT_USER);
        return cartService.list(user.getId());
    }


    /**
     * 添加商品到购物车
     * @param cartAddForm
     * @param session
     * @return
     */
    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm, HttpSession session){
        User user = (User) session.getAttribute(CURRENT_USER);
        return cartService.add(user.getId(), cartAddForm);
    }

    /**
     * 更新购物车商品
     * @param cartUpdataForm
     * @param session
     * @param productId
     * @return
     */
    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> update(@Valid @RequestBody CartUpdataForm cartUpdataForm,
                                     HttpSession session,
                                     @PathVariable Integer productId){
        User user = (User) session.getAttribute(CURRENT_USER);
        return cartService.update(user.getId(),productId,cartUpdataForm);
    }

    /**
     * 删除购物车商品
     * @param session
     * @param productId
     * @return
     */
    @DeleteMapping("/carts/{productId}")
    public ResponseVo<CartVo> delete(HttpSession session,
                                     @PathVariable Integer productId){
        User user = (User) session.getAttribute(CURRENT_USER);
        return cartService.delete(user.getId(),productId);
    }

    /**
     * 商品全选
     * @param session
     * @return
     */
    @PutMapping("/carts/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession session){
        User user = (User) session.getAttribute(CURRENT_USER);
        return cartService.selectAll(user.getId());
    }

    /**
     * 商品全不选
     * @param session
     * @return
     */
    @PutMapping("/carts/unSelectAll")
    public ResponseVo<CartVo> unselectAll(HttpSession session){
        User user = (User) session.getAttribute(CURRENT_USER);
        return cartService.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public ResponseVo<Integer> sum(HttpSession session){
        User user = (User) session.getAttribute(CURRENT_USER);
        return cartService.sum(user.getId());
    }

}
