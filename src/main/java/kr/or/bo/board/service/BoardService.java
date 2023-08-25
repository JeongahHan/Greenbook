package kr.or.bo.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bo.board.dao.BoardDao;
import kr.or.bo.board.vo.Board;
import kr.or.bo.board.vo.BoardComment;
import kr.or.bo.board.vo.BoardFile;
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


/////////////////////////////////글 작성
	
	@Transactional  //커밋 롤백
	public int insertBoard(Board b, ArrayList<BoardFile> fileList) {
		//b는 무조건 존재 . fileList는 파일이 없으면 null. 있으면  Arraylist 객체
		
		int result = boardDao.insertBoard(b);
		
		if(fileList != null) {
			//notice_file 테이블에 insert할 notice_no 조회
			//-> 방금 if 문위에서 insert 된 notice의 notice_no를 조회
			int boardNo =  boardDao.getBoard();
			
			for(BoardFile file : fileList) {
				//바로 insert를 하게되면 file 객체 내부의 notice no 0
				//=> 0인 상태로 insert 하게되면 외래키 제약 조건 위배 
				file.setBoardNo(boardNo);
			result += boardDao.insertBoardFile(file);
			}
		}
		return result;
		
	}
/////////////////////////////////////////////////////
//게시글 삭제


	public List deleteBoard(int boardNo) {
		
		List list = boardDao.selectBoardFile(boardNo);
		int result = boardDao.deleteBoard(boardNo);
		
		if(result == 0) {
			return null;
		}
		return list;
	}

///////////////////////////////////////////////////////////
//게시글 수정
	
	//업데이트 하기 위해 조회 
	public Board getBoard(int boardNo) {
		Board b = boardDao.selectOneBoard(boardNo);
		List fileList = boardDao.selectBoardFile(boardNo);
		b.setFileList(fileList);
		return b;
	}

	@Transactional
	public List updateBoard(Board b, ArrayList<BoardFile> fileList, int[] delFileNo) {
		//1.board 테이블 update, 2.board_file 테이블에 insert, 3.board_file 에 delete, 4.board_file 에 select (=조회)
		//순서 : 1(업데이트) >>> 4(조회) >>> 3(삭제) >>>2(삽입)
		
		int result = boardDao.updateBoard(b);
		
		List delFileList = new ArrayList<BoardFile>();
		
		if(result>0) {
			
			//삭제파일이 있는 경우에만 진행(삭제파일 조회 -> 삭제파일 삭제)
			if(delFileNo !=null) {
				for(int fileNo : delFileNo) {
					
					//삭제할 파일 정보를 조회해서 리턴할 리스트에 추가
					BoardFile boardFile = boardDao.selectOneFile(fileNo);
					delFileList.add(boardFile);
					
					//db에서 파일 삭제
					result += boardDao.deleteFile(fileNo);
				}
			}
			//추가한파일이 있는 경우 파일 insert
			if(fileList != null) {
				for(BoardFile file : fileList) {
					result += boardDao.insertBoardFile(file);
				}
			}
		}
		
		int updateTotal = 1; //board 를 업데이트 해야하니까 기본 1개 확보
		updateTotal += delFileNo==null? 0:delFileNo.length; //삼항연산자 (배열이라 length)
		updateTotal += fileList==null? 0:fileList.size(); //새로들어온 파일 예상결과 추가 (ArrayList라서 size)
		
		if(result == updateTotal) {
			return delFileList;
		}else {
			return null;
		}
	}

//////////////////////////////////////////////////////////////////
////댓글 등록
	
	@Transactional
	public int insertComment(BoardComment bc) {
		int result = boardDao.insertComment(bc);
		return result;
	}


///////////////////////////////////////////////////////////////////
//댓글 대댓글 수정
	@Transactional
	public int updateComment(BoardComment bc) {
		int result = boardDao.updateComment(bc);
		return result; 
	}


	@Transactional
	public int deleteComment(int boardCommentNo) {
		int result =boardDao.deleteComment(boardCommentNo);
		return result;
	}



	public int insertCommentLike(int boardCommentNo, int memberNo) {
		int result = boardDao.insertCommentLike(boardCommentNo, memberNo);
		int likeCount = boardDao.likeCount(boardCommentNo);  //좋아요 수
		return likeCount;
	}



	public int removeCommentLike(int boardCommentNo, int memberNo) {
		int result = boardDao.removeCommentLike(boardCommentNo,memberNo);
		int likeCount = boardDao.likeCount(boardCommentNo);  //좋아요 수
		return likeCount;
	}



	public BoardListData mainSearchList(int reqPage, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	
	



}//서비스 종료