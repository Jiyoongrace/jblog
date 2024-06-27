package com.poscodx.jblog.service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    public BlogVo getBlogById(String id) {
        return blogRepository.findByBlogId(id);
    }

    public List<Map<String, Object>> getCategoryById(String id) {
        List<Map<String, Object>> categoryList = blogRepository.getCategoryById(id);
        return categoryList;
    }

    public List<PostVo> getPostById(Map<String, Object> map) {
        return blogRepository.getPostById(map);
    }

    public void modifyBlog(BlogVo blogVo) {
        blogRepository.modifyBlog(blogVo);
    }

    public int postCountByCategoryNo(Long no) {
        return blogRepository.postCountByCategoryNo(no);
    }

    public void changeNoCatagery(Long no, String id) {
        blogRepository.changeNoCatagery(no, id);
    }

    public void addCategory(CategoryVo categoryVo) {
        blogRepository.insertCategory(categoryVo);
    }

    public void deleteCategory(Long no) {
        blogRepository.deleteCategory(no);
    }

    public void addPost(PostVo postVo) {
        blogRepository.insertPost(postVo);
    }

    public List<PostVo> getPostList(String id) {
        return blogRepository.getPostList(id);
    }

    public List<PostVo> getCurrentPost(Map<String, Object> map) {
        return blogRepository.getCurrentPost(map);
    }

    public List<PostVo> getTopCategoryPost(Map<String, Object> map) {
        return blogRepository.getTopCategoryPost(map);
    }

    public List<PostVo> getOnePost(Map<String, Object> map) {
        return blogRepository.getPost(map);
    }
}
