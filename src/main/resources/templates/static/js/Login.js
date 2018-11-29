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

    // var link = document.location.href;
    // if(link.indexOf("login")){
    //     window.webInterface.responseData("login");
    // }else if(link.indexOf("SignInBasic") || link.indexOf("SignInSpec")){
    //     window.webInterface.responseData("signIn");
    // }

    $("button[name='button']").click(function () {
        console.log("click btn");
        $("#loginForm").submit();
    });

    $("button[name='checkDuplicate']").click(function () {
        email = $("input[name='username']").val();

        method = "POST";
        url = "/checkEmail";
        data = {"email": email};

        $.ajax({
            url: url,
            method: method,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            success: function (data) {
                if(data.comment == "사용 가능한 아이디입니다."){
                    flag = true;
                }else flag = false;

                console.log(flag);

                alert(data.comment);
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
        url = "/checkPwd";
        data = {"password": pwd};

        $.ajax({
            url: url,
            method: method,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            success: function (data) {
                $("#passwordComment").text(data.comment);

            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
        })
    })

    $("input[name='password_check']").keyup(function () {
        pwd = $("input[name='password']").val();
        DuplicatePwd = $("input[name='password_check']").val();

        method = "POST";
        url = "/checkDuplicate";
        data = {
            "password": pwd,
            "checkDuplicate": DuplicatePwd
        };

        $.ajax({
            url: url,
            method: method,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            success: function (data) {
                $("#duplicateComment").text(data.comment);
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
        })
    })

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

        console.log(data);

        $.ajax({
            url: "/redirectNext",
            method: "POST",
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            success: function (data) {
                if (data.comment == "다음으로 이동합니다." && flag == true) {
                    alert(data.comment);
                    window.location.href = "/SignInSpec";
                } else {
                    alert(data.comment);
                }
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
        })
    })

    var gender = null;
    $("#womanBtn").click(function () {
        gender = "WOMAN";
        console.log("woman");
        $(this).css("background", "orange");
        $("#manBtn").css("background", "darkgray")
    })
    $("#manBtn").click(function () {
        gender = "MAN";
        console.log("man");
        $(this).css("background", "orange");
        $("#womanBtn").css("background", "darkgray")
    })


    $("#telCompany").change(function () {
        TelCompany = $(":selected").text();
    })

    $("#auth").click(function () {
        if ($("input[name='tel']").val() != "" && TelCompany != "") {
            $.ajax({
                url: "/authRandom",
                method: "GET",
                success: function (data) {
                    $("input[name='authNum']").val(Math.floor(data.num));
                    isAuth = true;
                    console.log(isAuth);
                },
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
            })
        } else if ($("input[name='tel']").val() == "") {
            alert("연락처를 입력해주세요.");
        } else if (TelCompany == "") {
            alert("통신사를 선택해주세요.");
        }
    })

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
        console.log(data.auth);
        $.ajax({
            url: "/done",
            method: "POST",
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            success: function (data) {
                if (data.comment == "가입 완료") {
                    alert(data.comment);
                    window.location.href = "/login";
                } else {
                    alert(data.comment);
                }
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
        })
    })

    $("input[name='front']").keyup(function () {
        if ($(this).val().length > this.maxLength) {
            $(this).val($(this).val().slice(0, this.maxLength));
            console.log("access");
        }
    })
}