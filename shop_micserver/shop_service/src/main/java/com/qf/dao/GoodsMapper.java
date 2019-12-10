package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.entity.goods;

public interface GoodsMapper extends BaseMapper<goods> {
    IPage<goods> goodslist(Page<goods> page);
}
