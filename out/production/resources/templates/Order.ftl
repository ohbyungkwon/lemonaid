<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="/dist/js/orderTemp.css">
    <link rel="stylesheet" type="text/css" href="/dist/js/semantic.css">
    <link rel="stylesheet" type="text/css" href="/dist/js/bootstrap.css">
    <script src="/dist/js/vendor.js"></script>
    <script src="/dist/js/survey.js"></script>
    <script src="/dist/js/bootstrap.js"></script>
    <script src="/dist/js/semanticjs.js"></script>

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
    <div class="container">
        <div class="header_c">
            <p class="subject" style="margin-top: 10px;">주문내역</p>
            <hr class="line_color">
        </div>
        <div class="content_c">
            <div class="row" style="width: 20rem;">
                <div class="col align-self-center">
                    <div class="card" style="width: 20rem;">
                        <ul class="list-group list-group-flush">
                            <div class="row justify-content-between">
                                <div class="col-7">
                                    <li class="list-group-item">발기부전제 처방</li>
                                </div>
                                <div class="col-5">
                                    <li class="list-group-item">3000원</li>
                                </div>
                            </div>
                            <hr class="line_color">
                            <div class="row justify-content-between">
                                <div class="col-7">
                                    <li class="list-group-item"><strong>TOTAL</strong></li>
                                </div>
                                <div class="col-5">
                                    <li class="list-group-item" style="color: orange">3000원</li>
                                </div>
                            </div>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="ui orange message">만약 처방전을 받지 못하시면 100% 환불해 드립니다.</div>
        </div>
        <hr>
    </div>
    <div class="footer_c">
        <div class="row justify-content-xl-around">
            <button id="goCashView" class="col-xl-1 ui orange button">결제하기</button>
        </div>
    </div>
</body>
</html>