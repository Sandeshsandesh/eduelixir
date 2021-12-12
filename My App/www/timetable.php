<?php
    $con = mysqli_connect("192.168.43.110", "sandesh", "sankuSANDU$@", "eduelixir");
	$day = $_POST["day"];
	$class_id = $_POST["class_id"];
	$sec_id = $_POST["sec_id"];
	$query = "SELECT sub.name, st.first_name, st.last_name FROM edu_timetable tt
	LEFT JOIN edu_subjects sub ON tt.subject_id = sub.subject_id
	LEFT JOIN edu_staff st ON tt.staff_id = st.staff_id
	WHERE tt.day = '$day' AND tt.class_id = '$class_id' AND tt.sec_id = '$sec_id'
	ORDER BY tt.start_hour";
	
	$result = mysqli_query($con, $query);
	
	while($row = mysqli_fetch_assoc($result)){
		$array[] = $row;
	}
	echo json_encode($array);
?>