package com.board.domain.post;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.board.common.dto.SearchDto;

@Mapper
public interface PostMapper {
	
	void save(PostRequest params);
	
	PostResponse findById(Long id);
	
	void update(PostRequest params);
	
	void deleteById(Long id);
	
	List<PostResponse> findAll(SearchDto params);
	
	int count(SearchDto params);
	
}
