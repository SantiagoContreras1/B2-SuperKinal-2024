-- drop database if exists superKinal;

create database if not exists superKinal;

use superKinal;

create table Clientes(                       -- Sin FK HECHA
	clienteId int not null auto_increment,
    nombre varchar (30) not null,
    apellido varchar(30) not null,
    nit varchar(15) not null,
    telefono varchar(15),
    direccion varchar(150) not null,
    primary key PK_clienteId(clienteId)
);

create table Cargos(				-- Sin FK HECHA
	cargoId int not null auto_increment,
	nombreCargo varchar(30) not null,
    descripcionCargo varchar(100) not null,
    primary key PK_cargoId(cargoId)
);

create table Empleados(
	empleadoId int not null auto_increment,
    nombreEmpleado varchar(30) not null,
    apellidoEmpleado varchar(30) not null,
    sueldo decimal(10,2) not null,
    horaEntrada Time not null,
    horaSalida Time not null,
    cargoId int not null,
    encargadoId int,
    primary key PK_empleadoId(empleadoId),
    constraint FK_Empleados_Cargos foreign key(cargoId) references Cargos (cargoId),
    constraint FK_encargadoId foreign key(encargadoId) references Empleados(empleadoId)
);

create table Distribuidores(			-- Sin FK
	distribuidorId int not null auto_increment,
    nombreDistribuidor varchar(30) not null,
    direccionDistribuidor varchar(200) not null,
    nitDistribuidor varchar(15) not null,
    telefonoDistribuidor varchar(15) not null,
    web varchar(15),
    primary key PK_distribuidorId(distribuidorId)
);


create table CategoriaProductos(		-- Sin FK HECHA 
	categoriaProductosId int not null auto_increment,
    nombreCategoria varchar(30) not null,
    descripcionCategoria varchar(100) not null,
    primary key PK_categoriaProductosId(categoriaProductosId)
);

create table Compras(					-- Sin FK    UNIDA A DETALLECOMPRAS
	compraId int not null auto_increment,
    fechaCompra date not null,
    totalCompra decimal(10,2),
    primary key PK_compraId(compraId)
);



create table Facturas(
	facturaId int not null auto_increment,
    fecha date not null,
    hora time not null,
    clienteId int(11) not null,
    empleadoId int(11) not null,
    total decimal(10,2),
    primary key PK_facturaId(facturaId),
    constraint FK_Facturas_Clientes foreign key (clienteId) references Clientes(clienteId),
    constraint FK_Facturas_Empleados foreign key (empleadoId) references Empleados(empleadoId)
);

create table TicketSoporte(
	ticketSoporteId int not null auto_increment,
    descripcionTicket varchar(250) not null,
    estatus varchar(30) not null,
    clienteId int(11) not null,
    facturaId int(11),
    primary key PK_ticketSoporteId(ticketSoporteId),
    constraint FK_TicketSoporte_Clientes foreign key (clienteId) references Clientes(clienteId),
    constraint FK_TicketSoporte_Facturas foreign key (facturaId) references Facturas(facturaId)
);

create table Productos(
	productoId int not null auto_increment,
    nombreProducto varchar(50) not null,
    descripcionProducto varchar(100),
    cantidadStock int(11) not null,
    precioVentaUnitario decimal(10,2) not null,
    precioVentaMayor decimal(10,2) not null,
    precioCompra decimal(10,2) not null,
    imagenProducto longblob,
    distribuidorId int(11) not null,
    categoriaProductosId int(11) not null,
    
    primary key PK_productoId(productoId),
    constraint FK_Productos_Distribuidores foreign key (distribuidorId) references Distribuidores(distribuidorId),
    constraint FK_Productos_CategoriaProductos foreign key(categoriaProductosId) references CategoriaProductos(categoriaProductosId)
);

create table Promociones(
	promocionId int(11) not null auto_increment,
    precioPromocion decimal(10,2) not null,
    descripcionPromocion varchar(100),
    fechaInicio date not null,
    fechaFinalizacion date not null,
    productoId int(11) not null,
    
    primary key PK_promocionId(promocionId),
    constraint FK_Promociones_Productos foreign key(productoId) references Productos(productoId)
);



create table DetalleFactura(
	detalleFacturaId int(11) not null auto_increment,
    facturaId int(11) not null,
    productoId int(11) not null,
    
    primary key PK_DetalleFacturaId(detalleFacturaId),
    constraint FK_DetalleFacturas_Facturas foreign key (facturaId) references Facturas(facturaId),
    constraint FK_DetalleFacturas_Productos foreign key (productoId) references Productos(productoId)
);

insert into Clientes(nombre,apellido,nit,telefono,direccion) values
	('Elkyn', 'Samayoa','123456789-0', '6666-6666', 'El chiquito'),
    ('Jorge','Peralta','789456123-9','3333-3333', 'Amatitlan');
    

insert into Cargos (nombreCargo, descripcionCargo) values 
	('Gerente', 'Dirige y supervisa las operaciones generales de la empresa.'),
    ('Vendedor', 'Presenta, promociona y vende productos o servicios a los clientes.'),
    ('Desarrollador de Software', 'Diseña, crea, prueba y mantiene software.');
    
insert into Empleados (nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId) values 
	('Maria', 'Perez', 3500.00, '08:00:00', '17:00:00', 1),
    ('Juan', 'Lopez', 2800.00, '09:00:00', '18:00:00', 2),
    ('Pedro', 'Garcia', 4200.00, '10:00:00', '19:00:00', 3);



insert into Facturas(fecha,hora,clienteId,empleadoId,total) values
	('2024-04-26', '10:00:00', 1, 2,null),
    ('2024-04-25', '15:30:00', 2, 3,null),
    ('2024-04-24', '12:45:00', 1, 1,null);

insert into CategoriaProductos (nombreCategoria, descripcionCategoria) values
  ('Categoría 1', 'Descripción de la Categoría 1'),
  ('Categoría 2', 'Descripción de la Categoría 2'),
  ('Categoría 3', 'Descripción de la Categoría 3');

INSERT INTO Distribuidores (nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web)
VALUES
  ('Distribuidor 1', 'Dirección del Distribuidor 1', '1563325', '47854125',null),
  ('Distribuidor 2', 'Dirección del Distribuidor 2', '1256358', '65236523',null),
  ('Distribuidor 3', 'Dirección del Distribuidor 3', '154786', '96589658', 'distri3.com');

SELECT * FROM Distribuidores;

insert into Productos (nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra,imagenProducto, distribuidorId, categoriaProductosId) values
  ('Producto 1', 'Descripción del producto 1', 100, 12.50, 10.00, 8.50,null, 6, 1),
  ('Producto 2', 'Descripción del producto 2', 75, 15.00, 12.00, 9.50,null, 6, 1),
  ('Producto 3', 'Descripción del producto 3', 50, 18.00, 15.00, 12.00,null, 6, 1);
