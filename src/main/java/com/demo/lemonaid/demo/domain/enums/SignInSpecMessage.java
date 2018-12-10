package com.demo.lemonaid.demo.domain.enums;

public enum SignInSpecMessage {
    EMPTY_NAME(1,"이름을 입력해주세요"),
    EMPTY_PERSONAL_ID(2,"주민등록번호를 입력하세요"),
    EMPTY_GENDER(3, "성별을 입력하세요"),
    EMPTY_TEL(4, "핸드폰번호를 입력하세요."),
    EMPTY_ADDR(5, "주소를 입력하세요"),
    EMPTY_AGREE(6, "동의 여부를 체크해주세요."),
    REG_AUTH(7, "인증을 진행해주세요."),
    REG_TEL(8, "휴대폰번호 형식이 잘못되었습니다."),
    REG_NAME(9, "이름 형식이 잘못되었습니다."),
    REG_PERSONAL_ID(10, "주민등록번호 형식이 잘못되었습니다."),
    SUCCESS(11, "가입 성공"),
    FAIL(12, "가입 실패");

    private final int id;
    private final String message;

    SignInSpecMessage(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() { return id; }
    public String getMessage() { return message; }
}
