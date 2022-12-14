package com.board.domain.post;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.common.dto.MessageDto;

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
	public String updatePost(final PostRequest params) {
		postService.updatePost(params);
		return "redirect:/post/list.do";
	}

	@PostMapping("/post/delete.do")
	public String deletePost(@RequestParam final Long id) {
		postService.deletePost(id);
		return "redirect:/post/list.do";
	}

	@PostMapping("/post/save.do")
	public String savePost(final PostRequest params, Model model) {
		postService.savePost(params);
//		return "redirect:/post/list.do";
		MessageDto message = new MessageDto("게시글 생성이 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
		return showMessageAndRedirect(message, model);
	}

	@GetMapping("/post/list.do")
	public String openPostList(Model model) {
		List<PostResponse> posts = postService.findAllPost();
		model.addAttribute("posts", posts);
		return "post/list";
	}

	@GetMapping("/post/view.do")
	public String openPostView(@RequestParam final Long id, Model model) {
		PostResponse post = postService.findPostById(id);
		model.addAttribute("post", post);
		return "post/view";
	}

	private String showMessageAndRedirect(final MessageDto params, Model model) {
		model.addAttribute("params", params);
		return "common/messageRedirect";
	}

}
