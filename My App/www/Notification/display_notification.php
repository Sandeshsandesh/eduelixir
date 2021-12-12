<?php
    $con = mysqli_connect("192.168.43.110", "sandesh", "sankuSANDU$@", "eduelixir");
	$not_id = $_POST["not_id"];
	$usn = $_POST["usn"];
	$query = "SELECT not_id, title, message,date FROM `edu_notification` WHERE not_id>'$not_id' AND (usn = '$usn' or usn = '0')";
	$result = mysqli_query($con, $query);
	
	while($row = mysqli_fetch_assoc($result)){
		$array[] = $row;
	}
	echo json_encode($array);
?>