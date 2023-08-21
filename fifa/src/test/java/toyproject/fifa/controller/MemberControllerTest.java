package toyproject.fifa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import toyproject.fifa.controller.MemberController;
import toyproject.fifa.domain.Member;
import toyproject.fifa.dto.SignUp;
import toyproject.fifa.security.TokenProvider;
import toyproject.fifa.service.MemberService;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @MockBean

    private MemberService memberService;

    @MockBean
    private TokenProvider tokenProvider;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
//    @WithMockUser(username = "테스트계정", password = "custom_password", roles = {"USER","ADMIN"})
    @WithMockUser
    @DisplayName("회원가입 성공")
    void success_signup() throws Exception {
        Member member = Member.builder()
                .name("ronaldo")
                .userId("cr7")
                .password("1234")
                .build();

        // given
        given(memberService.signup(any())).willReturn(member);
        // when
        // then
        mockMvc.perform(post("/fifa/signup").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new SignUp.Request("ronaldo", "cr7", "1234"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ronaldo"))
                .andExpect(jsonPath("$.userId").value("cr7"))
                .andExpect(jsonPath("$.message").value("회원가입이 완료되었습니다."));
    }

    @Test
    @DisplayName("로그인 성공")
    void success_signin() {
        // given

        // when

        // then
    }
}