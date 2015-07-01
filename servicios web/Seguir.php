<?php

$id = $_REQUEST['id'];
$idseg = $_REQUEST['idseg'];

require_once 'ConexionPF.php';
$con = new Conexion();

	if($con->seguir($id,$idseg)){
		$resultado[]=array("logstatus"=>"1");
	}else{
		$resultado[]=array("logstatus"=>"0");
	}
	
	echo json_encode($resultado) ;

?>