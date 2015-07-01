<?php

$usuario = $_REQUEST['usuario'];
$passw = $_REQUEST['password'];

require_once 'ConexionPF.php';
$con = new Conexion();

	if($con->login($usuario,$passw)){
		$resultado[]=array("logstatus"=>"1");
	}else{
		$resultado[]=array("logstatus"=>"0");
	}
echo json_encode($resultado) ;

?>
