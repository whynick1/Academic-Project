function deleteAllCookies() {
	var cookies = document.cookie.split(";");
    for (var i = 0; i < cookies.length; i++) {
    	var cookie = cookies[i];
    	var eqPos = cookie.indexOf("=");
    	var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
    	document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }
}

function submitSuccess() {
	if (!document.getElementById("success"))	
		return false;
	var banner = document.getElementById( "success" ); 
	banner.setAttribute("class", "submit_success visable alert alert-success fade in text-center");
}

function edit(id) {
    var getname = document.getElementById( id ).innerHTML;
    window.location.href='admin.php?update_track_id=' + id + '&update_track_name=' + getname;
}

function delete_id(id) {
    if(confirm('Sure To Remove This Record ?'))
    {
        window.location.href='admin.php?delete_id='+id;
    }
}

function edit_user(id) {
    var getname = document.getElementById( id ).innerHTML;
    window.location.href='admin_user.php?update_user_id=' + id + '&update_user_name=' + getname;
}

function delete_user(id) {
    if(confirm('Sure To Remove This Record ?'))
    {
        window.location.href='admin_user.php?delete_id='+id;
    }
}

function reset_password(id) {
    var getpassword = document.getElementById( id * (-1) ).innerHTML;
    if(confirm('Sure To Reset This Password ?'))
    {
        window.location.href='admin_user.php?update_user_id=' + id + '&update_password=' + getpassword;
    }
}









