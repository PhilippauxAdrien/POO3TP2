drop table REMBOURSEMENT;
drop table CONTRAT;
drop table PRODUIT;
drop table CLIENT;
drop table RISQUE;
drop table NUMSECU;

create table RISQUE(
    nRisque  int not null AUTO_INCREMENT primary key,
    niveau   int not null
);

create table NUMSECU(
    nNumSecu int not null AUTO_INCREMENT primary key,
    sexe int not null,
    anneeNaissance int not null,
    moisNaissance int not null,
    departement int not null,
    commune int not null,
    ordre int not null,
    cle int not null,
    constraint UK_NUMSECU UNIQUE (sexe, anneeNaissance, moisNaissance, departement, commune, ordre, cle)
);

create table CLIENT(
    nClient  int not null AUTO_INCREMENT primary key,
    nom varchar(40) not null,
    prenom varchar(40) not null,
    nNumSecu int not null,
    telephone varchar(12),
    revenu numeric(10,2),
    nRisque int not null,
    constraint UK_Client UNIQUE(nNumSecu),
    constraint FK_CLIENT_RISQUE foreign key (nRisque) references RISQUE(nRisque),
    constraint FK_CLIENT_NUMSECU foreign key (nNumSecu) references NUMSECU(nNumSecu) ON DELETE CASCADE
);

create table PRODUIT(
    nProduit int not null AUTO_INCREMENT primary key,
    nom varchar(20),
    nRisque int,
    tauxRevenu numeric(5,2) not null,
    effortBudgetaire numeric(5,2) not null,
    constraint FK_PRODUIT_RISQUE foreign key (nRisque) references RISQUE(nRisque)   
);

create table CONTRAT(
    nContrat int not null AUTO_INCREMENT primary key,
    numero varchar(11) not null,
    nProduit int not null,
    nClient int not null,
    dateDebut date not null,
    dateEcheance date,
    constraint FK_CONTRAT_PRODUIT foreign key (nProduit) references PRODUIT(nProduit),
    constraint FK_CONTRAT_CLIENT foreign key (nClient) references CLIENT(nClient)
);

create table REMBOURSEMENT(
    nRemboursement int not null AUTO_INCREMENT primary key,
    nContrat int not null,
    montant numeric(7,2) not null,
    dateDommage date not null,
    constraint FK_DOMMAGE_CONTRAT foreign key (ncontrat) references CONTRAT(nContrat)
);


insert into RISQUE(niveau) values (1);
insert into RISQUE(niveau) values (2);
insert into RISQUE(niveau) values (3);
insert into RISQUE(niveau) values (4);
insert into RISQUE(niveau) values (5);

insert into NUMSECU(sexe, anneeNaissance, moisNaissance, departement, commune, ordre, cle)
    values(1, 60, 01, 21, 659, 518, 33);
insert into NUMSECU(sexe, anneeNaissance, moisNaissance, departement, commune, ordre, cle)
    values(1, 65, 03, 31, 349, 937, 89);
insert into NUMSECU(sexe, anneeNaissance, moisNaissance, departement, commune, ordre, cle)
    values(2, 59, 11, 37, 167, 176, 21);
insert into NUMSECU(sexe, anneeNaissance, moisNaissance, departement, commune, ordre, cle)
    values(1, 77, 10, 43, 781, 581, 71);
insert into NUMSECU(sexe, anneeNaissance, moisNaissance, departement, commune, ordre, cle)
    values(1, 75, 03, 45, 853, 367, 94);
insert into NUMSECU(sexe, anneeNaissance, moisNaissance, departement, commune, ordre, cle)
    values(1, 82, 08, 65, 179, 268, 16);

insert into CLIENT(nom, prenom, nNumSecu, telephone, revenu, nrisque)
    values('Le Blanc', 'Juste', 1, '0601020315', 50000, 2);
insert into CLIENT(nom, prenom, nNumSecu, telephone, revenu, nrisque)
    values('Petit', 'Philippe', 2, '0102030438', 45000, 1);
insert into CLIENT(nom, prenom, nNumSecu, telephone, revenu, nrisque)
    values('Grand', 'Vanessa', 3, '0706020356', 52200, 4);
insert into CLIENT(nom, prenom, nNumSecu, telephone, revenu, nrisque)
    values('Boon', 'Dany', 4, '0701030575', 145000, 2);
insert into CLIENT(nom, prenom, nNumSecu, telephone, revenu, nrisque)
    values('Felix', 'Mathieu', 5, '0702020364', 48000, 1);
insert into CLIENT(nom, prenom, nNumSecu, telephone, revenu, nrisque)
    values('Blue', 'Claude', 6, '0606030426', 53500, 3);
    
    insert into NUMSECU(sexe, anneeNaissance, moisNaissance, departement, commune, ordre, cle)
    values(1, 96, 09, 62, 150, 268, 24);
    insert into CLIENT(nom, prenom, nNumSecu, telephone, revenu, nrisque)
    values('Zidane', 'Zinedine', 7, '0666254578', 1000000, 2);