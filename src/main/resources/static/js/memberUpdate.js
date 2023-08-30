
const checkArr = [false,false];

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
	        checkArr[0] = true;

	    }else{
	        //정규표현식 만족하지 못한 경우
	        $("#checkPw").text("비밀번호는 영어 대문자, 소문자, 숫자로 8~12글자입니다.");
	        $("#checkPw").css("color","red");
	        $(this).css("border","1px solid red");
	        checkArr[0] = false;
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
    pwDupCHECK();
});
function pwDupCHECK(){
	const pwValue = $("#memberPw").val();
    const pwReValue = $("#memberPwRe").val();
    if($("#memberPwRe").val() != ""){
	    if(pwValue == pwReValue){
	        $("#checkPwRe").text("비밀번호가 일치합니다.");
	        $("#checkPwRe").css("color","blue");
	        $("#memberPwRe").css("border","1px solid blue");
	        checkArr[1] = true;
	    }else{
	        $("#checkPwRe").text("비밀번호가 일치하지 않습니다.");
	        $("#checkPwRe").css("color","red");
	        $("#memberPwRe").css("border","1px solid red");
	        checkArr[1] = false;


	    }
    }else{
    	$("#checkPwRe").text("");
    	$("#memberPwRe").css("border","1px solid #ccc");
    }

}

$(".update-btn").on("click",function(event){
    
	
    if($("#memberPw").val() ==""){
		alert("변경할 비밀번호를 입력해주세요");
        event.preventDefault();//클릭 작동 무효화
	}

	else if(!checkArr[1]){//새 비밀번호 확인이 일치하지 않을 경우
        //console.log("비밀번호 일치하지 않음");
        alert("비밀번호가 일치하지 않습니다.");
        event.preventDefault();//클릭 작동 무효화
    }

    else if (!checkArr[0]){// 새 비밀번호 정규표현식 만족하지 않을경우
        alert("사용할 수 없는 비밀번호입니다.");
        event.preventDefault();//클릭 작동 무효화


    }

});

