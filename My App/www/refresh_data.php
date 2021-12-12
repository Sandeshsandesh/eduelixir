<?php
    $con = mysqli_connect("192.168.43.110", "sandesh", "sankuSANDU$@", "eduelixir");
    
	$usn = $_POST["usn"];	
	
	$statement = mysqli_prepare($con, "SELECT * FROM edu_students WHERE usn = ?");   
    mysqli_stmt_bind_param($statement, "s", $usn);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $first_name, $last_name, $dob, $guardian_name, $guardian_occupation,$address,$city,$state,$zipcode,$country, $guardian_landline, $guardian_mobile, $doa, $class_id, $sec_id, $usn);
    
    $response = array();
    $response["success"] = false; 
	
	$statement1 = mysqli_prepare($con,"SELECT edu_class.class FROM `edu_students` INNER JOIN edu_class ON edu_students.class_id=edu_class.class_id WHERE edu_students.usn= ?");
	mysqli_stmt_bind_param($statement1, "s", $usn);
	mysqli_stmt_execute($statement1);
	
	mysqli_stmt_store_result($statement1);
	mysqli_stmt_bind_result($statement1,$class_name);
	
	$statement2 = mysqli_prepare($con,"SELECT edu_section.section FROM `edu_students` INNER JOIN edu_section ON edu_students.sec_id=edu_section.sec_id WHERE edu_students.usn= ?");
	mysqli_stmt_bind_param($statement2, "s", $usn);
	mysqli_stmt_execute($statement2);
	
	mysqli_stmt_store_result($statement2);
	mysqli_stmt_bind_result($statement2,$section_name);
		
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;  
        $response["first_name"] = $first_name;
		$response["last_name"] = $last_name;
        $response["dob"] = $dob;
        $response["guardian_name"] = $guardian_name;
        $response["guardian_occupation"] = $guardian_occupation;
		$response["address"] = $address;
		$response["city"] = $city;
		$response["state"] = $state;
		$response["zipcode"] = $zipcode;
		$response["country"] = $country;
		$response["guardian_landline"] = $guardian_landline;	
		$response["guardian_mobile"] = $guardian_mobile;
		$response["doa"] = $doa;
		$response["class_id"] = $class_id;
		$response["sec_id"] = $sec_id;
		$response["usn"] = $usn;
    }
	$response["class_name"] = "not defined";
	while(mysqli_stmt_fetch($statement1)){
		$response["class_name"] = $class_name;
	}
	
	while(mysqli_stmt_fetch($statement2)){
		$response["section_name"] = $section_name;
	}
    
    echo json_encode($response);
	
?>
