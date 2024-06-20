## Gestor Empleados Java - Alejandro Mas Diego

## Descripción

[GestorEmpleados] Programa realizado en Java que realiza la gestión de empleados en una base de datos.

## Funcionalidades
[MostrarEmpleados] Muestra todos los datos de los empleados almacenados.
[CrearEmpleado] Crea un empleado nuevo y lo inserta en la base de datos.
[ModificarEmpleado] Modifica los datos de un empleado seleccionado por el usuario
[EliminarEmpleado] Elimina los datos de un empleado elegido por el usuario.
[CrearDepartamento] Crea un departamento nuevo y pregunta al usuario su sede para asociarlo.
[CrearSede] Crea una nueva sede y la inserta en la base de datos.
[ExportarEmpleados] Exporta todos los datos de los empleados en formato CSV
[ImportarEmpleados] Importa datos de empleados nuevos a partir de un archivo CSV seleccionado por el usuario.

## Uso

[Importante] Iniciar servidor MYSQL de XAMPP para poder usar el programa. Si no se inicia el servidor MYSQL los botones se mostrarán deshabilitados.

## Script BBDD

-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-02-2024 a las 12:46:44
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyecto_orm`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id_categoria` int(11) NOT NULL,
  `nombre_categoria` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id_categoria`, `nombre_categoria`) VALUES
(1, 'JUNIOR'),
(2, 'AVANZADO'),
(3, 'SENIOR');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamento`
--

CREATE TABLE `departamento` (
  `id_depto` int(11) NOT NULL,
  `nom_depto` char(32) DEFAULT NULL,
  `id_sede` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `departamento`
--

INSERT INTO `departamento` (`id_depto`, `nom_depto`, `id_sede`) VALUES
(1, 'VENTAS', 4),
(2, 'I+D', 5),
(3, 'RECICLAJE', 5),
(4, 'COMERCIO', 5),
(5, 'PROGRAMACION', 4),
(6, 'MANTENIMIENTO', 7),
(7, 'RECURSOS HUMANOS', 9),
(8, 'PRUEBA', 7),
(9, 'TRANSPORTE', 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `dni` char(9) NOT NULL,
  `nom_emp` char(40) DEFAULT NULL,
  `id_depto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`dni`, `nom_emp`, `id_depto`) VALUES
('72343000W', 'luis juan', 3),
('72364888Z', 'pablo', 4),
('73273999W', 'jose carlos', 6),
('73456222Q', 'nuria', 3),
('73473000Z', 'mariano', 3),
('73473288Q', 'juan carlos', 3),
('74344469W', 'Nacho', 4),
('74363322Z', 'manuel', 2),
('74383000W', 'pepe', 1),
('74383000Y', 'federico', 2),
('74383001Z', 'antonio', 3),
('74383300Z', 'jose miguel', 3),
('74396369Q', 'Toni', 3),
('74396369W', 'prueba', 4),
('74396469W', 'juanan', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado_datos_prof`
--

CREATE TABLE `empleado_datos_prof` (
  `dni` char(9) NOT NULL,
  `categoria` int(11) DEFAULT NULL,
  `sueldo_bruto_anual` decimal(8,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `empleado_datos_prof`
--

INSERT INTO `empleado_datos_prof` (`dni`, `categoria`, `sueldo_bruto_anual`) VALUES
('72343000W', 3, 16000.00),
('72364888Z', 3, 14000.00),
('73273999W', 3, 12000.00),
('73456222Q', 2, 16000.00),
('73473000Z', 2, 12000.00),
('73473288Q', 2, 13000.00),
('74344469W', 2, 20000.00),
('74363322Z', 3, 15000.00),
('74383000W', 2, 15000.00),
('74383000Y', 2, 13000.00),
('74383001Z', 3, 10000.00),
('74383300Z', 2, 12000.00),
('74396369Q', 2, 20000.00),
('74396369W', 2, 20000.00),
('74396469W', 2, 20000.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sede`
--

CREATE TABLE `sede` (
  `id_sede` int(11) NOT NULL,
  `nom_sede` char(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `sede`
--

INSERT INTO `sede` (`id_sede`, `nom_sede`) VALUES
(4, 'MADRID'),
(5, 'ELCHE'),
(7, 'VALENCIA'),
(9, 'ALICANTE'),
(10, 'MALLORCA'),
(11, 'CANARIAS'),
(12, 'GALICIA'),
(13, 'PRUEBA'),
(14, 'BENIDORM');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id_categoria`);

--
-- Indices de la tabla `departamento`
--
ALTER TABLE `departamento`
  ADD PRIMARY KEY (`id_depto`),
  ADD KEY `fk_departamento_sede_idx` (`id_sede`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`dni`),
  ADD KEY `fk_empleado_departamento1_idx` (`id_depto`);

--
-- Indices de la tabla `empleado_datos_prof`
--
ALTER TABLE `empleado_datos_prof`
  ADD PRIMARY KEY (`dni`),
  ADD KEY `fk_empleado_datos_prof_categoria1` (`categoria`);

--
-- Indices de la tabla `sede`
--
ALTER TABLE `sede`
  ADD PRIMARY KEY (`id_sede`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id_categoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `departamento`
--
ALTER TABLE `departamento`
  MODIFY `id_depto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `sede`
--
ALTER TABLE `sede`
  MODIFY `id_sede` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `departamento`
--
ALTER TABLE `departamento`
  ADD CONSTRAINT `fk_departamento_sede` FOREIGN KEY (`id_sede`) REFERENCES `sede` (`id_sede`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD CONSTRAINT `fk_empleado_departamento1` FOREIGN KEY (`id_depto`) REFERENCES `departamento` (`id_depto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `empleado_datos_prof`
--
ALTER TABLE `empleado_datos_prof`
  ADD CONSTRAINT `fk_empleado_datos_prof_categoria1` FOREIGN KEY (`categoria`) REFERENCES `categoria` (`id_categoria`),
  ADD CONSTRAINT `fk_empleado_datos_prof_empleado1` FOREIGN KEY (`dni`) REFERENCES `empleado` (`dni`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
