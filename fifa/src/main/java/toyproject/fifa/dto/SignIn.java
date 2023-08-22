package toyproject.fifa.dto;

import lombok.*;
import toyproject.fifa.domain.Member;

public class SignIn {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        private String userId;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String token;
        private String message;

        public static Response fromDto(Member member, String token){
            return Response.builder()
                    .token(token)
                    .message(String.format("%s님 반갑습니다.", member.getName()))
                    .build();
        }
    }
}
