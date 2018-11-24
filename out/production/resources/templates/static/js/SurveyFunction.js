window.onload = function(){
    var radio_choice_id;
    var radio_choice_priority = null;

    var checkedRadioId=[];
    var checkedRadioPriority=[];

    var questionType = $(".hidden-text2").text();

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    var gender = null;
    console.log(gender);

    // if($(".hidden-text1").text() == 1) {//첫 페이지에서는 이동 x
    //     $("button[name='pre']").css("display","none");
    // }else{
    //     $("button[name='pre']").css("display","block");
    // }

    $("#womanBtn").click(function () {
        gender = "WOMAN";
        console.log("woman");
        $(this).css("background","orange");
        $("#manBtn").css("background","darkgray")
    })
    $("#manBtn").click(function () {
        gender = "MAN";
        console.log("man");
        $(this).css("background","orange");
        $("#womanBtn").css("background","darkgray")
    })

    $("#nextBtn").click(function () {
        var data = {
            "id" : $("#userID").val(),
            "personal_id" :  $("input[name='birth']").val(),
            "gender" : gender
        };

        console.log(data);

        $.ajax({
            url: '/TempUserSet',
            method: 'POST',
            dataType: "json",
            contentType: "application/json; charset=UTF-8",
            data: JSON.stringify(data),
            success: function(data){
                var link = document.location.href;
                alert(data.comment);
                console.log(link);
                if(data.comment == "설문을 시작합니다" && link.indexOf("%ED%83%88%EB%AA%A8") != -1){
                    window.location.href="/question?disease_name=탈모&priority=1&isLogin=1";
                }else if(data.comment == "설문을 시작합니다" && link.indexOf("%EB%B0%9C%EA%B8%B0%EB%B6%80%EC%A0%84") != -1){
                    window.location.href="/question?disease_name=발기부전&priority=1&isLogin=1";
                }
                else if(data.comment == "남성만 참여가능합니다"){
                    window.location.href="/WrongUser";
                }
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
        })
    })

    $("button[name='pre']").click(function(){
        if($(".hidden-text1").text() != 1) {//첫 페이지에서는 이동 x
            var num = Number($(".hidden-text1").text()) - 1;
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
        if(questionType == 'single'){
            data = {
                "choice_single_id" : radio_choice_id,
                "choice" : radio_choice_priority,
                "extra_info" : $("#extra_info").val()
            };
            urlTemp = "/response/single/" + $(".hidden-text1").text();
            if(data.choice == null){ return false;}
        }else if(questionType == 'multi'){
            for(var i=0; i<values.length; i++){
                if(values[i].checked){
                    checkedRadioId.push(Number($("input[name='choice_"+i+"']").attr("id"))+1);//다중 선택
                    // checkedRadioPriority.push(Number($("input[name='choice_"+i+"']").val()));
                };
            }//어떤 radio가 선택되었는지

            console.log($(".hidden-text1").text() + "번 문항");
            console.log(checkedRadioId + "선택");

            //보기 중 선택한 만큼의 id와 priority를 갖고 있어야한다.
            data = {
                "choice_multi_id" : radio_choice_id,
                "choice" : checkedRadioId,
                "extra_info" : $("#extra_info").val()
            };

            if(data.choice == null){ return false;}

            console.log(radio_choice_id);
            urlTemp = "/response/multi/" + $(".hidden-text1").text();
        }else if(questionType == 'write'){
            var Systolic = $("#Systolic").val();
            var Diastolic = $("#Diastolic").val();
            console.log(Systolic);
            console.log(Diastolic);
            console.log($("#ReSystolic").val());
            console.log($("#ReDiastolic").val());
            //
            // if($("input[name='ReSystolic']").val() != Systolic){
            //     alert("혈압을 정확히 입력해주세요.");
            //     return false;
            // }
            if(Systolic === '' || Diastolic === ''){
                alert("혈압을 입력해주세요.");
                return false;
            }

            data = {
                "write_id" : 1,
                "text" : Systolic +";"+Diastolic
            };

            urlTemp = "/response/write"
        }else{

        }

        $.ajax({
            url: urlTemp,
            method:'POST',
            dataType: "json",
            contentType: "application/json; charset=UTF-8",
            data: JSON.stringify(data),
            success: function(data){
                if($(".hidden-text1").text() != 30) {//마지막 페이지에서는 이동 x
                    console.log("dont");
                    var num = Number($(".hidden-text1").text()) + 1;
                    window.location.href = "/question?disease_name="+$(".card-title").text()+"&priority="+ num + "&isLogin=1";
                }else{
                    console.log("access");
                    window.location.href="/temp";
                }
            },
            fail: function () {
                alert("하나 이상 선택하세요.");
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
        })//db 저장 후 페이지 이동

        // if($(".hidden-text").text() != 30)//마지막 페이지에서는 이동 x
        //     window.location.href=Number($(".hidden-text").text())+1;

    })//next btn


    $("input[type=radio]").each(function(){
        var chk = $(this).is(":checked");
        var name = $(this).attr('name');
        if(chk == true) $("input[name='"+name+"']").data("previous",$(this).val());
    });//init

    $("input[type=radio]").click(function(){
        var pre = $(this).data("previous");
        var chk = $(this).is(":checked");
        var name = $(this).attr('name');

        radio_choice_id = $(this).val();
        radio_choice_priority = $(this).attr('id');//For ajax
        // console.log(radio_choice_priority);

        console.log(name);
        console.log($("label[name='"+ name +"']").text());
        if(chk == true && pre == $(this).val()){
            $(this).prop('checked',false);
            $("input[name='"+name+"']").data("previous",'');
            $("input[name="+ radio_choice_id +"]").css("display","none");
        }else if(chk == true && pre != $(this).val()){
            $("input[name='"+name+"']").data("previous",$(this).val());
            $("input[name="+ radio_choice_id +"]").css("display","block");
        }
        if($("label[name='"+ name +"']").text() == "해당사항 없음"){
            for(var i = 1; i < radio_choice_priority; i++){
                var temp = "choice_"+i;
                $("input[name='"+temp+"']").prop('checked',false);
            }
        }
    });//radio choice

    $("#goCashView").click(function () {
        window.location.href="/cash";
    })

    $("#goEndView").click(function () {
        window.location.href="/end";
    })
}
