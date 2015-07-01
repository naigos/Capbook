<?php

$usuario = $_REQUEST['usuario'];

require_once 'ConexionPF.php';
$con = new Conexion();

	if($con->borrarCuenta($usuario)){
		$resultado[]=array("logstatus"=>"1");
	}else{
		$resultado[]=array("logstatus"=>"0");
	}
echo json_encode($resultado) ;

?>