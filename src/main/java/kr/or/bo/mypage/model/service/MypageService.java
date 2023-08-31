package kr.or.bo.mypage.model.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bo.board.vo.Board;
import kr.or.bo.board.vo.BoardComment;
import kr.or.bo.board.vo.BoardFile;
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

		int numPerPage = 5;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		List mySellBookList = mypageDao.selectMyBoardList(memberId, start, end);
		
		
		// 2. 페이지 네비게이션 제작
		// 총 페이지 수 계산을 위해서는 총 게시물 수를 알아야함 -> DB에서 그룹함수로 조회
		int totalCount = mypageDao.selectMyBoardListTotalCount(memberId);		
		// 총 페이지 수 계산
		// 총 게시물수 130
		// 한페이지당 게시물 수 10
		int totalPage = (int) Math.ceil(totalCount / (double) numPerPage); /// Math.ceil로 올림 이게 제일 맘에듬
		
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

		int numPerPage = 5;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		List mySellBookList = mypageDao.selectMySellBook(m.getMemberId(), start, end);

		
		List mySellBookImgList = mypageDao.selectMySellBookImgList(m.getMemberId(), start, end);
		for(int i =0 ;i<mySellBookList.size();i++) {
			Product p = (Product)mySellBookList.get(i);
			ProductFile pf = (ProductFile)mySellBookImgList.get(i);
			p.setProductFile(pf);
			//p.set

		}
		
		
		// 2. 페이지 네비게이션 제작
		// 총 페이지 수 계산을 위해서는 총 게시물 수를 알아야함 -> DB에서 그룹함수로 조회
		int totalCount = mypageDao.selectMySellBookTotalCount(m.getMemberId());
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

		int numPerPage = 5;
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
		int totalCount = mypageDao.selectMyProductBoardCommentTotalCount(m.getMemberId());		
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
			pageNavi += "<a class='page-item' href='/mypage/myProductBoardComment?reqPage=" + (pageNo - 1) + "'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		// 페이지 숫자 제작
		for (int i = 0; i < pageNaviSize; i++) {
			if (pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item active-page' href='/mypage/myProductBoardComment?reqPage=" + (pageNo) + "'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			} else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/mypage/myProductBoardComment?reqPage=" + (pageNo) + "'>";
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
			pageNavi += "<a class='page-item' href='/mypage/myProductBoardComment?reqPage=" + (pageNo) + "'>";/// pageNo-1에서 바꿈
			pageNavi += "<span class='material-icons'>chevron_right</span>"; /// left를 right로
			pageNavi += "</a>";
			pageNavi += "</li>";
		}

		pageNavi += "</ul>";		
		
		MypageListData mld = new MypageListData(selectMyProductBoardCommentList, pageNavi);

		
		return mld;
	}//selectMyProductBoardComment()종료

	//독서노트 댓글 조회
	public MypageListData selectMyComment(Member m, int reqPage) {
		// TODO Auto-generated method stub
		
		//여기서 페이지 나비 만들기
		// 1. 한페이지당 게시물 수 지정 -> 10개

		int numPerPage = 5;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;		
		List selectMyCommentList = mypageDao.selectMyComment(m.getMemberId(), start, end);
		//자유게시판 댓글 단 게시글 담아오고 댓글객체()에 board객체 추가
		for(int i=0 ; i<selectMyCommentList.size();i++) {
			BoardComment bc = (BoardComment) selectMyCommentList.get(i);
			List selectMyBoardList = mypageDao.selectMyBoardList(bc.getBoardRef());
			bc.setBoard((Board)selectMyBoardList.get(0));
			
		}
		//독서노트 이미지 가져오기 //댓글이 어느글의 출처인지 들고가서
		for(int i =0 ;i<selectMyCommentList.size();i++) {
			BoardComment bc = (BoardComment) selectMyCommentList.get(i);
			List selectBoardFile = mypageDao.selectBoardFile(bc.getBoardRef());

			if(selectBoardFile.isEmpty()) {//사진 없으면 
				bc.setBoardFile(null);
			}else{//사진 있을경우
				bc.setBoardFile((BoardFile)selectBoardFile.get(0));
			}
			
			//bc.setBoardFile((BoardFile) selectBoardFile.get(0));
		}
		
		// 2. 페이지 네비게이션 제작
		// 총 페이지 수 계산을 위해서는 총 게시물 수를 알아야함 -> DB에서 그룹함수로 조회
		int totalCount = mypageDao.selectMyBoardCommentTotalCount(m.getMemberId());		
		// 총 페이지 수 계산
		// 총 게시물수 130
		// 한페이지당 게시물 수 10
		int totalPage = (int) Math.ceil(totalCount / (double) numPerPage); /// Math.ceil로 올림 이게 제일 맘에듬
		
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
			pageNavi += "<a class='page-item' href='/mypage/myComment?reqPage=" + (pageNo - 1) + "'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		// 페이지 숫자 제작
		for (int i = 0; i < pageNaviSize; i++) {
			if (pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item active-page' href='/mypage/myComment?reqPage=" + (pageNo) + "'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			} else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/mypage/myComment?reqPage=" + (pageNo) + "'>";
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
			pageNavi += "<a class='page-item' href='/mypage/myComment?reqPage=" + (pageNo) + "'>";/// pageNo-1에서 바꿈
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
		//먼저 검색해와서 이미 거래목록에 있는지 조회
		
		
		//인서트 진행
		int result = mypageDao.tradeInsert(m,p);
		return result;
		
	}

	//고객정보보기 조회
	public MypageListData selectConsumer(Product p, Member m, int reqPage) {
		//여기서 페이지 나비 만들기
		// 1. 한페이지당 게시물 수 지정 -> 10개

		int numPerPage = 5;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;	
		List selectConsumerList = mypageDao.selectConsumer(p,m, start, end);

		

		//신뢰도 가져오기
		for(int i =0 ; i<selectConsumerList.size();i++) {
			TradeList tradeList= (TradeList)selectConsumerList.get(i);
			List list = mypageDao.selectOneMember(tradeList.getConsumer());
			if(!list.isEmpty()) {
				tradeList.setMember((Member)list.get(0));
			}
		}
		
		
		// 2. 페이지 네비게이션 제작
		// 총 페이지 수 계산을 위해서는 총 게시물 수를 알아야함 -> DB에서 그룹함수로 조회
		int totalCount = mypageDao.selectConsumerTotalCount(p.getProductBoardNo());		
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
			//pageNavi += "<a class='page-item' href='/mypage/showConsumer?reqPage=" + (pageNo - 1) + "'>";
			pageNavi += "<a class='page-item' "
					+ "href='/mypage/showConsumer?"
					+ "productBoardNo="	+ p.getProductBoardNo()
					+ "&productBoardTitle="	+ p.getProductBoardTitle()
					+ "&productBoardContent="+p.getProductBoardContent()
					+ "&productBoardWriter="+p.getProductBoardWriter()
					+ "&productAuthor="+p.getProductAuthor()
					+ "&productPrice="+p.getProductPrice()
					+ "&productRegDate="+p.getProductRegDate()
					+ "&productCondition="+p.getProductCondition()
					+ "&fileNo="+p.getProductFile().getFileNo()
					+ "&productNo="+p.getProductFile().getProductNo()
					+ "&filename="+p.getProductFile().getFilename()
					+ "&filepath="+p.getProductFile().getFilepath()
					+ "&reqPage=" 
					+ (pageNo - 1) + "'>";///페이지나비수정
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		// 페이지 숫자 제작
		for (int i = 0; i < pageNaviSize; i++) {
			if (pageNo == reqPage) {
				pageNavi += "<li>";
				//pageNavi += "<a class='page-item active-page' href='/mypage/showConsumer?reqPage=" + (pageNo) + "'>";
				pageNavi += "<a class='page-item active-page' "
						+ "href='/mypage/showConsumer?"
						+ "productBoardNo="	+ p.getProductBoardNo()
						+ "&productBoardTitle="	+ p.getProductBoardTitle()
						+ "&productBoardContent="+p.getProductBoardContent()
						+ "&productBoardWriter="+p.getProductBoardWriter()
						+ "&productAuthor="+p.getProductAuthor()
						+ "&productPrice="+p.getProductPrice()
						+ "&productRegDate="+p.getProductRegDate()
						+ "&productCondition="+p.getProductCondition()
						+ "&fileNo="+p.getProductFile().getFileNo()
						+ "&productNo="+p.getProductFile().getProductNo()
						+ "&filename="+p.getProductFile().getFilename()
						+ "&filepath="+p.getProductFile().getFilepath()
						+ "&reqPage=" 
						+ (pageNo) + "'>";///페이지나비수정
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			} else {
				pageNavi += "<li>";
				//pageNavi += "<a class='page-item' href='/mypage/showConsumer?reqPage=" + (pageNo) + "'>";
				pageNavi += "<a class='page-item' "
						+ "href='/mypage/showConsumer?"
						+ "productBoardNo="	+ p.getProductBoardNo()
						+ "&productBoardTitle="	+ p.getProductBoardTitle()
						+ "&productBoardContent="+p.getProductBoardContent()
						+ "&productBoardWriter="+p.getProductBoardWriter()
						+ "&productAuthor="+p.getProductAuthor()
						+ "&productPrice="+p.getProductPrice()
						+ "&productRegDate="+p.getProductRegDate()
						+ "&productCondition="+p.getProductCondition()
						+ "&fileNo="+p.getProductFile().getFileNo()
						+ "&productNo="+p.getProductFile().getProductNo()
						+ "&filename="+p.getProductFile().getFilename()
						+ "&filepath="+p.getProductFile().getFilepath()
						+ "&reqPage=" 
						
						+ (pageNo) + "'>";///페이지나비수정
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
			//pageNavi += "<a class='page-item' href='/mypage/showConsumer?reqPage=" + (pageNo) + "'>";/// pageNo-1에서 바꿈
			pageNavi += "<a class='page-item' "
					+ "href='/mypage/showConsumer?"
					+ "productBoardNo="	+ p.getProductBoardNo()
					+ "&productBoardTitle="	+ p.getProductBoardTitle()
					+ "&productBoardContent="+p.getProductBoardContent()
					+ "&productBoardWriter="+p.getProductBoardWriter()
					+ "&productAuthor="+p.getProductAuthor()
					+ "&productPrice="+p.getProductPrice()
					+ "&productRegDate="+p.getProductRegDate()
					+ "&productCondition="+p.getProductCondition()
					+ "&fileNo="+p.getProductFile().getFileNo()
					+ "&productNo="+p.getProductFile().getProductNo()
					+ "&filename="+p.getProductFile().getFilename()
					+ "&filepath="+p.getProductFile().getFilepath()
					+ "&reqPage="  
					
					+ (pageNo) + "'>";/// pageNo-1에서 바꿈 ///페이지나비수정
			pageNavi += "<span class='material-icons'>chevron_right</span>"; /// left를 right로
			pageNavi += "</a>";
			pageNavi += "</li>";
		}

		pageNavi += "</ul>";
		
		MypageListData mld = new MypageListData(selectConsumerList, pageNavi);
		
		return mld;

	}

	@Transactional
	public MypageListData selectByRequestList(String memberId, int reqPage) {
		
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		
//		List mypageList = mypageDao.selectByRequestList(memberId, start, end);
		List byRequestList = mypageDao.selectByRequestList(memberId, start, end);
		
		int totalCount = mypageDao.selectByRequestListTotalCount(memberId);
		
		int totalPage = (int)Math.ceil(totalCount / (double)numPerPage);
		
		int pageNaviSize = 5;
		
		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;
		
		String pageNavi = "<ul class='pagination circle-style'>";
		// 이전버튼 제작 < 1 2
		if (pageNo != 1) {// 페이지 번호가 1이 아닌경우만 1이면 그 전이 없으니까
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/mypage/byRequestList?reqPage=" + (pageNo - 1) + "'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		// 페이지 숫자 제작
		for (int i = 0; i < pageNaviSize; i++) {
			if (pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item active-page' href='/mypage/byRequestList?reqPage=" + (pageNo) + "'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			} else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/mypage/byRequestList?reqPage=" + (pageNo) + "'>";
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
			pageNavi += "<a class='page-item' href='/mypage/byRequestList?reqPage=" + (pageNo) + "'>";/// pageNo-1에서 바꿈
			pageNavi += "<span class='material-icons'>chevron_right</span>"; /// left를 right로
			pageNavi += "</a>";
			pageNavi += "</li>";
		}

		pageNavi += "</ul>";

		MypageListData mld = new MypageListData(byRequestList, pageNavi);
		
		return mld;
	}

	public List selectTradeList(Member m) {
		List tradeList = mypageDao.selectTradeList(m);
		return tradeList;
	}

	//판매완료
	@Transactional
	public int soldOut(TradeList tradeList) {
		// TODO Auto-generated method stub
		
		//product_board의 PRODUCT_SELL_CHECK =1로 바꾸기
		int result = mypageDao.soldOutFromProductBoard(tradeList);
		if(result>0) {//product_board의 PRODUCT_SELL_CHECK =1로 update 성공시
			//trade_list의  TRADE_COMPLETE_DONE =1로 바꾸기
			//TRADE_COMPLETE_DATE 넣어주기
			int result2 = mypageDao.soldOutFromTradeList(tradeList);
			return result2;//둘다 성공시 리턴
		}
		
		return 0;//어느하나라도 실패시 0리턴
	}
	
//	//tradeList 중복 insert를 막기위해 추가
//	public String selectBuyRequester(int productBoardNo) {
//		// TODO Auto-generated method stub
//		String buyRequester = mypageDao.selectBuyRequester(productBoardNo);
//		
//		return buyRequester;
//	}

	//구매요청 취소
	@Transactional
	public int tradeDelete(Member m, Product p) {
		// TODO Auto-generated method stub
		
		int result =mypageDao.tradeDelete(m,p);
		
		return result;
	}

	//나의 독서 노트 삭제
	@Transactional
	public int myBoardDelete(Board board) {
		// TODO Auto-generated method stub
		int result = mypageDao.myBoardDelete(board);
		
		return result;
	}

	//독서노트 댓글 삭제
	@Transactional
	public int myCommentDelete(BoardComment boardComment) {
		// TODO Auto-generated method stub
		int result = mypageDao.myCommentDelete(boardComment);
		
		return result;
	}

	//중고책방 댓글 삭제
	@Transactional
	public int myProductBoardCommentDelete(ProductComment productComment) {
		// TODO Auto-generated method stub
		int result =mypageDao.myProductBoardCommentDelete(productComment);
		return result;
	}

	@Transactional
	public int gradeUp(String writer) {
		int result = mypageDao.gradeUp(writer);
		return result;
	}

	public int gradeDown(String writer) {
		int reuslt = mypageDao.gradeDown(writer);
		return reuslt;
	}

	
}
