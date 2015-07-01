<?php

$usuario = $_REQUEST['usuario'];
$valoracion = $_REQUEST['valoracion'];
$idfoto = $_REQUEST['idfoto'];

require_once 'ConexionPF.php';
$con = new Conexion();

	if($con->insertarValoracion($idfoto,$usuario,$valoracion)){
		$resultado[]=array("logstatus"=>"1");
	}else{
		$resultado[]=array("logstatus"=>"Error al valorar");
	}
	
	echo json_encode($resultado) ;

?>