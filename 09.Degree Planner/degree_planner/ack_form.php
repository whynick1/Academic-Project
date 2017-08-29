<?php
    session_start();
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
        width: 80%;
        height: auto;
        margin: 0 auto; 
    }
    .text_center {
        text-align: center;
    }
    .lable_size {
        margin-top: 5px;
    }
    .submit_success {
        position:fixed;
        top:50px;
        width: 100%;
        z-index: 2;
        visibility:hidden;
    }
    .visable {
        visibility:visible;
    }
    .democlass {
        color: red;
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
  <div class='submit_success alert alert-success fade in text-center' id="success">
      <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>
      <strong> Submit Success! </strong>
  </div>
  <br>
  <div class="container">
      <div class="panel panel-default">
          <div class="panel-heading"><b>Registeration Box</b></div><br><br>
          <div class="panel-body">
              <div class="form_register"> 
                  <div class="text_center">
                      <h4>Graduate Computer Science Department<br>The University of Texas at Dallas</h4>  
                      <h2>Master’s Acknowledgment of Policies Form</h2><br><br>
                      
                  </div>
                  <form role="form">
                      <p>** All students must read, complete, sign, and date this form upon entrance to the Graduate CS Department**</p><br>
                      <div class="row">
                          <label class="col-sm-3" for="name">Name (Last, First):</label>
                          <input class="col-sm-4" type="text" id="name" placeholder="Tayler Swift">
                      </div>
                      <div class="row">
                          <label class="col-sm-3" for="utd_id">UTD ID Number:</label>
                          <input class="col-sm-4" type="text" id="utd_id" placeholder="abc999999">
                      </div>
                      <div class="row">
                          <label class="col-sm-3" for="enroll">First Semester In Graduate Program:</label>
                          <input class="col-sm-4" type="text" id="enroll" placeholder="12Fall">
                      </div>
                      <div class="row">
                          <br>
                          <label class="col-sm-4" for="track">Degree Plan (check one)</label>
                          <div class="col-sm-4"><input type="checkbox" name="track" value="Tranditional CS"> Tranditional CS</div>
                          <div class="col-sm-4">
                              <input type="checkbox" name="track" value="Networks"> Networks & Telecommunications
                          </div>
                      </div>

                      <div class="row">
                          <div class="col-sm-4"></div>
                          <div class="col-sm-4">
                              <input type="checkbox" name="track" value="Intelligent Systems"> Intelligent Systems
                          </div>
                          <div class="col-sm-4">
                              <input type="checkbox" name="track" value="SYSTEMS"> SYSTEMS
                          </div>
                      </div>

                      <div class="row lable_size">
                          <div class="col-sm-4"></div>
                          <div class="col-sm-4">
                              <input type="checkbox" name="track" value="Software Engineering"> Software Engineering
                          </div>
                          <div class="col-sm-4">
                              <input type="checkbox" name="track" value="Information Assurance"> Information Assurance
                          </div>
                      </div>

                      <div class="row lable_size">
                          <div class="col-sm-4"></div>
                          <div class="col-sm-4">
                              <input type="checkbox" name="track" value="Data Science"> Data Science
                          </div> 
                          <div class="col-sm-4">
                              <input type="checkbox" name="track" value="Interactive Computing"> Interactive Computing
                          </div>  
                      </div><br><br>

                      <label>Prerequisites I was assigned in my admission letter/email (check all that apply):</label>

                      <div class="row">
                          <div class="col-sm-4">
                              <input type="checkbox" name="prereq" value="#"> CS 5303 Computer Science I
                          </div> 
                          <div class="col-sm-4">
                              <input type="checkbox" name="prereq" value="#"> CS 5330 Computer Science II 
                          </div>  
                          <div class="col-sm-4">
                              <input type="checkbox" name="prereq" value="#"> CS 5333 Discrete Structures
                          </div> 
                      </div>

                      <div class="row lable_size">
                          <div class="col-sm-4">
                              <input type="checkbox" name="prereq" value="#"> CS 5343 Data Structures 
                          </div> 
                          <div class="col-sm-4">
                              <input type="checkbox" name="prereq" value="#"> CS 5348 Operating Systems 
                          </div>  
                          <div class="col-sm-4">
                              <input type="checkbox" name="prereq" value="#"> CS 5349 Automata Theory 
                          </div>  
                      </div>

                      <div class="row lable_size">
                          <div class="col-sm-4">
                              <input type="checkbox" name="prereq" value="#"> CS 5354 Software Engineering
                          </div> 
                          <div class="col-sm-4">
                              <input type="checkbox" name="prereq" value="#"> CS 5390 Computer Networks 
                          </div>  
                          <div class="col-sm-4">
                              <input type="checkbox" name="prereq" value="#"> CS 3341 Probability & Statistics
                          </div>  
                      </div><br><br>

                      <label>By initialing each item below, I indicate that I understand the following policies of The University of Texas at Dallas and the Graduate Computer Science Department: </label>
                    
                      <div class="lable_size"><input  type="checkbox" name="law" value="#"> I must take all my assigned prerequisites unless it has been officially waived by the department or is not a requirement of my track/degree plan. </div>
                      <div class="lable_size"><input  type="checkbox" name="law" value="#"> I must meet with a Faculty Academic Advisor at least once a year to be advised.  </div>
                      <div class="lable_size"><input  type="checkbox" name="law" value="#"> I know that I will not be allowed to enroll in a closed course. No exceptions. No waitlists.  </div>
                      <div class="lable_size"><input  type="checkbox" name="law" value="#"> There is a 6-year window to complete all coursework.  </div> 
                      <div class="lable_size"><input  type="checkbox" name="law" value="#"> GPA is calculated on the + and – scale (A, A-, B+, B, B-, C+, C).  </div>
                      <div class="lable_size"><input  type="checkbox" name="law" value="#"> I must have a core GPA ≥ 3.19, an elective GPA ≥ 3.0, and an overall GPA ≥ 3.0 to graduate.  </div>
                      <div class="lable_size"><input  type="checkbox" name="law" value="#"> I may only repeat a course two times; I can only have a total of three repeats across all courses. </div>
                      <div class="lable_size"><input  type="checkbox" name="law" value="#"> I must make up any incomplete (I) grades by the deadline or it will turn into an F on my transcript. </div>
                      <div class="lable_size"><input  type="checkbox" name="law" value="#"> I know I must complete additional paperwork to change my major/program from CS to SE or from SE to CS at least two semesters prior to graduation.  </div>
                      <div class="lable_size"><input  type="checkbox" name="law" value="#"> I know that if I miss three or more lectures in the beginning of any semester, I may be dropped or reassigned to another course in that semester.  </div>
                      <br><br><br>

                      <div class="row lable_size">
                          <div class="col-sm-6">
                              <label>Student Signature & Date: </label>
                          </div> 
                          <div class="col-sm-6">
                              <input type="text" name="signature" placeholder="Xxx Xxx mm/dd/yyyy"> 
                          </div> 
                          <div class="col-sm-6">
                              <label>Graduate Advisor Signature & Date: </label>
                          </div> 
                          <div class="col-sm-6">
                              <input type="text" name="signature" placeholder="Xxx Xxx mm/dd/yyyy"> 
                          </div> 
                      </div><br>
                  </form>
                  <button type="submit" class="btn btn-default" id="submit" onclick="submitSuccess();">Submit</button>&nbsp&nbsp<br><br><br>
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


