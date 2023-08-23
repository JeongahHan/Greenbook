package kr.or.bo.msg.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
