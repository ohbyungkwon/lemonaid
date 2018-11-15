<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>LOGIN</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="/dist/js/login.css">
    <link rel="stylesheet" type="text/css" href="/dist/js/semantic.css">
    <link rel="stylesheet" type="text/css" href="/dist/js/bootstrap.css">
    <script src="/dist/js/vendor.js"></script>
    <script src="/dist/js/loginForm.js"></script>
    <script src="/dist/js/bootstrap.js"></script>
    <script src="/dist/js/semanticjs.js"></script>

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
<div class="container">
    <div class="row justify-content-sm-center">
        <div class="card card-center">
            <div class="card-body">
                <form id="loginForm" method="post" action="j_spring_security_check">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input name="j_username" type="text" class="form-control" placeholder="Email">
                    <input name="j_password" type="password" class="form-control" placeholder="Password">
                </form>
            </div>
        </div>
        <a class="center_object">
            로그인을 통해 원격
            <div style="color: orange;">
                처방 서비스 이용약관
            </div>
            동의합니다.
        </a>
        <button name="button" class="ui orange button center_object">로그인</button>
        <a href="/firstLogin">회원가입</a>
    </div>
</div>
</body>
</html>