package kr.or.bo.msg.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bo.msg.model.dao.MsgDao;
import kr.or.bo.msg.model.vo.Msg;
import kr.or.bo.msg.model.vo.MsgListData;

@Service
public class MsgService {
	@Autowired
	private MsgDao msgDao;

	public MsgListData selectReceiveList(String memberId, int reqPage) {
		int numPerPage = 5;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		List msgList = msgDao.selectReceiveList(start, end, memberId);
		
		int totalCount = msgDao.selectReceiveMsgTotalCount(memberId);
		int totalPage = (int)Math.ceil(totalCount/(double)numPerPage);
		
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;

		String pageNavi = "<ul class='pagination'>";				
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/msg/receiveList?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		//페이지 숫자
		for(int i=0;i<pageNaviSize;i++) {
			if(pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item active-page' href='/msg/receiveList?reqPage="+(pageNo)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/msg/receiveList?reqPage="+(pageNo)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			pageNo++;
			if(pageNo>totalPage) {
				break;
			}
		}
		//다음 버튼
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/msg/receiveList?reqPage="+(pageNo)+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		pageNavi += "</ul>";
		
		MsgListData mld = new MsgListData(msgList, pageNavi);
		return mld;
	}
	
	public MsgListData selectSendList(String memberId, int reqPage) {
		int numPerPage = 5;
		int end = reqPage * numPerPage;
		int start = end - numPerPage + 1;
		List msgList = msgDao.selectSendList(start, end, memberId);
		
		int totalCount = msgDao.selectSendMsgTotalCount(memberId);
		int totalPage = (int)Math.ceil(totalCount/(double)numPerPage);
		
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;

		String pageNavi = "<ul class='pagination'>";				
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/msg/sendList?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		//페이지 숫자
		for(int i=0;i<pageNaviSize;i++) {
			if(pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item active-page' href='/msg/sendList?reqPage="+(pageNo)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-item' href='/msg/sendList?reqPage="+(pageNo)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			pageNo++;
			if(pageNo>totalPage) {
				break;
			}
		}
		//다음 버튼
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-item' href='/msg/sendList?reqPage="+(pageNo)+"'>";
			pageNavi += "<span class='material-icons'>chevron_right</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		pageNavi += "</ul>";
		
		MsgListData mld = new MsgListData(msgList, pageNavi);
		return mld;
	}
	
	@Transactional
	public int sendMsgToAdmin(Msg msg) {
		int result = msgDao.sendMsgToAdmin(msg);
		return result;
	}

	public Msg selectReceiveView(int mid) {
		Msg msg = msgDao.selectReceiveView(mid);
		return msg;
	}
	
	@Transactional
	public int readMsg(int mid) {
		int result = msgDao.readMsg(mid);
		return result;
	}
	
	@Transactional
	public int deleteMsg(int mid) {
		int result = msgDao.deleteMsg(mid);
		return result;
	}
	
	@Transactional
	public int replyMsg(Msg msg) {
		int result = msgDao.replyMsg(msg);
		return result;
	}

	public int selectNotReadMsgCount(String memberId) {
		int letterCount = msgDao.selectNotReadMsgCount(memberId);
		return letterCount;
	}
	
	@Transactional
	public int adminSendMsg(Msg msg) {
		int result = msgDao.adminSendMsg(msg);
		return result;
	}

	public int productSendMsg(Msg msg) {
		int result = msgDao.productSendMsg(msg);
		return result;
	}

	public int boardSendMsg(Msg msg) {
		int result = msgDao.boardSendMsg(msg);
		return result;
	}
}
