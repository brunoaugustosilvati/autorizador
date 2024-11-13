create table cartoes(

	id bigint not null auto_increment,
	numero_cartao varchar(100) not null,
	senha varchar(100) not null,
	saldo decimal(15, 2) not null,

	primary key(id),
	unique(numero_cartao)
);