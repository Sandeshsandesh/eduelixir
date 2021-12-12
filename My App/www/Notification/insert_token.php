<?php
    $con = mysqli_connect("192.168.43.110", "sandesh", "sankuSANDU$@", "eduelixir");
	
	$stu_id = $_POST["stu_id"];
	$token = $_POST["token"];
	
	$query = "insert into edu_notification_token values('$stu_id','$token')";
	mysqli_query($con,$query);
	
	mysqli_close($con);
	
?>