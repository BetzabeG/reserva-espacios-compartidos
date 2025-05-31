INSERT INTO reservador (nombre, ap_paterno, ap_materno, tipo, correo_institucional, telefono, estado_e) VALUES

('Juan', 'Pérez', 'García', 'docente', 'jperez@umsa.bo', '72123456', true),
('María', 'López', 'Rodríguez', 'estudiante', 'mlopez@umsa.bo', '73234567', true),
('Carlos', 'González', 'Martínez', 'administrativo', 'cgonzalez@umsa.bo', '74345678', true),
('Ana', 'Ramírez', 'Sánchez', 'docente', 'aramirez@umsa.bo', '75456789', true),
('Luis', 'Fernández', 'Torres', 'estudiante', 'lfernandez@umsa.bo', '76567890', true),
('Sofía', 'Morales', 'Vargas', 'administrativo', 'smorales@umsa.bo', '77678901', true),
('Miguel', 'Díaz', 'Castillo', 'docente', 'mdiaz@umsa.bo', '78789012', true),
('Laura', 'Silva', 'Herrera', 'estudiante', 'lsilva@umsa.bo', '79890123', true),
('Andrés', 'Ruiz', 'Flores', 'administrativo', 'aruiz@umsa.bo', '70901234', true),
('Paola', 'Navarro', 'Mendoza', 'docente', 'pnavarro@umsa.bo', '70123456', true),
('Karen', 'Catari', 'Calderon', 'estudiante', 'krcatari@umsa.bo', '65135810', true);


INSERT INTO categoria (nombre, descripcion) VALUES
('Aula', 'Espacio estándar para clases teóricas con capacidad media'),
('Laboratorio', 'Equipado para prácticas de ciencias e ingeniería'),
('Auditorio', 'Espacio para eventos masivos con equipamiento audiovisual'),
('Sala de Reuniones', 'Área para juntas y trabajo colaborativo'),
('Taller', 'Espacio especializado para trabajo práctico y manualidades'),
('Biblioteca', 'Área de estudio con acceso a material bibliográfico'),
('Gimnasio', 'Instalación deportiva para actividades físicas'),
('Sala de Computo', 'Equipada con computadoras para prácticas digitales'),
('Cancha Deportiva', 'Área exterior para deportes al aire libre'),
('Sala de Conferencias', 'Espacio para presentaciones y seminarios');


INSERT INTO espacio (capacidad,carrera, descripcion, estado, facultad, ubicacion, id_categoria) VALUES
(40, 'Ingeniería de Sistemas', 'Aula con proyector y pizarra', true, 'Facultad de Tecnología', 'Edificio A, Piso 2', 1),
(25, 'Bioquímica', 'Laboratorio de análisis químico', true, 'Facultad de Ciencias', 'Edificio B, Piso 1', 2),
(100, 'General', 'Auditorio con tarima y sonido', true, 'Facultad de Derecho', 'Edificio Central', 3),
(15, 'Administración', 'Sala pequeña para reuniones',true, 'Facultad de Economía', 'Edificio C, Piso 3', 4),
(60, 'Informática', 'Laboratorio de programación', true, 'Facultad de Tecnología', 'Edificio A, Piso 4', 9);

INSERT INTO equipamiento (nombre, descripcion, estado_e) VALUES
('Proyector', 'Proyector multimedia para presentaciones.', TRUE),
('Computadora Samsung', 'Computadora de escritorio marca Samsung.', TRUE),
('Silla', 'Silla estándar para aula o sala.', TRUE),
('Mesa', 'Mesa de uso general.', TRUE),
('Red de Voleibol', 'Red para cancha de voleibol.', TRUE),
('Balón de Básquetbol', 'Balón reglamentario de baloncesto.', TRUE),
('Kit de Laboratorio', 'Set básico de instrumentos para laboratorio.', TRUE);

INSERT INTO espacio_equipamiento (id_equipamiento, id_espacio, cantidad) VALUES
(1, 1, 2),   -- 2 proyectores en el espacio 1
(7, 2, 1),   -- 1 kit de laboratorio en el espacio 2
(2, 5, 15),  -- 15 computadoras en el espacio 5
(6, 3, 3),   -- 3 balones en el auditorio (espacio 3)
(2, 1, 3);
