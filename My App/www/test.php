<?php
    $con = mysqli_connect("192.168.43.110", "sandesh", "sankuSANDU$@", "eduelixir");
	$usn = "adhasdha";
	$query = "SELECT * FROM edu_students WHERE usn ='$usn'";
	
	$result = mysqli_query($con, $query);
	
	while($row = mysqli_fetch_assoc($result)){
		$array[] = $row;
		$array1[] = $array;
	}
	echo json_encode($array);
	echo json_encode($array1);
	
?>