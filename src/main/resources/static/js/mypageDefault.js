
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
    
    const deleteTargetNo = $(this).attr("value");
    console.log(deleteTargetNo);
    const toControllPath = $(".toControllPath").attr("value");
    //console.log(toControllPath);


    $(".deleteBtn").attr("href",toControllPath+deleteTargetNo);
    

  });
  $(document).on("click", ".modal-close", function () {
    $(this).parents(".modal-wrap").parent().css("display", "none");
  });  
  $(".sub-navi").prev().after("<span class='material-icons dropdown'>expand_more</span>");
});



