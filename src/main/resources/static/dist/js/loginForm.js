webpackJsonp([6],{8:function(n,e,t){(function(n){window.onload=function(){var e,t,a,c,o,s,i=n("meta[name='_csrf']").attr("content"),u=n("meta[name='_csrf_header']").attr("content"),l="",r=!1;n("button[name='button']").click(function(){console.log("click btn"),n("#loginForm").submit()}),n("button[name='checkDuplicate']").click(function(){return e=n("input[name='username']").val(),c="POST",o="/checkEmail",s={email:e},n.ajax({url:o,method:c,dataType:"json",contentType:"application/json; charset=UTF-8",data:JSON.stringify(s),success:function(n){alert(n.comment)},beforeSend:function(n){n.setRequestHeader(u,i)}}),!1}),n("input[name='password']").keyup(function(){t=n("input[name='password']").val(),c="POST",o="/checkPwd",s={password:t},n.ajax({url:o,method:c,dataType:"json",contentType:"application/json; charset=UTF-8",data:JSON.stringify(s),success:function(e){n("#passwordComment").text(e.comment)},beforeSend:function(n){n.setRequestHeader(u,i)}})}),n("input[name='password_check']").keyup(function(){t=n("input[name='password']").val(),a=n("input[name='password_check']").val(),c="POST",o="/checkDuplicate",s={password:t,checkDuplicate:a},n.ajax({url:o,method:c,dataType:"json",contentType:"application/json; charset=UTF-8",data:JSON.stringify(s),success:function(e){n("#duplicateComment").text(e.comment)},beforeSend:function(n){n.setRequestHeader(u,i)}})}),n("#continueBtn").click(function(){e=n("input[name='username']").val(),t=n("input[name='password']").val(),a=n("input[name='password_check']").val(),s={email:e,password:t,checkDuplicate:a},console.log(s),n.ajax({url:"/redirectNext",method:"POST",dataType:"json",contentType:"application/json; charset=UTF-8",data:JSON.stringify(s),success:function(n){"다음으로 이동합니다."==n.comment?(alert(n.comment),window.location.href="/SignInSpec"):alert(n.comment)},beforeSend:function(n){n.setRequestHeader(u,i)}})});var m="-1";n("#womanBtn").click(function(){m="0",console.log("woman"),n(this).css("background","orange"),n("#manBtn").css("background","darkgray")}),n("#manBtn").click(function(){m="1",console.log("man"),n(this).css("background","orange"),n("#womanBtn").css("background","darkgray")}),n("#telCompany").change(function(){l=n(":selected").text()}),n("#auth").click(function(){""!=n("input[name='tel']").val()&&""!=l?n.ajax({url:"/authRandom",method:"GET",success:function(e){n("input[name='authNum']").val(Math.floor(e.num)),r=!0,console.log(r)},beforeSend:function(n){n.setRequestHeader(u,i)}}):""==n("input[name='tel']").val()?alert("연락처를 입력해주세요."):""==l&&alert("통신사를 선택해주세요.")}),n("#SignedBtn").click(function(){s={name:n("input[name='name']").val(),personalId:n("input[name='front']").val()+n("input[name='back']").val(),gender:m,tel:n("input[name='tel']").val(),addr:n("input[name='addr']").val(),checkAgree:n("input[name='agree']").is(":checked"),auth:r},console.log(s.auth),n.ajax({url:"/done",method:"POST",dataType:"json",contentType:"application/json; charset=UTF-8",data:JSON.stringify(s),success:function(n){"가입 완료"==n.comment?(alert(n.comment),window.location.href="/login"):alert(n.comment)},beforeSend:function(n){n.setRequestHeader(u,i)}})}),n("input[name='front']").keyup(function(){n(this).val().length>this.maxLength&&(n(this).val(n(this).val().slice(0,this.maxLength)),console.log("access"))})}}).call(e,t(0))}},[8]);