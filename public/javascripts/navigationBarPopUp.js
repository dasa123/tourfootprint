var mouse_is_inside = false;

$(document).ready(function() {
	
	/*
	 * Login Popup
	 */
    $(".login_button").click(function() {
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
    
	/*
	 * Register Popup
	 */
    $(".register_button").click(function() {
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
    
	/*
	 * Profile Popup
	 */
    $(".profile_button").click(function() {
        var profileBox = $("#profile_box");
        
        if (profileBox.is(":visible"))
            profileBox.fadeOut("300");
        else
            profileBox.fadeIn("300");
        return false;
    });
    
    $("#profile_box").hover(function(){ 
        mouse_is_inside=true; 
    }, function(){ 
        mouse_is_inside=false; 
    });

    $("body").click(function(){
        if(! mouse_is_inside) $("#profile_box").fadeOut("300");
    });
});
