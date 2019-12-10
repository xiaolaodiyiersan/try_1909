package com.qf.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.entity.goods;

public interface IGoodService {
    IPage<goods> selectList( Page<goods> page1);

    void insert(goods goods);

    goods queryOne(int id);
}
