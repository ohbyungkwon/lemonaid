<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Question</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" type="text/css" href="/dist/js/question.css">
        <link rel="stylesheet" type="text/css" href="/dist/js/semantic.css">
        <link rel="stylesheet" type="text/css" href="/dist/js/bootstrap.css">
        <script src="/dist/js/vendor.js"></script>
        <script src="/dist/js/bootstrap.js"></script>
        <script src="/dist/js/survey.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="header_c">
                <#--<div class="row justify-content-xl-center">-->
                ${question.getPriority()} / ${total_question}
                <#--</div>-->
            </div>
            <div class="content_c">
                <div class="row justify-content-xl-center">
                <#--<div class="col col-lg-2">-->
                <#--</div>-->
                    <div class="card col align-self-center" style="width: 30rem;">
                        <div class="card-body">
                            <h5 class="card-title">[ ${disease_name} ]</h5>
                            <a>${question.priority}.</a> <a class="card-text"> ${question.getContent()}</a>
                            <p class="hidden-text1" style="display: none">${question.priority}</p>
                            <p class="hidden-text2" style="display: none">${question.type}</p>
                            <hr class="line_color">
                            <div class="ui form">
                                <#if isState == 0>
                                    <#list choices as choice>
                                        <div class="ui radio checkbox">
                                            <input id="${choice.getPriority()}" class="radio_j" type="radio" name="choice" value="${choice.getId()}">
                                            <label>${choice.getContent()}</label>
                                            <#if choice.is_need_extra() == true>
                                                <input id="extra_info" name=${choice.getId()} type="text" placeholder="더 자세히 설명해 주세요." style="display: none">
                                            </#if>
                                        <#--널이 아닐때만 라디오 클릭시 텍스트 출력-->
                                        </div>
                                        <hr class="line_color">
                                    </#list>
                                <#elseIf isState == 1>
                                    <#list choices as choice>
                                        <div class="ui radio checkbox">
                                            <input id="${choice.getPriority()}" class="radio_j" type="radio" name="choice_${choice.getPriority()}" value="${choice.getId()}">
                                            <label>${choice.getContent()}</label>
                                            <#if choice.is_need_extra() == true>
                                                    <input name=${choice.getId()} type="text" placeholder="더 자세히 설명해 주세요." style="display: none">
                                            </#if>
                                        </div>
                                        <hr class="line_color">
                                    </#list>
                                <#elseIf isState == 2>
                                    <div class="row justify-content-center">
                                        <div class="ui input focus">
                                            <input id="Systolic" type="text" placeholder="Systolic">
                                        </div>
                                        <div class="ui input focus">
                                            <input id="Diastolic" type="text" placeholder="Diastolic">
                                        </div>
                                    </div>
                                </#if>
                            </div>
                        </div>
                    </div>
                <#--<div class="col col-lg-2">-->
                <#--</div>-->
                </div>
            </div>
        <#--<div class="footer_c">-->
        <#--<div class="row justify-content-xl-around">-->
        <#--<button id="1" name="pre" class= "col-xl-3 ui orange button footer_left"> 이전 </button>-->
        <#--<button id="2" name="next" class= "col-xl-3 ui orange button footer_right"> 다음 </button>-->
        <#--</div>-->
        <#--</div>-->
        </div>
        <div class="footer_c">
            <div class="row justify-content-xl-around">
                <button id="1" name="pre" class= "col-xl-1 ui orange button footer_left"> 이전 </button>
                <button id="2" name="next" class= "col-xl-1 ui orange button footer_right"> 다음 </button>
            </div>
        </div>
    </body>

</html>
