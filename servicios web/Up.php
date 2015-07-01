<?php

require_once 'ConexionPF.php';
$con = new Conexion();

$usuario = $_REQUEST['usuario'];
$tit = $_REQUEST['titulo'];
$id = $con->recuperarId($usuario);

$directorio = "usuarios/". $id;

if (!is_dir($directorio)) {
    mkdir($directorio, 0777, true);
}

$ruta = $directorio . "/" . basename( $_FILES['fotoUp']['name']);
$url = "/" . $ruta;

if(strlen($tit) < 1){
	$tit = "Sin titulo";
}

if(move_uploaded_file($_FILES['fotoUp']['tmp_name'], $ruta)){
    chmod ("".basename( $_FILES['fotoUp']['tmp_name']), 0644);	
	$con->insertarFoto($id,$tit,$url);	
}
	   	   
?>