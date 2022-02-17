package com.muhan.smart.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.muhan.smart.SmartApplicationTests;
import com.muhan.smart.form.CartAddForm;
import com.muhan.smart.form.CartUpdataForm;
import com.muhan.smart.vo.CartVo;
import com.muhan.smart.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
public class ICartServiceTest extends SmartApplicationTests {

    @Autowired
    private ICartService cartService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();  //,json序列化，方便打印

    @Test
    public void add() {
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(29);
        cartAddForm.setSelected(true);

        ResponseVo<CartVo> responseVo = cartService.add(1, cartAddForm);
        log.info("list = {}",gson.toJson(responseVo));
    }

    @Test
    public void list(){
        ResponseVo<CartVo> list = cartService.list(1);
        log.info("list = {}" , gson.toJson(list));
    }

    @Test
    public void update(){
        CartUpdataForm cartUpdataForm = new CartUpdataForm();
        cartUpdataForm.setQuantity(5);
        cartUpdataForm.setSelected(false);
        ResponseVo<CartVo> responseVo = cartService.update(1, 27,cartUpdataForm);
        log.info("result = {}",gson.toJson(responseVo));
    }

    @Test
    public void delete(){
        ResponseVo<CartVo> responseVo = cartService.delete(1, 29);
        log.info("result = {}",gson.toJson(responseVo));
    }

}