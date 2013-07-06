var mouse_is_inside = false;

$(document).ready(function() {
	
	/*
	 * Login Popup
	 */
    $(".navigation_bar_login_button").click(function() {
        var loginBox = $("#navigation_bar_login_box");
        var registerBox = $("#navigation_bar_register_box");
        if (loginBox.is(":visible"))
            loginBox.fadeOut("300");
        else
            loginBox.fadeIn("300");
            if (registerBox.is(":visible"))
            	registerBox.fadeOut("300");
        return false;
    });
    
    $("#navigation_bar_login_box").hover(function(){ 
        mouse_is_inside=true; 
    }, function(){ 
        mouse_is_inside=false; 
    });

    $("body").click(function(){
        if(! mouse_is_inside) $("#navigation_bar_login_box").fadeOut("300");
    });
    
	/*
	 * Register Popup
	 */
    $(".navigation_bar_register_button").click(function() {
        var loginBox = $("#navigation_bar_login_box");
        var registerBox = $("#navigation_bar_register_box");
        if (registerBox.is(":visible"))
            registerBox.fadeOut("300");
        else
            registerBox.fadeIn("300");
            if (loginBox.is(":visible"))
                loginBox.fadeOut("300");
        return false;
    });
    
    $("#navigation_bar_register_box").hover(function(){ 
        mouse_is_inside=true; 
    }, function(){ 
        mouse_is_inside=false; 
    });

    $("body").click(function(){
        if(! mouse_is_inside) $("#navigation_bar_register_box").fadeOut("300");
    });
    
	/*
	 * Profile Popup
	 */
    $(".navigation_bar_profile_button").click(function() {
        var profileBox = $("#navigation_bar_profile_box");
        
        if (profileBox.is(":visible"))
            profileBox.fadeOut("300");
        else
            profileBox.fadeIn("300");
        return false;
    });
    
    $("#navigation_bar_profile_box").hover(function(){ 
        mouse_is_inside=true; 
    }, function(){ 
        mouse_is_inside=false; 
    });

    $("body").click(function(){
        if(! mouse_is_inside) $("#navigation_bar_profile_box").fadeOut("300");
    });
});
