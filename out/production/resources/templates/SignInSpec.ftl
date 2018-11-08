<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SignIn</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="/dist/js/signInBasic.css">
    <link rel="stylesheet" type="text/css" href="/dist/js/semantic.css">
    <link rel="stylesheet" type="text/css" href="/dist/js/bootstrap.css">
    <script src="/dist/js/vendor.js"></script>
    <script src="/dist/js/bootstrap.js"></script>
    <script src="/dist/js/semanticjs.js"></script>
</head>
<body>
<div class="container">
    <div class="row justify-content-sm-center">
        <div class="card card-center">
            <div class="card-body">
                <form id="SignInForm">
                    <p style="margin-bottom: 0px; margin-left: 15px;">이름</p>
                    <div class="row">
                        <div class="col-sm">
                            <input name="name" type="text" class="form-control" placeholder="name">
                        </div>
                    </div>
                    <p style="margin-bottom: 0px; margin-left: 15px;">주민등록번호 13자리</p>
                    <div class="row">
                        <div class="col-6">
                            <input name="front" type="number" class="form-control" placeholder="앞자리" maxlength="6">
                        </div>
                        <div class="col-6">
                            <input name="back" type="password" class="form-control" placeholder="뒷자리" autocomplete="new-password" maxlength="7">
                        </div>
                    </div>
                    <p style="margin-bottom: 0px; margin-left: 15px;">성별</p>
                    <div class="row">
                        <div class="col-6">
                            <button style="width: 100%;" id="womanBtn" class="ui button">여성</button>
                        </div>
                        <div class="col-6">
                            <button style="width: 100%;" id="manBtn" class="ui button">남성</button>
                        </div>
                    </div>
                    <p style="margin-bottom: 0px; margin-left: 15px;">핸드폰 번호</p>
                    <div class="row">
                        <div class="col-4">
                            <select class="ui dropdown">
                                <option value="">통신사</option>
                                <option value="2">LG</option>
                                <option value="1">KT</option>
                                <option value="0">SKT</option>
                            </select>
                        </div>
                        <div class="col-8">
                            <input name="tel" type="number" class="form-control" placeholder="숫자만 입력" maxlength="11">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-5">
                           <button style="width: 100%;" class="ui yellow button">인증</button>
                        </div>
                        <div class="col-7">
                            <input name="tel" type="number" class="form-control" placeholder="인증번호" maxlength="11">
                        </div>
                    </div>
                    <p style="margin-bottom: 0px; margin-left: 15px;">주소</p>
                    <div class="row">
                        <div class="col-sm">
                            <input type="text" name="addr" class="form-control" placeholder="주소 입력">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm">
                            <div class="ui checkbox">
                                <input type="checkbox" name="agree">
                                <label><a style="color:orange">원격처방 서비스 이용약관</a>에 동의</label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <button id="continueBtn" class="ui orange button">가입완료</button>
        </div>
    </div>
</div>
</body>
</html>