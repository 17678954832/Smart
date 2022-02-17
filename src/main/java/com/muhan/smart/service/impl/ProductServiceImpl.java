package com.muhan.smart.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.muhan.smart.dao.ProductMapper;
import com.muhan.smart.pojo.Product;
import com.muhan.smart.service.ICategoryService;
import com.muhan.smart.service.IProductService;
import com.muhan.smart.vo.ProductDetailVo;
import com.muhan.smart.vo.ProductVo;
import com.muhan.smart.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.muhan.smart.enums.ProductStatusEnum.DELETE;
import static com.muhan.smart.enums.ProductStatusEnum.OFF_SALE;
import static com.muhan.smart.enums.ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE;

/**
 * @Author: Muhan.Zhou
 * @Description 查询商品实现类
 * @Date 2022/2/7 21:29
 */
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    //注入商品类目service
    @Autowired
    private ICategoryService categoryService;

    //注入商品mapper
    @Autowired
    private ProductMapper productMapper;

    /**
     * 查询商品列表
     * @param categoryId 商品类目id
     * @param pageNum    分页序号
     * @param pageSize   分页大小
     * @return
     */
    @Override
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        //new一个集合用来存放查出来的数据
        Set<Integer> categoryIdSet = new HashSet<>();

        if (categoryId != null){
            //先要把Category的子类id查出来
            categoryService.findSubCategoryId(categoryId,categoryIdSet);

            //上面查出来的只是子类，现在需要将自身也包含进去
            categoryIdSet.add(categoryId);
        }


        //pagehelper分页
        //pageSize 每一页的数量
        //pageNum 第几页
        // pageNum =1,pageSize = 2,第一页放两条数据
        PageHelper.startPage(pageNum,pageSize);

        //用查出来的id集合去product查商品
        List<Product> productList = productMapper.selectByCategoryIdSet(categoryIdSet);

        List<ProductVo> productVoList = productList.stream()
                .map(e -> {
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(e, productVo);
                    return productVo;
                })
                .collect(Collectors.toList());

        //productList 数据库里面查出来的信息
        //productVoList  返回view对象
        PageInfo pageInfo = new PageInfo<>(productList);
        pageInfo.setList(productVoList);

        return ResponseVo.success(pageInfo);
    }

    /**
     * 通过id查询商品详细信息
     * @param productId
     * @return
     */
    @Override
    public ResponseVo<ProductDetailVo> detail(Integer productId) {
        //查询数据库
        Product product = productMapper.selectByPrimaryKey(productId);

        //判断,商品下架或删除
        //if (product.getStatus().equals(OFF_SALE || DELETE)  )
        if (product.getStatus().equals(OFF_SALE.getCode()) || product.getStatus().equals(DELETE.getCode())){
            return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE);
        }

        //对象转换
        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product, productDetailVo);

        //库存比较敏感，不显示回去
        //如果大于100，就显示100，不大于就显示本身
        productDetailVo.setStock(product.getStock() > 100 ? 100 : product.getStock());
        return ResponseVo.success(productDetailVo);
    }
}
