package com.example.springSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.springSecurity.service.MemberService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	private MemberService memberService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		// static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 ) 파일 기준은 resource/static 디렉토리
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() //HttpServletRequest에 따라 접근제한
			//페이지 권한 설정
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/user/myinfo/**").hasRole("MEMBER")
			.antMatchers("/**").permitAll()
		.and() // 로그인 설정
			.formLogin()
			.loginPage("/user/login")
			.defaultSuccessUrl("user/login/result") //로그인 성공시 이동 페이지 -> 컨트롤러에서 URL 매핑이 되어있어야함
			.permitAll()
			// .usernameParameter("파라미터명") name=username인 input을 기본으로 인식하는데 이 파라미터명을 변경 가능
		.and() //로그아웃 설정
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
            .logoutSuccessUrl("/user/logout/result")
            .invalidateHttpSession(true)
            //.deleteCookies("KEY명") 로그아웃 시 쿠키 제거 가능
        .and()
        	// 403 예외처리
        	.exceptionHandling().accessDeniedPage("/user/denied");// 접근 권한 없을 시 바로 로그인 페이지로
	}
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
