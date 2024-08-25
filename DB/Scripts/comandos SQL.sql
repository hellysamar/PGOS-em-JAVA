-- RENAME TABLE dbinfox.tbclientes TO dbpgos.tblclientes,
--              dbinfox.tbos TO dbpgos.tblos,
--              dbinfox.tbusuarios TO dbpgos.tblusuarios,

-- Apagando banco
DROP DATABASE dbinfox;

-- comentários
-- Criando banco de dados
CREATE DATABASE dbpgos;

-- Escolhe e seleciona o banco de dados a ser utilizado
USE dbinfox;
USE dbpgos;

-- Criar uma tabela no banco selecionado "Bloco de Instruções"
CREATE TABLE tbusuarios (
	iduser INT PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL,
    fone VARCHAR(15),
    login VARCHAR(15) NOT NULL UNIQUE,
    senha VARCHAR(15) NOT NULL
);
INSERT INTO tblusuarios (idUser, usuario,fone,login,senha) VALUES (6, 'Ferdin', '55 95577-8899', 'fe', '123456');

-- Alterar nome do Campo na tabela
ALTER TABLE tblusuarios
CHANGE iduser idUser INT;

-- Descreve a tabela
DESCRIBE tblusuarios;

-- Inserir dados na tabela (CRUD) 
-- CREATE -> Insert
INSERT INTO tblusuarios (iduser, usuario, fone, login, senha)
VALUES (1, 'Hellysamar', '61 98284-4250', 'helly', '12345');

-- Exibe os dados da tabela (CRUD) 
-- READ -> Select
SELECT * FROM tblusuarios;

-- Alterar dados de uma tabela (CRUD) 
-- UPDATE -> Update
UPDATE tblusuarios SET fone = '61 88888-9999' WHERE iduser = 2;

-- Apaga um registro da tabela (CRUD) 
-- DELETE - Delete
DELETE FROM tblusuarios WHERE iduser = 4;


-- Criando tabela clientes
CREATE TABLE tblclientes (
	idCliente INT PRIMARY KEY AUTO_INCREMENT,
    nomeCliente VARCHAR(50) NOT NULL,
    enderecoCliente VARCHAR(100),
    foneCliente VARCHAR(50) NOT NULL,
    emailCliente VARCHAR(50)
);

DESCRIBE tblclientes;

INSERT INTO tblclientes (
	nomeCliente, enderecoCliente, foneCliente, emailCliente
) VALUES (
	'Deutrano', 'Comercial', '71 99999-9586', 'del@mail.com'
);

SELECT * FROM tblclientes;


-- Criando tabela OS Ordem de Serviço
-- Relacionamento de tabelas
CREATE TABLE tblos (
	os INT PRIMARY KEY AUTO_INCREMENT,
    dataOS TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    equipamento VARCHAR(150) NOT NULL,
    defeito VARCHAR(150) NOT NULL,
    servico VARCHAR(150),
    tecnico VARCHAR(30),
    valor DECIMAL(10,2),
    idCliente INT NOT NULL,
	FOREIGN KEY(idCliente) REFERENCES tblclientes(idCliente)
);

DESCRIBE tblos;

SELECT * FROM tblos;

INSERT INTO tblos (
	equipamento, defeito, servico, tecnico, valor, idCliente
) VALUES (
	'Pixelbook', 'minutenção', 'substituição de pelicula', 'tecnico', 75.00, 3
);
SELECT * FROM tblos;

-- Traz informações de duas ou mais tabelas
SELECT
O.os, equipamento, defeito, servico, valor,
C.nomeCliente, foneCliente
FROM tblos AS O
INNER JOIN tblclientes AS C
ON (O.idCliente = C.idCliente);

-- INCLUI UM CAMPO (Coluna) NA TABELA
ALTER TABLE tblusuarios ADD COLUMN perfil VARCHAR(20) NOT NULL;

-- DELETA UM CAMPO (Coluna) DA TABELA
ALTER TABLE tblusuarios DROP COLUMN perfil;

UPDATE tblusuarios SET perfil = 'user' WHERE idUser = 8;
SELECT * FROM tblusuarios;


ALTER TABLE tblOS ADD tipo VARCHAR(10) NOT NULL AFTER dataOS;
DESCRIBE tblOS;

ALTER TABLE tblOS ADD situacaoOS VARCHAR(20) NOT NULL AFTER tipo;
DESCRIBE tblOS;
ALTER TABLE tblOS MODIFY COLUMN situacaoOS varchar(30);

DESCRIBE tblOS; 




UPDATE tblOS SET tipo = 'OS', situacaoOS = 'Entregue na Assistência' WHERE os = 2;
SELECT * FROM tblOS;
UPDATE tblOS SET tipo = ?, situacaoOS = ?, equipamento = ?, defeito = ?, servico = ?, tecnico = ?, valor = ?, idCliente = ? WHERE os = ?;

SELECT idCliente AS ID_Cliente, nomeCliente AS Nome, foneCliente AS Telefone FROM tblClientes WHERE nomeCliente LIKE 'l%';

INSERT INTO tblOS (tipo, situacaoOS, equipamento, defeito, servico, tecnico, valor, idCliente) VALUES ('OS', 'Entregue na Assistência', 'macbook', 'tela preta', 'substituição de tela', 'Fulano Tec', 90, 4);
SELECT * FROM tblOS;

USE dbpgos;
SELECT * FROM tblOS WHERE os = 6;
DESCRIBE tblOS;

SELECT idCliente AS ID, nomeCliente AS NOME, enderecoCliente AS ENDEREÇO, foneCliente AS TELEFONE, emailCliente AS EMAIL FROM tblClientes;

SELECT * FROM tblClientes WHERE nomeCliente LIKE '%an%';


SELECT
O.os, dataOS, tipo, situacaoOS, equipamento, defeito, servico, tecnico, valor, O.idCliente,
C.nomeCliente
FROM tblos AS O
INNER JOIN tblclientes AS C
ON (O.idCliente = C.idCliente) 
WHERE O.os = 6;

DESCRIBE tblClientes;
SELECT
OS.os, dataos, tipo, situacaoOS, equipamento, tecnico, valor,
CL.nomeCliente, foneCliente
FROM tblOS AS OS
INNER JOIN tblClientes AS CL
ON (OS.idCliente = CL.idCliente);