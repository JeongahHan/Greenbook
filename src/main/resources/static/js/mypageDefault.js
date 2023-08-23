
// $(function () {
//   $(document).on("click", ".modal-open-btn", function () {//모달오픈버튼을 누르면
//     $($(this).attr("target")).css("display", "flex");
//   });
//   $(document).on("click", ".modal-close", function () {
//     $(this).parents(".modal-wrap").parent().css("display", "none");
//   });  
//   $(".sub-navi").prev().after("<span class='material-icons dropdown'>expand_more</span>");
// });


$(function () {
  $(document).on("click", ".modal-open-btn", function () {//모달오픈버튼을 누르면
    $($(this).attr("target")).css("display", "flex");
    const productBoardNo = $(this).attr("value");
    console.log(productBoardNo);

    $(".deleteBtn").attr("href","/mypage/mySellBookDelete?productBoardNo="+productBoardNo);
    

  });
  $(document).on("click", ".modal-close", function () {
    $(this).parents(".modal-wrap").parent().css("display", "none");
  });  
  $(".sub-navi").prev().after("<span class='material-icons dropdown'>expand_more</span>");
});



