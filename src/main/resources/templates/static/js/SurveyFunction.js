window.onload = function(){
    var radio_choice_id;
    var radio_choice_priority;

    var checkedRadioId=[];
    var checkedRadioPriority=[];

    var questionType = $(".hidden-text2").text();

    $("button[name='pre']").click(function(){
        if($(".hidden-text1").text() != 1)//첫 페이지에서는 이동 x
            window.location.href=Number($(".hidden-text1").text())-1;
    });//back btn

    $("button[name='next']").click(function () {
        var values = document.getElementsByClassName("radio_j");
        var urlTemp;

        if(questionType == 'single'){
            var data = {
                "choice_single_id" : radio_choice_id,
                "choice" : radio_choice_priority,
                "extra_info" : $("#extra_info").val()
            };
            urlTemp = "/response/single/" + $(".hidden-text1").text();
        }else if(questionType == 'multi'){
            for(var i=0; i<values.length; i++){
                if(values[i].checked){
                    checkedRadioId.push(Number($("input[name='choice_"+i+"']").attr("id"))+1);//다중 선택
                    // checkedRadioPriority.push(Number($("input[name='choice_"+i+"']").val()));
                };
            }//어떤 radio가 선택되었는지

            console.log($(".hidden-text1").text() + "번 문항");
            console.log(checkedRadioId + "선택");
            console.log(checkedRadioId + "선택");
            //보기 중 선택한 만큼의 id와 priority를 갖고 있어야한다.
            var data = {
                "choice_multi_id" : radio_choice_id,
                "choice" : checkedRadioId,
                "extra_info" : $("#extra_info").val()
            };
            console.log(radio_choice_id);
            urlTemp = "/response/multi/" + $(".hidden-text1").text();
        }else if(questionType == 'write'){

        }else{

        }

        $.ajax({
            url: urlTemp,
            method:'POST',
            dataType: "json",
            contentType: "application/json; charset=UTF-8",
            data: JSON.stringify(data),
            success: function(data){
                if($(".hidden-text1").text() != 30)//마지막 페이지에서는 이동 x
                    console.log(data.data);
                    window.location.href=Number($(".hidden-text1").text())+1;
            },
            fail: function () {
                alert("하나 이상 선택하세요.");
            }
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
        console.log(radio_choice_priority);

        if(chk == true && pre == $(this).val()){
            $(this).prop('checked',false);
            $("input[name='"+name+"']").data("previous",'');
            $("input[name="+ radio_choice_id +"]").css("display","none");
        }else if(chk == true && pre != $(this).val()){
                $("input[name='"+name+"']").data("previous",$(this).val());
                $("input[name="+ radio_choice_id +"]").css("display","block");
        }
    });//radio choice
}
