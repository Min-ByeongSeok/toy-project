package toyproject.fifa.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import toyproject.fifa.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

//TODO
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 성공")
    void success_signup() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 ID")
    void fail_signup_already_exist_userId() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("로그인 성공")
    void success_signin() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 ID")
    void fail_signin_not_found_userId() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("로그인 실패 - 일치하지 않는 비밀번호")
    void fail_signin_not_match_password() {
        // given

        // when

        // then
    }
}