CREATE DEFINER=`root`@`localhost` PROCEDURE `reset_tables`()
BEGIN

drop table apuntado;
drop table Fecha_Imparticion;
drop table Imparte;
drop table Terceros;
drop table Recibo;
drop table Actividad_Pericial;
drop table Perito;
drop table Colegiado;
drop table colectivos_asignados;
drop table Actividad_Formativa;
drop table colectivo;

create table colectivo(
	nombre_colectivo varchar(20),
	
	CONSTRAINT pk_colectivo PRIMARY KEY (nombre_colectivo)
);

create table Colegiado(
    id_colegiado VARCHAR(15),
    dni varchar(9),
    nombre varchar(25),
    apellidos VARCHAR(50),
    ciudad varchar(25),
    telefono VARCHAR(9),
    iban varchar(24),
    tipoSolicitud VARCHAR(15),
    fecha Date,
    titulacion VARCHAR(25),
	centro VARCHAR(25),
	año int,
    tipo int,
    
    constraint ch_colegiado CHECK(tipoSolicitud in ("PENDIENTE","VALIDANDO","ACEPTADA","RECHAZADA","DE BAJA")),
    CONSTRAINT pk_colegiado PRIMARY KEY (id_colegiado)
);


create table Actividad_Formativa(
    nombre_curso varchar(20),
    precio DOUBLE,
    inicio_inscripcion Date,
    fin_inscripcion Date,
    fecha_orientativa Date,
    numero_plazas Decimal(4),
    is_open boolean,
    estado varchar(10),
    cancelable bool,
    a_devolver double,
    
    CONSTRAINT ch_AF CHECK(estado in ("VIGENTE","CANCELADA")),
    CONSTRAINT pk_AF PRIMARY KEY (nombre_curso)
);

create table colectivos_asignados(
	nombre_curso varchar(20),
    nombre_colectivo varchar(20),
    precio_colectivo double,
    
    CONSTRAINT pk_colectivo_asig PRIMARY KEY (nombre_curso, nombre_colectivo),
    CONSTRAINT fk_colectivo_asig_AF FOREIGN KEY (nombre_curso) REFERENCES Actividad_Formativa(nombre_curso),
    CONSTRAINT fk_colectivo_asig_col FOREIGN KEY (nombre_colectivo) REFERENCES colectivo(nombre_colectivo)
);

create table apuntado(
    dni VARCHAR(15),
    nombre_curso varchar(20),
    fecha_inscripcion Date,
    pagado boolean,
    estado varchar(12),
	cantidad_abonada DOUBLE,
    cantidad_devolver DOUBLE,
    posicion_cola int,
	
	CONSTRAINT ch_ES CHECK(estado in ("CANCELADO","INSCRITO","PRE-INSCRITO")),    
    CONSTRAINT pk_FI PRIMARY KEY (nombre_curso, dni),
    CONSTRAINT fk_AF FOREIGN KEY (nombre_curso) REFERENCES Actividad_Formativa(nombre_curso)
);

create table Fecha_Imparticion(
    nombre_curso varchar(20),
    fecha Date,
	hora varchar(5),
	duracion Integer,
    
    CONSTRAINT pk_FI PRIMARY KEY (nombre_curso, fecha),
    CONSTRAINT fk_AF_2 FOREIGN KEY (nombre_curso) REFERENCES Actividad_Formativa(nombre_curso)
);

create table Recibo(
	id_recibo VARCHAR(15),
	emision Date,
	dni VARCHAR(9),
	iban VARCHAR(24),
	cantidad DOUBLE,
    pagado bool,

	CONSTRAINT pk_recibo PRIMARY KEY (ID_RECIBO)
);

create table Perito(
	id_perito varchar(15),
	id_colegiado varchar(15),
    posicion_lista integer,
    fecha_tope_renovacion date,
    activo bool,
    
    constraint pk_perito PRIMARY KEY (id_perito),
    CONSTRAINT fk_perito_Col FOREIGN KEY (id_colegiado) REFERENCES Colegiado(id_colegiado)
);

create table Actividad_Pericial(
	numero Integer,
	tipo_pericial varchar(10),
	prioridad varchar(10),
	nombre_solicitante varchar(20),
	mail_solicitante varchar(20),
	telefono_solicitante Integer,
	descripcion varchar(100),
	id_perito varchar(15),
	estado varchar(10),
	
	CONSTRAINT pk_ap PRIMARY KEY (numero),
    constraint fk_perito foreign key (id_perito) references Perito(id_perito),
	CONSTRAINT ch_tipo CHECK(tipo_pericial in ("JUDICIAL","PARTE")),
	CONSTRAINT ch_urgencia CHECK(prioridad in ("URGENTE","NORMAL")),
	CONSTRAINT ch_estado CHECK(estado in ("PENDIENTE","ASIGNADA","TERMINADA"))
);

create table Terceros(
	nombre varchar(25),
	dni varchar(25),
	descripcion varchar(50),
    colectivo varchar(20),
	
	constraint pk_profesor primary key (dni, colectivo)
);

create table Imparte(
	nombre_curso varchar(20),
	profesor varchar(15),

	CONSTRAINT pk_imparte PRIMARY KEY (nombre_curso,profesor),
	CONSTRAINT fk_nc FOREIGN KEY (nombre_curso) REFERENCES Actividad_Formativa(nombre_curso),
	CONSTRAINT fk_ic FOREIGN KEY (profesor) REFERENCES Terceros(dni)
);

END