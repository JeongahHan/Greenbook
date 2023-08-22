
$(function () {
  $(document).on("click", ".modal-open-btn", function () {
    $($(this).attr("target")).css("display", "flex");
  });
  $(document).on("click", ".modal-close", function () {
    $(this).parents(".modal-wrap").parent().css("display", "none");
  });  
  $(".sub-navi").prev().after("<span class='material-icons dropdown'>expand_more</span>");
});

