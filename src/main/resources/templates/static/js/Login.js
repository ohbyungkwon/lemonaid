window.onload = function () {
    var email, pwd, DuplicatePwd;

    var method;
    var url;
    var data;
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    $("button[name='button']").click(function () {
        console.log("click btn");
        $("#loginForm").submit();
    });

    $("button[name='checkDuplicate']").click(function () {
        email = $("input[name='username']").val();

        method="POST";
        url = "/checkEmail";
        data = { "email" : email };

        $.ajax({
            url: url,
            method: method,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data:JSON.stringify(data),
            success:function (data) {
                 alert(data.comment);
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
        });
        return false;
    });

    $("input[name='password']").keyup(function () {
        pwd = $("input[name='password']").val();
        method="POST";
        url = "/checkPwd";
        data = { "password" : pwd };

        $.ajax({
            url: url,
            method: method,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data:JSON.stringify(data),
            success:function (data) {
                $("#passwordComment").text(data.comment);

            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
        })
    })

    $("input[name='password_check']").keyup(function () {
        pwd = $("input[name='password']").val();
        DuplicatePwd = $("input[name='password_check']").val();

        method="POST";
        url = "/checkDuplicate";
        data = {
            "password" : pwd,
            "checkDuplicate" : DuplicatePwd
        };

        $.ajax({
            url: url,
            method: method,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data:JSON.stringify(data),
            success:function (data) {
                $("#duplicateComment").text(data.comment);
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
        })
    })

    $("#continueBtn").click(function () {
        email = $("input[name='username']").val();
        pwd = $("input[name='password']").val();
        DuplicatePwd = $("input[name='password_check']").val();
        data = {
            "email" : email,
            "password" : pwd,
            "checkDuplicate" : DuplicatePwd
        };

        console.log(data);

        $.ajax({
            url: "/redirectNext",
            method: "POST",
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data:JSON.stringify(data),
            success:function (data) {
                if(data.comment == "다음으로 이동합니다."){
                    alert(data.comment);
                    window.location.href="/SignInSpec";
                }else{
                    alert(data.comment);
                }
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
        })
    })

    var gender = "-1";
    $("#womanBtn").click(function () {
        gender = "0";
        console.log("woman");
        $(this).css("background","orange");
        $("#manBtn").css("background","darkgray")
    })
    $("#manBtn").click(function () {
        gender = "1";
        console.log("man");
        $(this).css("background","orange");
        $("#womanBtn").css("background","darkgray")
    })


    $("#SignedBtn").click(function () {
        data = {
            "name" : $("input[name='name']").val(),
            "personalId" : $("input[name='front']").val() + $("input[name='back']").val(),
            "gender" : gender,
            "tel" : $("input[name='tel']").val(),
            "addr" : $("input[name='addr']").val(),
            "checkAgree" : $("input[name='agree']").is(":checked"),
            "isAuth" : isAuth
        };
        $.ajax({
            url: "/done",
            method: "POST",
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data:JSON.stringify(data),
            success:function (data) {
                if(data.comment == "가입 완료"){
                    alert(data.comment);
                    window.location.href="/";
                }else{
                    alert(data.comment);
                }
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
        })
    })

    var TelCompany="";
    var isAuth = false;
    $("#telCompany").change(function () {
        TelCompany = $(":selected").text();
    })
    
    $("#auth").click(function (){
        if($("input[name='tel']").val() != "" && TelCompany != "") {
            $.ajax({
                url:"/authRandom",
                method: "GET",
                success : function (data) {
                    $("input[name='authNum']").val(Math.floor(data.num));
                    isAuth = true;
                },
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
            })
            //var num = Math.random() * (99999-1) + 1;
        }else if($("input[name='tel']").val() == ""){
            alert("연락처를 입력해주세요.");
        }else if(TelCompany == ""){
            alert("통신사를 선택해주세요.");
        }
    })
}