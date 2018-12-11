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
                        <form id="SignInForm">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="row" style="padding-right: 30px;" >
                                <div class="col-7">
                                    <input name="username" type="text" class="form-control" placeholder="Email">
                                </div>
                                <div class="col-5"">
                                    <button name="checkDuplicate"class="ui yellow button">확인</button>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm">
                                    <input name="password" type="password" class="form-control" placeholder="Password">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm">
                                    <p id="passwordComment" style="color:red;"></p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm">
                                   <input name="password_check" type="password" class="form-control" placeholder="Check Password">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm">
                                    <p id="duplicateComment"style="color:red;"></p>
                                </div>
                            </div>
                        </form>
                    </div>
                    <button id="continueBtn" class="ui orange button">계속</button>
                </div>
            </div>
        </div>
    </body>
</html>