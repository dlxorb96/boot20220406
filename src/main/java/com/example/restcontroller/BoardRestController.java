package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.dto.BoardDTO;
import com.example.entity.BoardEntity;
import com.example.mapper.BoardMapper;
import com.example.repository.BoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
public class BoardRestController {

    @Autowired
    BoardMapper bMapper;

    @Value("${board.page.count}")
    int PAGECNT;

    @Autowired
    BoardRepository boardRepository;

    @RequestMapping(value = "/updatehit1", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateHit2Board(@RequestParam(name = "no") long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            BoardEntity board = boardRepository.findById(no).orElse(null);
            board.setHit(board.getHit() + 1L);
            boardRepository.save(board);
            map.put("status", 200);

        } catch (Exception e) {
            map.put("status", 0);
        }
        return map;
    }

    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertBoard(@RequestBody BoardDTO board) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        int ret = bMapper.insertBoardOne(board);
        if (ret == 1) {
            map.put("status", 200);
        }
        return map;
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> deleteBoard(@RequestParam long bno) {

        System.out.println("test------------------" + bno);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        int ret = bMapper.deleteBoardOne(bno);
        if (ret == 1) {
            map.put("status", 200);
        }
        return map;
    }

    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateBoard(@RequestBody BoardDTO board) {
        System.out.println(board);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        int ret = bMapper.updateBoardOne(board);
        if (ret == 1) {
            map.put("status", 200);
        }
        return map;
    }

    @RequestMapping(value = "/selectOne", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOne(@RequestParam long bno) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        BoardDTO retboard = bMapper.selectBoardOne(bno);
        if (retboard != null) {
            map.put("status", 200);
            map.put("result", retboard);
        }
        return map;
    }

    @RequestMapping(value = "/next", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> next(@RequestParam long bno) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        BoardEntity board = boardRepository.findTop1ByNoGreaterThanOrderByNoAsc(bno);
        if (board != null) {
            map.put("status", 200);
            map.put("result", board);
        }
        return map;
    }

    @RequestMapping(value = "/selectList", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectList(@RequestParam(name = "page") int page) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        List<BoardDTO> retboard = bMapper.selectBoardList((page * PAGECNT) - (PAGECNT - 1), page * PAGECNT);
        System.out.println(retboard);

        if (!retboard.isEmpty()) {
            map.put("status", 200);
            map.put("result", retboard);
        }
        return map;
    }

    @RequestMapping(value = "/updateHit", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updateHit(@RequestParam long bno) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        int ret = bMapper.updateHit(bno);
        if (ret == 1) {
            map.put("status", 200);
        }
        return map;
    }
}
