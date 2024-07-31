package com.nc13.react_board.service;

import com.nc13.react_board.model.BoardDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BoardService {
    private final SqlSession SESSION;
    private final String NAMESPACE = "mapper.BoardMapper";
    // -------------- pageNation -------------
    private final int PAGE_SIZE = 20;


    @Autowired
    public BoardService(SqlSession session){
        SESSION = session;
    }

    public BoardDTO selectOne(int id){
        return SESSION.selectOne(NAMESPACE + ".selectOne", id);
    }

    // -------------- pageNation -------------
    public List<BoardDTO> selectAll(int pageNo){
        HashMap<String, Integer> paramMap = new HashMap<>();
        paramMap.put("startRow", (pageNo -1) * PAGE_SIZE);
        paramMap.put("size", PAGE_SIZE);

        return SESSION.selectList(NAMESPACE + ".selectList", paramMap);
    }

    public int selectMaxPage(){
        int maxRow = SESSION.selectOne(NAMESPACE + ".selectMaxPage");
        int maxPage = maxRow / PAGE_SIZE;

        // 자바의 삼항 연산자는 반드시 할당이 필요함. 단독으로 사용 불가함.
        maxPage = (maxRow % PAGE_SIZE == 0) ? maxPage : maxPage + 1;

        return maxPage;
    }
    // --------------   글쓰기   -----------------

    public void insert(BoardDTO boardDTO){
        SESSION.insert(NAMESPACE + ".insert", boardDTO);
    }

    //---------------   수정하기  ------------------
    public void update(BoardDTO boardDTO){
        SESSION.update(NAMESPACE + ".update", boardDTO);
    }

    // --------------  삭제하기  -------------------
   /* public void delete(int id){
        SESSION.delete(NAMESPACE + ".delete", id);
    }*/
}
