
// $(function () {
//   $(document).on("click", ".modal-open-btn", function () {//모달오픈버튼을 누르면
//     $($(this).attr("target")).css("display", "flex");
//   });
//   $(document).on("click", ".modal-close", function () {
//     $(this).parents(".modal-wrap").parent().css("display", "none");
//   });  
//   $(".sub-navi").prev().after("<span class='material-icons dropdown'>expand_more</span>");
// });

console.log("js연결확인")

$(function () {
  $(document).on("click", ".modal-open-btn", function () {//모달오픈버튼을 누르면
    $($(this).attr("target")).css("display", "flex");
    
    const deleteTargetNo = $(this).attr("value");
    console.log(deleteTargetNo);
    const toControllPath = $(".toControllPath").attr("value");
    console.log(toControllPath);

    if(deleteTargetNo!=null){//어디서 지울지 보내야한다면
      $(".deleteBtn").attr("href",toControllPath+deleteTargetNo);
      console.log("deleteTargetNo 들어있음");

    }else if(deleteTargetNo ==null){//회원탈퇴는 세션에서 꺼내쓸거라 deleteTargetNo가 필요없음
      $(".deleteBtn").attr("href",toControllPath);
      console.log("deleteTargetNo 비어있음");

    }
    

  });
  $(document).on("click", ".modal-close", function () {
    $(this).parents(".modal-wrap").parent().css("display", "none");
  });  

});



