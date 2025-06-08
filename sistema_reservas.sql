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


INSERT INTO categoria (nombre, descripcion, img_categoria) VALUES
('Aula', 'Espacio estándar para clases teóricas con capacidad media', 'https://example.com/img/aula.jpg'),
('Laboratorio', 'Equipado para prácticas de ciencias e ingeniería', 'https://example.com/img/laboratorio.jpg'),
('Auditorio', 'Espacio para eventos masivos con equipamiento audiovisual', 'https://example.com/img/auditorio.jpg'),
('Sala de Reuniones', 'Área para juntas y trabajo colaborativo', 'https://example.com/img/sala_reuniones.jpg'),
('Taller', 'Espacio especializado para trabajo práctico y manualidades', 'https://example.com/img/taller.jpg'),
('Biblioteca', 'Área de estudio con acceso a material bibliográfico', 'https://example.com/img/biblioteca.jpg'),
('Gimnasio', 'Instalación deportiva para actividades físicas', 'https://example.com/img/gimnasio.jpg'),
('Sala de Computo', 'Equipada con computadoras para prácticas digitales', 'https://example.com/img/sala_computo.jpg'),
('Cancha Deportiva', 'Área exterior para deportes al aire libre', 'https://example.com/img/cancha.jpg'),
('Sala de Conferencias', 'Espacio para presentaciones y seminarios', 'https://example.com/img/conferencias.jpg');


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

INSERT INTO reserva (id_reservador, id_espacio, fecha_creacion, fecha, hora_inicio, hora_fin, motivo, estado_e) VALUES
(1, 1, '2025-06-01', '2025-06-05', '08:00', '10:00', 'Clase de programación avanzada', 'CONFIRMADA'),
(2, 5, '2025-06-02', '2025-06-06', '09:00', '11:00', 'Práctica de desarrollo web', 'CONFIRMADA'),
(3, 2, '2025-06-03', '2025-06-07', '07:30', '09:30', 'Análisis de muestras químicas', 'CONFIRMADA'),
(4, 3, '2025-06-04', '2025-06-08', '10:00', '12:00', 'Seminario de derecho constitucional', 'PENDIENTE'),
(5, 4, '2025-06-05', '2025-06-09', '15:00', '16:30', 'Reunión administrativa mensual', 'CONFIRMADA'),
(6, 1, '2025-06-06', '2025-06-10', '18:00', '20:00', 'Grupo de estudio nocturno', 'CANCELADA'),
(7, 5, '2025-06-07', '2025-06-11', '13:00', '15:00', 'Simulación de hackathon', 'PENDIENTE'),
(8, 2, '2025-06-08', '2025-06-12', '14:00', '16:00', 'Investigación académica de bioquímica', 'CONFIRMADA');



UPDATE categoria
SET img_categoria = 'https://img.freepik.com/fotos-premium/pintura-digital-gato-sombrero-gafas-proteccion-gato-esta-mirando-al-espectador-expresion-curiosa_14117-270184.jpg?w=360'
WHERE id_categoria = 1;