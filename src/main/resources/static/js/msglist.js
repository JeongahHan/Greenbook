
const tabs = $(".tabs li");
tabs.on("click",function(){
    tabs.removeClass("active-tab");
    $(this).addClass("active-tab");
    const msg = $(".msg-wrap");
    msg.show();
});

$(function(){
    tabs.eq(0).click();
});