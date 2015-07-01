<?php
$id = $_REQUEST['id'];

require_once 'ConexionPF.php';
$con = new Conexion();
$datos = $con->RecuperarMisFotos($id);	

echo json_encode($datos) ;
?>