-- phpMyAdmin SQL Dump
-- version 4.0.10.12
-- http://www.phpmyadmin.net
--
-- Host: 127.10.59.130:3306
-- Generation Time: Mar 15, 2017 at 01:16 PM
-- Server version: 5.5.52
-- PHP Version: 5.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


--
-- Database: `e`
--

-- --------------------------------------------------------

--
-- Table structure for table `caixa`
--

CREATE TABLE caixa 
( 
  id_caixa INT(10) NOT NULL AUTO_INCREMENT
, data DATE NOT NULL 
, id_conta INT(10) NOT NULL 
, id_forma_pgto INT(10) NOT NULL 
, valor DECIMAL(10, 2) NOT NULL 
, responsavel VARCHAR(20) NULL 
, descricao VARCHAR(100) NULL 
, CONSTRAINT id_caixa UNIQUE KEY ( id_caixa ) 
, FOREIGN KEY(id_conta) REFERENCES conta(id_conta)
, FOREIGN KEY(id_forma_pgto) REFERENCES forma_pgto(id_forma_pgto)
) 
COMMENT = 'Tabela para registro de entradas';



-- --------------------------------------------------------

--
-- Table structure for table forma_pgto
--
CREATE TABLE forma_pgto 
( 
  id_forma_pgto INT(10) NOT NULL 
, forma_pgto VARCHAR(30) NOT NULL 
, CONSTRAINT id UNIQUE KEY ( id_forma_pgto ) ) 
COMMENT = 'Registro de formas de pagamento';

--
-- Dumping data for table forma_pgto
--

INSERT INTO e.forma_pgto (id_forma_pgto, forma_pgto) VALUES
(1, 'Debito'),
(2, 'Dinheiro'),
(3, 'Cheque'),
(4, 'VR');

-- --------------------------------------------------------

--
-- Table structure for table conta
--

CREATE TABLE conta 
( 
  id_conta INT(10) NOT NULL 
, conta VARCHAR(30) NOT NULL 
, categoria VARCHAR(20) NULL 
, CONSTRAINT id_conta UNIQUE KEY ( id_conta ) ) 
COMMENT = 'Registro de categorias de conta';

--alter table conta add categoria VARCHAR(20) NULL;

--
-- Dumping data for table conta
--

INSERT INTO e.conta (id_conta, conta, categoria) VALUES
(0, 'Outros', 'Outros'),
(1, 'Combustivel', 'Transporte'),
(2, 'Supermercado', 'Alimentacao'),
(3, 'Telefone', 'Comunicacao'),
(4, 'Condominio', 'Moradia'),
(5, 'Luz', 'Moradia'),
(6, 'Escola', 'Educacao'),
(7, 'Estacionamento', 'Transporte'),
(8, 'Restaurante', 'Alimentacao'),
(9, 'Viagem', 'Lazer'),
(10, 'Vestuario', 'Vestuario'),
(11, 'Feira', 'Alimentacao'),
(12, 'Padaria', 'Alimentacao'),
(13, 'Medico', 'Saude'),
(14, 'Financiamento', 'Moradia'),
(15, 'Papelaria', 'Educacao'),
(16, 'Internet', 'Comunicacao'),
(17, 'Pedagio', 'Transporte'),
(18, 'Farmacia', 'Saude'),
(19, 'Consorcio', 'Investimento'),
(20, 'Dentista', 'Saude'),
(21, 'Cabelo', 'Beleza'),
(22, 'Imposto', 'Imposto');

-- --------------------------------------------------------

--
-- Table structure for table ponto
--

