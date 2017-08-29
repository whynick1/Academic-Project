<?php
    session_start();
    if (isset($_COOKIE["userid"]))
        $userid_cookie = $_COOKIE["userid"];

    $query = "SELECT gpa FROM `wvGPA` WHERE wvGPA.userID='" . $userid_cookie . "'";

    if ( !( $database = mysql_connect( "localhost", "root", "128517" ) ) )
        die( "Could not connect to database </body></html>" );
    if ( !mysql_select_db( "planner", $database ) )
          die( "Could not open planner database </body></html>" );
    if ( !( $result = mysql_query( $query, $database ) ) )
    {
        print( "<p>Could not execute query!</p>" ); 
                 die( mysql_error() . "</body></html>" );
    }
    
    while ( $row = mysql_fetch_row( $result ) ) {
        foreach ( $row as $key => $value )
            $gpa = $value;   
    }
?>


<html lang="en">
<head>
  <title>Bootstrap Case</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="javascript/helper.js"></script>
  <style>
    .jumbotron { 
        background-color: #F8F8F8;
        color: #000000;
    }
    .logo_small {
        max-width: 50px;
        height: auto;
    }
    .logo_large {
        max-width: auto;
        height: 250px;
    }    
    .jumbotron p {
        font-size: 50px;
    }
    .form_panel {
      width: 90%;
      height: auto;
      margin: 0 auto; 
    }
    body {
	    margin: 25px 0 0 0; /* considering that the header is 25px */
	    padding-top: 25px; 
	    background-color: #ffffff;
        color: #000000;     
  	}
  </style>
</head>

<header>
  <nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <!-- <a class="navbar-brand" href="#myPage"><img class="logo" src="logo3.png"/></a> -->
        <a class="navbar-brand" href="index.php"><img class="logo_small" src="img/utd.png"></a>
      </div>
      <div class="collapse navbar-collapse" id="myNavbar">
        <ul class="nav navbar-nav">
          <li><a href="index.php">HOME PAGE</a></li>
          <li><a href="degree_plan.php">DEGREE PLAN</a></li>
          <li><a href="ack_form.php">ACKNOWLEDGE FORM</a></li>
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">ADMINISTER <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="admin.php">Manage Track</a></li>
              <li><a href="admin_course.php">Manage Course</a></li>
              <li><a href="admin_user.php">Manage User</a></li>
            </ul>
          </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
        <?php
            if (isset($_COOKIE["username"])) {
                $user_cookie = $_COOKIE["username"];
                print( "<li><a href='stu_profile.php'><span class='glyphicon glyphicon-user'></span> " . $user_cookie . " </a></li><li><a href='login.php' onclick='deleteAllCookies(); return true;'><span class='glyphicon glyphicon-log-in'></span> Logout </a></li>" );
            }else {
                print( "<li><a href='register.php'><span class='glyphicon glyphicon-user'></span> Sign Up </a></li><li><a href='login.php'><span class='glyphicon glyphicon-log-in'></span> Login </a></li>" );
            }
        ?>
        </ul>
      </div>
    </div>
  </nav>  
</header>

<body>
  	<br>
    <div class="container">
      	<div class="panel panel-default">
    	      <div class="panel-heading"><b>Student Profile</b></div><br>
  		      <div class="panel-body">
      				  <div class="form_panel">
                    <div class="well well-sm">Personal Information</div>
                    <?php 
                    	if (!isset($_COOKIE["username"])) 
                					print(" <p><b>Name: </b><u>Tyler Swift</u></p>
        		                    <p><b>NetID: </b><u>hxw999999</u></p>
        		                    <p><b>Email: </b><u>tylerswift@gamil.com</u></p>
        		                    <p><b>Mobile: </b><u>N/A</u></p>
        		                    <br>

        		                    <div class='well well-sm'>Degree Information</div>
        		                    <p><b>Major: </b><u>Computer Science</u></p>
        		                    <p><b>Track: </b><u>Data Science</u></p>
        		                    <p><b>Admitted: </b><u>N/A</u></p>
        		                    <p><b>Anticipated Graduation: </b><u>N/A</u></p>
        		                    <p><b>GPA: </b><u>N/A</u></p>
        		                    <br><br> ");
              				else {
              					$degname_cookie = $_COOKIE["degname"];
              					$name_cookie = $_COOKIE["name"];
              					$netid_cookie = $_COOKIE["netid"];
              					$email = $netid_cookie . "@utdallas.edu";
              					print(" <p><b>Name: </b><u>$name_cookie</u></p>
      		                    <p><b>NetID: </b><u>$netid_cookie</u></p>
      		                    <p><b>Email: </b><u>$email</u></p>
      		                    <p><b>Mobile: </b><u>(214) 999-9999</u></p>
      		                    <br>

      		                    <div class='well well-sm'>Degree Information</div>
      		                    <p><b>Major: </b><u>Computer Science</u></p>
      		                    <p><b>Track: </b><u>$degname_cookie</u></p>
      		                    <p><b>Admitted: </b><u>15Fall</u></p>
      		                    <p><b>Anticipated Graduation: </b><u>17Fall</u></p>
      		                    <p><b>GPA: </b><u>$gpa</u></p>
      		                    <br><br> ");
              				}
                    ?>
                </div>
            </div>
        </div>
  	</div>
</body>

<footer>
    <nav class="navbar navbar-default navbar-fixed-bottom">
      <div class="container">
        <div class="navbar-footer">
          <ul class="nav navbar-nav">
            <li><a href="#xxx">&copyCopyright 2016-2018 Hongyi Wang&nbsp&nbsp&nbsp&nbsp&nbsp
                <?php
                    $access_num = $_SESSION["login_count"];
                    print("Access Amount: $access_num");
                ?>
                </a>
            </li>
        </div>
      </div>
    </nav>
</footer>
