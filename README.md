# Spring Security
- 스프링 기반 어플리케이션의 보안을 담당하는 프레임워크
- 스프링 시큐리티를 사용하지 않았다면, 자체적으로 세션을 체크하고 리다이렉트 등을 구현해야함
- 보안관련 체계적으로 많은 옵션들을 지원해 준다.
- Filter 기반으로 동작, Spring MVC와 분리되어 관리 및 동작

## Spring Security Filter
- 클라이언트(브라우저)가 요청을 보내면 Spring MVC에선 DispatcherServlet에서 처리하는데 이전에 다양한 Filter들이 존재할 수 있음
- Filter는 클라이언트와 리소스 사이에서 요청과 응답정보를 이용해 다양한 처리를 한다.
  - ex) 클라이언트가 원래 요청한 리소스가 아닌 다른 리소스로 redirect도 가능하고 Filter 안에서 바로 클라이언트로 응답이 가능하다.

### Security Filter Chain
- Spring Security는 10개 이상의 기본 필터를 제공 

  |Filter|역할|
  |------|---|
  |SecurityContextPersistenceFilter|SecurityContextRepository에서 SecurityContext를 가져오거나 저장하는 역할을 한다.|
  |LogoutFilter|설정된 로그아웃 URL로 오는 요청을 감시하며, 해당 유저를 로그아웃 처리|
  |(UsernamePassword)AuthenticationFilter	|(아이디와 비밀번호를 사용하는 form 기반 인증) 설정된 로그인 URL로 오는 요청을 감시하며, 유저 인증 처리한다. <br>1. AuthenticationManager를 통한 인증 실행 <br>2. 인증 성공 시, 얻은 Authentication 객체를 SecurityContext에 저장 후 AuthenticationSuccessHandler 실행 <br>3. 인증 실패 시, AuthenticationFailureHandler 실행|
  |DefaultLoginPageGeneratingFilter|인증을 위한 로그인폼 URL을 감시한다.|
  |BasicAuthenticationFilter|HTTP 기본 인증 헤더를 감시하여 처리한다.|
  |RequestCacheAwareFilter|로그인 성공 후, 원래 요청 정보를 재구성하기 위해 사용된다.|
  |SecurityContextHolderAwareRequestFilter|HttpServletRequestWrapper를 상속한 SecurityContextHolderAware RequestWapper 클래스로 HttpServletRequest 정보를 감싼다. <br>Security ContextHolderAwareRequestWrapper 클래스는 필터 체인상의 다음 필터들에게 부가정보를 제공한다.|
  |AnonymousAuthenticationFilter|이 필터가 호출되는 시점까지 사용자 정보가 인증되지 않았다면 인증토큰에 사용자가 익명 사용자로 나타난다.|
  |SessionManagementFilter|이 필터는 인증된 사용자와 관련된 모든 세션을 추적한다.|
  |ExceptionTranslationFilter|이 필터는 보호된 요청을 처리하는 중에 발생할 수 있는 예외를 위임하거나 전달하는 역할을 한다.|
  |FilterSecurityInterceptor|이 필터는 AccessDecisionManager 로 권한부여 처리를 위임함으로써 접근 제어 결정을 쉽게 해준다.|
