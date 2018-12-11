package com.demo.lemonaid.demo.domain.enums;

public enum SignInBasicMessage {
    FAIL_INCLUDE_SPACE_PASSWORD(1,"공백을 제거해주세요."),
    FAIL_SHORT_PASSWORD(2,"비밀번호가 짧습니다."),
    FAIL_REG_EMAIL(3,"이메일 형식에 맞지 않습니다."),
    FAIL_CHECK_DUPLICATE_EMAIL(4,"이메일이 중복됩니다."),
    FAIL_IS_CHECK_EMAIL_REG(5, "이메일 중복여부를 체크해주세요."),
    FAIL_CHECK_PASSWORD(6,"비밀번호를 다시 확인해주세요."),
    FAIL_DIFFERENCE_PASSWORD(7, "비밀번호가 다릅니다."),
    SUCCESS_PASSWORD(8, "사용가능한 비밀번호입니다."),
    SUCCESS_CHECK_PASSWORD(9, "비밀번호가 같습니다."),
    SUCCESS_EMAIL(10, "사용 가능한 이메일 입니다."),
    NEXT(11, "다음으로 이동합니다.");

    private final int id;
    private final String message;

    SignInBasicMessage(int id, String message) {
        this.id = id;
        this.message = message;
    }
    public int getId() { return id; }
    public String getMessage() { return message; }
}