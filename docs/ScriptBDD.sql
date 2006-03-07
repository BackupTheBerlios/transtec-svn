								/* Script de création et de remplissage de la BDD */

/* Table Camion */

CREATE TABLE camions (
  idCamions int(10) unsigned NOT NULL default '0',
  Immatriculation char(25) NOT NULL default '',
  Etat smallint(6) default NULL,
  Hauteur FLOAT NOT NULL,
  Largeur FLOAT NOT NULL,
  Profondeur FLOAT NOT NULL,
  Volume FLOAT NOT NULL,
  VolumeDispo FLOAT,
  Origine int(11) NOT NULL default '0',
  Destination int(11) default NULL,
  PRIMARY KEY  (idCamions),
  KEY Colis_FKIndex1 (Origine),
  KEY Colis_FKIndex2 (Destination)
);

INSERT INTO camions (idCamions, Immatriculation, Etat, Hauteur, Largeur, Profondeur,Volume,VolumeDispo, Origine, Destination) VALUES (1, '1013TW78', 1,10,100,10, 19, 10,0, 0);
INSERT INTO camions (idCamions, Immatriculation, Etat, Hauteur, Largeur, Profondeur,Volume,VolumeDispo, Origine, Destination) VALUES (2, '2356AQH76', 2,10,100,10, 29, 10,3, 1);
INSERT INTO camions (idCamions, Immatriculation, Etat, Hauteur, Largeur, Profondeur,Volume,VolumeDispo, Origine, Destination) VALUES (3, '2356ASU45', 0,10,100,10, 64, 10,0, 0);
INSERT INTO camions (idCamions, Immatriculation, Etat, Hauteur, Largeur, Profondeur,Volume,VolumeDispo, Origine, Destination) VALUES (4, '652GFD75', 0,10,111,10,30, 10,0, 0);
INSERT INTO camions (idCamions, Immatriculation, Etat, Hauteur, Largeur, Profondeur,Volume,VolumeDispo, Origine, Destination) VALUES (5, '1012TW78', 1,10,100,10, 250, 10,1, 2);
INSERT INTO camions (idCamions, Immatriculation, Etat, Hauteur, Largeur, Profondeur,Volume,VolumeDispo, Origine, Destination) VALUES (6, '1312VS69', 1,10,100,10, 350, 10,3, 2);

-- --------------------------------------------------------

-- 
-- Structure de la table chargement
-- 

