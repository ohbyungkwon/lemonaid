<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cash</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="/dist/js/cash.css">
    <link rel="stylesheet" type="text/css" href="/dist/js/semantic.css">
    <link rel="stylesheet" type="text/css" href="/dist/js/bootstrap.css">
    <script src="/dist/js/vendor.js"></script>
    <script src="/dist/js/bootstrap.js"></script>
    <script src="/dist/js/semanticjs.js"></script>

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
<div class="container">
    <div class="header_c">
        <div class="row">
            <div class="col-6 offset-6">
                <img src="/dist/js/image/card.PNG">
            </div>
        </div>
    </div>
    <div class="content_c">
        <div class="row" style="padding: 10px;">
            <div class="col align-self-center">
                <div class="ui left icon input">
                    <input type="text" placeholder="카드번호를 입력해주세요.">
                    <i class="credit card outline icon"></i>
                </div>
            </div>
        </div>
        <div class="row" style="padding: 10px;">
            <div class="col align-self-center">
                <div class="ui input">
                    <input type="text" placeholder="카드사">
                </div>
            </div>
        </div>
        <div class="row" style="padding: 10px;">
            <div class="col col-6 ui input">
                <input type="text" placeholder="MM/YY">
            </div>
            <div class="col col-6 ui input">
                <input type="text" placeholder="CVV">
            </div>
        </div>
    </div>
</div>
<div class="footer_c">
    <div class="row justify-content-xl-around">
        <button class="col-xl-1 ui orange button">결제완료</button>
    </div>
</div>
</body>
</html>