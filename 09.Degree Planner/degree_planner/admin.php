<?php
    session_start();
    $newtrack = $_POST['newtrack'];
    if (isset($_COOKIE["username"]))
        $user_cookie = $_COOKIE["username"];
    if (strcmp($user_cookie,"admin") != 0){
        header('Location: login.php');
    }

    $query = "INSERT INTO DegreePlan(`DegName`, `DeptId`) VALUES ('" . $newtrack . "', 1)";
    if ( !( $database = mysql_connect( "localhost", "root", "128517" ) ) )
        die( "Could not connect to database </body></html>" );
    if ( !mysql_select_db( "planner", $database ) )
          die( "Could not open planner database </body></html>" );

    //delete a track
    if(isset($_GET['delete_id']))
    {
       $sql_query="DELETE FROM `DegreePlan` WHERE DegId=" . $_GET['delete_id'];
       if ( !( $result = mysql_query( $sql_query, $database ) ) )
       {
          print( "<p>Could not execute query!</p>" ); 
               die( mysql_error() . "</body></html>" );
       }
       header("Location: admin.php");
    }

    //update a track
    if(isset($_GET['update_track_id']))
    {
       $sql_query = "UPDATE DegreePlan SET DegName = '" . $_GET['update_track_name'] . "' WHERE DegId= " . $_GET['update_track_id'];
       //update degreeplan set degname = 'Net Track 2' where degid = 16;
       if ( !( $result = mysql_query( $sql_query, $database ) ) )
       {
          print( "<p>Could not execute query!</p>" ); 
               die( mysql_error() . "</body></html>" );
       }
       header("Location: admin.php");
    }


    //add a track
    if ( $newtrack != '') {
        $newtrack = '';
        if ( !( $result = mysql_query( $query, $database ) ) )
        {
            print( "<p>Could not execute query!</p>" ); 
                 die( mysql_error() . "</body></html>" );
        }
    }

    //select from database
    $query = "SELECT DegName from degreeplan";
    if ( !( $result = mysql_query( $query, $database ) ) )
    {
        print( "<p>Could not execute query!</p>" ); 
             die( mysql_error() . "</body></html>" );
    }
    $a = array();
    while ( $row = mysql_fetch_row( $result ) ) {
        foreach ( $row as $key => $value )
        {
            array_push($a, $value);
        }
    }
    
    $query = "SELECT DegId from degreeplan";
    if ( !( $result = mysql_query( $query, $database ) ) )
    {
        print( "<p>Could not execute query!</p>" ); 
             die( mysql_error() . "</body></html>" );
    }
    $b = array();
    while ( $row = mysql_fetch_row( $result ) ) {
        foreach ( $row as $key => $value )
        {
            array_push($b, $value);
        }
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
      width: 60%;
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
		    <div class="panel-heading"><b>Administer Panel</b></div><br><br>
		    <div class="panel-body">
    				<div class="form_panel"> 
                <form method="post" action="admin.php" role="form">
                    <p>* Input Track Name</p>
                    <div class="row">
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="newtrack" placeholder="Enter track name">
                        </div>
                        <div class="col-sm-6">
                            <button type="submit" class="btn btn-default"> Submit </button>&nbsp&nbsp
                        </div>
                    </div>
                </form><br>

                <div class="row lable_size">
                    <div class="col-sm-2">
                        <label>Track ID</label>
                    </div> 
                    <div class="col-sm-6">
                        <label>Track Name</label>
                    </div>
                    <div class="col-sm-4">
                        <label>Action</label>
                    </div> 
                </div>
                <?php
                    for ($x = 0; $x < sizeof($a); $x++) {
                        $track = $a[$x];
                        $track_id = $b[$x];
                ?>
                        <div class='row lable_size'>
                            <div class='col-sm-2'>
                                <p><?php echo $track_id; ?></p>
                            </div>
                            <div class='col-sm-6'>
                                <p contenteditable="true" id=<?php echo $track_id; ?>><?php echo $track; ?></p>
                            </div>
                            <div class='col-sm-4'>
                                <a href='javascript:edit(<?php echo $track_id; ?>)'>Update</a> || 
                                <a href='javascript:delete_id(<?php echo $track_id; ?>)'>Delete</a>
                            </div> 
                        </div>   
                <?php
                  }
                ?>                   
                <div class="lable_size">
                    <a href="admin_course.php">Want Modify Courses In Track?</a>
                </div>
                <br><br><br>
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