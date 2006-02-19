CREATE TABLE ModelesColis (
  idModelesColis INTEGER UNSIGNED NOT NULL,
  Forme INTEGER NOT NULL,
  Modele INTEGER NOT NULL,
  hauteur INTEGER UNSIGNED NULL,
  largeur INTEGER UNSIGNED NULL,
  Profondeur INTEGER UNSIGNED NULL,
  Diametre INTEGER UNSIGNED NULL,
  Volume FLOAT NULL,
  PRIMARY KEY(idModelesColis)
);

CREATE TABLE Localisation (
  idLocalisation INTEGER UNSIGNED NOT NULL,
  Adresse CHAR(50) NULL,
  CodePostal CHAR(15) NULL,
  Ville CHAR(50) NULL,
  PRIMARY KEY(idLocalisation)
);

CREATE TABLE Entrepots (
  idEntrepots INTEGER UNSIGNED NOT NULL,
  Localisation_idLocalisation INTEGER UNSIGNED NOT NULL,
  Telephone CHAR(15) NULL,
  PRIMARY KEY(idEntrepots),
  INDEX Entrepots_FKIndex1(Localisation_idLocalisation),
  FOREIGN KEY(Localisation_idLocalisation)
    REFERENCES Localisation(idLocalisation)
      ON DELETE CASCADE
      ON UPDATE NO ACTION
);


CREATE TABLE Personnes (
  idPersonnes INTEGER UNSIGNED NOT NULL,
  Localisation_idLocalisation INTEGER UNSIGNED NOT NULL,
  Nom CHAR(50) NULL,
  Prenom CHAR(50) NULL,
  Telephone CHAR(15) NULL,
  Email CHAR(50) NULL,
  PRIMARY KEY(idPersonnes),
  INDEX Personnes_FKIndex1(Localisation_idLocalisation),
  FOREIGN KEY(Localisation_idLocalisation)
    REFERENCES Localisation(idLocalisation)
      ON DELETE CASCADE
      ON UPDATE NO ACTION
);

CREATE TABLE Users (
  idUsers INTEGER UNSIGNED NOT NULL,
  Personnes_idPersonnes INTEGER UNSIGNED NOT NULL,
  Login CHAR(20) NULL,
  Password_2 CHAR(20) NULL,
  Type_2 SMALLINT UNSIGNED NULL,
  PRIMARY KEY(idUsers),
  INDEX Users_FKIndex1(Personnes_idPersonnes),
  FOREIGN KEY(Personnes_idPersonnes)
    REFERENCES Personnes(idPersonnes)
      ON DELETE CASCADE
      ON UPDATE NO ACTION
);

CREATE TABLE Camions (
  idCamions INTEGER UNSIGNED NOT NULL,
  Immatriculation CHAR(25) NOT NULL,
  Etat SMALLINT NULL,
  Volume INTEGER NULL,
  Origine INTEGER NOT NULL,
  Destination INTEGER NULL,
  PRIMARY KEY(idCamions),
  INDEX Colis_FKIndex1(Origine),
  INDEX Colis_FKIndex2(Destination),
  FOREIGN KEY(Origine)
    REFERENCES Entrepots(idEntrepots)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Destination)
    REFERENCES Entrepots(idEntrepots)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Colis (
  idColis INTEGER UNSIGNED NOT NULL,
  ModelesColis_idModelesColis INTEGER UNSIGNED NOT NULL,
  Createur INTEGER UNSIGNED NULL,
  Expediteur INTEGER NOT NULL,
  Destinataire INTEGER NOT NULL,
  Destination INTEGER UNSIGNED NULL,
  Code_barre VARCHAR(15),
  Poids INTEGER NULL,
  DateDepot DATETIME NULL,
  Valeur CHAR(20) NULL,
  Fragilite INTEGER UNSIGNED NULL,
  PRIMARY KEY(idColis),
  INDEX Colis_FKIndex1(ModelesColis_idModelesColis),
  INDEX Colis_FKIndex2(Destination),
  INDEX users_FKIndex3(Createur),
  FOREIGN KEY(ModelesColis_idModelesColis)
    REFERENCES ModelesColis(idModelesColis)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Destination)
    REFERENCES Entrepots(idEntrepots)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Createur)
    REFERENCES Users(idUsers)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);


