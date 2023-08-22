package kr.or.bo.msg.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bo.msg.model.dao.MsgDao;

@Service
public class MsgService {
	@Autowired
	private MsgDao msgDao;
}
