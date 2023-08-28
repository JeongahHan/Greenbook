package kr.or.bo.mypage.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bo.board.vo.Board;
import kr.or.bo.board.vo.BoardComment;
import kr.or.bo.member.model.vo.Member;
import kr.or.bo.mypage.model.dao.MypageDao;
import kr.or.bo.mypage.model.vo.MypageListData;
import kr.or.bo.mypage.model.vo.TradeList;
import kr.or.bo.product.model.vo.Product;
import kr.or.bo.product.model.vo.ProductComment;
import kr.or.bo.product.model.vo.ProductFile;

@Service
public class MypageService {
	@Autowired
	private MypageDao mypageDao;

	//회원정보 수정
	@Transactional
	public int updateMember(Member member) {
		// TODO Auto-generated method stub
		int result = mypageDao.updateMember(member);
		
		return result;
	}//업데이트 멤버 종료

	//내가쓴 게시물 조회
	public MypageListData selectMyBoardList(String memberId, int reqPage) {
		// TODO Auto-generated method stub
		//여기서 페이지 나비 만들기
		// 1. 한페이지당 게시물 수 지정 -> 10개

		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		List mySellBookList = mypageDao.selectMyBoardList(memberId, start, end);
		
		
		// 2. 페이지 네비게이션 제작
		// 총 페이지 수 계산을 위해서는 총 게시물 수를 알아야함 -> DB에서 그룹함수로 조회
		int totalCount = mypageDao.selectMyBoardListTotalCount(memberId);		
		System.out.println("MypageService 총 내가 판매하는 도서 수 : " + totalCount);
		// 총 페이지 수 계산
		// 총 게시물수 130
		// 한페이지당 게시물 수 10
		int totalPage = (int) Math.ceil(totalCount / (double) numPerPage); /// Math.ceil로 올림 이게 제일 맘에듬
		System.out.println("MypageService 총 페이지 수 : " + totalPage);
		
		// 페이지 네비게이션 사이즈 지정 /// <<1 2 3 4 ....10>> 이런거
		int pageNaviSize = 5;
		// 페이지 네비게이션 시작번호
		// reqPage 1 ~ 5 : 1 2 3 4 5
		// reqPage 6 ~ 10 : 6 7 8 9 10
		// reqPage 11 ~ 15 : 11 12 13 14 15
		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;

		// 페이지 네비게이션 제작 시작
		String pageNavi = "<ul class='pagination circle-style'>";
		// 이전버튼 제작 < 1 2
		if (pageNo != 1) {// 페이지 번호가 1이 아닌경우만 1이면 그 전이 없으니까
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/mypage/myBoard?reqPage=" + (pageNo - 1) + "'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		// 페이지 숫자 제작
		for (int i = 0; i < pageNaviSize; i++) {
			if (pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item active-page' href='/mypage/myBoard?reqPage=" + (pageNo) + "'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			} else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/mypage/myBoard?reqPage=" + (pageNo) + "'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			pageNo++;
			if (pageNo > totalPage) {/// 총 페이지보다 크면
				break;
			}
		}
		// 다음버튼 제작 >> ...4 5 >>
		if (pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/mypage/myBoard?reqPage=" + (pageNo) + "'>";/// pageNo-1에서 바꿈
			pageNavi += "<span class='material-icons'>chevron_right</span>"; /// left를 right로
			pageNavi += "</a>";
			pageNavi += "</li>";
		}

		pageNavi += "</ul>";
		
		MypageListData mld = new MypageListData(mySellBookList,pageNavi);
		
		return mld;
	}//selectMyBoardList()종료
	
	//내가 판매중인 도서 조회해오기
	public MypageListData selectMySellBook(Member m, int reqPage) {
		// TODO Auto-generated method stub
		
		
		//여기서 페이지 나비 만들기
		// 1. 한페이지당 게시물 수 지정 -> 10개

		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		List mySellBookList = mypageDao.selectMySellBook(m.getMemberId(), start, end);

		
		List mySellBookImgList = mypageDao.selectMySellBookImgList(m.getMemberId(), start, end);
		for(int i =0 ;i<mySellBookList.size();i++) {
			Product p = (Product)mySellBookList.get(i);
			ProductFile pf = (ProductFile)mySellBookImgList.get(0);
			p.setProductFile(pf);
			//p.set

		}
		
		
		
		// 2. 페이지 네비게이션 제작
		// 총 페이지 수 계산을 위해서는 총 게시물 수를 알아야함 -> DB에서 그룹함수로 조회
		int totalCount = mypageDao.selectMySellBookTotalCount(m.getMemberId());		
		System.out.println("MypageService 총 내가 판매하는 도서 수 : " + totalCount);
		// 총 페이지 수 계산
		// 총 게시물수 130
		// 한페이지당 게시물 수 10
		int totalPage = (int) Math.ceil(totalCount / (double) numPerPage); /// Math.ceil로 올림 이게 제일 맘에듬
		System.out.println("MypageService 총 페이지 수 : " + totalPage);
		
		// 페이지 네비게이션 사이즈 지정 /// <<1 2 3 4 ....10>> 이런거
		int pageNaviSize = 5;
		// 페이지 네비게이션 시작번호
		// reqPage 1 ~ 5 : 1 2 3 4 5
		// reqPage 6 ~ 10 : 6 7 8 9 10
		// reqPage 11 ~ 15 : 11 12 13 14 15
		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;

		
		// 페이지 네비게이션 제작 시작
		String pageNavi = "<ul class='pagination circle-style'>";
		// 이전버튼 제작 < 1 2
		if (pageNo != 1) {// 페이지 번호가 1이 아닌경우만 1이면 그 전이 없으니까
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/mypage/mySellBook?reqPage=" + (pageNo - 1) + "'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		// 페이지 숫자 제작
		for (int i = 0; i < pageNaviSize; i++) {
			if (pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item active-page' href='/mypage/mySellBook?reqPage=" + (pageNo) + "'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			} else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/mypage/mySellBook?reqPage=" + (pageNo) + "'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			pageNo++;
			if (pageNo > totalPage) {/// 총 페이지보다 크면
				break;
			}
		}
		// 다음버튼 제작 >> ...4 5 >>
		if (pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/mypage/mySellBook?reqPage=" + (pageNo) + "'>";/// pageNo-1에서 바꿈
			pageNavi += "<span class='material-icons'>chevron_right</span>"; /// left를 right로
			pageNavi += "</a>";
			pageNavi += "</li>";
		}

		pageNavi += "</ul>";
		
		MypageListData mld = new MypageListData(mySellBookList,pageNavi);
		
		return mld;
	}//selectMySellBook() 종료

	//회원탈퇴
	@Transactional
	public int deleteMember(int memberNo) {
		// TODO Auto-generated method stub
		int result = mypageDao.deleteMember(memberNo);

		return result;
	}//회원탈퇴 종료

	//중고책방 댓글
	public MypageListData selectMyProductBoardComment(Member m, int reqPage) {
		// TODO Auto-generated method stub
		
		//여기서 페이지 나비 만들기
		// 1. 한페이지당 게시물 수 지정 -> 10개

		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;		
		List selectMyProductBoardCommentList = mypageDao.selectMyProductBoardComment(m.getMemberId(),start,end);
		//중고책방 댓글 단 게시글 담아오고 댓글객체(ProductComment)에 추가 //내가 댓글단 게시글만 조회해서
		for(int i =0 ; i<selectMyProductBoardCommentList.size();i++) {
			ProductComment pc = (ProductComment) selectMyProductBoardCommentList.get(i);
			List selectMyProductBoardList = mypageDao.selectMyProductBoardList(pc.getProductRef());			
			//selectMyProductBoardCommentList.add(i, selectMyProductBoardList); 이건 왜 무한루프가 돌지?
			pc.setProduct((Product)selectMyProductBoardList.get(0));
			

		}
		//중고책방 댓글 단 게시글의 이미지 파일패스 담아오기
		for(int i =0 ; i<selectMyProductBoardCommentList.size();i++) {
			ProductComment pc = (ProductComment) selectMyProductBoardCommentList.get(i);
			List selectProductFile = mypageDao.selectProductFile(pc.getProductRef());
			//ProductComment 객체에 파일객체 추가
			pc.setProductFile((ProductFile)selectProductFile.get(0));
			
		}

		
		// 2. 페이지 네비게이션 제작
		// 총 페이지 수 계산을 위해서는 총 게시물 수를 알아야함 -> DB에서 그룹함수로 조회
		int totalCount = mypageDao.selectMySellBookTotalCount(m.getMemberId());		
		System.out.println("MypageService 총 내가 판매하는 도서 수 : " + totalCount);
		// 총 페이지 수 계산
		// 총 게시물수 130
		// 한페이지당 게시물 수 10
		int totalPage = (int) Math.ceil(totalCount / (double) numPerPage); /// Math.ceil로 올림 이게 제일 맘에듬
		System.out.println("MypageService 총 페이지 수 : " + totalPage);
		
		// 페이지 네비게이션 사이즈 지정 /// <<1 2 3 4 ....10>> 이런거
		int pageNaviSize = 5;
		// 페이지 네비게이션 시작번호
		// reqPage 1 ~ 5 : 1 2 3 4 5
		// reqPage 6 ~ 10 : 6 7 8 9 10
		// reqPage 11 ~ 15 : 11 12 13 14 15
		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;

		// 페이지 네비게이션 제작 시작
		String pageNavi = "<ul class='pagination circle-style'>";
		// 이전버튼 제작 < 1 2
		if (pageNo != 1) {// 페이지 번호가 1이 아닌경우만 1이면 그 전이 없으니까
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/mypage/mySellBook?reqPage=" + (pageNo - 1) + "'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		// 페이지 숫자 제작
		for (int i = 0; i < pageNaviSize; i++) {
			if (pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item active-page' href='/mypage/mySellBook?reqPage=" + (pageNo) + "'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			} else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/mypage/mySellBook?reqPage=" + (pageNo) + "'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			pageNo++;
			if (pageNo > totalPage) {/// 총 페이지보다 크면
				break;
			}
		}
		// 다음버튼 제작 >> ...4 5 >>
		if (pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/mypage/mySellBook?reqPage=" + (pageNo) + "'>";/// pageNo-1에서 바꿈
			pageNavi += "<span class='material-icons'>chevron_right</span>"; /// left를 right로
			pageNavi += "</a>";
			pageNavi += "</li>";
		}

		pageNavi += "</ul>";		
		
		MypageListData mld = new MypageListData(selectMyProductBoardCommentList, pageNavi);

		
		return mld;
	}//selectMyProductBoardComment()종료

	//내가 작성한 자유게시판 댓글 조회
	public MypageListData selectMyComment(Member m, int reqPage) {
		// TODO Auto-generated method stub
		
		//여기서 페이지 나비 만들기
		// 1. 한페이지당 게시물 수 지정 -> 10개

		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;		
		List selectMyCommentList = mypageDao.selectMyComment(m.getMemberId(), start, end);
		//자유게시판 댓글 단 게시글 담아오고 댓글객체()에 board객체 추가
		for(int i=0 ; i<selectMyCommentList.size();i++) {
			BoardComment bc = (BoardComment) selectMyCommentList.get(i);
			List selectMyBoardList = mypageDao.selectMyBoardList(bc.getBoardRef());
			bc.setBoard((Board)selectMyBoardList.get(0));
			
		}
		
		
		// 2. 페이지 네비게이션 제작
		// 총 페이지 수 계산을 위해서는 총 게시물 수를 알아야함 -> DB에서 그룹함수로 조회
		int totalCount = mypageDao.selectMySellBookTotalCount(m.getMemberId());		
		System.out.println("MypageService 총 내가 판매하는 도서 수 : " + totalCount);
		// 총 페이지 수 계산
		// 총 게시물수 130
		// 한페이지당 게시물 수 10
		int totalPage = (int) Math.ceil(totalCount / (double) numPerPage); /// Math.ceil로 올림 이게 제일 맘에듬
		System.out.println("MypageService 총 페이지 수 : " + totalPage);
		
		// 페이지 네비게이션 사이즈 지정 /// <<1 2 3 4 ....10>> 이런거
		int pageNaviSize = 5;
		// 페이지 네비게이션 시작번호
		// reqPage 1 ~ 5 : 1 2 3 4 5
		// reqPage 6 ~ 10 : 6 7 8 9 10
		// reqPage 11 ~ 15 : 11 12 13 14 15
		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;

		// 페이지 네비게이션 제작 시작
		String pageNavi = "<ul class='pagination circle-style'>";
		// 이전버튼 제작 < 1 2
		if (pageNo != 1) {// 페이지 번호가 1이 아닌경우만 1이면 그 전이 없으니까
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/mypage/mySellBook?reqPage=" + (pageNo - 1) + "'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		// 페이지 숫자 제작
		for (int i = 0; i < pageNaviSize; i++) {
			if (pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item active-page' href='/mypage/mySellBook?reqPage=" + (pageNo) + "'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			} else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/mypage/mySellBook?reqPage=" + (pageNo) + "'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			pageNo++;
			if (pageNo > totalPage) {/// 총 페이지보다 크면
				break;
			}
		}
		// 다음버튼 제작 >> ...4 5 >>
		if (pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/mypage/mySellBook?reqPage=" + (pageNo) + "'>";/// pageNo-1에서 바꿈
			pageNavi += "<span class='material-icons'>chevron_right</span>"; /// left를 right로
			pageNavi += "</a>";
			pageNavi += "</li>";
		}

		pageNavi += "</ul>";
		
		MypageListData mld = new MypageListData(selectMyCommentList, pageNavi);
		
		
		return mld;
	}//selectMyComment()종료

	//거래목록 인서트
	@Transactional
	public int tradeInsert(Member m, Product p) {
		// TODO Auto-generated method stub
		int result = mypageDao.tradeInsert(m,p);
		
		return result;
	}

	//고객정보보기 조회
	public MypageListData selectConsumer(Product p, Member m, int reqPage) {
		//여기서 페이지 나비 만들기
		// 1. 한페이지당 게시물 수 지정 -> 10개

		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;	
		List selectConsumerList = mypageDao.selectConsumer(p,m, start, end);


		//신뢰도 가져오기
		for(int i =0 ; i<selectConsumerList.size();i++) {
			TradeList tradeList= (TradeList)selectConsumerList.get(i);
			List list = mypageDao.selectOneMember(tradeList.getConsumer());
			tradeList.setMember((Member)list.get(0)); 

		}
		

		
		
		// 2. 페이지 네비게이션 제작
		// 총 페이지 수 계산을 위해서는 총 게시물 수를 알아야함 -> DB에서 그룹함수로 조회
		int totalCount = mypageDao.selectMySellBookTotalCount(m.getMemberId());		
		System.out.println("MypageService 총 내가 판매하는 도서 수 : " + totalCount);
		// 총 페이지 수 계산
		// 총 게시물수 130
		// 한페이지당 게시물 수 10
		int totalPage = (int) Math.ceil(totalCount / (double) numPerPage); /// Math.ceil로 올림 이게 제일 맘에듬
		System.out.println("MypageService 총 페이지 수 : " + totalPage);
		
		// 페이지 네비게이션 사이즈 지정 /// <<1 2 3 4 ....10>> 이런거
		int pageNaviSize = 5;
		// 페이지 네비게이션 시작번호
		// reqPage 1 ~ 5 : 1 2 3 4 5
		// reqPage 6 ~ 10 : 6 7 8 9 10
		// reqPage 11 ~ 15 : 11 12 13 14 15
		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;

		// 페이지 네비게이션 제작 시작
		String pageNavi = "<ul class='pagination circle-style'>";
		// 이전버튼 제작 < 1 2
		if (pageNo != 1) {// 페이지 번호가 1이 아닌경우만 1이면 그 전이 없으니까
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/mypage/mySellBook?reqPage=" + (pageNo - 1) + "'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		// 페이지 숫자 제작
		for (int i = 0; i < pageNaviSize; i++) {
			if (pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item active-page' href='/mypage/mySellBook?reqPage=" + (pageNo) + "'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			} else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/mypage/mySellBook?reqPage=" + (pageNo) + "'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			pageNo++;
			if (pageNo > totalPage) {/// 총 페이지보다 크면
				break;
			}
		}
		// 다음버튼 제작 >> ...4 5 >>
		if (pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/mypage/mySellBook?reqPage=" + (pageNo) + "'>";/// pageNo-1에서 바꿈
			pageNavi += "<span class='material-icons'>chevron_right</span>"; /// left를 right로
			pageNavi += "</a>";
			pageNavi += "</li>";
		}

		pageNavi += "</ul>";
		
		MypageListData mld = new MypageListData(selectConsumerList, pageNavi);
		
		return mld;

	}

	
	
}
