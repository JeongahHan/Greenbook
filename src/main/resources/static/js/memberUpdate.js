console.log("멤버업데이트 연결확인");

const checkArr = [false,false,false,false];

//비밀번호 유효성 검사

$("#memberPw").on("change",function(){
    //비밀번호 : 영어 소문자+대문자+숫자로 8~12글자
    const pwReg = /^[a-zA-Z0-9]{8,12}$/;
    const inputPw = $("#memberPw").val();
    const check = pwReg.test(inputPw);
    if($("#memberPw").val() != ""){
	   	if(check){
	        //정규표현식 만족한 경우
	        $("#checkPw").text("사용 가능한 비밀번호입니다.");
	        $("#checkPw").css("color","blue");
	        $(this).css("border","1px solid blue");
	        checkArr[1] = true;
	    }else{
	        //정규표현식 만족하지 못한 경우
	        $("#checkPw").text("비밀번호는 영어 대문자, 소문자, 숫자로 8~12글자입니다.");
	        $("#checkPw").css("color","red");
	        $(this).css("border","1px solid red");
	        checkArr[1] = false;
	    }
		if($("#checkPwRe").text() != ""){
	    	pwDupCHECK();
	    }
    }else{
    	$("#checkPw").text("");
    	$("#memberPw").css("border","1px solid #ccc");
    	$("#checkPwRe").text("");
    	$("#memberPwRe").css("border","1px solid #ccc");
    }
});

//새 비밀번호 확인 유효성 검사
$("#memberPwRe").on("change",function(){
    const pwValue = $("#memberPw").val();
    const pwReValue = $("#memberPwRe").val();
    if($("#memberPwRe").val() != ""){
	    if(pwValue == pwReValue){
	        $("#checkPwRe").text("비밀번호가 일치합니다.");
	        $("#checkPwRe").css("color","blue");
	        $("#memberPwRe").css("border","1px solid blue");
	        checkArr[2] = true;
	    }else{
	        $("#checkPwRe").text("비밀번호가 일치하지 않습니다.");
	        $("#checkPwRe").css("color","red");
	        $("#memberPwRe").css("border","1px solid red");
	        checkArr[2] = false;
	    }
    }else{
    	$("#checkPwRe").text("");
    	$("#memberPwRe").css("border","1px solid #ccc");
    }
});
