package com.nc13.react_board.controller;

import com.nc13.react_board.model.BoardDTO;
import com.nc13.react_board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/board/")
public class BoardController {
    private BoardService BOARD_SERVICE;

    @Autowired
    public BoardController (BoardService boardService){
        BOARD_SERVICE = boardService;
    }

    @GetMapping("showOne/{id}")
    public BoardDTO selectOne(@PathVariable int id) {
        return BOARD_SERVICE.selectOne(id);
    }

}
