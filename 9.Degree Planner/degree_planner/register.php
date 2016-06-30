<?php  
    session_start();      
    $stu_name = $_POST['stu_name'];
    $netid = $_POST['netid'];
    $username = $_POST['username'];
    $password = $_POST['password'];
    $track_id = $_POST['track_id'];

    $query = "INSERT INTO User (`Name`,`Username`, `Password`,`NetID`,`DegId`) VALUES ('" . $stu_name . "', '" . 
        $username . "', '" . $password . "', '" . $netid . "','" . $track_id . "')";

    if ( $password != '') {
        if ( !( $database = mysql_connect( "localhost", "root", "128517" ) ) )
            die( "Could not connect to database </body></html>" );
        if ( !mysql_select_db( "planner", $database ) )
            die( "Could not open planner database </body></html>" );

        if ( !( $result = mysql_query( $query, $database ) ) )
        {
            print( "<p>Could not execute query!</p>" ); 
               die( mysql_error() . "</body></html>" );
        }
        else
            echo "<script type='text/javascript'>alert('Registeration Success! Login Now!');</script>";
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
  <script type="text/javascript" src="javascript/helper2.js"></script>
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
          <li><a href="admin.php">ADMINISTER</a></li>
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
            <div class="panel-heading"><b>Registeration Box</b></div><br><br>
            <div class="panel-body">
                <div class="form_register"> 
                    <form role="form" method="post" action="register.php" id="myform">
                        <p>* Please input user's name and password to create a new accout.</p>                                                       
                            <div class="form-group">
                                <label for="email">Student Name</label>
                                <input type="text" class="form-control" name="stu_name" placeholder="Enter Full Name">
                            </div>
                            <div class="form-group">
                                <label for="email">NetID</label>
                                <input type="text" class="form-control" name="netid" placeholder="Enter NetID">
                            </div>
                            <div class="form-group">
                                <label for="email">Username</label>
                                <input type="text" class="form-control" name="username" placeholder="Enter Username">
                            </div>
                            <div class="form-group">
                                <label for="pwd">Password</label>
                                <input type="password" class="form-control" name="password" placeholder="Enter Password">
                            </div>

                            <label for="email">Select Your Track</label>
                                <div class="radio">
                                <label><input type="radio" name="track_id" value="1" >Tranditional CS</label>
                            </div>
                            <div class="radio">
                                <label><input type="radio" name="track_id" value="2" >Data Science</label>
                            </div>
                            <div class="radio">
                                <label><input type="radio" name="track_id" value="3" disabled>Networks & Telecommunications</label>
                            </div>
                            <div class="radio">
                                <label><input type="radio" name="track_id" value="4" disabled>Intelligent Systems</label>
                            </div>
                            <div class="radio">
                                <label><input type="radio" name="track_id" value="5" disabled>SYSTEMS</label>
                            </div>
                            <div class="radio">
                                <label><input type="radio" name="track_id" value="6" disabled>Software Engineering</label>
                            </div>
                            <div class="radio">
                                <label><input type="radio" name="track_id" value="7" disabled>Interactive Computing</label>
                            </div>
                            <div class="radio">
                                <label><input type="radio" name="track_id" value="8" disabled>Information Assurance</label>
                            </div><br>
                            <button type="submit" class="btn btn-default">Submit</button>&nbsp&nbsp
                            <button type="reset" class="btn btn-default">&nbspReset&nbsp</button><br><br>
                        <p>Aready Registered? Login <a href="login.php">here</a></p><br><br>
                    </form>
                </div>
            </div>
        </div><br><br><br>
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


