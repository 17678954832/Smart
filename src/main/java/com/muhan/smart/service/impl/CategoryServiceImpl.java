package com.muhan.smart.service.impl;

import com.muhan.smart.dao.CategoryMapper;
import com.muhan.smart.pojo.Category;
import com.muhan.smart.service.ICategoryService;
import com.muhan.smart.vo.CategoryVo;
import com.muhan.smart.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.muhan.smart.consts.SmartConst.ROOT_PARENT_ID;

/**
 * @Author: Muhan.Zhou
 * @Description 商品类目实现类
 * @Date 2022/1/31 10:24
 */
@Service
@Slf4j
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 一次性将所有的数据查出来后再去进行数据的遍历的排序等操作，其速度远远大于其直接和数据库打交道的速度
     * 耗时：HTTP请求 > 磁盘 > 内存
     * 最好不要在for循环里面进行http请求和使用sql语句对数据库进行操作，这是最耗时的。
     * 查询所有一级目录
     * @return
     */
    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {

        //List<CategoryVo> categoryVoList = new ArrayList<>();

        //查询所有
        List<Category> categories = categoryMapper.selectAll();

        //查出parent_id=0的数据
        //使用for循环的方式
       /* for (Category category : categories) {

            if (category.getParentId().equals(ROOT_PARENT_ID)){
                //构造一个新对象
                CategoryVo categoryView = new CategoryVo();
                //将查出来的数据复制给新对象
                BeanUtils.copyProperties(category,categoryView);
                //将新对象放进list并返回
                categoryVoList.add(categoryView);
            }

        }*/

        //lambda + stream
        List<CategoryVo> categoryVoList = categories.stream()
                .filter(e -> e.getParentId().equals(ROOT_PARENT_ID))   //parent_id = 0
                .map(this::category2CategoryView)
                .sorted(Comparator.comparing(CategoryVo::getSortOrder))   //对以及目录排序
                .collect(Collectors.toList());

        //查询子目录
        //categoryVoList  已经查出来的所有parent——id=0的数据都在里面
        //categories  //数据库里面所有的查出来的数据
        findSubCategory(categoryVoList,categories);

        //log.info("返回数据是：" + ResponseVo.success(categoryVoList));
        return ResponseVo.success(categoryVoList);
    }

    /**
     * 查询商品信息
     * 根据id查询所有的子类
     * @param id   子类的id
     * @param resultSet   结果集
     */
    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        //查询所有
        List<Category> categories = categoryMapper.selectAll();

        //直接递归
        //由参数决定调用的方法
        findSubCategoryId(id, resultSet,categories);

        //对查出来的结果进行遍历，防止每次递归都要查询数据库，将遍历单独写成方法
        /*for (Category category : categories) {
            if (category.getParentId().equals(id)){  //如果传进来的id等于parent_id，那么就是其子目录，
                //然后将这个id放到结果集里面
                resultSet.add(category.getId());

                //递归，继续使用这个id去查其下的所有子目录
                findSubCategoryId(category.getId(),resultSet);

            }
        }*/
    }

    /**
     * findSubCategoryId方法的重载
     * 为了避免递归自己调用自己时，每次都要执行一次数据库查询，所以将递归查询子目录单独拿出来写一个方法
     * @param id  传过来的id
     * @param resultSet   结果集
     * @param categories  数据源
     */
    private void findSubCategoryId(Integer id, Set<Integer> resultSet ,List<Category> categories){
        //对数据源进行遍历
        for (Category category : categories) {
            if (category.getParentId().equals(id)){  //如果传进来的id等于parent_id，那么就是其子目录，
                //然后将这个id放到结果集里面
                resultSet.add(category.getId());

                //递归，继续使用这个id去查其下的所有子目录
                findSubCategoryId(category.getId(),resultSet ,categories);
            }
        }
    }


    /**
     * 查询子目录的方法
     * @param categoryVoList
     * @param categories
     */
    private void findSubCategory(List<CategoryVo> categoryVoList, List<Category> categories){
        //遍历所有parent_id=0的数据，获得其id
        //将一级目录里面的id作为条件去查询二级目录里面的parent_id
        for (CategoryVo categoryVo : categoryVoList) { //一级目录
            //存放子目录
            List<CategoryVo> subCategoryVoList = new ArrayList<>();

            for (Category category : categories) {   //二级目录
                //如果查到内容，设置subCateGory，继续往下查
                if (categoryVo.getId().equals(category.getParentId())){
                    CategoryVo sucCategoryVo = category2CategoryView(category);
                    subCategoryVoList.add(sucCategoryVo);  //将查出来的的内容添加进subCategoryViewList集合里面
                }
                //将数据排序
                //subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder));  //升序，从小到大
                //对CategoryView里面的getSortOrder字段进行排序
                subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());  //反转一下，就是从大到小，数字越大，优先级越高

                //将值放进之前查询一级目录时的subCategory里面
                categoryVo.setSubCategories(subCategoryVoList);

                //上面设置完成后，继续往下查询，自己调用自己，递归
                //subCategoryVoList  查出来的子目录
                //categories  数据源
                findSubCategory(subCategoryVoList,categories);
            }
        }
    }


    /**
     * 将category转换程CategoryView的方法
     * @param category
     * @return
     */
    private CategoryVo category2CategoryView(Category category){
        //构造一个新对象
        CategoryVo categoryVo = new CategoryVo();
        //将查出来的数据复制给新对象
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
