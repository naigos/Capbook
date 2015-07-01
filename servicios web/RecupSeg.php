<?php
$id = $_REQUEST['id'];

require_once 'ConexionPF.php';
$con = new Conexion();
$datos = $con->recuperarSeguidos($id);	

echo json_encode($datos) ;
?>

