package com.example.springSecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springSecurity.dto.MemberDto;
import com.example.springSecurity.service.MemberService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MemberController {
	private MemberService memberService;
	
	//메인페이지
	@GetMapping("/")
	public String index() {
		return "/index";
	}
	
	//회원가입 페이지
	@GetMapping
	public String dispSignup() {
		return "/signup";
	}
	
	//회원가입 처리
	@PostMapping("/user/signup")
	public String execSignup(MemberDto memberDto) {
		memberService.joinUser(memberDto);
		
		return "redirect:/user/login";
	}
	
	//로그인 페이지
	@GetMapping
	public String dispLogin() {
		return "/login";
	}
	
	//로그인 결과 페이지
	@GetMapping("/user/logout/result")
	public String dispLogout () {
		return "/logout";
	}
	
	//접근 거부 페이지
	@GetMapping("/user/denied")
	public String dispDenied() {
		return "/denied";
	}
	
	//내 정보 페이지
	@GetMapping("/user/info")
	public String dispMyInfo() {
		return "/myinfo";
	}
	
	//admin 페이지
	@GetMapping("/admin") 
	public String dispAdmin() {
		return "/admin";
	}
}
