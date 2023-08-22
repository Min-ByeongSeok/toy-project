package toyproject.fifa.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import toyproject.fifa.domain.Member;
import toyproject.fifa.dto.SignIn;
import toyproject.fifa.dto.SignUp;
import toyproject.fifa.exception.MyException;
import toyproject.fifa.repository.MemberRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static toyproject.fifa.type.ErrorCode.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 성공")
    void success_signup() {
        Member member = Member.builder()
                .name("messi")
                .userId("lm10")
                .password(passwordEncoder.encode("1234"))
//                .password("1234")
                .build();

        // given
        given(memberRepository.existsByUserId(anyString())).willReturn(false);
        given(memberRepository.save(any())).willReturn(member);
        // when
        Member newMember = memberService.signup(SignUp.Request.builder()
                .name("messi")
                .userId("lm10")
                .password("1234")
                .build());

//        Member findMember = memberRepository.findByUserId(newMember.getUserId()).get();
        // then
        assertEquals(member.getName(), newMember.getName());
        assertNull(newMember.getPassword());
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 ID")
    void fail_signup_already_exist_userId() {
        // given
        given(memberRepository.existsByUserId(anyString())).willReturn(true);
        // when
        MyException myException = assertThrows(MyException.class, () -> memberService.signup(SignUp.Request.builder()
                .name("messi")
                .userId("lm10")
                .password("1234")
                .build()));
        // then
        assertEquals(ALREADY_EXIST_USER_ID, myException.getErrorCode());
    }

    @Test
    @DisplayName("로그인 성공")
    void success_signin() {
        Member member = Member.builder()
                .name("messi")
                .userId("lm10")
                .password(passwordEncoder.encode("1234"))
                .build();

        SignIn.Request request = SignIn.Request.builder()
                .userId("lm10")
                .password("1234")
                .build();

        // given
        given(memberRepository.findByUserId(anyString())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(request.getPassword(), member.getPassword())).willReturn(true);
        // when
        Member joinMember = memberService.signin(request);
        // then
        assertEquals("messi", joinMember.getName());
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 ID")
    void fail_signin_not_found_userId() {
        SignIn.Request request = SignIn.Request.builder()
                .userId("lm10")
                .password("1234")
                .build();
        // given
        given(memberRepository.findByUserId(anyString())).willReturn(Optional.empty());
        // when
        MyException myException = assertThrows(MyException.class, () -> memberService.signin(request));
        // then
        assertEquals(NOT_FOUND_USER_ID, myException.getErrorCode());
    }

    @Test
    @DisplayName("로그인 실패 - 일치하지 않는 비밀번호")
    void fail_signin_not_match_password() {
        Member member = Member.builder()
                .name("messi")
                .userId("lm10")
                .password(passwordEncoder.encode("1234"))
                .build();

        SignIn.Request request = SignIn.Request.builder()
                .userId("lm10")
                .password("1111")
                .build();

        // given
        given(memberRepository.findByUserId(anyString())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(request.getPassword(), member.getPassword())).willReturn(false);
        // when
        MyException myException = assertThrows(MyException.class, () -> memberService.signin(request));
        // then
        assertEquals(NOT_MATCH_PASSWORD, myException.getErrorCode());
    }
    
    @Test
    @DisplayName("유저 디테일 불러오기 성공")
    void success_loadUserByUsername() {
        Member member = Member.builder()
                .name("messi")
                .userId("lm10")
                .password(passwordEncoder.encode("1234"))
                .build();
        // given
        given(memberRepository.findByUserId(anyString())).willReturn(Optional.of(member));
        // when
        UserDetails userDetails = memberService.loadUserByUsername(member.getUsername());
        // then
        assertEquals("lm10", userDetails.getUsername());
    }

    @Test
    @DisplayName("유저 디테일 불러오기 실패 - 존재하지 않는 ID")
    void fail_loadUserByUsername() {
        // given
        given(memberRepository.findByUserId(anyString())).willReturn(Optional.empty());
        // when
        MyException myException = assertThrows(MyException.class, () -> memberService.loadUserByUsername(anyString()));
        // then
        assertEquals(NOT_FOUND_USER_ID, myException.getErrorCode());

    }
}