package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.goods;
import com.qf.service.IGoodService;
import com.qf.service.ISearchService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("goods")
public class searchController {
    @Reference
    ISearchService searchService;
    @Reference
    IGoodService goodService;

    @RequestMapping("search")
    public String search(Model model,String keyWord){
        System.out.println("关键字："+keyWord);
        List<goods> list = searchService.queryGoods(keyWord);
        model.addAttribute("list",list);

        return "goodslist";
    }

    @RequestMapping("queryOne")
    public String queryOne(Model model,int id){
        goods good = goodService.queryOne(id);
        model.addAttribute("good",good);
        return "gooddetail";
    }
}
