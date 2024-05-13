use superKinal;

-- total -----
DELIMITER $$
	create procedure sp_asignarTotal(in tot decimal(10,2),in factID int)
    begin
		update Facturas
			set total = tot
            where facturaID = factID;
    end$$
DELIMITER ;
 
DELIMITER $$
CREATE Function FN_precio(proId int) returns decimal(10,2) deterministic
BEGIN
	DECLARE precio DECIMAL(10,2);
    DECLARE i INT DEFAULT 1;
    DECLARE curPromocionId INT;
    DECLARE precioPromocion DECIMAL(10,2);
    DECLARE fechaInicio DATE;
    DECLARE fechaFinalizacion DATE;
    DECLARE productoId INT;

    DECLARE cursorPromociones CURSOR FOR
        SELECT promocionId, precioPromocion, fechaInicio, fechaFinalizacion, productoId FROM Promociones;

    OPEN cursorPromociones;

    precioLoop: LOOP
        FETCH cursorPromociones INTO curPromocionId, precioPromocion, fechaInicio, fechaFinalizacion, productoId;
        IF (i = curPromocionId) AND (productoId = proId) AND (CURDATE() BETWEEN fechaInicio AND fechaFinalizacion) THEN
            SET precio = precioPromocion;
        ELSE
            SET precio = precioVentaUnitario;
        END IF;

        IF i = (SELECT COUNT(*) FROM Productos) THEN
            LEAVE precioLoop;
        END IF;

        SET i = i + 1;
    END LOOP precioLoop;

    CLOSE cursorPromociones;
    RETURN precio;
END $$
DELIMITER ;
 
 
-- funcion calcular total
DELIMITER $$
	create function fn_calcularTotal(factID int) returns decimal(10,2) deterministic
    begin
		DECLARE total DECIMAL(10,2) DEFAULT 0.0;
		DECLARE precio DECIMAL(10,2);
		DECLARE i INT DEFAULT 1;
		DECLARE curFacturaID, curProductoID INT;

		DECLARE cursorDetalleFactura CURSOR FOR 
			SELECT DF.facturaId, DF.productoId FROM DetalleFactura DF;

		OPEN cursorDetalleFactura;

		totalLoop: LOOP
			FETCH cursorDetalleFactura INTO curFacturaID, curProductoID;
			IF (factID = curFacturaID) THEN
				SET precio = (FN_precio(curProductoID));
				SET total = total + precio;
			END IF;

			IF i = (SELECT COUNT(*) FROM DetalleFactura) THEN
				LEAVE totalLoop;
			END IF;

			SET i = i + 1;
		END LOOP totalLoop;

		CLOSE cursorDetalleFactura;

		CALL sp_asignarTotal(total, factID);
		RETURN total;
    end$$
DELIMITER ;
 
select fn_calcularTotal(9);
select * from facturas;
DELIMITER $$
	create trigger tg_totalFactura
    after insert on DetalleFactura
    for each row
    begin
		declare total decimal(10,2);
        set total = fn_calcularTotal(new.facturaID);
    end$$
DELIMITER ;
 
DELIMITER $$
create trigger tg_restarStock
before insert on detalleFactura
for each row
begin
    DECLARE cantidad_stock INT;
    SELECT cantidadStock INTO cantidad_stock FROM Productos WHERE productoId = NEW.productoId;
    
    IF cantidad_stock = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No hay existencias de ese producto, intentelo mas tarde';
    ELSE
        UPDATE Productos SET cantidadStock = cantidad_stock - 1 WHERE productoId = NEW.productoId;
    END IF;
end $$
DELIMITER ;
 
 
DELIMITER $$
create trigger fechaHoraFactura
before insert on DetalleFactura
for each row
begin
	Update Facturas
	set
			fecha = CURDATE(),
            hora = CURTIME()
			where facturaId = New.facturaId;
end $$
DELIMITER ;