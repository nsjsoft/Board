package com.board.domain.post;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.board.common.dto.SearchDto;
import com.board.paging.Pagination;
import com.board.paging.PagingResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostMapper postMapper;

	@Transactional
	public Long savePost(final PostRequest params) {
		postMapper.save(params);
		return params.getId();
	}

	public PostResponse findPostById(final Long id) {
		return postMapper.findById(id);
	}

	@Transactional
	public Long updatePost(final PostRequest params) {
		postMapper.update(params);
		return params.getId();
	}

	@Transactional
	public Long deletePost(final Long id) {
		postMapper.deleteById(id);
		return id;
	}

	public PagingResponse<PostResponse> findAllPost(final SearchDto params) {
		int count = postMapper.count(params);
		if(count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		List<PostResponse> list = postMapper.findAll(params);
		
		return new PagingResponse<>(list, pagination);
	}
}
