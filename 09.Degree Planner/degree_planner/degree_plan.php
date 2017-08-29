<?php
    $course_no = $_POST['course_no'];
    $score = $_POST['score'];
    $pass = 1;
    $grade = 4.0;
    if (isset($_COOKIE["userid"]))
        $userid_cookie = $_COOKIE["userid"];
    if ($score > 90)
        $grade = 4.0;
    else if ($score >85)
        $grade = 3.5;
    else if ($score >80)
        $grade = 3.0;
    else if ($score >75)
        $grade = 2.5;
    else {
        $grade = 2.0;
        $pass = 0;
    }

    $query = "INSERT INTO Grade (`ClassId`,`UserId`,`Passed`,`Grade`,`GpaWeight`) VALUES (" . $course_no . "," . 
        $userid_cookie . "," . $pass . "," . $score . "," . $grade . ")";

    if ( !( $database = mysql_connect( "localhost", "root", "128517" ) ) )
        die( "Could not connect to database </body></html>" );
    if ( !mysql_select_db( "planner", $database ) )
          die( "Could not open planner database </body></html>" );
    if ( $score != '') {
        if ( !( $result = mysql_query( $query, $database ) ) )
        {
            print( "<p>Could not execute query!</p>" ); 
                 die( mysql_error() . "</body></html>" );
        }
    }
    //////////////////////////////////////////////////////////

    $userid_cookie = $_COOKIE["userid"];
    $query = "SELECT *" . " FROM vwClassesPassed WHERE vwClassesPassed.userID='" . $userid_cookie . "'";
    if ( !( $result = mysql_query( $query, $database ) ) )
    {
        print( "<p>Could not execute query!</p>" ); 
             die( mysql_error() . "</body></html>" );
    }
    $data = array(); 
    while ( $row = mysql_fetch_row( $result ) ) {
        $a = array();
        foreach ( $row as $key => $value )
        {
            array_push($a, $value);
        }
        array_push($data, $a);
    }

    $query = "SELECT *" . " FROM vwClassesRequired WHERE vwClassesRequired.userID='" . $userid_cookie . "'";
    if ( !( $result = mysql_query( $query, $database ) ) )
    {
        print( "<p>Could not execute query!</p>" ); 
             die( mysql_error() . "</body></html>" );
    }
    $data2 = array(); 
    while ( $row = mysql_fetch_row( $result ) ) {
        $a2 = array();
        foreach ( $row as $key => $value )
        {
            array_push($a2, $value);
        }
        array_push($data2, $a2);
    }
    mysql_close( $database );  
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
    		    <div class="panel-heading"><b>Welcome</b></div>
    		    <div class="panel-body">
                <div class="form_panel">
                    <?php
                        if (isset($_COOKIE["name"]))
                            $name_cookie = $_COOKIE["name"];    
                        print(" <h1>$name_cookie</h1> ");
                    ?> 
                    <a href="stu_profile.php">&nbspView Profile</a><br><br>

                    <div class="row lable_size">
                        <form method="post" action="degree_plan.php" id="addcourse">
                            <div class="col-sm-3">
                                <input type="text" class="form-control" name="course_no" placeholder="Enter Course ID">
                            </div>
                            <div class="col-sm-2">
                                <input type="text" class="form-control" name="score" placeholder="Enter Score">
                            </div>
                            <div class="col-sm-2">
                                <input type="submit" class="btn btn-default" value="Add Course">
                            </div>
                        </form>
                    </div><br>

                    <div class="well well-sm">Course Taken</div>
                     <div class='row lable_size'>
                        <div class='col-sm-5'>
                            <label>Course Name</label>
                        </div>
                        <div class='col-sm-3'>
                            <label>Course NO.</label>
                        </div>
                        <div class='col-sm-2'>
                            <label>Hours</label>
                        </div>
                        <div class='col-sm-2'>
                            <label>Grade</label>
                        </div> 
                    </div>
                    <?php
                        for ($x = 0; $x < sizeof($data); $x++) {
                            $array = array();
                            array_push($array, $data[$x][5], $data[$x][4], $data[$x][3]);
                            print("<div class='row lable_size'>
                                <div class='col-sm-5'>
                                    <p>$array[0]</p>
                                </div>
                                <div class='col-sm-3'>
                                    <p>$array[1]</p>
                                </div>
                                <div class='col-sm-2'>
                                    <p>3</p>
                                </div>
                                <div class='col-sm-2'>
                                    <p>$array[2]</p>
                                </div> 
                            </div> ");
                        }                      
                    ?>
                    <br>
                    <div class="well well-sm">Course Required</div>
                    <div class="row lable_size">
                        <div class="col-sm-5">
                            <label>Course Name</label>
                        </div>
                        <div class="col-sm-3">
                            <label>Course NO.</label>
                        </div>
                        <div class="col-sm-2">
                            <label>Hours</label>
                        </div>
                    </div>
                    <?php
                        for ($x = 0; $x < sizeof($data2); $x++) {
                            $array2 = array();
                            array_push($array2, $data2[$x][3], $data2[$x][2]);
                            print("<div class='row lable_size'>
                                <div class='col-sm-5'>
                                    <p>$array2[0]</p>
                                </div>
                                <div class='col-sm-3'>
                                    <p>$array2[1]</p>
                                </div>
                                <div class='col-sm-2'>
                                    <p>3</p>
                                </div>
                            </div> ");
                        }                      
                    ?>
                    <br>
                </div>
            </div>
    	</div><br>
   	</div><br>
</body>

<footer>
    <nav class="navbar navbar-default navbar-fixed-bottom">
      <div class="container">
        <div class="navbar-footer">
          <ul class="nav navbar-nav">
            <li><a href="#xxx">&copyCopyright 2016-2018 Hongyi Wang</a></li>
        </div>
      </div>
    </nav>
</footer>



