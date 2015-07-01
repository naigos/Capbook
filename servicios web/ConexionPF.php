<?php

define("DB_HOST", 		"localhost");
define("DB_DATABASE", 	"proyecto");
define("DB_USER", 		"root");
define("DB_PASSWORD", 	"");
 
class Conexion {
 
    private $db;
 
	function __construct() {
        $db = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
        mysql_select_db(DB_DATABASE);
	}
 
    function __destruct() {
		mysql_close();
    }
 
    public function login($xusuario,$xpassword){
	
		$result=mysql_query("SELECT COUNT(*) FROM usuarios WHERE nombre= '$xusuario' AND password= '$xpassword' "); 
		$count = mysql_fetch_row($result);

		if ($count[0]==1){ return true;} else { return false; }
	}
	
	public function registrar($xusuario,$xpassword){	
		
		$result=mysql_query("SELECT COUNT(*) FROM usuarios WHERE nombre= '$xusuario'"); 
		$count = mysql_fetch_row($result);

		if ($count[0] > 0){ 
			$correcto=0;
		} else { 
			$result=mysql_query("INSERT INTO usuarios(nombre,password) VALUES ('$xusuario','$xpassword');"); 
			if($result){
				$correcto=1;
			}else{
				$correcto=-1;
			}
		}
		
		return $correcto;
	}
	
	public function borrarCuenta($xusuario){	
		
		$result=mysql_query("DELETE FROM usuarios WHERE nombre= '$xusuario'"); 

		if ($result){ 
			$correcto=1;
		} else { 
			$correcto=0;
		}
		
		return $correcto;
	}
	
	public function cambiarNombre($xnuevo,$xusuario){	
		
		$result=mysql_query("SELECT COUNT(*) FROM usuarios WHERE nombre= '$xnuevo'"); 
		$count = mysql_fetch_row($result);

		if ($count[0] > 0){ 
			$correcto=0;
		} else { 
			$result=mysql_query("UPDATE usuarios SET nombre = '$xnuevo' WHERE nombre= '$xusuario'"); 
			if($result){
				$correcto=1;
			}else{
				$correcto=-1;
			}
		}
		
		return $correcto;
	}
	
	public function recuperarId($xusuario){
		$result = mysql_query("SELECT id FROM usuarios WHERE nombre= '$xusuario'"); 
		$fila = mysql_fetch_row($result);
		$id = $fila[0];
		
		return $id;
	}
	
	public function recuperarSeguidos($xusuario){
		$id = $this->recuperarId($xusuario);
		
		$sql = "SELECT usuarios.nombre as nombre, seguidos.idseguido as idseg FROM usuarios, seguidos WHERE idusuario = ".$id." AND seguidos.idseguido=usuarios.id";
		$datos = array();
		$consulta = mysql_query($sql) or die(mysql_error());
		while ($resultado = mysql_fetch_assoc($consulta)){
			$datos[] = $resultado;
		}
		
		return $datos;
	}
	
	public function dejarDeSeguir($xusuario,$xidseg){	
		$xid = $this->recuperarId($xusuario);
		$result=mysql_query("DELETE FROM seguidos WHERE idusuario = ".$xid." AND idseguido = ".$xidseg); 

		if ($result){ 
			$correcto=1;
		} else { 
			$correcto=0;
		}
		
		return $correcto;
	}
	
	public function seguir($xusuario,$xidseg){	
		$xid = $this->recuperarId($xusuario);
		$result=mysql_query("INSERT INTO seguidos (idusuario, idseguido) VALUES (".$xid.",".$xidseg.")"); 

		if ($result){ 
			$correcto=1;
		} else { 
			$correcto=0;
		}
		
		return $correcto;
	}
	
	public function buscar($xusuario,$texto){
		$xid = $this->recuperarId($xusuario);
		$sql="SELECT nombre, id FROM usuarios WHERE id NOT IN (SELECT seguidos.idseguido FROM usuarios, seguidos WHERE idusuario = ".$xid." AND seguidos.idseguido=usuarios.id) AND nombre LIKE '%".$texto."%'";
		
		$datos = array();
		$consulta = mysql_query($sql) or die(mysql_error());
		while ($resultado = mysql_fetch_assoc($consulta)){
			$datos[] = $resultado;
		}
		
		return $datos;
	}
	
