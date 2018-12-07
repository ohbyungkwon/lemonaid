package com.demo.lemonaid.demo.domain.enums;

public enum SignInBasicMessage {
    INCLUDE_SPACE_PASSWORD(1,"공백을 제거해주세요."),
    SHORT_PASSWORD(2,"비밀번호가 짧습니다."),
    CHECK_DUPLICATE_EMAIL(3,"이메일이 중복됩니다."),
    CHECK_PASSWORD(4,"비밀번호를 다시 확인해주세요."),
    NEXT(5,"다음으로 이동합니다.");

    private final int id;
    private final String message;

    SignInBasicMessage(int id, String message) {
        this.id = id;
        this.message = message;
    }
    public int getId() { return id; }
    public String getMessage() { return message; }
}