package kr.or.bo.member.controller;

import javax.servlet.http.HttpSession;

import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import kr.or.bo.EmailSender;
import kr.or.bo.member.model.service.MemberService;
import kr.or.bo.member.model.vo.Member;
import kr.or.bo.member.model.vo.adminListData;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private EmailSender emailSender;
	
	// login 인터셉터
	@GetMapping(value="/loginMsg")
	public String loginMsg(Model model) {
		model.addAttribute("title", "접근 권한 없음");
		model.addAttribute("msg", "로그인 후 이용 가능합니다.");
		model.addAttribute("loc", "/");
		return "common/msg";
	}
	
	// admin 인터셉터
	@GetMapping(value="/adminMsg")
	public String adminMsg(Model model) {
		model.addAttribute("title", "접근 권한 없음");
		model.addAttribute("msg", "관리자만 이용 가능합니다.");
		model.addAttribute("loc", "/");
		return "common/msg";
	}
	
	// 블랙이 인터셉터
	@GetMapping(value="/blackMsg")
	public String blackMsg(Model model) {
		model.addAttribute("title", "이용 제한 있음");
		model.addAttribute("msg", "블랙이는 이용에 제한이 있습니다.");
		model.addAttribute("loc", "/");
		return "common/msg";
	}
	
	// loginFrm.html로 이동
	@GetMapping(value = "/loginFrm")
	public String loginFrm() {
		return "member/loginFrm";
	}

	// 로그인 기능
	@PostMapping(value = "/login")
	public String login(String memberId, String memberPw, Model model, HttpSession session) {
		Member m = memberService.selectOneMember(memberId, memberPw);
		if (m != null) {
			if (m.getMemberLevel() == 3) {
				// 블랙이인 경우
				session.setAttribute("m", m);
				model.addAttribute("title", "이용 제한 있음");
				model.addAttribute("msg", "블랙이로 변경되었습니다. 쪽지함을 확인하세요.");
				model.addAttribute("loc", "/msg/receiveList?reqPage=1");
			} else {
				// session에 로그인한 회원 정보를 저장
				session.setAttribute("m", m);
				// 관리자, 그린이인 경우
				model.addAttribute("title", "로그인 성공");
				model.addAttribute("msg", "환영합니다.");
				model.addAttribute("loc", "/");
			}
		} else {
			// 로그인 실패(아이디 or 비밀번호 불일치)
			model.addAttribute("title", "로그인 실패");
			model.addAttribute("msg", "아이디 또는 비밀번호를 확인하세요");
			model.addAttribute("loc", "/member/loginFrm");
		}
		return "common/msg";
	}

	// 로그아웃 기능
	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		// 현재 세션에 저장되어있는 정보 파기
		session.invalidate();
		return "redirect:/";
	}

	// searchIdFrm.html로 이동
	@GetMapping(value = "/searchIdFrm")
	public String searchIdFrm() {
		return "member/searchIdFrm";
	}

	// 아이디 찾기 기능
	@ResponseBody
	@PostMapping(value = "/searchId")
	public String searchId(String memberName, String memberEmail) {
		Member m = memberService.selectMemberId(memberName, memberEmail);
		if (m != null) {
			return m.getMemberId();
		}
		return null;
	}

	// seachPw.html로 이동
	@GetMapping(value = "/searchPwFrm")
	public String searchPwFrm() {
		return "member/searchPwFrm";
	}

	// 비밀번호 찾기 -> 아이디,이메일 일치 확인
	@ResponseBody
	@PostMapping(value = "/searchPw")
	public Member searchPw(String memberId, String memberEmail) {
		Member m = memberService.selectMemberPw(memberId, memberEmail);
		if (m != null) {
			return m;
		}
		return null;
	}

	// 비밀번호 찾기 -> 새 비밀번호 받아 비밀번호 변경
	@PostMapping(value = "/updatePw")
	public String updatePw(int memberNo, String memberPw, Model model) {
		int result = memberService.updatePw(memberNo, memberPw);
		if (result > 0) {
			model.addAttribute("title", "비밀번호 변경완료");
			model.addAttribute("msg", "새 비밀번호로 변경되었으니 로그인 부탁 드립니다.");
		} else {
			model.addAttribute("title", "비밀번호 변경실패");
			model.addAttribute("msg", "비밀번호 변경을 실패하였습니다.");
		}
		model.addAttribute("loc", "/member/loginFrm");
		return "common/msg";
	}

	// joinFrm.html으로 이동
	@GetMapping(value = "/joinFrm")
	public String joinFrm() {
		return "member/joinFrm";
	}

	// 회원가입 -> 아이디 중복체크
	@ResponseBody
	@PostMapping(value = "/checkId")
	public String checkId(String memberId) {
		Member m = memberService.checkId(memberId);
		if (m == null) {
			return "0";
		} else {
			return "1";
		}
	}

	// 회원가입 -> 이메일 중복체크
	@ResponseBody
	@PostMapping(value = "/checkEmail")
	public String checkEmail(String memberEmail) {
		Member m = memberService.checkEmail(memberEmail);
		if (m == null) {
			return "0";
		} else {
			return "1";
		}
	}

	// 회원가입 -> 이메일 인증코드 보내기
	@ResponseBody
	@PostMapping(value = "/auth")
	public String authMail(String memberEmail) {
		String authCode = emailSender.authMail(memberEmail);
		return authCode;
	}

	// 회원가입
	@PostMapping(value = "/signup")
	public String signup(Member member, Model model) {
		int result = memberService.insertMember(member);
		if (result > 0) {
			model.addAttribute("title", "회원가입 성공");
			model.addAttribute("msg", "신규 회원 가입을 축하합니다.");
			model.addAttribute("loc", "/");
		} else {
			model.addAttribute("title", "회원가입 실패");
			model.addAttribute("msg", "정보 입력 확인 부탁 드립니다.");
			model.addAttribute("loc", "/");
		}
		return "common/msg";
	}
	/*
	@GetMapping(value = "/admin")
	public String admin(Model model) {
		List list = memberService.selectAllMember();
		model.addAttribute("list", list);
		return "member/admin";
	}
	*/
	// 쪽지 보내기 기능으로 rePage추가
	@GetMapping(value = "/list")
	public String adminList(Model model, int reqPage) {
		adminListData nld = memberService.selectAdminList(reqPage);
		model.addAttribute("adminList", nld.getAdminList());
		model.addAttribute("pageNavi", nld.getPageNavi());
		model.addAttribute("reqPage", nld.getReqPage());
		System.out.println(nld.getPageNavi());
		return "member/admin";
	}
	
	@GetMapping(value = "/changeLevel")
	public String changeLevel(int memberNo, int memberLevel, Model model) {
		int result = memberService.changeLevel(memberNo, memberLevel);
		if (result > 0) {
			model.addAttribute("title", "등급변경성공!");
			model.addAttribute("msg", "등급변경성공하였습니다");
		} else {
			model.addAttribute("title", "등급변경실패하였습니다");
			model.addAttribute("msg", "등급변경실패! 관리자에게 문의하세요");
		}
		model.addAttribute("loc", "/member/list?reqPage=1");
		return "common/msg";
	}

	@GetMapping(value = "/checkedchangeLevel")
	public String checkedchangeLevel(String no, String level, Model model) {
		boolean result = memberService.checkedChangeLevel(no, level);
		if (result) { 
			model.addAttribute("title", "등급변경성공!");
			model.addAttribute("msg", "등급변경성공하였습니다");
		} else {
			model.addAttribute("title", "등급변경실패하였습니다");
			model.addAttribute("msg", "등급변경실패! 관리자에게 문의하세요");
		}
		model.addAttribute("loc", "/member/list?reqPage=1");
		return "common/msg";
	}
	

	@ResponseBody
	@PostMapping(value = "/find")
	public Member find(String memberId, Model model) {
		Member member = memberService.selectOneMember(memberId);
		
			return member;
	}

	@PostMapping(value = "/findresult")
	public String findresult(String memberId ,Model model) {
		Member m = memberService.selectOneMember(memberId);
		model.addAttribute("m", m);
		if(m == null) {
			model.addAttribute("title", "존재하지않는 회원!");
			model.addAttribute("msg", "아이디 확인후 재검색해주세요~!");
			model.addAttribute("loc", "/member/list?reqPage=1");
			return "common/msg";
		}else {
			return "member/findresult";
		}
		
	}
	@GetMapping(value = "/levelSearchList")
	public String levelSearchList(int reqPage,String memberlevel,Model model) {
		adminListData nld = memberService.selectLevelList(reqPage, memberlevel);
		model.addAttribute("adminList", nld.getAdminList());
		model.addAttribute("pageNavi", nld.getPageNavi());
		return "member/admin";
	}
}
