package com.poscodx.jblog.repository;

import com.poscodx.jblog.vo.UserVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepository {
    @Autowired
    private SqlSession sqlSession;

    public UserVo findById(String id) {
        return sqlSession.selectOne("user.findById", id);
    }

    public UserVo findByIdAndPassword(String id, String password) {
        return sqlSession.selectOne("user.findByIdAndPassword", Map.of("id", id, "password", password));
    }

    public void insert(UserVo vo) {
        sqlSession.insert("user.insert", vo);
    }
}
