use superKinal;

select * from Clientes;

-- Procedimientos Almacenados

-- CLIENTES ---------------------------------------------------------------------------------------------------------------
-- AGREGAR
DELIMITER $$
create procedure sp_AgregarCliente(in nom varchar(30),in ape varchar(30),in _nit varchar(15),in tel varchar(15),in dir varchar(150))
begin
	insert into Clientes(nombre,apellido,nit,telefono,direccion) values
		(nom,ape,_nit,tel,dir);
end $$
DELIMITER ;
-- LISTAR select
DELIMITER $$
create procedure sp_ListarClientes()
begin
	select * from Clientes;
end $$
DELIMITER ;

-- Eliminar delete
DELIMITER $$ 
create procedure sp_EliminarCliente(in cliId int)
begin
	delete from Clientes where clienteId = cliId;
end $$
DELIMITER ;

-- BUSCAR select where
DELIMITER $$
create procedure sp_BuscarCliente(in cliId int)
begin
	select * from Clientes where clienteId = cliId;
end $$
DELIMITER ;



-- Editar update
DELIMITER $$
create procedure sp_EditarCliente(in cliId int,in nom varchar(30),in ape varchar(30),in _nit varchar(15),in tel varchar(15),in dir varchar(150))
begin
	update Clientes	
		set 
			nombre = nom,
            apellido = ape,
            nit = _nit,
            telefono = tel,
            direccion = dir
				where clienteId = cliId;
	
end $$
DELIMITER ;

-- ------------------------------ Cargos -------------------------------------
-- agregar
DELIMITER $$
	create procedure sp_agregarCargo(nomC varchar(30),desCar varchar(100))
    begin
		insert into Cargos(nombreCargo,descripcionCargo) values
			(nomC, desCar);
    end $$
DELIMITER ;
call sp_agregarCargo('prueba', '1');
-- listar
DELIMITER $$
create procedure sp_listarCargos()
	begin
		select * from Cargos;
    end $$
DELIMITER ;
 call sp_listarCargos();
-- eliminar
DELIMITER $$
create procedure sp_eliminarCargo(carId int)
	begin
		delete from Cargos where cargoId = carId;
    end $$
DELIMITER ;

-- call sp_eliminarCargo(10);
 
-- buscar
DELIMITER $$
create procedure sp_buscarCargo(carId int)
	begin 
		select *from Cargos
        where cargoId = carId;
    end $$
DELIMITER ;
 
-- editar
DELIMITER $$
create procedure sp_editarCargos(carId int, nomCar varchar(30), desCar varchar(100)  )
	begin
        update Cargos
			set
            nombreCargo = nomCar,
            descripcionCargo = desCar
            where cargoId = carId;			
    end $$
DELIMITER ;

-- call sp_editarCargos(3,'Supervisor de Bodegas','Encargado de controlar el flujo de stock');

 
-- crud de Distribuidores
-- agregar
DELIMITER $$
	create procedure sp_agregarDistribuidores(nomDis varchar(30), dirDis varchar(200), nitDis varchar(15), telDis varchar(15), web varchar(50))
    begin
		insert into Distribuidores(nombreDistribuidor,direccionDistribuidor,nitDistribuidor,telefonoDistribuidor,web) values
			(nomDis, dirDis, nitDis, telDis, web);
    end $$
DELIMITER ;

call sp_agregarDistribuidores('aaa','ssss','33330','9898','kk');
 
-- listar
DELIMITER $$
create procedure sp_listarDistribuidores()
	begin
		select * from Distribuidores;
    end $$
DELIMITER ;

-- call sp_listarDistribuidores();
 
-- eliminar
DELIMITER $$
create procedure sp_eliminarDistribuidores(disId int)
	begin
		delete from Distribuidores
		where distribuidorId = disId;
    end $$
DELIMITER ;
 
-- buscar
DELIMITER $$
create procedure sp_buscarDistribuidores(disId int)
	begin 
		select *from Distribuidores
        where distribuidorId = disId;
    end $$
DELIMITER ;
 
-- editar
DELIMITER $$
create procedure sp_editarDistribuidores(disId int,nomDis varchar(30), dirDis varchar(200), nitDis varchar(15), telDis varchar(15), web varchar(50))
	begin
        update Distribuidores
			set
            nombreDistribuidor = nomDis,
            direccionDistribuidor = dirDis,
            nitDistribuidor = nitDis,
            telefonoDistribuidor = telDis,
            web = web
            where distribuidorId = disId;			
    end $$
DELIMITER ;
 
