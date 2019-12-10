package com.qf.service.Impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.dao.GoodsImageMapper;
import com.qf.dao.GoodsMapper;
import com.qf.entity.goods;
import com.qf.entity.goodsImages;
import com.qf.service.IGoodService;
import com.qf.service.ISearchService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodServiceImpl implements IGoodService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsImageMapper goodsImageMapper;
    @Reference
    ISearchService searchService;
    @Autowired
    RabbitTemplate rabbitTemplate;


    @Override
    public IPage<goods> selectList(Page<goods> page1) {


        IPage<goods> page = goodsMapper.goodslist(page1);
        List<goods> list = page.getRecords();
        for (goods good : list) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("gid", good.getId());
            queryWrapper.eq("isfengmian", 1);
            goodsImages goodsImage = goodsImageMapper.selectOne(queryWrapper);
            good.setFmurl(goodsImage.getUrl());
            System.out.println(good);
            System.out.println(goodsImage);
        }
        return page;
    }

    @Override
    @Transactional
    public void insert(goods good) {
        goodsMapper.insert(good);
        goodsImageMapper.insert(new goodsImages().setGid(good.getId()).setIsfengmian(1).setUrl(good.getFmurl()));
        for (String otherurl : good.getOtherurls()) {

            goodsImageMapper.insert(new goodsImages().setGid(good.getId()).setUrl(otherurl).setIsfengmian(0));
        }
        //searchService.insert(good);
        rabbitTemplate.convertAndSend("good_exchange","",good);

    }

    /**
     * 查找商品
     */
    @Override
    public goods queryOne(int id) {
        goods good = goodsMapper.selectById(id);
        QueryWrapper<goodsImages> wrapper = new QueryWrapper<>();
        wrapper.eq("gid", good.getId());
        List<goodsImages> list = goodsImageMapper.selectList(wrapper);
        List<String> urls = new ArrayList<>();
        for (goodsImages goodsImages : list) {
            if (goodsImages.getIsfengmian() > 0) {
                good.setFmurl(goodsImages.getUrl());
            } else {
                urls.add(goodsImages.getUrl());
            }
        }
        good.setOtherurls(urls);
        return good;
    }
}
