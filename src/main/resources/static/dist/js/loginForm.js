webpackJsonp([7],{9:function(e,n,t){(function(e){window.onload=function(){var n,t,a,o,s,r,c=e("meta[name='_csrf']").attr("content"),i=e("meta[name='_csrf_header']").attr("content"),u="",p=!1,l=!1;e("button[name='button']").click(function(){e("#loginForm").submit()}),e("button[name='checkDuplicate']").click(function(){return n=e("input[name='username']").val(),o="POST",s="/api/checkEmail",r={email:n},e.ajax({url:s,method:o,dataType:"text",contentType:"application/json; charset=UTF-8",data:JSON.stringify(r),success:function(){l=!0,alert("사용 가능한 아이디입니다.")},error:function(){l=!1,alert("사용 불가한 아이디입니다.")},beforeSend:function(e){e.setRequestHeader(i,c)}}),!1}),e("input[name='password']").keyup(function(){t=e("input[name='password']").val(),o="POST",s="/api/checkPwd",r={password:t},e.ajax({url:s,method:o,dataType:"json",contentType:"application/json; charset=UTF-8",data:JSON.stringify(r),success:function(){e("#passwordComment").text("양식과 일치합니다.")},error:function(n){console.log(n),"SHORT_PASSWORD"===n.responseJSON?e("#passwordComment").text("6자 이상 입력해주세요."):"INCLUDE_SPACE_PASSWORD"===n.responseJSON&&e("#passwordComment").text("공백은 불가합니다.")},beforeSend:function(e){e.setRequestHeader(i,c)}})}),e("input[name='password_check']").keyup(function(){t=e("input[name='password']").val(),a=e("input[name='password_check']").val(),o="POST",s="/api/checkDuplicate",r={password:t,checkDuplicate:a},e.ajax({url:s,method:o,dataType:"text",contentType:"application/json; charset=UTF-8",data:JSON.stringify(r),success:function(){e("#duplicateComment").text("비밀번호가 같습니다.")},error:function(){e("#duplicateComment").text("비밀번호가 다릅니다.")},beforeSend:function(e){e.setRequestHeader(i,c)}})}),e("#continueBtn").click(function(){n=e("input[name='username']").val(),t=e("input[name='password']").val(),a=e("input[name='password_check']").val(),r={email:n,password:t,checkDuplicate:a,emailCheck:l},e.ajax({url:"/api/redirectNext",method:"POST",dataType:"json",contentType:"application/json; charset=UTF-8",data:JSON.stringify(r),success:function(){!0===l&&(alert("다음으로 이동합니다."),window.location.href="/SignInSpec")},error:function(e){"INCLUDE_SPACE_PASSWORD"===e.responseJSON?alert("공백은 불가합니다."):"SHORT_PASSWORD"===e.responseJSON?alert("6자 이상 입력해주세요."):"CHECK_DUPLICATE_EMAIL"===e.responseJSON?alert("이메일 중복 확인해주세요."):"CHECK_PASSWORD"===e.responseJSON&&alert("비밀번호를 다시한번 확인해주세요.")},beforeSend:function(e){e.setRequestHeader(i,c)}})});var d=null;e("#womanBtn").click(function(){d="WOMAN",e(this).css("background","orange"),e("#manBtn").css("background","darkgray")}),e("#manBtn").click(function(){d="MAN",e(this).css("background","orange"),e("#womanBtn").css("background","darkgray")}),e("#telCompany").change(function(){u=e(":selected").text()}),e("#auth").click(function(){var n=e("input[name='tel']").val().trim();""!==n&&""!==u?e.ajax({url:"/api/authRandom",method:"GET",success:function(n){e("input[name='authNum']").val(Math.floor(n.num)),p=!0},beforeSend:function(e){e.setRequestHeader(i,c)}}):""===n?alert("연락처를 입력해주세요."):""===u&&alert("통신사를 선택해주세요.")}),e("#SignedBtn").click(function(){r={name:e("input[name='name']").val(),personalId:e("input[name='front']").val()+e("input[name='back']").val(),gender:d,tel:e("input[name='tel']").val(),addr:e("input[name='addr']").val(),checkAgree:e("input[name='agree']").is(":checked"),auth:p},e.ajax({url:"/api/done",method:"POST",dataType:"json",contentType:"application/json; charset=UTF-8",data:JSON.stringify(r),success:function(){alert("가입 완료"),window.location.href="/login"},error:function(e){"EMPTY_NAME"===e.responseJSON?alert("이름을 입력하세요."):"EMPTY_PERSONAL_ID"===e.responseJSON?alert("주민등록번호를 입력하세요."):"EMPTY_GENDER"===e.responseJSON?alert("성별을 입력하세요."):"EMPTY_TEL"===e.responseJSON?alert("핸드폰번호를 입력하세요."):"EMPTY_ADDR"===e.responseJSON?alert("주소를 입력하세요."):"EMPTY_AGREE"===e.responseJSON?alert("동의 여부를 체크해주세요."):"REG_AUTH"===e.responseJSON?alert("인증을 진행해주세요."):"REG_TEL"===e.responseJSON?alert("휴대폰번호 형식이 잘못되었습니다."):"REG_NAME"===e.responseJSON?alert("이름 형식이 잘못되었습니다."):"REG_PERSONAL_ID"===e.responseJSON?alert("주민등록번호 형식이 잘못되었습니다."):"FAIL"===e.responseJSON&&alert("가입 실패")},beforeSend:function(e){e.setRequestHeader(i,c)}})}),e("input[name='front']").keyup(function(){e(this).val().length>this.maxLength&&e(this).val(e(this).val().slice(0,this.maxLength))})}}).call(n,t(0))}},[9]);