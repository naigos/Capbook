<?php

$usuario = $_REQUEST['usuario'];
$texto = $_REQUEST['comentario'];
$idfoto = $_REQUEST['idfoto'];

require_once 'ConexionPF.php';
$con = new Conexion();

	if($con->insertarComentario($idfoto,$usuario,$texto)){
		$resultado[]=array("logstatus"=>"1");
	}else{
		$resultado[]=array("logstatus"=>"Error al comentar");
	}
	
	echo json_encode($resultado) ;

?>
