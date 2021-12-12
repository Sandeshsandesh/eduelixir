<?php
    $con = mysqli_connect("192.168.43.110", "sandesh", "sankuSANDU$@", "eduelixir");
	
	$class_id = $_POST["class_id"];
	$sec_id = $_POST["sec_id"];
	
	$query = "SELECT start_hour,start_minute,end_hour, end_minute FROM `edu_timetable` WHERE class_id = '$class_id' AND sec_id = '$sec_id' AND day = 'mon'
ORDER BY start_hour";
	
	$result = mysqli_query($con, $query);
	
	while($row = mysqli_fetch_assoc($result)){
		$array[] = $row;
	}
	echo json_encode($array);
?>