-- ------------------------- CategoriaProductos ------------------------------------------
-- agregar
DELIMITER $$
	create procedure sp_agregarCategoriaProducto(nomCat varchar(30), desCat varchar(100))
    begin
		insert into CategoriaProductos(nombreCategoria, descripccionCategoria) values
			(nomCat, desCat);
    end $$
DELIMITER ;
 
-- listar
DELIMITER $$
create procedure sp_listarCategoriaProductos()
	begin
		select * from CategoriaProductos;
    end $$
DELIMITER ;
 
-- eliminar
DELIMITER $$
create procedure sp_eliminarCategoriaProducto(catId int)
	begin
		delete from CategoriaProductos
		where categoriaProductosId = catId;
    end $$
DELIMITER ;
 
-- buscar
DELIMITER $$
create procedure sp_buscarCategoriaProducto(catId int)
	begin 
		select *from CategoriaProductos
        where categoriaProductoId = catId;
    end $$
DELIMITER ;
 
-- Editar
DELIMITER $$
create procedure sp_editarCategoriaProductos(catId int,nomCat varchar(30), desCat varchar(100))
	begin
        update CategoriaProductos
			set
            nombreCategoria = nomCat,
            descripccionCategoria = desCat
            where categoriaProductoId = catId;			
    end $$
DELIMITER ;






























/*
DELIMITER $$
create procedure sp_ListarEmpleados()
	begin 
        select e.empleadoId, e.nombreEmpleado, e.apellidoEmpleado, e.sueldo, e.horaEntrada, e.horaSalida, e.encargadoId,
        CONCAT('ID: ', e.cargoId, ' | ', 'Cargo: ', c.nombreCargo, ' | ', 'Descripcion: ', c.descripcionCargo) as 'Cargo'
		from Empleados e
		join Cargos c ON e.cargoId = c.cargoId;
    end $$
DELIMITER ;


*/


-- ------------------------------ Compras ----------------------------------------------------
DELIMITER $$
	create procedure sp_AgregarCompra(fec date, tot decimal(10,2))
    begin
		insert into Clientes(fechaCompra.totalCompra) values
			(fec,tot);
    end $$
DELIMITER ;
 
DELIMITER $$
	create procedure sp_ListarCompra()
    begin
		select* from Compras;
    end$$
DELIMITER ;
 
DELIMITER $$
	create procedure sp_EliminarCompra(comId int)
    begin
		delete from Comprars
        where compraId = comId;
    end$$
DELIMITER ;
 
DELIMITER $$
create procedure sp_buscarCompras(comId int)
	BEGIN
		select * 
			from Compras
            where compraId = comId;
	END $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_editarCompra(comId int, fec date, tot decimal(10,2))
	BEGIN
		update Compras
			set
				fechaCompra = fec,
                totalCompra = tot
					where compraId = comId;
	END $$
DELIMITER ;
 
-- ----------------------------------- DetalleCompra
 
DELIMITER $$
	create procedure sp_AgregarDetalleCompra(cantC int, proId int,comId int)
    begin
		insert into DetalleCompra(cantidadCompra,productoId,compraId) values
			(cantC,proId,comId);
    end $$
DELIMITER ;
 
DELIMITER $$
	create procedure sp_ListarDetalleCompra()
    begin
		select* from DetalleCompra;
    end$$
DELIMITER ;
 
DELIMITER $$
	create procedure sp_EliminarDetalleCompra(dtId int)
    begin
		delete from DetalleCompra
        where detalleCompraId = dtId;
    end$$
DELIMITER ;
 
DELIMITER $$
create procedure sp_buscarDetalleCompra(dtId int)
	BEGIN
		select * 
			from DetalleCompra 
            where detalleCompraId = dtId;
	END $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_editarDetalleCompra(dtId int,cantC int, proId int,comId int)
	BEGIN
		update DetalleCompra
			set
				cantidadCompra = cantC,
				productoId = proId,
				compraId = comId
					where dtId = detalleCompraId;
	END $$
DELIMITER ;






-- ------------ Productos ---------------------------------------------------------------------
-- Agregar
DELIMITER $$
create procedure sp_AgregarProducto(in nomP varchar(50),in desP varchar(100),in canS int(11),in preVU decimal(10,2), in preVM decimal(10,2), in preC decimal(10,2),in imgP longblob,in disId int(11),in catPId int(11))
begin
	insert into Productos(nombreProducto,descripcionProducto,cantidadStock,precioVentaUnitario,precioVentaMayor,precioCompra,imagenProducto,distribuidorId,categoriaProductosId) values
		(nomP,desP,canS,preVU,preVM,preC,imgP,disId,catPId);
	
