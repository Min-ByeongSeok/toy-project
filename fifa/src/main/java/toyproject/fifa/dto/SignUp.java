package toyproject.fifa.dto;

import lombok.*;
import toyproject.fifa.domain.Member;

public class SignUp {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        private String name;
        private String userId;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String name;
        private String userId;
        private String message;

        public static Response fromDto(Member member){
            return Response.builder()
                    .name(member.getName())
                    .userId(member.getUserId())
                    .message("회원가입이 완료되었습니다.")
                    .build();
        }
    }
}
