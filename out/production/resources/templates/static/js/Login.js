window.onload = function () {
    $("button[name='button']").click(function () {
        console.log("click btn");
        $("#loginForm").submit();
    });

    var email, pwd, DuplicatePwd;

    var method;
    var url;
    var data;

    $("button[name='checkDuplicate']").click(function () {
        email = $("input[name='username']").val();

        method="POST";
        url = "/checkEmail";
        data = { "email" : email };
        console.log(data);

        $.ajax({
            url: url,
            method: method,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data:JSON.stringify(data),
            success:function (data) {
                if(data.isExist == 1){
                    alert("이미 존재합니다.")
                }else{ alert("사용가능한 아이디입니다."); }
            }
        })
    })

    $("input[name='password']").keyup(function () {
        pwd = $("input[name='password']").val();
        method="POST";
        url = "/checkPwd";
        data = { "password" : pwd };
        console.log(data);

        $.ajax({
            url: url,
            method: method,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data:JSON.stringify(data),
            success:function () {

            }
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
        console.log(data);

        $.ajax({
            url: url,
            method: method,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data:JSON.stringify(data),
            success:function () {

            }
        })
    })
}