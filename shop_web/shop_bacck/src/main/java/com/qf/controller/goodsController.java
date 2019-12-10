package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.ResultData;
import com.qf.entity.goods;
import com.qf.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/goods")
public class goodsController {


    @Reference
    IGoodService goodService;

    @Autowired
    FastFileStorageClient fastFileStorageClient;

    @RequestMapping("/list")
    public String goodsList(Model model) {
        Page<goods> page1 = new Page<>(1, 10);
        IPage<goods> page = goodService.selectList(page1);
        model.addAttribute("list", page.getRecords());
        for (goods record : page.getRecords()) {
            System.out.println(record);
        }

        return "goodsList";
    }

    @RequestMapping("uploader")
    @ResponseBody
    public ResultData<String> uploader(MultipartFile file) {
        String path = null;
        try {
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), "JPG", null);
            path = storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultData<String>().setCode(ResultData.errorList.OK).setData("http://www.image.com:8080/" + path);
    }

    @RequestMapping("insert")
    public String insert( goods goods){
        goodService.insert(goods);
        return "redirect:/goods/list";
    }

}
