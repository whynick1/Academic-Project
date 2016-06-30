<?php
    session_start();
    $userName = $_POST['user'];
    $passWord = $_POST['password'];
    $remember = $_POST['remember'];
    $query = "SELECT *" . " FROM User WHERE username='" . $userName . "'";
    if ( $userName != '') {
        if ( !( $database = mysql_connect( "localhost", "root", "128517" ) ) )
            die( "Could not connect to database </body></html>" );
        if ( !mysql_select_db( "planner", $database ) )
            die( "Could not open planner database </body></html>" );

        if ( !( $result = mysql_query( $query, $database ) ) )
        {
            print( "<p>Could not execute query!</p>" ); 
               die( mysql_error() . "</body></html>" );
        }

        define( "HALF_HOUR", 60 * 30 ); // define constant
        if ( $row = mysql_fetch_row( $result ) ) {
            $a = array();
            foreach ( $row as $key => $value )
            {
                array_push($a, $value);
            }
            if ($a[3] == $passWord) {
                setcookie( "userid", $a[0], time() + HALF_HOUR );
                setcookie( "username", $a[2], time() + HALF_HOUR );
                setcookie( "name", $a[1], time() + HALF_HOUR );
                setcookie( "netid", $a[4], time() + HALF_HOUR );
                setcookie( "degid", $a[5], time() + HALF_HOUR );
                
                $query = "SELECT DegName" . " FROM DegreePlan WHERE degid='" . $a[5] . "'";  
                if ( !( $result = mysql_query( $query, $database ) ) )
                {
                    print( "<p>Could not execute query!</p>" ); 
                    die( mysql_error() . "</body></html>" );
                }
                // fetch each record in result set
                while ( $row = mysql_fetch_row( $result ) ) {
                    foreach ( $row as $key => $value ) {
                        setcookie( "degname", $value, time() + HALF_HOUR );
                    }
                }
                header('Location: index.php');
            }
        } 
        mysql_close( $database );  
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
    .form_register {
      	width: 50%;
      	height: auto;
      	margin: 0 auto;
    }
    .text-center {
        text-align: center;
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
    <?php
        if ($value == $passWord && $userName != '') {   
            $userName = '';
            print( "<div class='alert alert-success fade in text-center'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a><strong> Login Success! </strong></div>" );
        }else if (isset($_COOKIE["username"])) {
            print( "<div class='alert alert-success fade in text-center'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a><strong> Login Success! </strong></div>" );
        }else if ( $userName != '') {
                print( "<div class='alert alert-danger fade in text-center'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a><strong> Login Fail! </strong> Please Login Again! </div>" );
        }       
    ?>
    <br>
  	<div class="container">
    		<div class="panel panel-default">
    		    <div class="panel-heading"><b>Registeration Box</b></div><br><br>
    		    <div class="panel-body">
    				    <div class="form_register"> 
          					<form method="post" action="login.php" role="form">
          						  <p>* Please input user's name and password then click submit.</p>
          					    <div class="form-group">
            					      <label for="user">Username</label>
            					      <input type="text" class="form-control" id="user" name="user" placeholder="Enter Username">
          					    </div>
          					    <div class="form-group">
            					      <label for="pwd">Password</label>
            					      <input type="password" class="form-control" id="pwd" name="password" placeholder="Enter Password">
          					    </div>
                        <!-- implement cookie here -->
          					    <div class="checkbox">
          					        <label><input type="checkbox" name="remember" value="remember"> Remember me</label>
          					    </div>				
          					    <input type="submit" class="btn btn-default" value=" Submit " onclick="reload(); return true;">&nbsp
          		   				<input type="reset" class="btn btn-default" value=" Reset&nbsp "><br><br><br><br>
      				      </form>
    		  		  </div>
    		  	</div><br>
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



