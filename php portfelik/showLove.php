<?php


$con = mysqli_connect("mysql1.000webhost.com", "a3021026_patryk", "haslo123", "a3021026_wallet");

$username = $_POST["username"];
$password = $_POST["password"];

$statement = mysqli_prepare($con, "SELECT expense, description , category
FROM expense
WHERE category = 'love' AND userID = (
SELECT userID
FROM user
WHERE username = ?
AND password = ? )");

mysqli_stmt_bind_param($statement, "ss", $username, $password );
mysqli_stmt_execute($statement);
$statement->bind_result($expense, $description , $category);

mysqli_stmt_store_result($statement);

$result = array();
while($statement->fetch()){
	array_push($result,
	array('expense'=>$expense),
	array('description'=>$description)
	);
}
echo json_encode(array("expense"=>$result));
mysqli_close($con);
?>