function deleteAllCookies() {
	var cookies = document.cookie.split(";");
    for (var i = 0; i < cookies.length; i++) {
    	var cookie = cookies[i];
    	var eqPos = cookie.indexOf("=");
    	var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
    	document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }
}


function init() {
    $('#track').change(function(){
        if($(this).children('option:selected').filter('#option2').length) {
           document.getElementById("1").innerHTML = "Database Design";
           document.getElementById("2").innerHTML = "CS6360";
           document.getElementById("3").innerHTML = "Database Design";
           document.getElementById("4").innerHTML = "CS6360";
           document.getElementById("5").innerHTML = "Database Design";
           document.getElementById("6").innerHTML = "CS6360";
           document.getElementById("7").innerHTML = "Database Design";
           document.getElementById("8").innerHTML = "CS6360";
        }
        else{
           document.getElementById("1").innerHTML = "Advanced Operating System";
           document.getElementById("2").innerHTML = "CS6666";
           document.getElementById("3").innerHTML = "Advanced Operating System";
           document.getElementById("4").innerHTML = "CS6666";
           document.getElementById("5").innerHTML = "Advanced Operating System";
           document.getElementById("6").innerHTML = "CS6666";
           document.getElementById("7").innerHTML = "Advanced Operating System";
           document.getElementById("8").innerHTML = "CS6666";
        }
    });
}

window.addEventListener( "load", init, false );












