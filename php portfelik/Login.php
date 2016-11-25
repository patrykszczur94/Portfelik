<?php
   
   $con = mysqli_connect("mysql1.000webhost.com", "a3021026_patryk", "haslo123", "a3021026_wallet");
 	
			session_start();

    $username = $_POST["username"];
    $password = $_POST["password"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM user WHERE username = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $username, $password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $name, $email, $username, $password);
    
    $_SESSION = array();
    $_SESSION["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
		header("http://ratcooding.site88.net/showExpense.php");
		$_SESSION["success"] = true;  
		$_SESSION["userID"] = $userID;
        $_SESSION["name"] = $name;
        $_SESSION["email"] = $email;
        $_SESSION["username"] = $username;
        $_SESSION["password"] = $password;
    }
    
    echo json_encode($_SESSION);
?>