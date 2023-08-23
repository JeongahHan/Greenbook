package kr.or.bo.msg.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.bo.msg.model.dao.MsgDao;
import kr.or.bo.msg.model.vo.Msg;

@Service
public class MsgService {
	@Autowired
	private MsgDao msgDao;

	public List selectReceiveList(String memberId) {
		List list = msgDao.selectReceiveList(memberId);
		return list;
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
}