end $$
DELIMITER ;
select * from Productos;
-- Listar
DELIMITER $$
create procedure sp_ListarProducto()
begin
	select p.productoId,p.nombreProducto,p.descripcionProducto,p.cantidadStock,p.precioVentaUnitario,p.precioVentaMayor,p.precioCompra,p.imagenProducto,
    CONCAT('ID: ',p.distribuidorId, ' | ','Nombre: ',d.nombreDistribuidor) as 'Distribuidores',
    CONCAT('ID: ',cp.categoriaProductosId, ' | ',cp.nombreCategoria) as 'Categoria'from Productos p
    join Distribuidores d on p.distribuidorId = d.distribuidorId
    join CategoriaProductos cp on p.categoriaProductosId = cp.categoriaProductosId;
end $$
DELIMITER ;


-- Eliminar
DELIMITER $$
create procedure sp_EliminarProducto(in proId int)
begin
	delete from Productos where productoId = proId; 
end $$
DELIMITER ;

-- Buscar
DELIMITER $$
create procedure sp_BuscarProducto(proId int)
begin
	select * from Productos where productoId = proId;
end $$
DELIMITER ;

--  Editar	
DELIMITER $$
create procedure sp_EditarProducto(proId int,in nomP varchar(50),in desP varchar(100),in canS int(11),in preVU decimal(10,2), in preVM decimal(10,2), in preC decimal(10,2),in imgP blob,in disId int(11),in catPId int(11))
begin
	Update Productos
		set
			nombreProducto = nomP,
            descripcionProducto = desP,
            cantidadStock = canS,
            precioVentaUnitario = preVU,
            precioVentaMayor = preVM,
            precioCompra = preC,
            imagenProducto = imaP,
            distribuidorId = disId,
            categoriaProductoId = catPId
				where productoId = proId;
	
end $$
DELIMITER ;


-- ----------- Facturas ----------------------
-- Agregar
DELIMITER $$
create procedure sp_AgregarFactura(fech date, hor time, cliId int(11), empId int(11), tot decimal(10,2))
begin
	insert into Facturas(fecha,hora,clienteId,empleadoId,total)values
		(fech,hor,cliId,empId,tot);
end $$
DELIMITER ;

-- Listar
DELIMITER $$
create procedure sp_ListarFacturas()
begin
	select * from Facturas;
end $$
DELIMITER ;

-- Eliminar
DELIMITER $$
create procedure sp_EliminarFactura(facId int)
begin
	delete from Facturas where facturaId = facId;
end $$
DELIMITER ;

-- Buscar
DELIMITER $$ 
create procedure sp_BuscarFactura(facId int)
begin
	select * from Facturas where facturaId = facId;

end $$
DELIMITER ;

-- Editar
DELIMITER $$
create procedure sp_EditarFactura(facId int,fech date, hor time, cliId int(11), empId int(11), tot decimal(10,2))
begin
	Update Facturas
		set
			fecha = fech,
            hora = hor,
            clienteId = cliId,
            empleadoId = empId,
            total = tot
				where facturaId = facId;
end $$
DELIMITER ;

-- ------------------ DetalleFacturas ------------------------------------------------
-- Agregar
DELIMITER $$
create procedure sp_AgregarDetalleFactura(facId int(11), proId int(11))
begin
	insert into DetalleFactura(facturaId, productoId) values (facId, proId);
end $$
DELIMITER ;


-- Listar
DELIMITER $$
create procedure sp_ListarDetalleFactura()
begin
	select * from DetalleFactura;
end $$
DELIMITER ;

-- Eliminar
DELIMITER $$
create procedure sp_EliminarDetalleFactura(detFId int(11))
begin
	delete from DetalleFactura where detalleFacturaId = detFId;
end $$
DELIMITER ;

-- Buscar
DELIMITER $$
create procedure sp_BuscarDetalleFactura(detFId int(11))
begin
	select * from DetalleFactura where detalleFacturaId = detFId;
end $$
DELIMITER ;

-- Editar
DELIMITER $$
create procedure sp_EditarDetalleFactura(detFId int(11),facId int(11), proId int(11))
begin
	Update DetalleFactura
		set
			facturaId = facId,
            productoId = proId
				where detalleFacturaId = detFId;
end $$
DELIMITER ;

-- call sp_AgregarDetalleFactura(1,1);
DELIMITER $$
	create procedure sp_AgregarEmpleado(nom varchar(30), ape varchar(30), sue decimal (10,2), horE Time, horS time, carId int)
		begin
        
			
			insert into Empleados (nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId) values 
				(nom, ape, sue, horE, horS, carId);
			
			
        end $$
