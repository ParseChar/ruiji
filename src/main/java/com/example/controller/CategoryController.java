package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.entity.Category;
import com.example.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     *
     *添加种类
     * @author H2
     * @date  19:47
     * @param category
     * @return R<String>
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("执行了:插入{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    @GetMapping("/page")
    public R<Page> page(Integer page,Integer pageSize){
        //分页构造器
        Page<Category>pageInfo =new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件
        queryWrapper.orderByDesc(Category::getSort);
        //分页查询
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("删除分类, id为：{}",ids);
        //categoryService.removeById(id);
        categoryService.remove(ids);
        return R.success("删除成功");
    }
    @PutMapping
    public R<String> update(@RequestBody Category category){

        log.info("更新了:{}",category);
        categoryService.updateById(category);
        return R.success("修改更新分类成功");
    }

    /**
     *
     *根据条件查询分类数据
     * @author H2
     * @date  10:38
     * @param category
     * @return R<List<Category>>
     */
    @GetMapping("/list")
    public R<List<Category>> list (Category category){
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper =new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        //排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
