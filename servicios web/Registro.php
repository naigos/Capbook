<?php

$usuario = $_REQUEST['usuario'];
$passw = $_REQUEST['password'];

require_once 'ConexionPF.php';
$con = new Conexion();

	$control = $con->registrar($usuario,$passw);

	if($control == 1){
		$resultado[]=array("logstatus"=>"1");
	}else if($control == -1){
		$resultado[]=array("logstatus"=>"Error al insertar");
	}else if($control == 0){
		$resultado[]=array("logstatus"=>"Ya existe alguien con ese nombre");
	}
echo json_encode($resultado) ;

?>