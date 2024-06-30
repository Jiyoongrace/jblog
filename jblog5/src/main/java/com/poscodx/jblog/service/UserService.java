package com.poscodx.jblog.service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.repository.UserRepository;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserVo duplicateCheckId(String id) {
        return userRepository.findById(id);
    }

    public UserVo getUser(String id, String password) {
        return userRepository.findByIdAndPassword(id, password);
    }

    public void join(@Valid UserVo userVo) {
        userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
        userRepository.insert(userVo);

        BlogVo blogVo = new BlogVo();
        blogVo.setId(userVo.getId());
        blogVo.setTitle(userVo.getName() + " 블로그");
        blogVo.setLogo("/assets/images/spring-logo.jpg");
        blogRepository.insert(blogVo);

        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setNo(1L);
        categoryVo.setName("미분류");
        categoryVo.setDescription("카테고리를 지정하지 않은 경우");
        categoryVo.setBlogId(userVo.getId());
        blogRepository.insertCategory(categoryVo);
    }
}