	public function recuperarInicio($xusuario){
		$xid = $this->recuperarId($xusuario);
		$sql="SELECT usuarios.nombre as nombre, fotos.id as idfoto, fotos.titulo as titulo, fotos.url as url, fotos.fecha as fecha, fotos.idusuario as idusuario FROM fotos, usuarios WHERE fotos.idusuario = usuarios.id AND fotos.idusuario IN (SELECT idseguido FROM SEGUIDOS WHERE idusuario = $xid ) ORDER BY fotos.id DESC";
		
		$datos = array();
		$consulta = mysql_query($sql) or die(mysql_error());
		while ($resultado = mysql_fetch_assoc($consulta)){
			$datos[] = $resultado;
		}
		
		return $datos;
	}
	
	public function insertarFoto($xid,$xtit,$xurl){			
		$result=mysql_query("SELECT COUNT(*) FROM fotos WHERE idusuario = $xid AND url = '$xurl'"); 
		$count = mysql_fetch_row($result);

		if ($count[0] <= 0){ 
			mysql_query("INSERT INTO fotos(idusuario,titulo,fecha,url) VALUES ($xid,'$xtit',NOW(),'$xurl');");
		} 
	}
	
	public function recuperarComentarios($xidfoto){
		$sql="SELECT comentarios.texto as texto, comentarios.fecha as fecha, usuarios.nombre as nombre FROM comentarios, usuarios WHERE comentarios.idusuario = usuarios.id AND comentarios.idfoto = $xidfoto ORDER BY comentarios.fecha ASC;";
		
		$datos = array();
		$consulta = mysql_query($sql) or die(mysql_error());
		while ($resultado = mysql_fetch_assoc($consulta)){
			$datos[] = $resultado;
		}
		
		return $datos;
	}
	
	public function recuperarValoraciones($xidfoto){
		$sql="SELECT valoracion FROM valoraciones WHERE idfoto=$xidfoto;";
		
		$datos = array();
		$consulta = mysql_query($sql) or die(mysql_error());
		while ($resultado = mysql_fetch_assoc($consulta)){
			$datos[] = $resultado;
		}
		
		return $datos;
	}
	
	public function insertarComentario($xid,$xidusu,$xtxt){			
		$xusu = $this->recuperarId($xidusu);	
		$result=mysql_query("INSERT INTO comentarios(idfoto,idusuario,texto,fecha) VALUES ($xid, $xusu, '$xtxt', NOW());");
			
		if ($result){ 
			$correcto=1;
		} else { 
			$correcto=0;
		}
		
		return $correcto;
	}
	
	public function insertarValoracion($xid,$xidusu,$xval){			
		$xusu = $this->recuperarId($xidusu);
		
		$res = mysql_query("SELECT COUNT(*) FROM valoraciones WHERE idusuario = $xusu AND idfoto = $xid;"); 
		$count = mysql_fetch_row($res);

		if ($count[0] <= 0){ 
			$result=mysql_query("INSERT INTO valoraciones(idfoto,idusuario,valoracion) VALUES ($xid, $xusu, $xval);");
		}else{
			$result=mysql_query("UPDATE valoraciones SET valoracion = $xval WHERE idusuario = $xusu AND idfoto = $xid;");
		}		

		if ($result){ 
			$correcto=1;
		} else { 
			$correcto=0;
		}
		
		return $correcto;
	}
	
	public function RecuperarMisFotos($xusuario){
		$xid = $this->recuperarId($xusuario);
		$sql="SELECT * FROM fotos WHERE idusuario = $xid ORDER BY id DESC";
		
		$datos = array();
		$consulta = mysql_query($sql) or die(mysql_error());
		while ($resultado = mysql_fetch_assoc($consulta)){
			$datos[] = $resultado;
		}
		
		return $datos;
	}

}
  
?>
