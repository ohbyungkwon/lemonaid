webpackJsonp([3],{5:function(t,e,i){(function(t){window.onload=function(){var e,i,n=[],o=t(".hidden-text2").text(),a=t("meta[name='_csrf']").attr("content"),c=t("meta[name='_csrf_header']").attr("content");t("button[name='pre']").click(function(){1!=t(".hidden-text1").text()&&(window.location.href=Number(t(".hidden-text1").text())-1)}),t("button[name='next']").click(function(){var s,l,d=document.getElementsByClassName("radio_j");if("single"==o)l={choice_single_id:e,choice:i,extra_info:t("#extra_info").val()},s="/response/single/"+t(".hidden-text1").text();else if("multi"==o){for(var r=0;r<d.length;r++)d[r].checked&&n.push(Number(t("input[name='choice_"+r+"']").attr("id"))+1);console.log(t(".hidden-text1").text()+"번 문항"),console.log(n+"선택"),l={choice_multi_id:e,choice:n,extra_info:t("#extra_info").val()},console.log(e),s="/response/multi/"+t(".hidden-text1").text()}else if("write"==o){var h=t("#Systolic").val(),u=t("#Diastolic").val();l={write_id:1,text:h+";"+u},s="/response/write"}t.ajax({url:s,method:"POST",dataType:"json",contentType:"application/json; charset=UTF-8",data:JSON.stringify(l),success:function(e){30!=t(".hidden-text1").text()?(console.log("dont"),window.location.href=Number(t(".hidden-text1").text())+1):(console.log("access"),window.location.href="/temp")},fail:function(){alert("하나 이상 선택하세요.")},beforeSend:function(t){t.setRequestHeader(c,a)}})}),t("input[type=radio]").each(function(){var e=t(this).is(":checked"),i=t(this).attr("name");1==e&&t("input[name='"+i+"']").data("previous",t(this).val())}),t("input[type=radio]").click(function(){var n=t(this).data("previous"),o=t(this).is(":checked"),a=t(this).attr("name");e=t(this).val(),i=t(this).attr("id"),console.log(i),1==o&&n==t(this).val()?(t(this).prop("checked",!1),t("input[name='"+a+"']").data("previous",""),t("input[name="+e+"]").css("display","none")):1==o&&n!=t(this).val()&&(t("input[name='"+a+"']").data("previous",t(this).val()),t("input[name="+e+"]").css("display","block"))})}}).call(e,i(0))}},[5]);