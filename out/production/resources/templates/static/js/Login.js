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
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            success: function (data) {
                flag=true;
                alert(data.message);
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
            success: function (data) {
                $("#passwordComment").text(data.message);
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
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            success: function (data) {
                $("#duplicateComment").text(data.message);
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
            success: function (data) {
                if (flag === true) {
                    alert(data.message);
                    window.location.href = "/SignInSpec";
                }
            },
            error: function(data){
                alert(data.responseJSON.message);
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
            success: function (data) {
                    alert(data.message);
                    window.location.href = "/login";
            },
            error: function(data){
                console.log(data);
                alert(data.responseJSON.message);
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