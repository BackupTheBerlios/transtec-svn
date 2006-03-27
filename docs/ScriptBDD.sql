-- phpMyAdmin SQL Dump
-- version 2.6.1
-- http://www.phpmyadmin.net
-- 
-- Serveur: localhost
-- Généré le : Lundi 27 Mars 2006 à 15:39
-- Version du serveur: 4.1.9
-- Version de PHP: 4.3.10
-- 
-- Base de données: `transtec`
-- 

-- --------------------------------------------------------

-- 
-- Structure de la table `camions`
-- 

CREATE TABLE `camions` (
  `idCamions` int(10) unsigned NOT NULL default '0',
  `Immatriculation` char(25) NOT NULL default '',
  `Etat` smallint(6) default NULL,
  `Hauteur` float NOT NULL default '0',
  `Largeur` float NOT NULL default '0',
  `Profondeur` float NOT NULL default '0',
  `Volume` float NOT NULL default '0',
  `VolumeDispo` float default NULL,
  `Origine` int(11) NOT NULL default '0',
  `Destination` int(11) default NULL,
  PRIMARY KEY  (`idCamions`),
  KEY `Colis_FKIndex1` (`Origine`),
  KEY `Colis_FKIndex2` (`Destination`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `camions`
-- 

INSERT DELAYED IGNORE INTO `camions` (`idCamions`, `Immatriculation`, `Etat`, `Hauteur`, `Largeur`, `Profondeur`, `Volume`, `VolumeDispo`, `Origine`, `Destination`) VALUES (1, '1013TW10', 0, 2, 5, 2, 20, 20, 1, 7),
(2, '2356AQH11', 0, 2, 5, 2, 20, 20, 2, 7),
(3, '2356ASU12', 0, 2, 5, 2, 20, 20, 3, 7),
(4, '652GFD13', 0, 2, 5, 2, 20, 20, 4, 0),
(5, '1012TW14', 0, 2, 5, 2, 20, 20, 6, 0),
(6, '1312VS69', 0, 2, 5, 2, 20, 20, 7, 0),
(7, '2356AQH76', 0, 2, 5, 2, 20, 20, 8, 0),
(8, '2356ASU75', 0, 2, 5, 2, 20, 20, 9, 0),
(9, '652GFD74', 0, 2, 5, 2, 20, 20, 10, 0),
(10, '1012TW78', 0, 2, 5, 2, 20, 20, 11, 0),
(11, '1312VS77', 0, 2, 5, 2, 20, 20, 12, 0),
(12, '2356AQH68', 0, 2, 5, 2, 20, 20, 13, 0),
(13, '2356ASU67', 0, 2, 5, 1.5, 15, 15, 14, 0),
(14, '652GFD25', 0, 1.5, 5, 1.5, 11.25, 11.25, 1, 8),
(15, '1012TW32', 0, 2, 4, 2, 16, 16, 16, 0),
(16, '1312VS45', 0, 2, 3, 2, 12, 12, 17, 0),
(17, '2356AQH78', 0, 1.5, 7, 1, 10.5, 10.5, 18, 0),
(18, '2356ASU90', 0, 2, 5, 2, 20, 20, 19, 0),
(19, '652GFD93', 1, 2, 5, 2, 20, 20, 20, 0),
(20, '1012TW94', 2, 2, 5, 2, 20, 20, 21, 0);

-- --------------------------------------------------------

-- 
-- Structure de la table `chargement`
-- 

CREATE TABLE `chargement` (
  `idChargement` int(10) unsigned NOT NULL default '0',
  `Camions_idCamions` int(10) unsigned NOT NULL default '0',
  `NbColis` int(11) NOT NULL default '0',
  `VolChargement` float NOT NULL default '0',
  `DateCreation` datetime NOT NULL default '0000-00-00 00:00:00',
  `Users_idUsers` int(11) NOT NULL default '0',
  `CodeBarre` varchar(15) default NULL,
  `Etat` int(1) default NULL,
  PRIMARY KEY  (`idChargement`),
  KEY `Incidents_FKIndex1` (`Users_idUsers`),
  KEY `Chargement_FKIndex2` (`Camions_idCamions`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `chargement`
-- 

INSERT DELAYED IGNORE INTO `chargement` (`idChargement`, `Camions_idCamions`, `NbColis`, `VolChargement`, `DateCreation`, `Users_idUsers`, `CodeBarre`, `Etat`) VALUES (0, 1, 6, 200, '2006-02-19 00:00:00', 1, '987654321', 1);

-- --------------------------------------------------------

-- 
-- Structure de la table `chargement_colis`
-- 

CREATE TABLE `chargement_colis` (
  `idChargement` int(10) unsigned NOT NULL default '0',
  `idColis` int(10) unsigned NOT NULL default '0',
  `Numero` int(11) NOT NULL default '0',
  KEY `Incidents_FKIndex1` (`idChargement`),
  KEY `Chargement_FKIndex2` (`idColis`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `chargement_colis`
-- 


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
  `Origine` int(11) NOT NULL default '0',
  `Destination` int(10) unsigned default NULL,
  `EntrepotEnCours` int(11) NOT NULL default '0',
  `Code_barre` varchar(15) default NULL,
  `Poids` int(11) default NULL,
  `DateDepot` datetime default NULL,
  `Valeur` varchar(20) default NULL,
  `Fragilite` int(10) unsigned default NULL,
  `Volume` float NOT NULL default '0',
  PRIMARY KEY  (`idColis`),
  KEY `Colis_FKIndex1` (`ModelesColis_idModelesColis`),
  KEY `Colis_FKIndex2` (`Destination`),
  KEY `users_FKIndex3` (`Createur`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `colis`
-- 

INSERT DELAYED IGNORE INTO `colis` (`idColis`, `ModelesColis_idModelesColis`, `Createur`, `Expediteur`, `Destinataire`, `Origine`, `Destination`, `EntrepotEnCours`, `Code_barre`, `Poids`, `DateDepot`, `Valeur`, `Fragilite`, `Volume`) VALUES (8, 1, 3, 5, 1, 1, 4, 1, '225300223', 5, '2006-03-05 13:44:21', '0', 0, 0.008),
(9, 3, 3, 1, 5, 1, 4, 1, '786086629', 12, '2006-03-05 13:45:59', '0', 0, 0.216),
(6, 5, 3, 6, 6, 1, 7, 1, '756576279', 54, '2006-03-05 13:42:55', '0', 0, 0.48),
(7, 5, 3, 1, 4, 1, 7, 1, '070431174', 14, '2006-03-05 13:43:50', '0', 0, 0.48),
(5, 6, 3, 5, 3, 1, 7, 1, '676132437', 524, '2006-03-05 13:37:36', '0', 0, 0.072),
(4, 2, 3, 3, 2, 1, 7, 1, '049138101', 19, '2006-02-21 23:18:16', '0', 0, 0.064),
(3, 6, 3, 7, 5, 1, 4, 1, '355987463', 11, '2006-02-21 23:14:17', '0', 0, 0.072),
(2, 4, 3, 6, 5, 1, 4, 1, '218245514', 23, '2006-02-21 23:12:53', '0', 2, 0.024),
(1, 5, 3, 4, 2, 1, 9, 1, '866312927', 18, '2006-02-21 23:11:46', '0', 1, 0.48);

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

INSERT DELAYED IGNORE INTO `entrepots` (`idEntrepots`, `Localisation_idLocalisation`, `Telephone`) VALUES (1, 1, '0565487887'),
(2, 2, '0165322356'),
(0, 0, NULL),
(9, 9, '0163259687'),
(8, 8, '0396857423'),
(7, 7, '0474586332'),
(6, 6, '0136542596'),
(5, 5, '0365214896'),
(4, 4, '0496587463'),
(3, 3, '0147963215'),
(10, 17, '0212565423'),
(11, 18, '0478562552'),
(12, 19, '0245123265'),
(13, 20, '0245789885'),
(14, 21, '0545652112'),
(15, 22, '0565213212'),
(16, 23, '0278899887'),
(17, 24, '0545566554'),
(18, 25, '0323313665'),
(19, 26, '0545123265'),
(20, 27, '0545217813'),
(21, 28, '0445653254'),
(22, 29, '0565243857'),
(23, 30, '0312545879'),
(24, 31, '0345653212'),
(25, 32, '0345653212'),
(26, 33, '0545126598'),
(27, 34, '0425368856'),
(28, 35, '0245659878'),
(29, 36, '0212456532'),
(30, 37, '0545782355'),
(31, 38, '0523212624'),
(32, 39, '0512121212'),
(33, 40, '0598654874'),
(34, 41, '0512236545'),
(35, 42, '0212213223'),
(36, 43, '0345216598'),
(37, 44, '0356549887'),
(38, 45, '0476804651'),
(39, 46, '0312459878'),
(40, 47, '0545125445'),
(41, 48, '0312654875'),
(42, 49, '0512654592'),
(43, 50, '0312659874'),
(44, 51, '0248789852'),
(45, 52, '0245659878'),
(46, 53, '0532129875'),
(47, 54, '0512459872'),
(48, 55, '0345652198'),
(49, 56, '0212654791'),
(50, 57, '0354255884'),
(51, 58, '0245986715'),
(52, 59, '0362189443');

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
  `Zone` int(1) unsigned default NULL,
  PRIMARY KEY  (`idIncidents`),
  KEY `Incidents_FKIndex1` (`Users_idUsers`),
  KEY `Incidents_FKIndex2` (`Colis_idColis`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

-- 
-- Contenu de la table `incidents`
-- 

INSERT DELAYED IGNORE INTO `incidents` (`idIncidents`, `Colis_idColis`, `Users_idUsers`, `Description`, `DateCreation`, `Type_2`, `Etat`, `Zone`) VALUES (5, 3, 3, 'un probleme avec le carton ouvert', '2006-02-21 23:21:35', 10, 1, 0),
(4, 1, 3, 'il faut mieux jeter le colis', '2006-02-21 23:21:08', 10, 1, 0),
(3, 1, 3, 'le colis est foutu', '2006-02-21 23:20:33', 10, 2, 0),
(2, 1, 3, 'un autre coin vient de s''abimer', '2006-02-21 23:20:21', 10, 0, 0),
(1, 1, 3, 'Il y a un coin abim? sur le colis', '2006-02-21 23:20:06', 10, 2, 0);

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

INSERT DELAYED IGNORE INTO `localisation` (`idLocalisation`, `Adresse`, `CodePostal`, `Ville`) VALUES (1, '2 rue Jean Jaur', '94800', 'Villejuif'),
(2, '19 avenue Foch', '94300', 'Rungis'),
(3, '27 avenue du Poitoux', '64000', 'Pau'),
(4, '13 place du Moustique', '72000', 'Le Mans'),
(5, '3 allee des Accacias', '14000', 'Caen'),
(6, 'impasse des Condamines', '60000', 'Beauvais'),
(0, NULL, NULL, 'Ind?fini'),
(16, '9 impasse du vieux port', '84000', 'Avignon'),
(15, '46 rue de villacoublay', '78000', 'Versailles'),
(14, '6 rue des tombes', '12000', 'Rodez'),
(13, '6 rue du clos', '78150', 'Le Chesnay'),
(12, '9 rue de villacoublay', '78220', 'Viroflay'),
(11, '6 rue de la dame', '46000', 'Cahors'),
(10, '6 rue des tombes', '44000', 'Nantes'),
(9, '4 rue de la grande arm', '91000', 'Evry'),
(8, '4 rue de la montagne', '27000', 'Evreux'),
(7, '98 rue Pommery', '51000', 'Chalons en Champagne'),
(17, '8 Avenue de la mine', '28000', 'Chartres'),
(18, '156 Impasse Camus', '06000', 'Nice'),
(19, '56 avenue Ramier', '01000', 'Bourg-En-Bresse'),
(20, '48 rue de la Chanterelle', '02000', 'Verneuil-sur-Serre'),
(21, '25 Boulevard St Mand', '05000', 'Gap'),
(22, '45 Avenue des longs cous', '07000', 'Privas'),
(23, '65 Rue Ren? jean', '08000', 'Charleville Mezi?res'),
(24, '8 rue de la rive', '09000', 'Foix'),
(25, '8 Chemin des Trembliers', '10000', 'Troyes'),
(26, '8 Avenue du rempart', '11000', 'Carcassonne'),
(27, '45 Avenue Chaubert', '12000', 'Rodez'),
(28, '8 rue du Vieux port', '13000', 'Marseille'),
(29, '8 Avenue de l''Aubrac', '15000', 'Aurillac'),
(30, '8 rue de la Chocolaterie', '16000', 'Angoul?me'),
(31, '54 Voie du Port', '17000', 'La Rochelle'),
(32, '45 Boulevard des Arches', '18000', 'Bourges'),
(33, '87 rue des vignes', '19000', 'Tulles'),
(34, '8 rue des sangliers', '20000', 'Ajaccio'),
(35, '8 Boulevard Amora', '21000', 'Dijon'),
(36, '32 Avenue Fauche', '22000', 'St Brieuc'),
(37, '58 Avenue du port', '30000', 'N?mes'),
(38, '5 Avenue du Capitole', '31000', 'Toulouse'),
(39, '8 place des bovins', '32000', 'Auch'),
(40, '8 Place du centre', '33000', 'Bordeaux'),
(41, '56 Avenue du front de mer', '34000', 'Montpellier'),
(42, '8 Rue des brumes', '35000', 'Rennes'),
(43, '8 Avenue de l''a?roport', '36000', 'Ch?teauroux'),
(44, '8 Rue de la gare', '37000', 'Tours'),
(45, '8 Boulevard St Issoire', '38000', 'Grenoble'),
(46, '8 rue des Pommiers', '39000', 'Lons Le Saunier'),
(47, '6 Impasse des pin?des', '40000', 'Mont de Marsan'),
(48, '8 Rue du Ch?teau', '41000', 'Blois'),
(49, '98 Avenue du Stade', '42000', 'St Etienne'),
(50, '8 rue du Puy', '43000', 'Le Puy en Velay'),
(51, '56 Boulevard du Pont', '44000', 'Nantes'),
(52, 'Place des orl?anais', '45000', 'Orl?ans'),
(53, '2 Chemin du viaduc', '46000', 'Millau'),
(54, '8 Boulevard des prunes', '47000', 'Agen'),
(55, '8 rue des Anges', '49000', 'Angers'),
(56, '8 rue de l''eau', '50000', 'St Lo'),
(57, '8 rue Volta', '57000', 'Metz'),
(58, '8 rue de Magnycourt', '58000', 'Nevers'),
(59, '87 Avenue de Belgique', '59000', 'Lille');

-- --------------------------------------------------------

-- 
-- Structure de la table `modelescolis`
-- 

CREATE TABLE `modelescolis` (
  `idModelesColis` int(10) unsigned NOT NULL default '0',
  `Forme` int(11) NOT NULL default '0',
  `Modele` int(11) NOT NULL default '0',
  `hauteur` float unsigned default NULL,
  `largeur` float unsigned default NULL,
  `Profondeur` float unsigned default NULL,
  PRIMARY KEY  (`idModelesColis`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `modelescolis`
-- 

INSERT DELAYED IGNORE INTO `modelescolis` (`idModelesColis`, `Forme`, `Modele`, `hauteur`, `largeur`, `Profondeur`) VALUES (1, 0, 0, 0.2, 0.2, 0.2),
(2, 0, 1, 0.4, 0.4, 0.4),
(3, 0, 2, 0.6, 0.6, 0.6),
(4, 1, 0, 0.2, 0.6, 0.2),
(5, 1, 1, 0.6, 0.8, 1),
(6, 1, 2, 0.3, 0.6, 0.4);

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

INSERT DELAYED IGNORE INTO `personnes` (`idPersonnes`, `Localisation_idLocalisation`, `Nom`, `Prenom`, `Telephone`, `Email`) VALUES (1, 3, 'Chaubert', 'Jean-Jacques', '0165654545', 'jj.chaubert@transtec.de'),
(2, 4, 'Toumou', 'Raymong', '0124466224', 'r.dumou@wanadoo.fr'),
(3, 5, 'Jardin', 'Maurice', '0123456598', 'hortifleur@trantec.com'),
(4, 6, 'Banal', 'EncorePlus', '01000100001', 'neutre@neutral.com'),
(7, 16, 'Jardin', 'Maurice', '0123456598', 'hortifleur@trantec.com'),
(6, 15, 'Lacombe', 'Julien', '0134651037', 'neutre@neutral.com'),
(5, 14, 'Sengler', 'nicolas', '0165654545', 'jj.chaubert@transtec.de');

-- --------------------------------------------------------

-- 
-- Structure de la table `plan`
-- 

CREATE TABLE `plan` (
  `idChargement` int(11) NOT NULL default '0',
  `dessus` blob NOT NULL,
  `dessous` blob NOT NULL,
  `face` blob NOT NULL,
  `arriere` blob NOT NULL,
  `gauche` blob NOT NULL,
  `droite` blob NOT NULL,
  PRIMARY KEY  (`idChargement`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `plan`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `preparation`
-- 

CREATE TABLE `preparation` (
  `idPreparation` int(10) unsigned NOT NULL default '0',
  `idPreparateur` int(10) unsigned NOT NULL default '0',
  `idDestination` int(10) unsigned NOT NULL default '0',
  `idCamion` int(10) default NULL,
  `Origine` int(10) default NULL,
  `Etat` int(10) unsigned NOT NULL default '0',
  `Volume` float NOT NULL default '0',
  `ChargementEnCours` int(11) NOT NULL default '0',
  `Chargement` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`idPreparation`),
  KEY `Incidents_FKIndex1` (`idPreparateur`),
  KEY `Incidents_FKIndex2` (`idDestination`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `preparation`
-- 

INSERT DELAYED IGNORE INTO `preparation` (`idPreparation`, `idPreparateur`, `idDestination`, `idCamion`, `Origine`, `Etat`, `Volume`, `ChargementEnCours`, `Chargement`) VALUES (1, 3, 7, 1, 1, 0, 20, 0, 0),
(2, 3, 7, 2, 1, 0, 20, 0, 0),
(3, 3, 7, 3, 1, 0, 20, 0, 0),
(4, 3, 8, 14, 1, 0, 11.25, 0, 0);

-- --------------------------------------------------------

-- 
-- Structure de la table `routage`
-- 

CREATE TABLE `routage` (
  `idRoutage` int(10) unsigned NOT NULL default '0',
  `Origine` int(11) NOT NULL default '0',
  `Destination` int(11) NOT NULL default '0',
  `PlatInter` int(11) default NULL,
  `Distance` float default NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Contenu de la table `routage`
-- 

INSERT DELAYED IGNORE INTO `routage` (`idRoutage`, `Origine`, `Destination`, `PlatInter`, `Distance`) VALUES (1, 3, 7, 1, 1059),
(2, 6, 3, 1, 961),
(3, 1, 7, 6, 188),
(4, 8, 9, 0, 129),
(5, 5, 6, 0, 211),
(6, 2, 9, 0, 23),
(7, 47, 37, 0, 475),
(8, 47, 27, 0, 582),
(9, 47, 48, 0, 530),
(10, 47, 1, 0, 703),
(11, 47, 23, 0, 252),
(12, 47, 32, 0, 76),
(13, 47, 22, 0, 255),
(14, 47, 6, 0, 252),
(15, 47, 41, 0, 544),
(16, 47, 33, 0, 140),
(17, 47, 12, 0, 716),
(18, 47, 25, 0, 541),
(19, 47, 5, 0, 737),
(20, 47, 19, 0, 207),
(21, 47, 7, 0, 889),
(22, 47, 16, 0, 935),
(23, 47, 10, 0, 655),
(24, 47, 36, 0, 440),
(25, 47, 28, 0, 705),
(26, 47, 8, 0, 743),
(27, 47, 9, 0, 698),
(28, 47, 17, 0, 199),
(29, 47, 14, 0, 675);

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

INSERT DELAYED IGNORE INTO `users` (`idUsers`, `Personnes_idPersonnes`, `Login`, `Password_2`, `Type_2`) VALUES (1, 1, 'user1', 'user1', 0),
(2, 2, 'user2', 'user2', 0),
(3, 3, 'user3', 'user3', 1),
(4, 4, 'user4', 'user4', 2),
(5, 3, 'user5', 'user5', 1);
        