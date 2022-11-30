CREATE DEFINER=`root`@`localhost` PROCEDURE `add_elements`()
BEGIN
	INSERT INTO Actividad_Formativa(nombre_curso,precio, is_open, fecha_orientativa,estado) VALUES('actividad_1', 100, false, "2022-7-7","VIGENTE");
    INSERT INTO Actividad_Formativa(nombre_curso,precio, is_open, fecha_orientativa,estado) VALUES('actividad_3', 20, false, "2022-8-8","VIGENTE");
    INSERT INTO Actividad_Formativa(nombre_curso,precio, is_open, fecha_orientativa,estado) VALUES('actividad_4', 600, false, "2022-7-7","VIGENTE");
    INSERT INTO Actividad_Formativa(nombre_curso,precio, is_open, fecha_orientativa,estado) VALUES('actividad_6', 310, false, "2022-7-7","VIGENTE");
    INSERT INTO Actividad_Formativa(nombre_curso,precio, is_open, fecha_orientativa,estado) VALUES('actividad_7', 140, false, "2022-7-7","VIGENTE");
    INSERT INTO Actividad_Formativa(nombre_curso,precio, is_open, fecha_orientativa,estado) VALUES('actividad_9', 104, false, "2022-7-7","VIGENTE");
    INSERT INTO Actividad_Formativa(nombre_curso,precio, is_open, fecha_orientativa,estado) VALUES('actividad_11', 300, false, "2022-7-7","VIGENTE");
    INSERT INTO Actividad_Formativa(nombre_curso,precio, is_open, fecha_orientativa,estado) VALUES('actividad_12', 200, false, "2022-7-7","VIGENTE");
    INSERT INTO Actividad_Formativa(nombre_curso,precio, is_open, fecha_orientativa,estado) VALUES('actividad_14', 1200, false, "2022-7-7","VIGENTE");
    
    INSERT INTO Actividad_Formativa(nombre_curso,precio, is_open, inicio_inscripcion, fin_inscripcion, numero_plazas, fecha_orientativa,estado) VALUES('actividad_2', 50, true, "2022-9-30", "2023-10-26", 50,  "2024-7-7","VIGENTE");
    INSERT INTO Actividad_Formativa(nombre_curso,precio, is_open, inicio_inscripcion, fin_inscripcion, numero_plazas, fecha_orientativa,estado) VALUES('actividad_13', 1030, true, "2022-9-30", "2023-10-20", 2,"2024-7-7","VIGENTE");
    INSERT INTO Actividad_Formativa(nombre_curso,precio, is_open, inicio_inscripcion, fin_inscripcion, numero_plazas, fecha_orientativa,estado) VALUES('actividad_10', 600, true, "2022-11-01", "2023-11-13", 50 , "2024-7-7","VIGENTE");
    INSERT INTO Actividad_Formativa(nombre_curso,precio, is_open, inicio_inscripcion, fin_inscripcion, numero_plazas, fecha_orientativa,estado) VALUES('actividad_8', 130, true, "2022-9-10", "2023-09-30", 15, "2024-7-7","VIGENTE");
    INSERT INTO Actividad_Formativa(nombre_curso,precio, is_open, inicio_inscripcion, fin_inscripcion, numero_plazas, fecha_orientativa,estado) VALUES('actividad_5', 250, true, "2023-9-30", "2023-10-25", 1, "2024-7-7","VIGENTE");
    
	insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_1', "2022-10-30");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_10', "2022-10-30");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_1', "2022-10-28");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_1', "2022-10-29");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_2', "2022-10-30");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_2', "2022-11-01");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_3', "2022-12-10");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_4', "2022-11-04");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_5', "2022-10-30");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_5', "2022-11-10");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_6', "2022-10-30");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_7', "2022-11-30");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_7', "2022-12-03");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_7', "2022-12-05");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_8', "2022-10-30");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_8', "2022-10-27");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_8', "2022-11-03");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_9', "2022-10-29");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_10', "2022-11-15");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_11', "2022-10-17");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_11', "2022-10-20");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_12', "2022-10-22");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_13', "2022-10-25");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES('actividad_13', "2022-10-30");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES("actividad_14", "2022-11-10");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES("actividad_14", "2022-11-11");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES("actividad_14", "2022-11-13");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES("actividad_14", "2022-11-15");
    insert into Fecha_imparticion(nombre_curso,fecha)VALUES("actividad_14", "2022-11-17");

    INSERT INTO Colegiado(id_colegiado,dni,nombre,apellidos,tipoSolicitud,telefono,iban,tipo) VALUES("1",1,"Juan","García","PENDIENTE",111111111,"iban1",1);
	INSERT INTO Colegiado(id_colegiado,dni,nombre,apellidos,tipoSolicitud,telefono,iban,tipo) VALUES("2",2,"Manolo","Lopez","PENDIENTE",222222222,"iban2",1);
	INSERT INTO Colegiado(id_colegiado,dni,nombre,apellidos,tipoSolicitud,telefono,iban,tipo) VALUES("3",3,"Marcos","Suarez","PENDIENTE",333333333,"iban3",1);
	INSERT INTO Colegiado(id_colegiado,dni,nombre,apellidos,tipoSolicitud,telefono,iban,tipo) VALUES("4",4,"Alberto","Ruiz","PENDIENTE",444444444,"iban4",1);
    
    INSERT INTO Colegiado(id_colegiado,dni,nombre,apellidos,tipoSolicitud,telefono,iban,tipo) VALUES("5",5,"Juan","Lopez","PENDIENTE",123456789,"iban5",0);
	INSERT INTO Colegiado(id_colegiado,dni,nombre,apellidos,tipoSolicitud,telefono,iban,tipo) VALUES("6",6,"Pepe","Ruiz","PENDIENTE",333444333,"iban6",0);
	INSERT INTO Colegiado(id_colegiado,dni,nombre,apellidos,tipoSolicitud,telefono,iban,tipo) VALUES("7",7,"Ana","Suarez","PENDIENTE",356748433,"iban7",0);
	INSERT INTO Colegiado(id_colegiado,dni,nombre,apellidos,tipoSolicitud,telefono,iban,tipo) VALUES("8",8,"Belen","Ruiz","PENDIENTE",444777444,"iban8",0);
    
    INSERT INTO apuntado VALUES("1","actividad_2","2022-1-1",false,"INSCRITO",10,0,0);
	INSERT INTO apuntado VALUES("1","actividad_3","2022-1-1",false,"INSCRITO",20,0,0);
	INSERT INTO apuntado VALUES("2","actividad_5","2022-1-1",false,"INSCRITO",30,0,0);
	INSERT INTO apuntado VALUES("3","actividad_8","2022-1-1",false,"INSCRITO",15,0,0);
	INSERT INTO apuntado VALUES("4","actividad_13","2022-1-1",false,"INSCRITO",60,0,0);
	INSERT INTO apuntado VALUES("3","actividad_3","2022-1-1",false,"INSCRITO",9,0,0);
    INSERT INTO apuntado VALUES("4","actividad_10","2022-1-1",false,"INSCRITO",10,0,0);
	INSERT INTO apuntado VALUES("3","actividad_10","2022-1-1",false,"INSCRITO",1,0,0);
	INSERT INTO apuntado VALUES("Juan","actividad_10","2022-1-1",false,"INSCRITO",1,0,0);
	INSERT INTO apuntado VALUES("3","actividad_10","2022-1-1",true,"INSCRITO",9,0,0);
    
    INSERT INTO RECIBO(id_recibo,emision,dni,iban,cantidad,pagado) VALUES("1","2022-2-2","1","65",65,true);
	INSERT INTO RECIBO(id_recibo,emision,dni,iban,cantidad,pagado) VALUES("2","2022-12-12","3","75",65,false);
	INSERT INTO RECIBO(id_recibo,emision,dni,iban,cantidad,pagado) VALUES("3","2022-3-2","2","65",65,true);
	INSERT INTO RECIBO(id_recibo,emision,dni,iban,cantidad,pagado) VALUES("4","2021-12-12","4","75",65,false);
	INSERT INTO RECIBO(id_recibo,emision,dni,iban,cantidad,pagado) VALUES("5","2022-12-12","3","75",65,true);
    
    INSERT INTO TERCEROS(nombre,dni,descripcion,colectivo) VALUES("Manolo", "10","Ciberseguridad","profesor");
    INSERT INTO TERCEROS(nombre,dni,descripcion,colectivo) VALUES("Pepe","11","Web","profesor");
    INSERT INTO TERCEROS(nombre,dni,descripcion,colectivo) VALUES("Juan","12","Java","profesor");
    INSERT INTO TERCEROS(nombre,dni,descripcion,colectivo) VALUES("Carlos","13","Python","profesor");
    
    INSERT INTO TERCEROS(nombre,dni,colectivo) VALUES("Manolo", "16","estudiante");
    INSERT INTO TERCEROS(nombre,dni,colectivo) VALUES("Pepe","17","estudiante");
    INSERT INTO TERCEROS(nombre,dni,colectivo) VALUES("Juan","18","estudiante");
    INSERT INTO TERCEROS(nombre,dni,colectivo) VALUES("Carlos","19","estudiante");
    
    INSERT INTO colectivo(nombre_colectivo) values("colegiado");	
    INSERT INTO colectivo(nombre_colectivo) values("pre-colegiado");
	INSERT INTO colectivo(nombre_colectivo) values("estudiante");
	INSERT INTO colectivo(nombre_colectivo) values("profesor");    
        
    insert into colectivos_asignados(nombre_curso,nombre_colectivo,precio_colectivo) values ("actividad_10","colegiado","550");
    insert into colectivos_asignados(nombre_curso,nombre_colectivo,precio_colectivo) values ("actividad_10","profesor","540");
    insert into colectivos_asignados(nombre_curso,nombre_colectivo,precio_colectivo) values ("actividad_10","estudiante","600");
    insert into colectivos_asignados(nombre_curso,nombre_colectivo,precio_colectivo) values ("actividad_10","pre-colegiado","580");
    
    insert into colectivos_asignados(nombre_curso,nombre_colectivo,precio_colectivo) values ("actividad_2","colegiado","50");
    insert into colectivos_asignados(nombre_curso,nombre_colectivo,precio_colectivo) values ("actividad_2","profesor","40");
    insert into colectivos_asignados(nombre_curso,nombre_colectivo,precio_colectivo) values ("actividad_2","estudiante","60");
    insert into colectivos_asignados(nombre_curso,nombre_colectivo,precio_colectivo) values ("actividad_2","pre-colegiado","80");
    
    insert into colectivos_asignados(nombre_curso,nombre_colectivo,precio_colectivo) values ("actividad_13","colegiado","1250");
    insert into colectivos_asignados(nombre_curso,nombre_colectivo,precio_colectivo) values ("actividad_13","profesor","1240");
    insert into colectivos_asignados(nombre_curso,nombre_colectivo,precio_colectivo) values ("actividad_13","pre-colegiado","1080");
    
    insert into colectivos_asignados(nombre_curso,nombre_colectivo,precio_colectivo) values ("actividad_8","colegiado","150");
    insert into colectivos_asignados(nombre_curso,nombre_colectivo,precio_colectivo) values ("actividad_8","estudiante","160");
    
    INSERT INTO perito (id_perito, id_colegiado, fecha_tope_renovacion, activo) VALUES("1", "1", "2021-1-1", false);
    INSERT INTO perito (id_perito, id_colegiado, fecha_tope_renovacion, activo) VALUES("2", "2", "2020-1-1", false);
    INSERT INTO perito (id_perito, id_colegiado, posicion_lista, fecha_tope_renovacion, activo) VALUES("3", "4", "1", "2023-1-1", true);
    
END