CREATE TABLE IF NOT EXISTS ponto (
  batida datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table ponto
--

INSERT INTO e.ponto (batida) VALUES
('2016-11-07 13:59:03'),
('2016-11-07 13:59:44'),
('2016-11-07 14:01:50'),
('2016-11-07 14:04:47');

-- --------------------------------------------------------

--
-- Table structure for table usuario
--

CREATE TABLE usuario 
( 
  usuario VARCHAR(20) NOT NULL 
, senha VARCHAR(15) NOT NULL 
, email VARCHAR(30) NULL 
, nome VARCHAR(30) NULL
, perfil INT(10) NULL 
, ativo BIT NULL DEFAULT 1 
, data DATETIME NULL  
, CONSTRAINT usuario UNIQUE KEY ( usuario ) ) 
COMMENT = 'Tabela de usuarios';

--
-- Dumping data for table usuario
--

INSERT INTO e.usuario (usuario, senha, email, perfil, ativo, data, nome) VALUES
('etrapp', 'visnh9;=', 'etrapp@gmail.com', 0, 1, now(), 'Emiliano');
INSERT INTO e.usuario (usuario, senha, email, perfil, ativo, data, nome) VALUES
('lu', 'visnh9;=', 'lutrapp@gmail.com', 0, 1, now(), 'Lu');


-- --------------------------------------------------------


CREATE TABLE auditoria
( 
  data DATETIME NOT NULL 
, usuario VARCHAR(20) NULL 
, acao VARCHAR(20) NOT NULL 
, descricao VARCHAR(150) NULL 
) 
COMMENT = 'Tabela de auditoria';



INSERT INTO e.caixa ('data', 'id_conta', 'id_forma_pgto', 'valor', 'responsavel', 'descricao') VALUES 
(now(),1,1,100.45,'Emi','conta1'),
(now(),2,2,133.45,'Emi','contax'),
(now(),3,3,47.45,'Emi','conta2'),
(now(),4,4,30.45,'Emi','conta1'),
(now(),5,1,260.45,'Emi','conta4'),
(now(),6,1,100.45,'Lu','conta'),
(now(),7,2,87.45,'Emi','conta'),
(now(),8,3,10.45,'Emi','conta'),
(now(),9,4,80.45,'Emi','conta'),
(now(),10,1,15.45,'Lu','conta'),
(now(),11,1,145,'Emi','conta'),
(now(),12,2,45,'Emi','conta'),
(now(),1,3,58,'Emi','conta'),
(now(),1,4,29,'Lu','conta');



--
-- Estrutura para tabela organizacao
--

CREATE TABLE IF NOT EXISTS e.organizacao (
  id int(11) NOT NULL,
  nome varchar(50) NOT NULL,
  descricao varchar(300) NOT NULL,
  ativo tinyint(1) NOT NULL,
  logo varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO e.organizacao (id, nome, descricao, ativo, logo) VALUES
(0, 'Home', 'Casa', 1, ''),
(1, 'MadameFit', 'Madame Fit', 1, 'madamefit.jpg');



-- --------------------------------------------------------
--
-- Estrutura para tabela usuario
--

CREATE TABLE IF NOT EXISTS e.usuario (
  usuario varchar(20) NOT NULL,
  senha varchar(15) NOT NULL,
  email varchar(30) DEFAULT NULL,
  nome varchar(30) DEFAULT NULL,
  perfil int(10) DEFAULT NULL,
  ativo bit(1) DEFAULT b'1',
  data datetime DEFAULT NULL,
  id_organizacao int(11) NOT NULL,
  UNIQUE KEY usuario (usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabela de usuarios';

--
-- Fazendo dump de dados para tabela usuario
--

INSERT INTO usuario (usuario, senha, email, nome, perfil, ativo, data, id_organizacao) VALUES
('etrapp', 'visnh9;=', 'etrapp@gmail.com', 'Emi', 0, 1, '2017-03-16 14:56:38', 0),
('lu', 'visnh9;=', 'lutrapp@gmail.com', 'Lu', 0, 1, '2017-03-16 14:56:38', 0),
('fit', 'visnh9;=', 'madamefi@gmail.com', 'Madame Fit', 0, 1, now(), 1);

-- Alteracao
ALTER TABLE  e.caixa ADD  id_organizacao INT NOT NULL ;
ALTER TABLE  e.usuario ADD  id_organizacao INT NOT NULL ;
ALTER TABLE  e.conta ADD  id_organizacao INT NOT NULL ;

--ALTER TABLE  e.conta ADD  id_deb_cred CHAR(1) NOT NULL DEFAULT 'd';
--ALTER TABLE  e.conta drop column id_deb_cred;

ALTER TABLE  e.caixa ADD  dc CHAR(1) NOT NULL DEFAULT 'd';
