package kr.or.bo.msg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import kr.or.bo.msg.model.service.MsgService;

@Controller
public class MsgController {
	@Autowired
	private MsgService msgService;
}