DELIMITER ;
 
-- call  sp_AgregarEmpleado('1', '2', 2.5, '10:10:10', '10:10:10', 1);
 
DELIMITER $$
create procedure sp_ListarEmpleados()
	begin 
        select e.empleadoId, e.nombreEmpleado, e.apellidoEmpleado, e.sueldo, e.horaEntrada, e.horaSalida, e.encargadoId,
        CONCAT('ID: ', e.cargoId, ' | ', 'Cargo: ', c.nombreCargo, ' | ', 'Descripcion: ', c.descripcionCargo) as 'Cargo'
		from Empleados e
		join Cargos c ON e.cargoId = c.cargoId;
    end $$
DELIMITER ;
 
call sp_ListarEmpleados();
 
DELIMITER $$
create procedure sp_EditarEmpleado(empId int, nom varchar(30), ape varchar(30), sue decimal (10,2), horE Time, horS time, carId int)
	begin
		update Empleados
			set nombreEmpleado = nom,
				apellidoEmpleado = ape,
                sueldo = sue,
                horaEntrada = horE,
                horaSalida = horS,
                cargoId = carId
                where empId = empleadoId;
    end $$
DELIMITER ;
 
-- call  sp_EditarEmpleado(1,'1', '2', 2.5, '10:10:10', '10:10:10', 1);
 
DELIMITER $$
create procedure sp_EliminarEmpleado(empId int)
	begin
		delete from Empleados
			where empId = empleadoId;
    end $$
DELIMITER ;
 
-- call sp_EliminarEmpleado(2);
 
DELIMITER $$
create procedure sp_BuscarEmpleado(empId int)
	begin
		select e.empleadoId, e.nombreEmpleado, e.apellidoEmpleado, e.sueldo, e.horaEntrada, e.horaSalida, e.encargadoId,
        CONCAT('ID: ', e.cargoId, ' | ', 'Cargo: ', c.nombreCargo, ' | ', 'Descripcion: ', c.descripcionCargo) as 'Cargo'
		from Empleados e
		join Cargos c ON e.cargoId = c.cargoId
		where empleadoId = empId;
    end $$
DELIMITER ;
 
-- call sp_BuscarEmpleado(6);
 
DELIMITER $$
create procedure sp_AsignarEncargado(empId int, encId int)
	begin
    Update Empleados
		set encargadoId = encId 
			where empId = empleadoId;
    end $$
DELIMITER ;
 
-- call sp_AsignarEncargado(1,1);
 
-- ---------------------- ticketSoporte -------------------------------------
 DELIMITER $$
	create procedure sp_AgregarTicketSoporte(des varchar(250), cliId int(11),facId int)
		begin
			insert into TicketSoporte (descripcionTicket, estatus, clienteId,facturaId) values 
				(des, 'Recién Creado', cliId,facId);
        end $$
DELIMITER ;

 call sp_AgregarTicketSoporte('aaaa',1,1);
DELIMITER $$
create procedure sp_ListarTicketSoporte()
	begin 
		select TS.ticketSoporteId,TS.descripcionTicket, TS.estatus,
        concat('Id: ',C.clienteId,' | ',C.nombre,' ',C.apellido) AS 'cliente',
        TS.facturaId from TicketSoporte TS
        
        join Clientes C on TS.clienteId = C.clienteId;  -- join Tabla on Tabla.atributo
    end $$
DELIMITER ;
 
-- call sp_ListarTicketSoporte();
 
DELIMITER $$
create procedure sp_EditarTicketSoporte(ticId int, des varchar(250), est varchar(30), cliId int,facId int(11))
	begin
		update TicketSoporte
			set descripcionTicket = des,
				estatus = est,
                clienteId = cliId,
                facturaId = facId
                where ticId = ticketSoporteId;
    end $$
DELIMITER ;
 
-- call sp_EditarTicketSoporte(1, 'ppp', 'reciente',1);
 
DELIMITER $$
create procedure sp_EliminarTicketSoporte(ticId int)
	begin
		delete from TicketSoporte
			where ticId = ticketSoporteId;
    end $$
DELIMITER ;
 
call sp_EliminarTicketSoporte(2);
 
DELIMITER $$
create procedure sp_BuscarTicketSoporte(ticId int)
	begin
		select *
			from TicketSoporte
            where ticId = ticketSoporteId;
    end $$
DELIMITER ;
 
call sp_BuscarTicketSoporte(1);
