$(function () {
  $(document).on("click", ".modal-open-btn", function () {
    $($(this).attr("target")).css("display", "flex");
  });
  $(document).on("click", ".modal-close", function () {
    $(this).parents(".modal-wrap").parent().css("display", "none");
  });  
});
function alertFunc(icon){
	swal({
        title: '제목',
        text: '내용내용내용',
        icon: icon
      });
      //이후 페이지 이동 시 추가
      /*
      .then(function(){
    	 window.location = "${loc}"; 
      });
      */
}