CREATE TABLE Chargement (
  idChargement INTEGER UNSIGNED NOT NULL,
  Camions_idCamions INTEGER UNSIGNED NOT NULL,
  NbColis INTEGER NOT NULL,
  VolChargement FLOAT NOT NULL,
  DateCreation DATETIME NOT NULL,
  Users_idUsers INTEGER NOT NULL,
  CodeBarre INTEGER NOT NULL,
  PRIMARY KEY(idChargement),
  INDEX Incidents_FKIndex1(Users_idUsers),
  INDEX Chargement_FKIndex2(Camions_idCamions),
  FOREIGN KEY(Users_idUsers)
    REFERENCES Users(idUsers)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Camions_idCamions)
    REFERENCES Camions(idCamions)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);


CREATE TABLE Chargement_Colis (
  idChargement INTEGER UNSIGNED NOT NULL,
  idColis INTEGER UNSIGNED NOT NULL,
  INDEX Incidents_FKIndex1(idChargement),
  INDEX Chargement_FKIndex2(idColis),
  FOREIGN KEY(idChargement)
    REFERENCES Chargement(idChargement)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idColis)
    REFERENCES Colis(idColis)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);


CREATE TABLE Incidents (
  idIncidents INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Colis_idColis INTEGER UNSIGNED NOT NULL,
  Users_idUsers INTEGER UNSIGNED NOT NULL,
  Description CHAR(255) NULL,
  DateCreation DATETIME NULL,
  Type_2 SMALLINT UNSIGNED NULL,
  Etat INTEGER UNSIGNED NULL,
  PRIMARY KEY(idIncidents),
  INDEX Incidents_FKIndex1(Users_idUsers),
  INDEX Incidents_FKIndex2(Colis_idColis),
  FOREIGN KEY(Users_idUsers)
    REFERENCES Users(idUsers)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Colis_idColis)
    REFERENCES Colis(idColis)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Routage (
  idRoutage INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Origine CHAR(45) NULL,
  Destination CHAR(45) NULL,
  Distance FLOAT NULL,
  PRIMARY KEY(idRoutage)
);

