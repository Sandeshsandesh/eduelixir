<?php
  
	$con = mysqli_connect("192.168.43.110", "sandesh", "sankuSANDU$@", "eduelixir");
	$message = $_POST["message"];
	$title = $_POST["title"];
	$path_to_fcm = "https://fcm.googleapis.com/fcm/send";
	$server_key = "AAAAOF-FEmk:APA91bEpsVye_Nf0enslRY_Bgl4szl8oFVyEcpAJK8d0GZIv9c51xL7Rfeor5-00u55QpdNEtxdQAsh73WWA0bONadhDicjb25acIaPsrloY7JGTWbpp4EDRLl3CHV3b5LBVKgPuXf7v";
	date_default_timezone_set('Asia/Kolkata');
	$date= date('jS F Y h:i A');

	if ($_POST["send"] == "send_to_all") {
		$query = "SELECT token from edu_notification_token";
		$db_query = "INSERT INTO edu_notification (usn,title,message,date) VALUES ('0','$title','$message','$date')";
		}
	else{
		$usn = $_POST["usn"];
		$query = "SELECT token FROM edu_notification_token JOIN edu_students ON edu_notification_token.stu_id=edu_students.stud_id WHERE edu_students.usn='$usn'";
		$db_query = "INSERT INTO edu_notification (usn,title,message,date) VALUES ('$usn','$title','$message','$date')";
	}
	$result = mysqli_query($con,$query);
	$tokens = array();
	
	if (mysqli_num_rows($result) > 0){
		mysqli_query($con,$db_query);
		while ($row = mysqli_fetch_assoc($result)){
			$tokens[] = $row["token"];
		}
	}
	else {
		echo "Not registered in the application";
		mysqli_close($con);
		exit;
	}
	mysqli_close($con);
	
	$fields = array('registration_ids'=>$tokens,
					'notification'=>array('title'=>$title,'body'=>$message,
					'click_action'=>'com.eduelixir.eduelixir_TARGET_NOTIFICATION',
					'sound'=>'default'));
					
	$headers  = array(
					'Authorization:key=' .$server_key,
					'Content-Type:application/json'
				);
				
	$payload = json_encode($fields);
		
	$curl_session = curl_init();
	curl_setopt($curl_session, CURLOPT_URL, $path_to_fcm);
	curl_setopt($curl_session, CURLOPT_POST, true);
	curl_setopt($curl_session, CURLOPT_HTTPHEADER, $headers);
	curl_setopt($curl_session, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($curl_session, CURLOPT_SSL_VERIFYPEER, false);
	curl_setopt($curl_session, CURLOPT_IPRESOLVE, CURL_IPRESOLVE_V4);
	curl_setopt($curl_session, CURLOPT_POSTFIELDS, $payload);
	$result = curl_exec($curl_session);
		
	if($result == FALSE){
		die('Curl failed:'.curl_error($curl_session));
	}
	curl_close($curl_session);	
	echo $result;
?>