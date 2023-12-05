<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.Map" %>

<%@ include file="../part/head.jspf"%>

<script>
  function LoginForm_submit(form) {
    let LoginForm__submitDone = false;

    if(LoginForm__submitDone) {
      alert('처리 중입니다.');
      return;
    }

    form.loginId.value = form.loginId.value.trim();

    if(form.loginId.value.length == 0) {
      alert('로그인 아이디를 입력해주세요');
      form.loginId.focus();
      return;
    }

    form.loginPw.value = form.loginPw.value.trim();

    if(form.loginPw.value.length == 0) {
      alert('로그인 비번을 입력해주세요');
      form.loginPw.focus();
      return;
    }

    form.submit();
    LoginForm__submitDone = true;
  }
</script>

<style>
  .btn-group > button {
    width: calc(100% / 2 - 7px);
  }

  .btn-group > .btn-login {
    background-color : oklch(0.4912 0.3096 275.75) !important;
  }

  .btn-group > .btn-login:hover {
    background-color : oklab(0.44208 0.0279164 -0.277238) !important;
  }

  .btn-group > .btn-cancel {
    background-color : oklch(0.6971 0.329 342.55) !important;
  }

  .btn-group > .btn-cancel:hover {
    background-color : oklab(0.62739 0.282473 -0.0887925) !important;
  }
</style>

<section class="member-login-wrap h-screen">
  <div class="con mx-auto w-[1100px] h-full flex items-center justify-center">
    <div class="login-box flex flex-col w-[500px] h-[300px] p-[20px]">
      <div class="login-box__head">
        <div class="login-title text-center text-[2rem] font-bold">
          로그인
        </div>
      </div>
      <div class="login-box__body flex items-center justify-center flex-grow">
        <form class="w-full" action="doLogin" method="POST" onsubmit="LoginForm_submit(this); return false;">
          <div class="input-box flex flex-col items-center gap-y-3">
            <input placeholder="아이디" name="loginId" type="text" class="input input-bordered w-full" />
            <input placeholder="비밀번호" name="loginPw" type="password" class="input input-bordered w-full" />
          </div>
          <div class="btn-group flex justify-center gap-x-3 mt-[10px]">
            <button type="submit" class="btn btn-primary btn-login !text-[20px]">로그인</button>
            <button type="button" class="btn btn-secondary btn-cancel !text-[20px]">
              <a href="list">취소</a>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</section>
<%@ include file="../part/foot.jspf"%>
