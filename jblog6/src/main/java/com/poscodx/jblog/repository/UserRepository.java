package com.poscodx.jblog.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poscodx.jblog.vo.UserVo;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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

    public UserVo findById2(String username) {
        return sqlSession.selectOne("user.findById2", username);
    }

    public <R> R findByIdSC(String id, Class<R> resultType) {
        FindByIdResultHandler<R> findByIdResultHandler = new FindByIdResultHandler<>(resultType);
        sqlSession.select("user.findByIdSC", id, findByIdResultHandler);

        return findByIdResultHandler.result;
    }

    private class FindByIdResultHandler<R> implements ResultHandler<Map<String, Object>> {
        private R result;
        private Class<R> resultType;

        FindByIdResultHandler(Class<R> resultType) {
            this.resultType = resultType;
        }

        @Override
        public void handleResult(ResultContext<? extends Map<String, Object>> resultContext) {
            Map<String, Object> resultMap = resultContext.getResultObject();
            result = new ObjectMapper().convertValue(resultMap, resultType);
        }
    }
}
