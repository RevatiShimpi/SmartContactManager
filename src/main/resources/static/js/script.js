console.log("This is script file")

const toggleSidebar=()=>{
    if($('.sidebar').is(":visible")){
        $(".sidebar").css("display","none");
        $(".content").css("margin-left","0%")
    }
    else{
        $(".sidebar").css("display","block");
        $(".content").css("margin-left","20%")
    }
}