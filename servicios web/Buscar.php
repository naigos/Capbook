<?php
$id = $_REQUEST['id'];
$texto = $_REQUEST['texto'];

require_once 'ConexionPF.php';
$con = new Conexion();
$datos = $con->buscar($id,$texto);	

echo json_encode($datos) ;
?>