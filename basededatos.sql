CREATE DATABASE IF NOT EXISTS Proyecto DEFAULT CHARACTER SET = 'utf8' DEFAULT COLLATE 'utf8_general_ci';
USE Proyecto;

CREATE TABLE usuarios (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, 
	nombre VARCHAR(50) NOT NULL, 
	password VARCHAR(50) NOT NULL
);
CREATE TABLE seguidos (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, 
	idusuario INT NOT NULL, 
	idseguido INT NOT NULL, 
	FOREIGN KEY (idusuario) REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE, 
	FOREIGN KEY (idseguido) REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE fotos (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, 
	idusuario INT NOT NULL, 
	titulo VARCHAR(50) NOT NULL, 
	fecha DATETIME NOT NULL, 
	url VARCHAR(200) NOT NULL, 
	FOREIGN KEY (idusuario) REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE comentarios (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, 
	idfoto INT NOT NULL, 
	idusuario INT NOT NULL, 
	texto VARCHAR(300) NOT NULL, 
	fecha DATETIME NOT NULL, 
	FOREIGN KEY (idfoto) REFERENCES fotos(id) ON DELETE CASCADE ON UPDATE CASCADE, 
	FOREIGN KEY (idusuario) REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE valoraciones (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, 
	idfoto INT NOT NULL, 
	idusuario INT NOT NULL,
	valoracion INT NOT NULL,	
	FOREIGN KEY (idfoto) REFERENCES fotos(id) ON DELETE CASCADE ON UPDATE CASCADE, 
	FOREIGN KEY (idusuario) REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO usuarios (nombre,password) VALUES ('Nacho','123456');
INSERT INTO usuarios (nombre,password) VALUES ('Animal','Animal');
INSERT INTO usuarios (nombre,password) VALUES ('Cast','Cast');
INSERT INTO usuarios (nombre,password) VALUES ('Beer','Beer');
INSERT INTO usuarios (nombre,password) VALUES ('Car','Car');
INSERT INTO usuarios (nombre,password) VALUES ('Color','Color');
INSERT INTO usuarios (nombre,password) VALUES ('Ed','Ed');
INSERT INTO usuarios (nombre,password) VALUES ('Flora','Flora');
INSERT INTO usuarios (nombre,password) VALUES ('Moonsunsky','Moonsunsky');
INSERT INTO usuarios (nombre,password) VALUES ('Musica','Musica');
INSERT INTO usuarios (nombre,password) VALUES ('Sport','Sport');
INSERT INTO usuarios (nombre,password) VALUES ('Tech','Tech');

INSERT INTO seguidos (idusuario,idseguido) VALUES (1,2);
INSERT INTO seguidos (idusuario,idseguido) VALUES (1,3);
INSERT INTO seguidos (idusuario,idseguido) VALUES (1,4);
INSERT INTO seguidos (idusuario,idseguido) VALUES (1,6);
INSERT INTO seguidos (idusuario,idseguido) VALUES (1,7);

INSERT INTO seguidos (idusuario,idseguido) VALUES (2,8);
INSERT INTO seguidos (idusuario,idseguido) VALUES (2,9);

INSERT INTO seguidos (idusuario,idseguido) VALUES (3,10);
INSERT INTO seguidos (idusuario,idseguido) VALUES (3,11);

INSERT INTO seguidos (idusuario,idseguido) VALUES (4,1);
INSERT INTO seguidos (idusuario,idseguido) VALUES (4,6);

INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (1,'Naranjas', NOW(), '/usuarios/1/naranjas.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (1,'Mariposas', NOW(), '/usuarios/1/mariposas.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (1,'Naturaleza', NOW(), '/usuarios/1/naturaleza.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (1,'Arbol', NOW(), '/usuarios/1/arbol.jpg');

INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (2,'Animal1', NOW(), '/usuarios/2/animal.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (2,'Animal2', NOW(), '/usuarios/2/animal2.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (2,'Animal3', NOW(), '/usuarios/2/animal3.jpg');

INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (3,'Castillo1', NOW(), '/usuarios/3/castle.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (3,'Castillo2', NOW(), '/usuarios/3/castle2.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (3,'Castillo3', NOW(), '/usuarios/3/castle3.jpg');

INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (4,'Cerveza1', NOW(), '/usuarios/4/cerveza.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (4,'Cerveza2', NOW(), '/usuarios/4/cerveza2.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (4,'Cerveza3', NOW(), '/usuarios/4/cerveza3.jpg');

INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (5,'Coche1', NOW(), '/usuarios/5/coche.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (5,'Coche2', NOW(), '/usuarios/5/coche2.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (5,'Coche3', NOW(), '/usuarios/5/coche3.jpg');

INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (6,'Color1', NOW(), '/usuarios/6/color.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (6,'Color2', NOW(), '/usuarios/6/color2.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (6,'Color4', NOW(), '/usuarios/6/color3.jpg');

INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (7,'Edificio1', NOW(), '/usuarios/7/edificio.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (7,'Edificio2', NOW(), '/usuarios/7/edificio2.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (7,'Edificio3', NOW(), '/usuarios/7/edificio3.jpg');

INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (8,'Flor1', NOW(), '/usuarios/8/flores.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (8,'Flor2', NOW(), '/usuarios/8/flores2.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (8,'Flor3', NOW(), '/usuarios/8/flores3.jpg');

INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (9,'Vista1', NOW(), '/usuarios/9/moonsunsky.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (9,'Vista2', NOW(), '/usuarios/9/moonsunsky2.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (9,'Vista3', NOW(), '/usuarios/9/moonsunsky3.jpg');

INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (10,'Musica1', NOW(), '/usuarios/10/musica.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (10,'Musica2', NOW(), '/usuarios/10/musica2.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (10,'Musica3', NOW(), '/usuarios/10/musica3.jpg');

INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (11,'Deporte1', NOW(), '/usuarios/11/sport.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (11,'Deporte2', NOW(), '/usuarios/11/sport2.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (11,'Deporte3', NOW(), '/usuarios/11/sport3.jpg');

INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (12,'Tecnología1', NOW(), '/usuarios/12/tech.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (12,'Tecnología2', NOW(), '/usuarios/12/tech2.jpg');
INSERT INTO fotos (idusuario,titulo,fecha,url) VALUES (12,'Tecnología3', NOW(), '/usuarios/12/tech3.jpg');


INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (1,9,'Me gusta tu foto',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (2,9,'Me gusta tu foto',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (3,9,'Me gusta tu foto',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (4,9,'Me gusta tu foto',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (5,9,'Me gusta tu foto',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (6,9,'Me gusta tu foto',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (7,9,'Me gusta tu foto',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (8,9,'Me gusta tu foto',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (9,9,'Me gusta tu foto',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (10,9,'Me gusta tu foto',NOW());

INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (1,6,'Si muy bonita foto',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (2,7,'Que buena red social',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (3,8,'Probando a comentar jeje',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (4,10,':)',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (5,11,'ooooh',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (6,12,'Que guay!',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (7,5,'Sublime :D',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (8,4,'Moonsunsky hace spam xD',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (9,3,'This works',NOW());
INSERT INTO comentarios (idfoto, idusuario, texto, fecha) VALUES (10,2,'Lalalala',NOW());

INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (1,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (2,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (3,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (4,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (5,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (6,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (7,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (8,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (9,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (10,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (11,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (12,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (13,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (14,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (15,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (16,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (17,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (18,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (19,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (20,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (21,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (22,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (23,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (24,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (25,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (26,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (27,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (28,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (29,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (30,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (31,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (32,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (33,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (34,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (35,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (36,5,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (37,5,7);

INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (1,9,1);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (2,9,2);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (3,9,3);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (4,9,4);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (5,9,5);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (6,9,6);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (7,9,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (8,9,8);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (9,9,9);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (10,9,10);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (11,9,9);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (12,9,8);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (13,9,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (14,9,6);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (15,9,5);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (16,9,4);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (17,9,3);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (18,9,2);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (19,9,1);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (20,9,2);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (21,9,3);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (22,9,4);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (23,9,5);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (24,9,6);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (25,9,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (26,9,8);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (27,9,9);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (28,9,10);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (29,9,9);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (30,9,8);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (31,9,7);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (32,9,6);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (33,9,5);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (34,9,4);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (35,9,3);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (36,9,2);
INSERT INTO valoraciones (idfoto,idusuario,valoracion) VALUES (37,9,1);










