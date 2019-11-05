package com.baizhi.service.impl;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;


    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Article article = new Article();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Article> list = articleDao.selectByRowBounds(article, rowBounds);
        int count = articleDao.selectCount(article);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);

        return map;
    }

    @Override
    public String add(Article article) {
        article.setId(UUID.randomUUID().toString());
        int i = articleDao.insert(article);
        if(i==0){
            throw new RuntimeException("添加失败");
        }
        return article.getId();
    }

    @Override
    public void edit(Article article) {
        int i = articleDao.updateByPrimaryKeySelective(article);
        if(i==0){
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public void del(String id) {
        articleDao.selectByPrimaryKey(id);
        int i = articleDao.deleteByPrimaryKey(id);

    }
}
