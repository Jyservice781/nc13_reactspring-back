package com.nc13.react_board.service;

import com.nc13.react_board.model.BoardDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final SqlSession SESSION;
    private final String NAMESPACE = "mapper.BoardMapper";

    @Autowired
    public BoardService(SqlSession session){
        SESSION = session;
    }

    public BoardDTO selectOne(int id){
        return SESSION.selectOne(NAMESPACE + ".selectOne", id);
    }
}
