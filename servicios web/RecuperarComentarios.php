<?php
$idfoto = $_REQUEST['idfoto'];

require_once 'ConexionPF.php';
$con = new Conexion();
$datos = $con->recuperarComentarios($idfoto);	

echo json_encode($datos) ;
?>