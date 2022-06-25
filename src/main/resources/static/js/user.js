let index = {
    init: function() {
        $("#btn-save").on("click", ()=> {
            this.save();
        });
        $("#btn-login").on("click", ()=> {
            this.login();
        });
    },
    save: function() {
        let data = {
            userName : $("#userName").val(),
            password : $("#password").val(),
            email : $("#email").val()
        };

//        console.log(data);
        $.ajax({
            type: "POST",
            url: "/api/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8", // MIME TYPE
            dataType: "json" // 응답이 json이라고 알려주면 json문자열을 자바스크립트 오브벡트로 변환하여 다음 함수의 인자로 전달
        // 성공
        }).done(function(resp) {
            alert("회원가입이 완료되었습니다.");
            location.href = "/"
        // 실해
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
    login: function() {
        let data = {
            userName : $("#userName").val(),
            password : $("#password").val(),
        };

//        console.log(data);
        $.ajax({
            type: "POST",
            url: "/api/user/login",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8", // MIME TYPE
            dataType: "json" // 응답이 json이라고 알려주면 json문자열을 자바스크립트 오브벡트로 변환하여 다음 함수의 인자로 전달
        // 성공
        }).done(function(resp) {
            alert("로그인이 완료되었습니다.");
            location.href = "/"
        // 실해
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    }
}

index.init();
