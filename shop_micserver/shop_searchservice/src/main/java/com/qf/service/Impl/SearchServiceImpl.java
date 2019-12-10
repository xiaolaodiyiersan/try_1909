package com.qf.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.goods;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements ISearchService {
    @Autowired
    SolrClient solrClient;

    /**
     * 加入solr
     *
     * @param good
     */
    @Override
    public void insert(goods good) {

        SolrInputDocument solrInputFields = new SolrInputDocument();
        solrInputFields.addField("id", good.getId() + "");
        solrInputFields.addField("subject", good.getSubject());
        solrInputFields.addField("info", good.getInfo());
        solrInputFields.addField("price", good.getPrice().doubleValue());
        solrInputFields.addField("save", good.getSave());
        solrInputFields.addField("image", good.getFmurl());

        try {
            solrClient.add(solrInputFields);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<goods> queryGoods(String keyWord) {
        System.out.println("搜索关键字："+keyWord);
        SolrQuery solrQuery = new SolrQuery();
        if(keyWord != null && !keyWord.equals("")){
            solrQuery.setQuery("subject: " + keyWord +" || info: " +keyWord);
        }else{
            solrQuery.setQuery("*:*");
        }


        solrQuery.setStart(0);
        solrQuery.setRows(10);
        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        solrQuery.addHighlightField("subject");
        try {
            QueryResponse response = solrClient.query(solrQuery);
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            SolrDocumentList result = response.getResults();
            long numFound = result.getNumFound();
            List<goods> list = new ArrayList<>();
             for (SolrDocument entries : result) {
                goods good = new goods();
                good.setId(Integer.parseInt((String) entries.get("id")));
                good.setSubject(entries.get("subject")+"")
                        .setFmurl(entries.get("image")+"")
                        .setPrice(BigDecimal.valueOf((Double) entries.get("price")))
                        .setSave((int)entries.get("save"));

                if(highlighting.containsKey(good.getId()+"")){
                    Map<String,List<String>> map = highlighting.get(good.getId()+"");
                    List<String> list2 = map.get("subject");
                    if(list2 != null){
                        good.setSubject(list2.get(0));
                    }

                }
                list.add(good);
                 System.out.println(good);
            }
             return list;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





}
