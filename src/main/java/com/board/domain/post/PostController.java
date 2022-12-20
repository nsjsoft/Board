package com.board.domain.post;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.common.dto.MessageDto;
import com.board.common.dto.SearchDto;
import com.board.paging.PagingResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping("/post/write.do")
	public String openPostWrite(@RequestParam(value="id", required=false) final Long id, Model model) {

		if(id != null) {
			PostResponse post = postService.findPostById(id);
			model.addAttribute("post", post);
		}

		return "post/write";
	}

	@PostMapping("/post/update.do")
	public String updatePost(final PostRequest params, Model model) {
		postService.updatePost(params);
		MessageDto message = new MessageDto("게시글 수정이 완료되었습니다", "/post/list.do", RequestMethod.GET, null);
		//return "redirect:/post/list.do";
		return showMessageAndRedirect(message, model);
	}

	@PostMapping("/post/delete.do")
	public String deletePost(@RequestParam final Long id, Model model) {
		postService.deletePost(id);
		MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다", "/post/list.do", RequestMethod.GET, null);
		//return "redirect:/post/list.do";
		return showMessageAndRedirect(message, model);
	}

	@PostMapping("/post/save.do")
	public String savePost(final PostRequest params, Model model) {
		//System.out.println(params);
		postService.savePost(params);
//		return "redirect:/post/list.do";
		MessageDto message = new MessageDto("게시글 생성이 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
		//String temp = showMessageAndRedirect(message, model);
		//System.out.println(temp);		
		return showMessageAndRedirect(message, model);
		//return temp;
	}

	@GetMapping("/post/list.do")
	public String openPostList(@ModelAttribute("params") final SearchDto params, Model model) {
		PagingResponse<PostResponse> response = postService.findAllPost(params);
		model.addAttribute("response", response);
		return "post/list";
	}

	@GetMapping("/post/view.do")
	public String openPostView(@RequestParam final Long id, Model model) {
		PostResponse post = postService.findPostById(id);
		model.addAttribute("post", post);
		return "post/view";
	}

	private String showMessageAndRedirect(final MessageDto params, Model model) {
	    //System.out.println(params);
		model.addAttribute("params", params);
		return "common/messageRedirect";
	}

}
