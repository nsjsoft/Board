package com.board.paging;

import com.board.common.dto.SearchDto;

import lombok.Getter;

@Getter
public class Pagination {

	private int totalRecordCount; //전체 데이터수
	private int totalPageCount;   //전체 페이지수
	private int startPage;        //첫 페이지 번호
	private int endPage;          //끝 페이지 번호
	private int limitStart;       //LIMIT 시작 위치
	private boolean existPrevPage; //이전 페이지 존재 여부
	private boolean existNextPage; //다음 페이지 존재 여부
	
	public Pagination(int totalRecordCount, SearchDto params) {
		if(totalRecordCount > 0) {
			this.totalRecordCount = totalRecordCount;
			this.calculation(params);
		}
	}
	
	private void calculation(SearchDto params) {
		totalPageCount =((totalRecordCount - 1) / params.getRecordSize()) + 1;
		
		if(params.getPage() > totalPageCount) {
			params.setPage(totalPageCount);
		}
		
		startPage = ((params.getPage() - 1) / params.getPageSize()) * params.getPageSize() + 1;
		
		endPage = startPage + params.getPageSize() - 1;
		
		if(endPage > totalPageCount) {
			endPage = totalPageCount;
		}
		
		limitStart = (params.getPage() - 1) * params.getRecordSize();
		
		existPrevPage = startPage != 1;
		
		existNextPage = (endPage * params.getRecordSize()) < totalRecordCount;
	}
}