CREATE TABLE Preparation (
  idPreparation INTEGER UNSIGNED NOT NULL,
  idPreparateur INTEGER UNSIGNED NOT NULL,
  idDestination INTEGER UNSIGNED NOT NULL,
  Etat INTEGER UNSIGNED NOT NULL,
  Volume INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(idPreparation),
  INDEX Incidents_FKIndex1(idPreparateur),
  INDEX Incidents_FKIndex2(idDestination),
  FOREIGN KEY(idPreparateur)
    REFERENCES Users(idUsers)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idDestination)
    REFERENCES Entrepots(idEntrepots)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Prep_Camions(
  idPreparation INTEGER UNSIGNED NOT NULL,
  idCamion INTEGER UNSIGNED NOT NULL,
  INDEX prep_camions_FKIndex1(idPreparation),
  INDEX prep_camions_FKIndex2(idCamion),
  FOREIGN KEY(idPreparation)
    REFERENCES Preparation(idPreparation)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(idCamion)
    REFERENCES Camions(idCamions)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

INSERT INTO `modelescolis` VALUES (1, 0, 0, 20, 20, 20, 0, 0);
INSERT INTO `modelescolis` VALUES (2, 0, 1, 40, 40, 40, 0, 0);
INSERT INTO `modelescolis` VALUES (3, 0, 2, 60, 60, 60, 0, 0);
INSERT INTO `modelescolis` VALUES (4, 1, 0, 20, 60, 20, 0, 0);
INSERT INTO `modelescolis` VALUES (5, 1, 1, 60, 80, 100, 0, 0);
INSERT INTO `modelescolis` VALUES (6, 1, 2, 30, 60, 40, 0, 0);
INSERT INTO `modelescolis` VALUES (7, 2, 0, 80, 20, 20, 0, 0);
INSERT INTO `modelescolis` VALUES (8, 2, 1, 40, 10, 10, 0, 0);
INSERT INTO `modelescolis` VALUES (9, 2, 2, 100, 40, 40, 0, 0);

INSERT INTO `entrepots` VALUES (1, 8, '06-15-11-31-30');
INSERT INTO `entrepots` VALUES (2, 9, '06-15-11-31-30');
INSERT INTO `entrepots` VALUES (3, 10, '06-15-11-31-30');
INSERT INTO `entrepots` VALUES (4, 11, '06-15-11-31-30');

INSERT INTO `localisation` VALUES (1, '2 rue du Poitou', '64500', 'Biarritz');
INSERT INTO `localisation` VALUES (2, '19 avenue Foch', '94300', 'Rungis');
INSERT INTO `localisation` VALUES (3, '2 rue Jean Jaur', '94800', 'Villejuif');
INSERT INTO `localisation` VALUES (4, '13 place du Moustique', '94800', 'VILLE');
INSERT INTO `localisation` VALUES (5, '3 allee des Accacias', '91500', 'Rungis');
INSERT INTO `localisation` VALUES (6, 'impasse des Condamines', '23100', 'De Base');
INSERT INTO `localisation` VALUES (7, 'adresse2', '94800', 'Villejuif');
INSERT INTO `localisation` VALUES (8, 'adresse', '94800', 'Villejuif');
INSERT INTO `localisation` VALUES (9, 'adresse', '94800', 'Villejuif');
INSERT INTO `localisation` VALUES (10, 'adresse', '94800', 'Villejuif');
INSERT INTO `localisation` VALUES (11, 'adresse', '94800', 'Villejuif');

INSERT INTO `personnes` VALUES (1, 3, 'Chaubert', 'Jean-Jacques', '0165654545', 'jj.chaubert@transtec.de');
INSERT INTO `personnes` VALUES (2, 4, 'DUMOL', 'Raymong', '0124466224', 'r.dumou@wanadoo.fr');
INSERT INTO `personnes` VALUES (3, 5, 'Jardin', 'Maurice', '0123456598', 'hortifleur@trantec.com');
INSERT INTO `personnes` VALUES (4, 6, 'Banal', 'EncorePlus', '01000100001', 'neutre@neutral.com');

INSERT INTO `users` VALUES (1, 1, 'user1', 'user1', 1);
INSERT INTO `users` VALUES (2, 2, 'user2', 'user2', 2);
INSERT INTO `users` VALUES (3, 3, 'user3', 'user3', 0);
INSERT INTO `users` VALUES (4, 4, 'user4', 'user4', 1);

INSERT INTO `prep_camions` VALUES (1, 1);
INSERT INTO `prep_camions` VALUES (1, 2);

INSERT INTO `preparation` VALUES (1, 1, 2, 0, 60);
INSERT INTO `preparation` VALUES (2, 1, 3, 0, 450);

INSERT INTO `camions` VALUES (1, 'gtgdsdsd', 1, 250, 1, 2);
INSERT INTO `camions` VALUES (2, 'uyuiiyiu', 1, 350, 3, 2);

INSERT INTO `colis` VALUES (1, 1, 2, 1, 2, 2, 'vjv2454kjnhjkhj', 50, '2006-02-01 17:33:20', '5000', 2);
INSERT INTO `colis` VALUES (2, 1, 2, 3, 4, 2, 'tonCulJulien', 150, '2006-01-10 17:34:56', '14000', 3);

