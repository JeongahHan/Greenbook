package kr.or.bo.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bo.board.dao.BoardDao;
import kr.or.bo.board.vo.Board;
import kr.or.bo.board.vo.BoardListData;
import kr.or.bo.board.vo.BoardViewData;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	/*
	public List selectBoardList(int reqPage) {
		List boardList = boardDao.selectBoardList();
		return boardList;
	}
	*/

	public BoardListData selectBoardList(int reqPage) {
		//1. 한 페이지 당 게시물 수 지정 ->> 10개
		int numPerPage = 10;
		//reqPage가 1이면 -> 1~10
		//reqPage가 2이면 -> 11~20
		//reqPage가 3이면 -> 21~30

		int end = reqPage * numPerPage;
		int start = (end-numPerPage)+1;
		
		List boardList = boardDao.selectBoardList(start,end); //boardList 조회
		
		
		//2. 페이지 네비게이션 제작
		//총 페이지 수 계산을 위해서는 "총 게시물 수"를 알아야함  ->>  DB에서 그룹함수로 조회
		int totalCount = boardDao.selectBoardTotalCount();
		//총 페이지 수 계산
		//총 게시물 수 100
		//한 페이지당 게시물 수 : 10
		
		int totalPage; //총 페이지 선언
		
		if(totalCount%numPerPage == 0) { //나머지가 없을때
			totalPage = totalCount/numPerPage;
		}else { //나머지가 있을때
			totalPage = (totalCount/numPerPage)+1;
		}
	
		//2-1.페이지 네비게이션 사이즈 지정
		//  1 2 3 4 5 >(다음)  제작
		// <6 7 8 9 10 >
		
		//길이지정
		int pageNaviSize = 5;
		
		
		//페이지 네비게이션 시작 번호
		//reqPage 1 ~ 5 : 1 2 3 4 5
		//reqPage 6 ~ 10 : 6 7 8 9 10
		//reqPage 11 ~ 15 : 11 12 13 14 15
						
		//페이지 네비게이션 - 페이지
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		
		
		//2-2 페이지 네비게이션 제작 시작
		// <<< 이전버튼 만들기
		String pageNavi = "<ul class='pagination square-style'>";
		if(pageNo != 1) { //페이지리스트가 1 2 3 4 5 가 아닐때  >> 이전버튼 생김
		pageNavi += "<li>";
		pageNavi += "<a class='page-item' href='/board/list?reqPage="+(pageNo-1)+"'>";
		pageNavi += "<span class='material-icons'>chevron_left</span>";
		pageNavi += "</a>";
		pageNavi += "</li>";
		}
		
		//페이지 "숫자" 만들기
		for(int i=0; i<pageNaviSize; i++) {
			if(pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item active-page' href='/board/list?reqPage="+(pageNo)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/board/list?reqPage="+(pageNo)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
		
			pageNo++;
		
			if(pageNo>totalPage) {  //true 조건. 페이지 안늘어나게 멈추고 반복문 나가기. 
				break;
			}
		} //for문 종료
		
		// >>> 다음 버튼 만들기
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/board/list?reqPage="+(pageNo)+"'>";  //이미 pageNo++; 상태로 반복문을 나왔기 때문에 pageNo+1 이 아님. 
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		
		pageNavi += "</ul>";
		
		BoardListData bld =  new BoardListData(boardList, pageNavi);
		
		return bld;
	}

	

	////////////////////////////////////////////////////////////////////////////
	
	//제목 : 내용 찾기
	public BoardListData getSearchList(int reqPage, String type, String keyword) {
				//1. 한 페이지 당 게시물 수 지정 ->> 10개
				int numPerPage = 10;
				//reqPage가 1이면 -> 1~10
				//reqPage가 2이면 -> 11~20
				//reqPage가 3이면 -> 21~30
				
				int end = reqPage * numPerPage;
				int start = (end-numPerPage)+1;
				
				List boardList = boardDao.getSearchList(start,end,type,keyword); //boardList 조회
				
				
				//2. 페이지 네비게이션 제작
				//총 페이지 수 계산을 위해서는 "총 게시물 수"를 알아야함  ->>  DB에서 그룹함수로 조회
				int totalCount = boardDao.getSearchListTotalCount(type,keyword);
				//총 페이지 수 계산
				//총 게시물 수 100
				//한 페이지당 게시물 수 : 10
				
				int totalPage; //총 페이지 선언
				
				if(totalCount%numPerPage == 0) { //나머지가 없을때
					totalPage = totalCount/numPerPage;
				}else { //나머지가 있을때
					totalPage = (totalCount/numPerPage)+1;
				}
			
				//2-1.페이지 네비게이션 사이즈 지정
				//  1 2 3 4 5 >(다음)  제작
				// <6 7 8 9 10 >
				
				//길이지정
				int pageNaviSize = 5;
				
				
				//페이지 네비게이션 시작 번호
				//reqPage 1 ~ 5 : 1 2 3 4 5
				//reqPage 6 ~ 10 : 6 7 8 9 10
				//reqPage 11 ~ 15 : 11 12 13 14 15
								
				//페이지 네비게이션 - 페이지
				int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
				
				
				//2-2 페이지 네비게이션 제작 시작
				// <<< 이전버튼 만들기
				String pageNavi = "<ul class='pagination square-style'>";
				if(pageNo != 1) { //페이지리스트가 1 2 3 4 5 가 아닐때  >> 이전버튼 생김
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/board/getSearchList?reqPage="+(pageNo-1)+"&type="+(type)+"&keyword="+(keyword)+"'>";
				pageNavi += "<span class='material-icons'>chevron_left</span>";
				pageNavi += "</a>";
				pageNavi += "</li>";
				}
				
				//페이지 "숫자" 만들기
				for(int i=0; i<pageNaviSize; i++) {
					if(pageNo == reqPage) {
						pageNavi += "<li>";
						pageNavi += "<a class='page-item active-page' href='/board/getSearchList?reqPage="+(pageNo)+"&type="+(type)+"&keyword="+(keyword)+"'>";
						pageNavi += pageNo;
						pageNavi += "</a>";
						pageNavi += "</li>";
					}else {
						pageNavi += "<li>";
						pageNavi += "<a class='page-item' href='/board/getSearchList?reqPage="+(pageNo)+"&type="+(type)+"&keyword="+(keyword)+"'>";
						pageNavi += pageNo;
						pageNavi += "</a>";
						pageNavi += "</li>";
					}
				
					pageNo++;
				
					if(pageNo>totalPage) {  //true 조건. 페이지 안늘어나게 멈추고 반복문 나가기. 
						break;
					}
				} //for문 종료
				
				// >>> 다음 버튼 만들기
				if(pageNo <= totalPage) {
					pageNavi += "<li>";
					pageNavi += "<a class='page-item' href='/board/getSearchList?reqPage="+(pageNo)+"&type="+(type)+"&keyword="+(keyword)+"'>";  //이미 pageNo++; 상태로 반복문을 나왔기 때문에 pageNo+1 이 아님. 
					pageNavi += "<span class='material-icons'>chevron_right</span>";
					pageNavi += "</a>";
					pageNavi += "</li>";
				}
				
				pageNavi += "</ul>";
				
				BoardListData bld =  new BoardListData(boardList, pageNavi);
				
				return bld;
			}


	/////////////////////////////////////////////////////////////
	//자유게시판 글 상세보기
	
	@Transactional
	public BoardViewData selectOneBoard(int boardNo, int memberNo) {
		int result = boardDao.updateReadCount(boardNo); //조회수 업데이트
		
		if(result > 0) {
			Board b = boardDao.selectOneBoard(boardNo); // 게시판 번호로 게시글 (한 개) 찾기
					
			List fileList = boardDao.selectBoardFile(boardNo); //첨부파일도 보일수 있도록 추가
			b.setFileList(fileList);
			
			//댓글 조회(일반댓글 -> 대댓글을 제외한 댓글)
			List commentList = boardDao.selectCommentList(boardNo,memberNo); //해당 게시물에 대한 댓글을 조회하기 위해 boardNo 넘김
			//대댓글만 조회
			List reCommentList = boardDao.selectReCommentList(boardNo,memberNo);
			
			BoardViewData bvd = new BoardViewData(b, commentList , reCommentList);
			
			return bvd;
		
		}else {
			return null;
		}
		
		
	}


	




}//서비스 종료