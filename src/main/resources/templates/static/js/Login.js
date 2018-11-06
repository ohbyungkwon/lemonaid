window.onload = function () {
    $("button[name='button']").click(function () {
        console.log("click btn");
        $("#loginForm").submit();
    })
}