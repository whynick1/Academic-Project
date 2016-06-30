<?php
    $username = $_POST['username'];
    $password = $_POST['password'];
    $track = $_POST['track'];
    $stu_name = $_POST['stu_name'];
    $netid = $_POST['netid'];

    if (isset($_COOKIE["username"]))
        $user_cookie = $_COOKIE["username"];
    if (strcmp($user_cookie,"admin") != 0){
        header('Location: login.php');
    }

    //add user 
    $query = "INSERT INTO User (`Name`,`Username`, `Password`,`NetID`,`DegId`) VALUES ('" . $stu_name . "', '" . 
        $username . "', '" . $password . "', '" . $netid . "','" . $track . "')";

    if ( !( $database = mysql_connect( "localhost", "root", "128517" ) ) )
        die( "Could not connect to database </body></html>" );
    if ( !mysql_select_db( "planner", $database ) )
          die( "Could not open planner database </body></html>" );
    if ( $netid != '') {
        if ( !( $result = mysql_query( $query, $database ) ) )
        {
            print( "<p>Could not execute query!</p>" ); 
                 die( mysql_error() . "</body></html>" );
        }
    }

    //delete a user
    if(isset($_GET['delete_id']))
    {
       $sql_query="DELETE FROM `User` WHERE UserId=" . $_GET['delete_id'];
       if ( !( $result = mysql_query( $sql_query, $database ) ) )
       {
          print( "<p>Could not execute query!</p>" ); 
               die( mysql_error() . "</body></html>" );
       }
       header("Location: admin_user.php");
    }

    //update a user
    if(isset($_GET['update_user_name']))
    {
       $sql_query = "UPDATE User SET Name = '" . $_GET['update_user_name'] . "' WHERE UserId= " . $_GET['update_user_id'];
       //update degreeplan set degname = 'Net Track 2' where degid = 16;
       if ( !( $result = mysql_query( $sql_query, $database ) ) )
       {
          print( "<p>Could not execute query!</p>" ); 
               die( mysql_error() . "</body></html>" );
       }
       header("Location: admin_user.php");
    }

    //update a password
    if(isset($_GET['update_password']))
    {
       $sql_query = "UPDATE User SET Password = '" . $_GET['update_password'] . "' WHERE UserId= " . $_GET['update_user_id'];
       //update degreeplan set degname = 'Net Track 2' where degid = 16;
       if ( !( $result = mysql_query( $sql_query, $database ) ) )
       {
          print( "<p>Could not execute query!</p>" ); 
               die( mysql_error() . "</body></html>" );
       }
       header("Location: admin_user.php");
    }

    //select from database
    $query = "SELECT UserID, Name, Password from User";
    if ( !( $result = mysql_query( $query, $database ) ) )
    {
        print( "<p>Could not execute query!</p>" ); 
             die( mysql_error() . "</body></html>" );
    }
    $a = array();
    $b = array();
    $c = array();
    while ( $row = mysql_fetch_row( $result ) ) {
        array_push($b, $row[0]);
        array_push($a, $row[1]);
        array_push($c, $row[2]);
    }

    // $query = "SELECT UserId from User";
    // if ( !( $result = mysql_query( $query, $database ) ) )
    // {
    //     print( "<p>Could not execute query!</p>" ); 
    //          die( mysql_error() . "</body></html>" );
    // }
    // $b = array();
    // while ( $row = mysql_fetch_row( $result ) ) {
    //     foreach ( $row as $key => $value )
    //     {
    //         array_push($b, $value);
    //     }
    // }

    // $query = "SELECT Password from User";
    // if ( !( $result = mysql_query( $query, $database ) ) )
    // {
    //     print( "<p>Could not execute query!</p>" ); 
    //          die( mysql_error() . "</body></html>" );
    // }
    // $c = array();
    // while ( $row = mysql_fetch_row( $result ) ) {
    //     foreach ( $row as $key => $value )
    //     {
    //         array_push($c, $value);
    //     }
    // }
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
    .lable_size {
        margin-top: 5px;
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
		    <div class="panel-heading"><b>Course List</b></div><br>
		    <div class="panel-body">
            <div class="form_panel">
                <div class="well well-sm">User Management</div>
                <div class="row lable_size">
                    <form method="post" action="admin_user.php">
                        <div class="col-sm-2">
                            <input type="text" class="form-control" name="stu_name" placeholder="Enter Full Name">
                        </div>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" name="netid" placeholder="Enter NetID">
                        </div>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" name="username" placeholder="Enter Username">
                        </div>
                        <div class="col-sm-2">
                            <input type="password" class="form-control" name="password" placeholder="Enter Password">
                        </div>
                        <div class="col-sm-2">
                            <select name="track" class="form-control">
                                <option value="1">Tranditional CS</option>
                                <option value="2">Data Science</option>
                                <option value="#xxx" disabled>Networks & Telecommunications</option>
                                <option value="#xxx" disabled>Intelligent Systems</option>
                                <option value="#xxx" disabled>SYSTEMS</option>
                                <option value="#xxx" disabled>Software Engineering</option>
                                <option value="#xxx" disabled>Interactive Computing</option>
                                <option value="#xxx" disabled>Information Assurance</option>
                            </select> 
                        </div>
                        <div class="col-sm-2">
                            <input type="submit" class="btn btn-default" value=" Add User ">
                        </div>
                    </form>
                </div><br>

                <div class="well well-sm">Modify Users</div>
                <div class="row lable_size">
                    <div class="col-sm-4">
                        <label>User Name</label>
                    </div>
                    <div class="col-sm-4">
                        <label>Password</label>
                    </div>
                    <div class="col-sm-4">
                        <label>Action</label>
                    </div> 
                </div>
                <?php
                    for ($x = 0; $x < sizeof($a); $x++) {
                        $user = $a[$x];
                        $user_id = $b[$x];
                        $password = $c[$x];
                ?>
                        <div class='row lable_size'>
                            <div class='col-sm-4'>
                                <p contenteditable="true" id=<?php echo $user_id; ?>><?php echo $user; ?></p>
                            </div>
                            <div class='col-sm-4'>
                                <p contenteditable="true" id=<?php echo $user_id * (-1); ?>><?php echo $password; ?></p>
                            </div>
                            <div class='col-sm-4'>
                                <a href='javascript:edit_user(<?php echo $user_id; ?>)'>Update</a> || 
                                <a href='javascript:delete_user(<?php echo $user_id; ?>)'>Delete</a> || 
                                <a href='javascript:reset_password(<?php echo $user_id; ?>)'>Reset Password</a>
                            </div> 
                        </div>   
                <?php
                    }
                ?>
                <br><br>
            </div>
		  	</div><br>
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



