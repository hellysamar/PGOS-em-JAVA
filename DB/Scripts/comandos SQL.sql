-- comentários
-- Criando banco de dados
CREATE DATABASE dbinfox;

-- Escolhe e seleciona o banco de dados a ser utilizado
USE dbinfox;

-- Criar uma tabela no banco selecionado "Bloco de Instruções"
CREATE TABLE tbusuarios (
	iduser INT PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL,
    fone VARCHAR(15),
    login VARCHAR(15) NOT NULL UNIQUE,
    senha VARCHAR(15) NOT NULL
);
INSERT INTO tbusuarios (idUser, usuario,fone,login,senha) VALUES (4, 'Luan', '88 97777-8899', 'FuLuan', '123456');

-- Alterar nome do Campo na tabela
ALTER TABLE tbusuarios
CHANGE iduser idUser INT;

-- Descreve a tabela
DESCRIBE tbusuarios;

-- Inserir dados na tabela (CRUD) 
-- CREATE -> Insert
INSERT INTO tbusuarios (iduser, usuario, fone, login, senha)
VALUES (1, 'Hellysamar', '61 98284-4250', 'helly', '12345');

-- Exibe os dados da tabela (CRUD) 
-- READ -> Select
SELECT * FROM tbusuarios;

-- Alterar dados de uma tabela (CRUD) 
-- UPDATE -> Update
UPDATE tbusuarios SET fone = '61 88888-9999' WHERE iduser = 2;

-- Apaga um registro da tabela (CRUD) 
-- DELETE - Delete
DELETE FROM tbusuarios WHERE iduser = 4;


-- Criando tabela clientes
CREATE TABLE tbclientes (
	idCliente INT PRIMARY KEY AUTO_INCREMENT,
    nomeCliente VARCHAR(50) NOT NULL,
    enderecoCliente VARCHAR(100),
    foneCliente VARCHAR(50) NOT NULL,
    emailCliente VARCHAR(50)
);

DESCRIBE tbclientes;

INSERT INTO tbclientes (
	nomeCliente, enderecoCliente, foneCliente, emailCliente
) VALUES (
	'Fulanin', 'Residencial', '61 98858-9586', 'ful@mail.com'
);

SELECT * FROM tbclientes;


-- Criando tabela OS Ordem de Serviço
-- Relacionamento de tabelas
CREATE TABLE tbos (
	os INT PRIMARY KEY AUTO_INCREMENT,
    dataOS TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    equipamento VARCHAR(150) NOT NULL,
    defeito VARCHAR(150) NOT NULL,
    servico VARCHAR(150),
    tecnico VARCHAR(30),
    valor DECIMAL(10,2),
    idCliente INT NOT NULL,
	FOREIGN KEY(idCliente) REFERENCES tbclientes(idCliente)
);

DESCRIBE tbos;

SELECT * FROM tbos;

INSERT INTO tbos (
	equipamento, defeito, servico, tecnico, valor, idCliente
) VALUES (
	'notebook dell', 'apagou, não liga', 'troca de cabo', 'helly', 150.25, 1
);

-- Traz informações de duas ou mais tabelas
SELECT
O.os, equipamento, defeito, servico, valor,
C.nomeCliente, foneCliente
FROM tbos AS O
INNER JOIN tbclientes AS C
ON (O.idCliente = C.idCliente);


-- CRUD
-- CREATE
INSERT INTO tbusuarios (idUser, usuario, fone, login, senha) VALUES (7, 'Francine', '61 98888-3456', 'fran', '123456');
SELECT * FROM tbusuarios;
-- READ
SELECT * FROM tbusuarios WHERE idUser = 4;
SELECT * FROM tbusuarios;
-- UPDATE
UPDATE tbusuarios SET login = 'luangin' WHERE idUser = 4;
SELECT * FROM tbusuarios;
-- DELETE
DELETE FROM tbusuarios WHERE idUser = 7;
SELECT * FROM tbusuarios;
