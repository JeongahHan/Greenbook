const checkArr = [false,false,false];

//아이디 유효성 검사 & 중복체크
$("#memberId").on("change",function(){
	const memberId = $("#memberId").val();
    //정규표현식을 통한 유효성 검사
    const idReg = /^[a-zA-Z0-9]{4,8}$/;
    if(idReg.test(memberId)){
        //유효성이 만족되면 -> DB에서 중복체크(ajax)
        $.ajax({
            url : "/member/checkId",
            type : "post",
            data : {memberId : memberId},
            success : function(data){
                if(data == "0"){
                    $("#ajaxCheckId").text("사용가능합니다.");
                    $("#ajaxCheckId").css("color","blue");
                    $("#memberId").css("border","1px solid blue");
                    checkArr[0] = true;
                }else{
                    $("#ajaxCheckId").text("이미 사용중인 아이디입니다.");
                    $("#ajaxCheckId").css("color","red");
                    $("#memberId").css("border","1px solid red");
                    checkArr[0] = false;	
                }
            }
        });
    }else{
        $("#ajaxCheckId").text("아이디는 영어/숫자로 4~8글자입니다.");
        $("#ajaxCheckId").css("color","red");
        $(this).css("border","1px solid red");
        checkArr[0] = false;
    }
});

$("#memberPw").on("change",function(){
	if($("#memberPwRe") != ""){
    	pwDupCHECK();
    }
});

$("#memberPwRe").on("change",function(){	
	pwDupCHECK();
});

//비밀번호, 비밀번호 확인 일치
function pwDupCHECK(){
	const pwValue = $("#memberPw").val();
    const pwReValue = $("#memberPwRe").val();
    if(pwValue == pwReValue){
        $("#checkPw").text("비밀번호가 일치합니다.");
        $("#checkPw").css("color","blue");
        $("#memberPwRe").css("border","1px solid blue");
        checkArr[1] = true;
    }else{
        $("#checkPw").text("비밀번호가 일치하지 않습니다.");
        $("#checkPw").css("color","red");
        $("#memberPwRe").css("border","1px solid red");
        checkArr[1] = false;
    }
}

let authCode = null;
//이메일 유효성 검사 & 이메일 중복검사 & 이메일 인증코드 검사
$("#memberEmail").on("change", function(){
	const memberEmail = $("#memberEmail").val();
	const emailReg = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
	//유효성 충족
    if(emailReg.test(memberEmail)){
    	//중복 체크
    	$.ajax({
            url : "/member/checkEmail",
            type : "post",
            data : {memberEmail : memberEmail},
            success : function(data){
            	//중복되지 않을 때
                if(data == "0"){
			        $("#checkEmail").text("인증을 완료하여 주세요.");
			        $("#checkEmail").css("color","blue");
			        $("#memberEmail").css("border","1px solid blue");
			        $("#emailChkBtn").on("click",function(){
			        	$.ajax({
					        url : "/member/auth",
					        data : {memberEmail : memberEmail},
					        type : "post",
					        success : function(data1) {
					            authCode = data1;
					            $("#auth").slideDown();
					            authTime();
					        }
			    		});
			        });
                //중복될 때
                }else{
			        $("#checkEmail").text("중복된 메일 주소입니다.");
			        $("#checkEmail").css("color","red");
			        $("#memberEmail").css("border","1px solid red");
			        checkArr[2] = false;
                }
            }
        });
    //유효성 불충족
    }else{
        $("#checkEmail").text("메일 주소가 유효하지 않습니다.");
        $("#checkEmail").css("color","red");
        $("#memberEmail").css("border","1px solid red");
        checkArr[2] = false;
    }
});

//이메일 인증 타이머 시작
let intervalId = null;
function authTime() {
    $("#timeZone").html("<span id='min'>3</span> : <span id='sec'>00</span>");
    intervalId = window.setInterval(function(){
        const min = $("#min").text();
        const sec = $("#sec").text();
        if(sec == "00"){
            if(min == "0") {
                window.clearInterval(intervalId);
                authCode = null;
                $("#authMsg").text("인증 시간 만료");
                $("#authMsg").css("color", "red");
                checkArr[2] = false;
            }else{
                const newMin = Number(min) - 1;
                $("#min").text(newMin);
                $("#sec").text(59);						
            }
        }else{
            const newSec = Number(sec) - 1;
            if(newSec < 10) {
                $("#sec").text("0"+newSec);
            }else{
                $("#sec").text(newSec);						
            }
        }
    }, 1000);
}
//인증코드 인증하기
$("#authBtn").on("click",function(){
    if(authCode != null){
        const inputCode = $("#authCode").val();
        if(authCode == inputCode){
            $("#authMsg").text("인증완료");
            $("#authMsg").css("color","blue");
            window.clearInterval(intervalId);
            $("#timeZone").empty();
        	checkArr[2] = true;
        }else{
            $("#authMsg").text("메일코드를 확인하세요");
            $("#authMsg").css("color","red");
            checkArr[2] = false;
        }
    }
});

//전체 동의 체크박스
$("#allAgreement").click(function() {
    if($("#allAgreement").is(":checked")) $(".agreecheck").prop("checked", true);
    else $(".agreecheck").prop("checked", false);
});

//이용약관 모달 열기
$(".use").on("click", function(){
    $(".use-wrap").css("display","flex");
});

//이용약관 모달 닫기
$(".use-close").on("click", function(){
    $(".use-wrap").css("display","none");
});

//개인정보수집 모달 열기
$(".privacy").on("click", function(){
    $(".privacy-wrap").css("display","flex");
});

//개인정보수집 모달 닫기
$(".privacy-close").on("click", function(){
    $(".privacy-wrap").css("display","none");
});

//회원 가입 버튼 클릭 시 아이디 유효성, 중복체크, 비밀번호 확인, 체크박스 여부 모두 체크하기
$("button[type=submit]").on("click",function(event){
	const agreeCheck = $("#useAgreement").is(":checked") && $("#privacyAgreement").is(":checked");
	if(!agreeCheck){
		$("#msg").text("약관동의는 필수입니다.");
		$(".modal-wrap").css("display","flex");
		event.preventDefault();
	}
	
	if($("#memberEmail").val() == ""){
		$("#msg").text("이메일을 입력해주세요.");
		$(".modal-wrap").css("display","flex");
		event.preventDefault();
	}
	
	if($("#memberPhone").val() == ""){
		$("#msg").text("휴대폰번호를 입력해주세요.");
		$(".modal-wrap").css("display","flex");
		event.preventDefault();
	}
		
	if($("#memberName").val() == ""){
		$("#msg").text("이름을 입력해주세요.");
		$(".modal-wrap").css("display","flex");
		event.preventDefault();
	}
	
	if($("#memberPwRe").val() == ""){
		$("#msg").text("비밀번호를 한번 더 입력해주세요.");
		$(".modal-wrap").css("display","flex");
		event.preventDefault();
	}
	
	if($("#memberPw").val() == ""){
		$("#msg").text("비밀번호를 입력해주세요.");
		$(".modal-wrap").css("display","flex");
		event.preventDefault();
	}
	
	if($("#memberId").val() == ""){
		$("#msg").text("아이디를 입력해주세요.");
		$(".modal-wrap").css("display","flex");
		event.preventDefault();
	}
	
    const check = checkArr[0] && checkArr[1] && checkArr[2];
    if(!check){
        event.preventDefault();
    }
});

$("#close").on("click", function(){
	$(".modal-wrap").css("display","none");
});
