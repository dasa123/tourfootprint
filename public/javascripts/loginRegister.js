var mouse_is_inside = false;

$(document).ready(function() {
    $(".login_btn").click(function() {
        var loginBox = $("#login_box");
        var registerBox = $("#register_box");
        if (loginBox.is(":visible"))
            loginBox.fadeOut("300");
        else
            loginBox.fadeIn("300");
        	if (registerBox.is(":visible"))
            	registerBox.fadeOut("300");
        return false;
        
    });
    
    $("#login_box").hover(function(){ 
        mouse_is_inside=true; 
    }, function(){ 
        mouse_is_inside=false; 
    });

    $("body").click(function(){
        if(! mouse_is_inside) $("#login_box").fadeOut("300");
    });
    
    
    $(".register_btn").click(function() {
        var loginBox = $("#login_box");
        var registerBox = $("#register_box");
        if (registerBox.is(":visible"))
            registerBox.fadeOut("300");
        else
            registerBox.fadeIn("300");
        	if (loginBox.is(":visible"))
        		loginBox.fadeOut("300");
        return false;
    });
    
    $("#register_box").hover(function(){ 
        mouse_is_inside=true; 
    }, function(){ 
        mouse_is_inside=false; 
    });

    $("body").click(function(){
        if(! mouse_is_inside) $("#register_box").fadeOut("300");
    });
});