<?php
    $con = mysqli_connect("mysql1.000webhost.com", "a3021026_patryk", "haslo123", "a3021026_wallet");
    
    $name = $_POST["name"];
    $username = $_POST["username"];
    $password = $_POST["password"];
	$email = $_POST["email"];

    $statement = mysqli_prepare($con, "INSERT INTO user (name,email ,username, password) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssss", $name , $email , $username, $password);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>