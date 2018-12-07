window.onload = function(){
    var radio_choice_id;
    var radio_choice_priority = null;

    var checkedRadioId=[];

    var questionType = $(".hidden-text2").text();

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var radio = $("input[type=radio]");

    var gender = null;

    $("#womanBtn").click(function () {
        gender = "WOMAN";
        $(this).css("background","orange");
        $("#manBtn").css("background","darkgray")
    });
    $("#manBtn").click(function () {
        gender = "MAN";
        $(this).css("background","orange");
        $("#womanBtn").css("background","darkgray")
    });

    $("#nextBtn").click(function () {
        var data = {
            "personalId" :  $("input[name='birth']").val(),
            "gender" : gender
        };
        var link = document.location.href;
        var disease;
        if(link.indexOf("%ED%83%88%EB%AA%A8") !== -1){
            disease = "탈모";
        }else disease="발기부전";

        $.ajax({
            url: '/api/TempUserSet?disease_name='+disease,
            method: 'POST',
            dataType: "json",
            contentType: "application/json; charset=UTF-8",
            data: JSON.stringify(data),
            success: function(data){
                alert("설문을 시작합니다.");
                if(data === "CONTINUE" && disease === "탈모"){
                    window.location.href="/question?disease_name=탈모&priority=1&isLogin=1";
                }else if(data === "CONTINUE" && disease === "발기부전"){
                    window.location.href="/question?disease_name=발기부전&priority=1&isLogin=1";
                }
            },
            error: function(data){
                console.log(data);
                if(data.responseJSON === "ERR_BIRTH")
                    alert("생년월일을 입력해주세요.");
                else if(data.responseJSON === "ERR_GENDER")
                    alert("성별을 골라주세요.");
                else if(data.responseJSON === "ERR_EXCEPT_GENDER" || data.responseJSON === "ERR_EXCEPT_AGE") {
                    alert("참여 대상자가 아닙니다.");
                    window.location.href="/WrongUser";
                }
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            }
        })
    });

    $("button[name='pre']").click(function(){
        var priority = $(".hidden-text1").text();
        if(priority !== 1) {//첫 페이지에서는 이동 x
            var num = Number(priority) - 1;
            window.location.href = "/question?disease_name=" + $(".card-title").text() + "&priority=" + num + "&isLogin=1";
        }else{
            alert("안내로 이동하시겠습니까?");
            window.location.href="/question?isLogin=0";
        }
    });//back btn

    $("button[name='next']").click(function () {
        var values = document.getElementsByClassName("radio_j");
        var urlTemp;
        var data;
        if(questionType === 'single'){
            data = {
                "choiceSingleId" : radio_choice_id,
                "choice" : radio_choice_priority,
                "extraInfo" : $("#extra_info").val()
            };

            urlTemp = "/response/single/" + $(".hidden-text1").text();
            if(radio_choice_priority == null){
                alert("해당 문진을 완료해주세요.");
                return false;
            }
        } else if(questionType === 'multi') {
            checkedRadioId.length = [];
            for (var i = 0; i < values.length; i++) {
                if (values[i].checked) {
                    checkedRadioId.push(Number($("input[name='choice_" + i + "']").attr("id")) + 1);//다중 선택
                }
            }//어떤 radio가 선택되었는지

            //보기 중 선택한 만큼의 id와 priority를 갖고 있어야한다.
            data = {
                "choiceMultiId" : radio_choice_id,
                "choice" : checkedRadioId,
                "extraInfo" : $("#extra_info").val()
            };

            if(data.choice.length === 0){
                alert("해당 문진을 완료해주세요.");
                return false;
            }
            urlTemp = "/response/multi/" + $(".hidden-text1").text();
        }else if(questionType === 'write') {
            var Systolic = $("#Systolic").val();
            var Diastolic = $("#Diastolic").val();

            if (Systolic === '' || Diastolic === '') {
                alert("혈압을 입력해주세요.");
                return false;
            } else if (Systolic !== $("#ReSystolic").val() || Diastolic !== $("#ReDiastolic")) {
                alert("혈압을 확인해주세요.");
                return false;
            }

            data = {
                "writeId" : 1,
                "text" : Systolic +";"+Diastolic
            };

            urlTemp = "/response/write"
        }

        $.ajax({
            url: urlTemp,
            method:'POST',
            dataType: "json",
            contentType: "application/json; charset=UTF-8",
            data: JSON.stringify(data),
            success: function(){
                var diseaseName = $(".card-title").text();
                var priority = $(".hidden-text1").text();
                var num;

                if(diseaseName === "발기부전"){
                    if(priority !== "30") {//마지막 페이지에서는 이동 x
                        num = Number(priority) + 1;
                        window.location.href = "/question?disease_name="+diseaseName+"&priority="+ num + "&isLogin=1";
                    }else{
                        window.location.href="/temp";
                    }
                }else {
                    if(priority !== "2") {//마지막 페이지에서는 이동 x
                        num = Number(priority) + 1;
                        window.location.href = "/question?disease_name="+diseaseName+"&priority="+ num + "&isLogin=1";
                    }else{
                        window.location.href="/temp";
                    }
                }
            },
            fail: function () {
                alert("하나 이상 선택하세요.");
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            }
        })//db 저장 후 페이지 이동
    });//next btn


    radio.each(function(){
        var chk = $(this).is(":checked");
        var name = $(this).attr('name');
        if(chk === true) {
            $("input[name='" + name + "']").data("previous", $(this).val());
        }
    });//init

    radio.click(function(){
        var pre = $(this).data("previous");
        var chk = $(this).is(":checked");
        var name = $(this).attr('name');

        radio_choice_id = $(this).val();
        radio_choice_priority = $(this).attr('id');//For ajax

        if(chk === true && pre === $(this).val()){
            $(this).prop('checked',false);
            checkedRadioId.pop();
            radio_choice_priority = null;
            $("input[name='"+name+"']").data("previous",'');
            $("input[name="+ radio_choice_id +"]").css("display","none");
        }else if(chk === true && pre !== $(this).val()){
            $("input[name='"+name+"']").data("previous",$(this).val());
            $("input[name="+ radio_choice_id +"]").css("display","block");
        }

        if($("label[name='"+ name +"']").text() === "해당사항 없음"){
            for(var i = 1; i < radio_choice_priority; i++){
                var temp = "choice_"+i;
                $("input[name='"+temp+"']").prop('checked',false);
            }
        }
    });//radio choice

    $("#goCashView").click(function () {
        window.location.href="/cash";
    });

    $("#goEndView").click(function () {
        window.location.href="/end";
    });
};
