package com.qf.service;

import com.qf.entity.goods;

import java.util.List;

public interface ISearchService {
    /**
     * 向solr添加数据
     * @param good
     */
    void insert(goods good);

    /**
     * 搜索数据
     * @param keyWord
     * @return
     */
    List<goods> queryGoods(String keyWord);


}
