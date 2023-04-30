package kr.co.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.service.BoardService;
import kr.co.vo.BoardVO;
import kr.co.vo.Criteria;
import kr.co.vo.PageMaker;
import kr.co.vo.SearchCriteria;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	BoardService service;
	
	// 게시판 글 작성 화면
	@RequestMapping(value="/writeView", method=RequestMethod.GET)
	public void writeView() throws Exception{
		logger.info("글쓰기 페이지 진입완료!");
	}
	
	// 게시판 글 작성
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(BoardVO boardVO) throws Exception{
		logger.info("글쓰기 시도");
		
		service.write(boardVO);
		
		logger.info("글쓰기 성공");
		
		return "redirect:/board/list";
	}
	
	// 게시판 목록 조회
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model, @ModelAttribute("scri")SearchCriteria scri , Criteria cri) throws Exception{
		
		logger.info("게시판 리스트 출력");
		
		model.addAttribute("list", service.list(scri));
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(service.listCount(scri));
		
		model.addAttribute("pageMaker", pageMaker);
		
		return "board/list";
	}
	
	// 게시글 읽기
	@RequestMapping(value="/readView", method=RequestMethod.GET)
	public String read(BoardVO boardVO, Model model) throws Exception{
		
		logger.info("게시판 글 읽기!");
		
		model.addAttribute("read", service.read(boardVO.getBno()));
		
		return "board/readView";
	}
	
	// 게시판 수정 뷰
	@RequestMapping(value="/updateView", method = RequestMethod.GET)
	public String updateView(BoardVO boardVO, Model model) throws Exception{
		logger.info("게시글 수정 페이지 진입");
		
		model.addAttribute("update", service.read(boardVO.getBno()));
		
		logger.info("게시글 수정 페이지 진입 성공!");
		
		return "board/updateView";
	}
	
	// 게시판 수정
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(BoardVO boardVO) throws Exception{
		logger.info("게시글 수정 시도!");
		
		service.update(boardVO);
		
		logger.info("게시글 수정에 성공했습니다.");
		
		return "redirect:/board/list";
	}
	
	// 게시판 삭제
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(BoardVO boardVO) throws Exception{
		logger.info("글 삭제 시도");
		
		service.delete(boardVO.getBno());
		
		logger.info("글 삭제 성공!");
		
		return "redirect:/board/list";
	}

}
