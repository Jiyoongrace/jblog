package com.poscodx.jblog.repository;

import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BlogRepository {
    @Autowired
    private SqlSession sqlSession;

    public BlogVo findByBlogId(String id) {
        return sqlSession.selectOne("blog.findByBlogId", id);
    }

    public void insert(BlogVo blogVo) {
        sqlSession.insert("blog.insert", blogVo);
    }

    public List<Map<String, Object>> getCategoryById(String id) {
        return sqlSession.selectList("category.getCategoryById", id);
    }

    public List<PostVo> getPostById(Map<String, Object> map) {
        return sqlSession.selectList("blog.getPostById", map);
    }

    public void modifyBlog(BlogVo blogVo) {
        sqlSession.update("blog.modifyBlog", blogVo);
    }

    public int postCountByCategoryNo(Long no) {
        return sqlSession.selectOne("category.postCountByCategoryNo", no);
    }

    public void changeNoCatagery(Long no, String id) {
        sqlSession.update("category.changeNoCatagery", Map.of("no", no, "id", id));
    }

    public void insertCategory(CategoryVo categoryVo) {
        sqlSession.insert("category.insertCategory", categoryVo);
    }

    public void deleteCategory(Long no) {
        sqlSession.delete("category.deleteCategory", no);
    }

    public void insertPost(PostVo postVo) {
        sqlSession.insert("blog.insertPost", postVo);
    }

    public List<PostVo> getPostList(String id) {
        return sqlSession.selectList("blog.getPostList", id);
    }

    public List<PostVo> getCurrentPost(Map<String, Object> map) {
        return sqlSession.selectList("blog.getCurrentPost", map);
    }

    public List<PostVo> getTopCategoryPost(Map<String, Object> map) {
        return sqlSession.selectList("blog.getTopCategoryPost", map);
    }

    public List<PostVo> getPost(Map<String, Object> map) {
        return sqlSession.selectList("blog.getPost", map);
    }
}
