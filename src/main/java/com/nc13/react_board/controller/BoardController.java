package com.nc13.react_board.controller;

import com.nc13.react_board.model.BoardDTO;
import com.nc13.react_board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping("/board/")
public class BoardController {
    private BoardService BOARD_SERVICE;

    @Autowired
    public BoardController(BoardService boardService) {
        BOARD_SERVICE = boardService;
    }

    @GetMapping("showOne/{id}")
    public BoardDTO selectOne(@PathVariable int id) {
        return BOARD_SERVICE.selectOne(id);
    }

    @GetMapping("showList/{pageNo}")
    public HashMap<String, Object> selectList(@PathVariable int pageNo) {
        HashMap<String, Object> resultMap = new HashMap<>();

        int maxPage = BOARD_SERVICE.selectMaxPage();

        int startPage = 1;
        int endPage = 1;

        if (maxPage < 5) {
            endPage = maxPage;
        } else if (pageNo <= 3) {
            endPage = 5;
        } else if (pageNo >= maxPage - 2) {
            startPage = maxPage - 4;
            endPage = maxPage;
        } else {
            startPage = pageNo - 2;
            endPage = pageNo + 2;
        }

        resultMap.put("currentPage", pageNo);
        resultMap.put("startPage", startPage);
        resultMap.put("endPage", endPage);
        resultMap.put("maxPage", maxPage);
        resultMap.put("boardList", BOARD_SERVICE.selectAll(pageNo));

        return resultMap;

    }
}
