package com.example.controller;

import java.util.List;

import com.example.entity.BoardEntity;
import com.example.repository.BoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

    @Autowired
    BoardRepository boardRepository;

    @Value("${board.page.count}")
    int PAGECNT;

    @GetMapping(value = "/insert")
    public String insertGet() {
        return "board/insert";
    }

    @PostMapping(value = "/insert")
    public String postGet(@ModelAttribute BoardEntity board) {

        // save(entity객체) == INSERT INTO
        boardRepository.save(board);
        return "redirect:/board/insert";

    }

    @GetMapping(value = "/selectlist")
    public String selectListGet(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "txt", defaultValue = "") String title) {
        // 페이지네이션 ( 시작페이지(0부터), 개수)

        PageRequest pageRequest = PageRequest.of(page - 1, PAGECNT);
        List<BoardEntity> list = boardRepository.findByTitleContainingOrderByNoDesc(title, pageRequest);
        model.addAttribute("list", list);

        long total = boardRepository.countByTitleContaining(title);
        model.addAttribute("pages", (total - 1) / PAGECNT + 1);
        return "board/selectlist";
    }

    @GetMapping(value = "/selectone")
    public String selectoneGet(
            @RequestParam(name = "no") long no,
            Model model) {
        // BoardEntity board = boardRepository.findDistinctByNo(no);
        BoardEntity board = boardRepository.findById(no).orElse(null);
        // optional일 때 null일 경우 뒤에 orElse를 붙여 한번에 처리 가능
        model.addAttribute("tmp", board);
        return "board/selectone";
    }

    @GetMapping(value = "/prev")
    public String prevGet(
            @RequestParam(name = "no") long no,
            Model model) {

        BoardEntity board = boardRepository.findTop1ByNoLessThanOrderByNoDesc(no);

        model.addAttribute("tmp", board);
        return "redirect:/board/selectone?no=" + board.getNo();
    }

    @GetMapping(value = "/next")
    public String nextGet(
            @RequestParam(name = "no") long no,
            Model model) {
        BoardEntity board = boardRepository.findTop1ByNoGreaterThanOrderByNoAsc(no);
        model.addAttribute("tmp", board);
        return "redirect:/board/selectone?no=" + board.getNo();
    }

}
