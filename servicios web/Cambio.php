<?php

$usuario = $_REQUEST['usuario'];
$nuevo = $_REQUEST['nombre'];

require_once 'ConexionPF.php';
$con = new Conexion();

	$control = $con->cambiarNombre($nuevo,$usuario);

	if($control == 1){
		$resultado[]=array("logstatus"=>"1");
	}else if($control == -1){
		$resultado[]=array("logstatus"=>"Error al actualizar");
	}else if($control == 0){
		$resultado[]=array("logstatus"=>"Ya existe alguien con ese nombre");
	}
echo json_encode($resultado) ;

?>