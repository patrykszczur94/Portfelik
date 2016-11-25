<?php
    $con = mysqli_connect("mysql1.000webhost.com", "a3021026_patryk", "haslo123", "a3021026_wallet");
	$expense = $_POST["expense"];
    $description = $_POST["description"];
	$userID = $_POST["userID"];

	$category = 'bills' ; 
    $statement = mysqli_prepare($con, "INSERT INTO expense (description ,expense , category , userID) VALUES (?, ? , ? , ?)");
    mysqli_stmt_bind_param($statement, "ssss", $description , $expense , $category , $userID);
    mysqli_stmt_execute($statement);
    $response = array();
    $response["success"] = true;  
    echo json_encode($response);
?>