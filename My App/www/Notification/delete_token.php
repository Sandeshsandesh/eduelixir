<?php
    $con = mysqli_connect("192.168.43.110", "sandesh", "sankuSANDU$@", "eduelixir");
	
	
	$stu_id = $_POST["stu_id"];
	
	$query = "delete from edu_notification_token where stu_id='$stu_id'";
	mysqli_query($con,$query);
	
	mysqli_close($con);
	
?>