CREATE TABLE chargement (
  `idChargement` int(10) unsigned NOT NULL default '0',
  `Camions_idCamions` int(10) unsigned NOT NULL default '0',
  `NbColis` int(11) NOT NULL default '0',
  `VolChargement` float NOT NULL default '0',
  `DateCreation` datetime NOT NULL default '0000-00-00 00:00:00',
  `Users_idUsers` int(11) NOT NULL default '0',
  `CodeBarre` varchar(15) default NULL,
  Etat INTEGER(1),
  PRIMARY KEY  (`idChargement`),
  KEY `Incidents_FKIndex1` (`Users_idUsers`),
  KEY `Chargement_FKIndex2` (`Camions_idCamions`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `chargement`
-- 

INSERT INTO `chargement` (`idChargement`, `Camions_idCamions`, `NbColis`, `VolChargement`, `DateCreation`, `Users_idUsers`, `CodeBarre`,Etat ) VALUES (0, 1, 6, 200, '2006-02-19 00:00:00', 1, '987654321',1);
INSERT INTO `chargement` (`idChargement`, `Camions_idCamions`, `NbColis`, `VolChargement`, `DateCreation`, `Users_idUsers`, `CodeBarre`,Etat ) VALUES (1, 2, 3, 200, '2006-02-17 00:00:00', 1, '123456789',1);

-- --------------------------------------------------------

-- 
-- Structure de la table `chargement_colis`
-- 

CREATE TABLE `chargement_colis` (
  `idChargement` int(10) unsigned NOT NULL default '0',
  `idColis` int(10) unsigned NOT NULL default '0',
  KEY `Incidents_FKIndex1` (`idChargement`),
  KEY `Chargement_FKIndex2` (`idColis`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `chargement_colis`
-- 

INSERT INTO `chargement_colis` (`idChargement`, `idColis`) VALUES (1, 3);
INSERT INTO `chargement_colis` (`idChargement`, `idColis`) VALUES (1, 1);
INSERT INTO `chargement_colis` (`idChargement`, `idColis`) VALUES (0, 2);
INSERT INTO `chargement_colis` (`idChargement`, `idColis`) VALUES (0, 4);
INSERT INTO `chargement_colis` (`idChargement`, `idColis`) VALUES (0, 5);
INSERT INTO `chargement_colis` (`idChargement`, `idColis`) VALUES (0, 6);
INSERT INTO `chargement_colis` (`idChargement`, `idColis`) VALUES (0, 7);
INSERT INTO `chargement_colis` (`idChargement`, `idColis`) VALUES (0, 8);
INSERT INTO `chargement_colis` (`idChargement`, `idColis`) VALUES (1, 9);

-- --------------------------------------------------------

-- 
-- Structure de la table `colis`
-- 

CREATE TABLE `colis` (
  `idColis` int(10) unsigned NOT NULL default '0',
  `ModelesColis_idModelesColis` int(10) unsigned NOT NULL default '0',
  `Createur` int(10) unsigned default NULL,
  `Expediteur` int(11) NOT NULL default '0',
  `Destinataire` int(11) NOT NULL default '0',
  Origine int(11) NOT NULL,
  `Destination` int(10) unsigned default NULL,
  EntrepotEnCours int(11) NOT NULL,
  `Code_barre` varchar(15) default NULL,
  `Poids` int(11) default NULL,
  `DateDepot` datetime default NULL,
  `Valeur` varchar(20) default NULL,
  `Fragilite` int(10) unsigned default NULL,
  `Volume` int(11) NOT NULL default '0',
  PRIMARY KEY  (`idColis`),
  KEY `Colis_FKIndex1` (`ModelesColis_idModelesColis`),
  KEY `Colis_FKIndex2` (`Destination`),
  KEY `users_FKIndex3` (`Createur`)
  ) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `colis`
-- 

INSERT INTO `colis` (`idColis`, `ModelesColis_idModelesColis`, `Createur`, `Expediteur`, `Destinataire`,Origine, `Destination`,EntrepotEnCours, `Code_barre`, `Poids`, `DateDepot`, `Valeur`, `Fragilite`, `Volume`) VALUES (8, 15, 3, 5, 1, 1,4,1, '225300223', 5, '2006-03-05 13:44:21', '0', 0, 615250);
INSERT INTO `colis` (`idColis`, `ModelesColis_idModelesColis`, `Createur`, `Expediteur`, `Destinataire`,Origine, `Destination`,EntrepotEnCours, `Code_barre`, `Poids`, `DateDepot`, `Valeur`, `Fragilite`, `Volume`) VALUES (9, 17, 3, 1, 5, 1,4,1, '786086629', 12, '2006-03-05 13:45:59', '0', 0, 125);
INSERT INTO `colis` (`idColis`, `ModelesColis_idModelesColis`, `Createur`, `Expediteur`, `Destinataire`,Origine, `Destination`,EntrepotEnCours, `Code_barre`, `Poids`, `DateDepot`, `Valeur`, `Fragilite`, `Volume`) VALUES (6, 13, 3, 6, 6, 1,7,1, '756576279', 54, '2006-03-05 13:42:55', '0', 0, 5832);
INSERT INTO `colis` (`idColis`, `ModelesColis_idModelesColis`, `Createur`, `Expediteur`, `Destinataire`,Origine, `Destination`,EntrepotEnCours, `Code_barre`, `Poids`, `DateDepot`, `Valeur`, `Fragilite`, `Volume`) VALUES (7, 14, 3, 1, 4, 1,7,1, '070431174', 14, '2006-03-05 13:43:50', '0', 0, 75480);
INSERT INTO `colis` (`idColis`, `ModelesColis_idModelesColis`, `Createur`, `Expediteur`, `Destinataire`,Origine, `Destination`,EntrepotEnCours, `Code_barre`, `Poids`, `DateDepot`, `Valeur`, `Fragilite`, `Volume`)  VALUES (5, 12, 3, 5, 3, 1,9,1, '676132437', 524, '2006-03-05 13:37:36', '0', 0, 4050);
INSERT INTO `colis` (`idColis`, `ModelesColis_idModelesColis`, `Createur`, `Expediteur`, `Destinataire`,Origine, `Destination`,EntrepotEnCours, `Code_barre`, `Poids`, `DateDepot`, `Valeur`, `Fragilite`, `Volume`)  VALUES (4, 11, 3, 3, 2, 1,5,1, '049138101', 19, '2006-02-21 23:18:16', '0', 0, 17035);
INSERT INTO `colis` (`idColis`, `ModelesColis_idModelesColis`, `Createur`, `Expediteur`, `Destinataire`,Origine, `Destination`,EntrepotEnCours, `Code_barre`, `Poids`, `DateDepot`, `Valeur`, `Fragilite`, `Volume`) VALUES (3, 6, 3, 7, 5, 1,4,1, '355987463', 11, '2006-02-21 23:14:17', '0', 0, 72000);
INSERT INTO `colis` (`idColis`, `ModelesColis_idModelesColis`, `Createur`, `Expediteur`, `Destinataire`,Origine, `Destination`,EntrepotEnCours, `Code_barre`, `Poids`, `DateDepot`, `Valeur`, `Fragilite`, `Volume`)  VALUES (2, 10, 3, 6, 5, 1,4,1, '218245514', 23, '2006-02-21 23:12:53', '0', 2, 340929);
INSERT INTO `colis` (`idColis`, `ModelesColis_idModelesColis`, `Createur`, `Expediteur`, `Destinataire`,Origine, `Destination`,EntrepotEnCours, `Code_barre`, `Poids`, `DateDepot`, `Valeur`, `Fragilite`, `Volume`)  VALUES (1, 8, 3, 4, 2, 1,9,1, '866312927', 18, '2006-02-21 23:11:46', '0', 1, 3142);


-- --------------------------------------------------------

-- 
-- Structure de la table `entrepots`
-- 

CREATE TABLE `entrepots` (
  `idEntrepots` int(10) unsigned NOT NULL default '0',
  `Localisation_idLocalisation` int(10) unsigned NOT NULL default '0',
  `Telephone` char(15) default NULL,
  PRIMARY KEY  (`idEntrepots`),
  KEY `Entrepots_FKIndex1` (`Localisation_idLocalisation`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `entrepots`
-- 

INSERT INTO `entrepots` (`idEntrepots`, `Localisation_idLocalisation`, `Telephone`) VALUES (1, 1, '0565487887');
INSERT INTO `entrepots` (`idEntrepots`, `Localisation_idLocalisation`, `Telephone`) VALUES (2, 2, '0165322356');
INSERT INTO `entrepots` (`idEntrepots`, `Localisation_idLocalisation`, `Telephone`) VALUES (0, 0, NULL);
INSERT INTO `entrepots` (`idEntrepots`, `Localisation_idLocalisation`, `Telephone`) VALUES (9, 9, '0163259687');
INSERT INTO `entrepots` (`idEntrepots`, `Localisation_idLocalisation`, `Telephone`) VALUES (8, 8, '0396857423');
INSERT INTO `entrepots` (`idEntrepots`, `Localisation_idLocalisation`, `Telephone`) VALUES (7, 7, '0474586332');
INSERT INTO `entrepots` (`idEntrepots`, `Localisation_idLocalisation`, `Telephone`) VALUES (6, 6, '0136542596');
INSERT INTO `entrepots` (`idEntrepots`, `Localisation_idLocalisation`, `Telephone`) VALUES (5, 5, '0365214896');
INSERT INTO `entrepots` (`idEntrepots`, `Localisation_idLocalisation`, `Telephone`) VALUES (4, 4, '0496587463');
INSERT INTO `entrepots` (`idEntrepots`, `Localisation_idLocalisation`, `Telephone`) VALUES (3, 3, '0147963215');

-- --------------------------------------------------------

-- 
-- Structure de la table `incidents`
-- 

CREATE TABLE `incidents` (
  `idIncidents` int(10) unsigned NOT NULL auto_increment,
  `Colis_idColis` int(10) unsigned NOT NULL default '0',
  `Users_idUsers` int(10) unsigned NOT NULL default '0',
  `Description` char(255) default NULL,
  `DateCreation` datetime default NULL,
  `Type_2` smallint(5) unsigned default NULL,
  `Etat` int(10) unsigned default NULL,
  PRIMARY KEY  (`idIncidents`),
  KEY `Incidents_FKIndex1` (`Users_idUsers`),
  KEY `Incidents_FKIndex2` (`Colis_idColis`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `incidents`
-- 

INSERT INTO `incidents` (`idIncidents`, `Colis_idColis`, `Users_idUsers`, `Description`, `DateCreation`, `Type_2`, `Etat`) VALUES (5, 3, 3, 'un probleme avec le carton ouvert', '2006-02-21 23:21:35', 10, 1);
INSERT INTO `incidents` (`idIncidents`, `Colis_idColis`, `Users_idUsers`, `Description`, `DateCreation`, `Type_2`, `Etat`) VALUES (4, 1, 3, 'il faut mieux jeter le colis', '2006-02-21 23:21:08', 10, 1);
INSERT INTO `incidents` (`idIncidents`, `Colis_idColis`, `Users_idUsers`, `Description`, `DateCreation`, `Type_2`, `Etat`) VALUES (3, 1, 3, 'le colis est foutu', '2006-02-21 23:20:33', 10, 2);
INSERT INTO `incidents` (`idIncidents`, `Colis_idColis`, `Users_idUsers`, `Description`, `DateCreation`, `Type_2`, `Etat`) VALUES (2, 1, 3, 'un autre coin vient de s''abimer', '2006-02-21 23:20:21', 10, 0);
INSERT INTO `incidents` (`idIncidents`, `Colis_idColis`, `Users_idUsers`, `Description`, `DateCreation`, `Type_2`, `Etat`) VALUES (1, 1, 3, 'Il y a un coin abimé sur le colis', '2006-02-21 23:20:06', 10, 2);

-- --------------------------------------------------------

-- 
-- Structure de la table `localisation`
-- 

CREATE TABLE `localisation` (
  `idLocalisation` int(10) unsigned NOT NULL default '0',
  `Adresse` char(50) default NULL,
  `CodePostal` char(15) default NULL,
  `Ville` char(50) default NULL,
  PRIMARY KEY  (`idLocalisation`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `localisation`
-- 

INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (1, '2 rue du Poitoux', '64000', 'Pau');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (2, '19 avenue Foch', '94300', 'Rungis');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (3, '2 rue Jean Jaures', '94800', 'Villejuif');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (4, '13 place du Moustique', '94800', 'Villejuif');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (5, '3 allee des Accacias', '14000', 'Caen');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (6, 'impasse des Condamines', '60000', 'Beauvais');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (0, NULL, NULL, 'Indéfini');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (16, '9 impasse du vieux port', '84000', 'Avignon');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (15, '46 rue de villacoublay', '78000', 'Versailles');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (14, '6 rue des tombes', '12000', 'Rodez');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (13, '6 rue du clos', '78150', 'Le Chesnay');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (12, '9 rue de villacoublay', '78220', 'Viroflay');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (11, '6 rue de la dame', '46000', 'Cahors');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (10, '6 rue des tombes', '44000', 'Nantes');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (9, '4 rue de la grande armée', '91000', 'Evry');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (8, '4 rue de la montagne', '27000', 'Evreux');
INSERT INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (7, '98 rue de la place', '51000', 'Lille');

-- --------------------------------------------------------

-- 
-- Structure de la table `modelescolis`
-- 

CREATE TABLE `modelescolis` (
  `idModelesColis` int(10) unsigned NOT NULL default '0',
  `Forme` int(11) NOT NULL default '0',
  `Modele` int(11) NOT NULL default '0',
  `hauteur` int(10) unsigned default NULL,
  `largeur` int(10) unsigned default NULL,
  `Profondeur` int(10) unsigned default NULL,
  `Diametre` int(10) unsigned default NULL,
  PRIMARY KEY  (`idModelesColis`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `modelescolis`
-- 

INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (1, 0, 0, 20, 20, 20, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (2, 0, 1, 40, 40, 40, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (3, 0, 2, 60, 60, 60, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (4, 1, 0, 20, 60, 20, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (5, 1, 1, 60, 80, 100, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (6, 1, 2, 30, 60, 40, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (7, 2, 0, 80, 20, 20, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (8, 2, 1, 40, 10, 10, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (9, 2, 2, 100, 40, 40, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (17, 0, 3, 5, 0, 0, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (16, 2, 3, 42, 32, 0, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (10, 1, 3, 183, 27, 69, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (15, 2, 3, 85, 96, 0, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (14, 1, 3, 12, 85, 74, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (13, 0, 3, 18, 0, 0, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (12, 1, 3, 18, 45, 5, 0);
INSERT INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`, `Diametre`) VALUES (11, 2, 3, 41, 23, 19, 0);

-- --------------------------------------------------------

-- 
-- Structure de la table `personnes`
-- 

CREATE TABLE `personnes` (
  `idPersonnes` int(10) unsigned NOT NULL default '0',
  `Localisation_idLocalisation` int(10) unsigned NOT NULL default '0',
  `Nom` char(50) default NULL,
  `Prenom` char(50) default NULL,
  `Telephone` char(15) default NULL,
  `Email` char(50) default NULL,
  PRIMARY KEY  (`idPersonnes`),
  KEY `Personnes_FKIndex1` (`Localisation_idLocalisation`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `personnes`
-- 

INSERT INTO `personnes` (`idPersonnes`, `Localisation_idLocalisation`, `Nom`, `Prenom`, `Telephone`, `Email`) VALUES (1, 3, 'Chaubert', 'Jean-Jacques', '0165654545', 'jj.chaubert@transtec.de');
INSERT INTO `personnes` (`idPersonnes`, `Localisation_idLocalisation`, `Nom`, `Prenom`, `Telephone`, `Email`) VALUES (2, 4, 'Toumou', 'Raymong', '0124466224', 'r.dumou@wanadoo.fr');
INSERT INTO `personnes` (`idPersonnes`, `Localisation_idLocalisation`, `Nom`, `Prenom`, `Telephone`, `Email`) VALUES (3, 5, 'Jardin', 'Maurice', '0123456598', 'hortifleur@trantec.com');
INSERT INTO `personnes` (`idPersonnes`, `Localisation_idLocalisation`, `Nom`, `Prenom`, `Telephone`, `Email`) VALUES (4, 6, 'Banal', 'EncorePlus', '01000100001', 'neutre@neutral.com');
INSERT INTO `personnes` (`idPersonnes`, `Localisation_idLocalisation`, `Nom`, `Prenom`, `Telephone`, `Email`) VALUES (7, 16, 'Jardin', 'Maurice', '0123456598', 'hortifleur@trantec.com');
INSERT INTO `personnes` (`idPersonnes`, `Localisation_idLocalisation`, `Nom`, `Prenom`, `Telephone`, `Email`) VALUES (6, 15, 'Lacombe', 'Julien', '0134651037', 'neutre@neutral.com');
INSERT INTO `personnes` (`idPersonnes`, `Localisation_idLocalisation`, `Nom`, `Prenom`, `Telephone`, `Email`) VALUES (5, 14, 'Sengler', 'nicolas', '0165654545', 'jj.chaubert@transtec.de');


-- --------------------------------------------------------

-- 
-- Structure de la table `preparation`
-- 

CREATE TABLE `preparation` (
  `idPreparation` int(10) unsigned NOT NULL default '0',
  `idPreparateur` int(10) unsigned NOT NULL default '0',
  `idDestination` int(10) unsigned NOT NULL default '0',
  `idCamion` int(10) ,
  `Origine` int(10),
  `Etat` int(10) unsigned NOT NULL default '0',
  `Volume` FLOAT NOT NULL default '0',
  Chargement int(10) unsigned NOT NULL,
  PRIMARY KEY  (`idPreparation`),
  KEY `Incidents_FKIndex1` (`idPreparateur`),
  KEY `Incidents_FKIndex2` (`idDestination`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `preparation`
-- 

INSERT INTO `preparation` (`idPreparation`, `idPreparateur`, `idDestination`, `idCamion`,`Origine`, `Etat`, `Volume`) VALUES (1, 3, 2,1,1, 0, 60);
INSERT INTO `preparation` (`idPreparation`, `idPreparateur`, `idDestination`, `idCamion`,`Origine`, `Etat`, `Volume`) VALUES (2, 3, 3,1,1, 0, 450);


/* La table de routage */

CREATE TABLE routage (
  idRoutage INTEGER(11) NOT NULL,
  Origine INTEGER(11) NOT NULL,
  Destination INTEGER(11) NOT NULL,
  PlatInter INTEGER(11),
  Distance FLOAT
);

-- 
-- Contenu de la table `routage`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `users`
-- 

CREATE TABLE `users` (
  `idUsers` int(10) unsigned NOT NULL default '0',
  `Personnes_idPersonnes` int(10) unsigned NOT NULL default '0',
  `Login` char(20) default NULL,
  `Password_2` char(20) default NULL,
  `Type_2` smallint(5) unsigned default NULL,
  PRIMARY KEY  (`idUsers`),
  KEY `Users_FKIndex1` (`Personnes_idPersonnes`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `users`
-- 

INSERT INTO `users` (`idUsers`, `Personnes_idPersonnes`, `Login`, `Password_2`, `Type_2`) VALUES (1, 1, 'user1', 'user1', 0);
INSERT INTO `users` (`idUsers`, `Personnes_idPersonnes`, `Login`, `Password_2`, `Type_2`) VALUES (2, 2, 'user2', 'user2', 0);
INSERT INTO `users` (`idUsers`, `Personnes_idPersonnes`, `Login`, `Password_2`, `Type_2`) VALUES (3, 3, 'user3', 'user3', 1);
INSERT INTO `users` (`idUsers`, `Personnes_idPersonnes`, `Login`, `Password_2`, `Type_2`) VALUES (4, 4, 'user4', 'user4', 2);
        
