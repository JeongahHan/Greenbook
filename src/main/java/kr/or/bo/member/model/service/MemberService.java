package kr.or.bo.member.model.service;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bo.member.model.dao.MemberDao;
import kr.or.bo.member.model.vo.Member;
import kr.or.bo.member.model.vo.adminListData;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	public Member selectOneMember(String memberId, String memberPw) {
		Member m = memberDao.selectOneMember(memberId, memberPw);
		return m;
	}
	/*
	public List selectAllMember() {
		List list = memberDao.selectAllMember();
		return list;
	}*/
	
	@Transactional
	public int changeLevel(int memberNo, int memberLevel) {
		return memberDao.changeLevel(memberNo, memberLevel);
	}

	@Transactional
	public boolean checkedChangeLevel(String no, String level) {
		StringTokenizer sT1 = new StringTokenizer(no, "/");
		StringTokenizer sT2 = new StringTokenizer(level, "/");
		boolean result = true;
		while(sT1.hasMoreTokens()) {
			int memberNo = Integer.parseInt(sT1.nextToken());
			int memberLevel = Integer.parseInt(sT2.nextToken());
			int changeResult = memberDao.changeLevel(memberNo, memberLevel);	
			if(changeResult == 0) {	
				result = false;
				break;	
			}
		}
		return result;
	}
	public Member selectOneMember(String checkId) {
		Member m = memberDao.selectOneMember(checkId);
		return m;
	}


	public Member selectMemberId(String memberName, String memberEmail) {
		Member m = memberDao.selectMemberId(memberName, memberEmail);
		return m;
	}

	public Member selectMemberPw(String memberId, String memberEmail) {
		Member m = memberDao.selectMemberPw(memberId, memberEmail);
		return m;
	}
	
	@Transactional
	public int updatePw(int memberNo, String memberPw) {
		int result = memberDao.updatePw(memberNo, memberPw);
		return result;
	}

	public Member checkId(String memberId) {
		Member m = memberDao.checkId(memberId);
		return m;
	}
	
	@Transactional
	public int insertMember(Member member) {
		int result = memberDao.insertMember(member);
		return result;
	}

	public Member checkEmail(String memberEmail) {
		Member m = memberDao.checkEmail(memberEmail);
		return m;
	}

	public adminListData selectAdminList(int reqPage) {
				int numPerPage = 10;
				int end = reqPage * numPerPage;
				int start = end-numPerPage +1;
				List adminList = memberDao.selectAdminList(start,end);
				int totalCount = memberDao.selectAdminTotalCount();
				int totalPage = (int)Math.ceil(totalCount/(double)numPerPage);
				
				int pageNaviSize = 5;
				
				int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
				
				String pageNavi = "<ul class='pagination square-style'>";
				
				if(pageNo != 1) {
					pageNavi += "<li>";
					pageNavi += "<a class='page-item' href='/member/admin?reqPage="+(pageNo-1)+"'>";
					pageNavi += "<span class='material-icons'>chevron_left</span>";
					pageNavi += "</a>";
					pageNavi += "</li>";
				}
					
				for(int i=0 ; i<pageNaviSize ; i++) {
					if(pageNo == reqPage) {
						pageNavi += "<li>";
						pageNavi += "<a class='page-item active-page' href='/member/admin?reqPage="+(pageNo)+"'>";
						pageNavi += pageNo;
						pageNavi += "</a>";
						pageNavi += "</li>";
					} else {
						pageNavi += "<li>";
						pageNavi += "<a class='page-item' href='/member/admin?reqPage="+(pageNo)+"'>";
						pageNavi += pageNo;
						pageNavi += "</a>";
						pageNavi += "</li>";
					}
					pageNo++;
					if(pageNo>totalPage) {
						break;	
					}
				}
					
				if(pageNo <= totalPage) {
					pageNavi += "<li>";
					pageNavi += "<a class='page-item' href='/member/admin?reqPage="+(pageNo)+"'>";
					pageNavi += "<span class='material-icons'>chevron_right</span>";
					pageNavi += "</a>";
					pageNavi += "</li>";
				}
				pageNavi += "</ul>";
				
				adminListData nld = new adminListData(adminList,pageNavi,reqPage); 
				return nld;
			}

	public adminListData selectLevelList(int reqPage, String memberlevel) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end-numPerPage +1;
		List adminList = memberDao.selectLevelList(start,end, memberlevel);
		int totalCount = memberDao.selectAdminTotalCount();
		int totalPage = (int)Math.ceil(totalCount/(double)numPerPage);
		
		int pageNaviSize = 5;
		
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		
		String pageNavi = "<ul class='pagination square-style'>";
		
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/member/levelSearchList?reqPage="+(pageNo-1)+"&memberlevel="+(memberlevel)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
			
		for(int i=0 ; i<pageNaviSize ; i++) {
			if(pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item active-page' href='/member/levelSearchList?reqPage="+(pageNo)+"&memberlevel="+(memberlevel)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			} else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/member/levelSearchList?reqPage="+(pageNo)+"&memberlevel="+(memberlevel)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			pageNo++;
			if(pageNo>totalPage) {
				break;	
			}
		}
			
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/member/levelSearchList?reqPage="+(pageNo)+"&memberlevel="+(memberlevel)+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		pageNavi += "</ul>";
		
		adminListData nld = new adminListData(adminList,pageNavi,reqPage); 
		return nld;
	}


}
