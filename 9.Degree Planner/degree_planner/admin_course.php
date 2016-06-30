<?php
    session_start();
    if (isset($_COOKIE["username"]))
        $user_cookie = $_COOKIE["username"];
    if (strcmp($user_cookie,"admin") != 0){
        header('Location: login.php');
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
  <script type="text/javascript" src="javascript/helper3.js"></script>
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
                <div class="well well-sm">Select Track</div>
                <div class="row">
                    <div class="col-sm-4">
                        <select name="#xxx" class="form-control" id="track">
                            <option value="option1" id="option1">Tranditional CS</option>
                            <option value="option2" id="option2">Data Science</option>
                            <option value="#xxx" disabled>Networks & Telecommunications</option>
                            <option value="#xxx" disabled>Intelligent Systems</option>
                            <option value="#xxx" disabled>SYSTEMS</option>
                            <option value="#xxx" disabled>Software Engineering</option>
                            <option value="#xxx" disabled>Interactive Computing</option>
                            <option value="#xxx" disabled>Information Assurance</option>
                        </select>
                    </div>
                </div><br>

                <div class="well well-sm">Add Courses</div>
                <div class="row lable_size">
                    <form action="#xxx" id="#xxx">
                        <div class="col-sm-2">
                            <input type="text" class="form-control" name="course_name" placeholder="Enter Course Name">
                        </div>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" name="course_no" placeholder="Enter Course NO.">
                        </div>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" name="hours" placeholder="Enter Hours">
                        </div>
                        <div class="col-sm-2">
                            <input type="submit" class="btn btn-default" value="Add">
                        </div>
                    </form>
                </div><br>

                <div class="well well-sm">Core Courses</div>
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
                    <div class="col-sm-2">
                        <label>Action</label>
                    </div> 
                </div>

                <div class="row lable_size">
                    <div class="col-sm-5">
                        <p id="1">Advanced Operating System</p>
                    </div>
                    <div class="col-sm-3">
                        <p id="2">CS6666</p>
                    </div>
                    <div class="col-sm-2">
                        <p>3</p>
                    </div>
                    <div class="col-sm-2">
                        <a href="#xxx">Edit</a> || 
                        <a href="#xxx">Delete</a>
                    </div> 
                </div>
                <div class="row lable_size">
                    <div class="col-sm-5">
                        <p id="3">Advanced Operating System</p>
                    </div>
                    <div class="col-sm-3">
                        <p id="4">CS6666</p>
                    </div>
                    <div class="col-sm-2">
                        <p>3</p>
                    </div>
                    <div class="col-sm-2">
                        <a href="#xxx">Edit</a> || 
                        <a href="#xxx">Delete</a>
                    </div> 
                </div><br>

                <div class="well well-sm">Elective Courses</div>
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
                    <div class="col-sm-2">
                        <label>Action</label>
                    </div> 
                </div>

                <div class="row lable_size">
                    <div class="col-sm-5">
                        <p id="5">Advanced Operating System</p>
                    </div>
                    <div class="col-sm-3">
                        <p id="6">CS6666</p>
                    </div>
                    <div class="col-sm-2">
                        <p>3</p>
                    </div>
                    <div class="col-sm-2">
                        <a href="#xxx">Edit</a> || 
                        <a href="#xxx">Delete</a>
                    </div> 
                </div>
                <div class="row lable_size">
                    <div class="col-sm-5">
                        <p id="7">Advanced Operating System</p>
                    </div>
                    <div class="col-sm-3">
                        <p id="8">CS6666</p>
                    </div>
                    <div class="col-sm-2">
                        <p>3</p>
                    </div>
                    <div class="col-sm-2">
                        <a href="#xxx">Edit</a> || 
                        <a href="#xxx">Delete</a>
                    </div> 
                </div><br>

                <div class="well well-sm">Pre-requisite Courses</div>
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
                    <div class="col-sm-2">
                        <label>Action</label>
                    </div> 
                </div>

                <div class="row lable_size">
                    <div class="col-sm-5">
                        <p>Advanced Operating System</p>
                    </div>
                    <div class="col-sm-3">
                        <p>CS6666</p>
                    </div>
                    <div class="col-sm-2">
                        <p>3</p>
                    </div>
                    <div class="col-sm-2">
                        <a href="#xxx">Edit</a> || 
                        <a href="#xxx">Delete</a>
                    </div> 
                </div>
                <div class="row lable_size">
                    <div class="col-sm-5">
                        <p>Advanced Operating System</p>
                    </div>
                    <div class="col-sm-3">
                        <p>CS6666</p>
                    </div>
                    <div class="col-sm-2">
                        <p>3</p>
                    </div>
                    <div class="col-sm-2">
                        <a href="#xxx">Edit</a> || 
                        <a href="#xxx">Delete</a>
                    </div> 
                </div><br><br>
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



