<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>NonLogin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="/dist/js/nonLoginUser.css">
    <link rel="stylesheet" type="text/css" href="/dist/js/semantic.css">
    <link rel="stylesheet" type="text/css" href="/dist/js/bootstrap.css">
    <script src="/dist/js/vendor.js"></script>
    <script src="/dist/js/bootstrap.js"></script>
    <script src="/dist/js/survey.js"></script>
</head>
<body>
    <div class="container">
        <div class="header_c">
            <p class="subject">기본 인적사항</p>
        </div>
        <div class="content_c">
            <p class="title_c">본인의 성별을 입력하세요.</p>
            <div class="content_layout">
                <button class="ui button">여자</button>
                <button class="ui button">남자</button>
            </div>
        </div>
        <hr class="line_color">
        <div class="content_c">
            <p class="title_c">본인의 생일을 입력해 주세요.</p>
            <div class="content_layout">
                <div class="ui input focus">
                    <input type="number" maxlength="6" placeholder="yy/mm/dd">
                    <#--max, minlength는 text type에서 가능, 다른 형태는 javascript를 사용-->
                </div>
            </div>
        </div>
        <hr class="line_color">
        <hr>
    </div>
    <div class="footer_c">
        <div class="row justify-content-xl-around">
            <button class="col-xl-1 ui orange button">이전</button>
            <button class="col-xl-1 ui orange button">다음</button>
        </div>
    </div>
</body>
</html>