window.onload = function () {
    var email, pwd, DuplicatePwd;
    var method;
    var url;
    var data;
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var TelCompany = "";
    var isAuth = false;
    var flag = false;


    $("button[name='button']").click(function () {
        $("#loginForm").submit();
    });

    $("button[name='checkDuplicate']").click(function () {
        email = $("input[name='username']").val();

        method = "POST";
        url = "/api/checkEmail";
        data = {"email": email};

        $.ajax({
            url: url,
            method: method,
            dataType: 'text',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            success: function () {
                flag=true;
                alert("사용 가능한 아이디입니다.");
            },
            error: function(){
                flag=false;
                alert("사용 불가한 아이디입니다.");
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
        });
        return false;
    });

    $("input[name='password']").keyup(function () {
        pwd = $("input[name='password']").val();

        method = "POST";
        url = "/api/checkPwd";

        data = {"password": pwd};

        $.ajax({
            url: url,
            method: method,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            success: function () {
                $("#passwordComment").text("양식과 일치합니다.");
            },
            error: function(data){
                console.log(data);
                if(data.responseJSON === "SHORT_PASSWORD")
                    $("#passwordComment").text("6자 이상 입력해주세요.");
                else if(data.responseJSON === "INCLUDE_SPACE_PASSWORD")
                    $("#passwordComment").text("공백은 불가합니다.");
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
        })
    });

    $("input[name='password_check']").keyup(function () {
        pwd = $("input[name='password']").val();
        DuplicatePwd = $("input[name='password_check']").val();

        method = "POST";
        url = "/api/checkDuplicate";
        data = {
            "password": pwd,
            "checkDuplicate": DuplicatePwd
        };

        $.ajax({
            url: url,
            method: method,
            dataType: 'text',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            success: function () {
                $("#duplicateComment").text("비밀번호가 같습니다.");
            },
            error: function(){
                $("#duplicateComment").text("비밀번호가 다릅니다.");
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
        })
    });

    $("#continueBtn").click(function () {
        email = $("input[name='username']").val();
        pwd = $("input[name='password']").val();
        DuplicatePwd = $("input[name='password_check']").val();
        data = {
            "email": email,
            "password": pwd,
            "checkDuplicate": DuplicatePwd,
            "emailCheck" : flag
        };


        $.ajax({
            url: "/api/redirectNext",
            method: "POST",
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            success: function () {
                if (flag === true) {
                    alert("다음으로 이동합니다.");
                    window.location.href = "/SignInSpec";
                }
            },
            error: function(data){
                if(data.responseJSON === "INCLUDE_SPACE_PASSWORD") alert("공백은 불가합니다.");
                else if(data.responseJSON === "SHORT_PASSWORD") alert("6자 이상 입력해주세요.");
                else if(data.responseJSON === "CHECK_DUPLICATE_EMAIL") alert("이메일 중복 확인해주세요.");
                else if(data.responseJSON === "CHECK_PASSWORD") alert("비밀번호를 다시한번 확인해주세요.");
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
        })
    });

    var gender = null;
    $("#womanBtn").click(function () {
        gender = "WOMAN";
        $(this).css("background", "orange");
        $("#manBtn").css("background", "darkgray")
    });
    $("#manBtn").click(function () {
        gender = "MAN";
        $(this).css("background", "orange");
        $("#womanBtn").css("background", "darkgray")
    });


    $("#telCompany").change(function () {
        TelCompany = $(":selected").text();
    });

    $("#auth").click(function () {
        var telNum = $("input[name='tel']").val().trim();
        if (telNum !== "" && TelCompany !== "") {
            $.ajax({
                url: "/api/authRandom",
                method: "GET",
                success: function (data) {
                    $("input[name='authNum']").val(Math.floor(data.num));
                    isAuth = true;
                },
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
            })
        } else if (telNum === "") {
            alert("연락처를 입력해주세요.");
        } else if (TelCompany === "") {
            alert("통신사를 선택해주세요.");
        }
    });

    $("#SignedBtn").click(function () {
        data = {
            "name": $("input[name='name']").val(),
            "personalId": $("input[name='front']").val() + $("input[name='back']").val(),
            "gender": gender,
            "tel": $("input[name='tel']").val(),
            "addr": $("input[name='addr']").val(),
            "checkAgree": $("input[name='agree']").is(":checked"),
            "auth": isAuth
        };

        $.ajax({
            url: "/api/done",
            method: "POST",
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            success: function () {
                    alert("가입 완료");
                    window.location.href = "/login";
            },
            error: function(data){
                if(data.responseJSON === "EMPTY_NAME")
                    alert("이름을 입력하세요.");
                else if(data.responseJSON === "EMPTY_PERSONAL_ID")
                    alert("주민등록번호를 입력하세요.");
                else if(data.responseJSON === "EMPTY_GENDER")
                    alert("성별을 입력하세요.");
                else if(data.responseJSON === "EMPTY_TEL")
                    alert("핸드폰번호를 입력하세요.");
                else if(data.responseJSON === "EMPTY_ADDR")
                    alert("주소를 입력하세요.");
                else if(data.responseJSON === "EMPTY_AGREE")
                    alert("동의 여부를 체크해주세요.");
                else if(data.responseJSON === "REG_AUTH")
                    alert("인증을 진행해주세요.");
                else if(data.responseJSON === "REG_TEL")
                    alert("휴대폰번호 형식이 잘못되었습니다.");
                else if(data.responseJSON === "REG_NAME")
                    alert("이름 형식이 잘못되었습니다.");
                else if(data.responseJSON === "REG_PERSONAL_ID")
                    alert("주민등록번호 형식이 잘못되었습니다.");
                else if(data.responseJSON === "FAIL")
                    alert("가입 실패");
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
        })
    });

    $("input[name='front']").keyup(function () {
        if ($(this).val().length > this.maxLength) {
            $(this).val($(this).val().slice(0, this.maxLength));
        }
    })
};