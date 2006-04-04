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

INSERT INTO `colis` VALUES (25, 6, 1, 14, 15, 7, 7, 7, '409119504', 46, '2006-04-03 18:20:21', '0', 2, 0.072);
INSERT INTO `colis` VALUES (24, 5, 1, 8, 5, 1, 1, 1, '538091034', 17, '2006-04-03 18:20:08', '0', 1, 0.48);
INSERT INTO `colis` VALUES (23, 6, 1, 12, 2, 7, 7, 7, '847233289', 12, '2006-04-03 18:19:02', '0', 0, 0.072);
INSERT INTO `colis` VALUES (22, 26, 1, 2, 9, 8, 8, 8, '110442102', 45, '2006-04-03 18:18:51', '0', 0, 0.328509);
INSERT INTO `colis` VALUES (21, 6, 1, 4, 1, 6, 6, 6, '137012981', 11, '2006-04-03 18:18:37', '0', 2, 0.072);
INSERT INTO `colis` VALUES (20, 25, 1, 3, 4, 6, 6, 6, '360163172', 13, '2006-04-03 18:18:21', '0', 1, 0.017576);
INSERT INTO `colis` VALUES (19, 24, 1, 2, 5, 1, 1, 1, '051437319', 16, '2006-04-03 18:17:57', '0', 0, 0.08901);
INSERT INTO `colis` VALUES (18, 3, 1, 4, 8, 7, 7, 7, '472668430', 46, '2006-04-03 18:17:39', '0', 0, 0.216);
INSERT INTO `colis` VALUES (17, 6, 1, 14, 12, 7, 7, 7, '873379231', 76, '2006-04-03 18:17:23', '0', 2, 0.072);
INSERT INTO `colis` VALUES (16, 23, 1, 3, 2, 7, 7, 7, '262438092', 47, '2006-04-03 18:17:09', '0', 1, 0.884736);
INSERT INTO `colis` VALUES (15, 22, 1, 10, 1, 6, 6, 6, '018483656', 16, '2006-04-03 18:16:33', '0', 0, 0.175616);
INSERT INTO `colis` VALUES (14, 5, 1, 6, 15, 7, 7, 7, '937847596', 42, '2006-04-03 18:16:18', '0', 0, 0.48);
INSERT INTO `colis` VALUES (13, 1, 1, 9, 3, 1, 1, 1, '922611512', 24, '2006-04-03 18:16:07', '0', 0, 0.008);
INSERT INTO `colis` VALUES (12, 21, 1, 12, 10, 6, 6, 6, '582050775', 16, '2006-04-03 18:15:40', '0', 1, 0.018122);
INSERT INTO `colis` VALUES (11, 20, 1, 9, 3, 8, 8, 8, '370941249', 52, '2006-04-03 18:15:08', '0', 0, 0.175616);
INSERT INTO `colis` VALUES (10, 19, 1, 14, 4, 6, 6, 6, '210976293', 41, '2006-04-03 18:14:40', '0', 0, 0.012167);
INSERT INTO `colis` VALUES (9, 6, 1, 6, 3, 1, 1, 1, '384509423', 13, '2006-04-03 18:14:15', '0', 0, 0.072);
INSERT INTO `colis` VALUES (8, 2, 1, 10, 1, 6, 6, 6, '398942354', 78, '2006-04-03 18:14:00', '0', 0, 0.064);
INSERT INTO `colis` VALUES (7, 6, 1, 14, 12, 17, 7, 7, '873379231', 74, '2006-04-03 18:17:23', '0', 0, 0.072);
INSERT INTO `colis` VALUES (6, 6, 1, 14, 2, 7, 7, 7, '243768772', 77, '2006-04-03 18:13:33', '0', 0, 0.072);
INSERT INTO `colis` VALUES (5, 3, 1, 15, 8, 7, 7, 7, '305110661', 4, '2006-04-03 18:13:24', '0', 1, 0.216);
INSERT INTO `colis` VALUES (4, 5, 1, 15, 3, 8, 8, 8, '915169925', 47, '2006-04-03 18:13:05', '0', 1, 0.48);
INSERT INTO `colis` VALUES (1, 1, 1, 9, 1, 6, 6, 6, '536497405', 12, '2006-04-03 18:12:02', '0', 0, 0.008);
INSERT INTO `colis` VALUES (2, 4, 1, 6, 6, 7, 7, 7, '688067917', 45, '2006-04-03 18:12:30', '0', 2, 0.024);
INSERT INTO `colis` VALUES (3, 2, 1, 12, 6, 7, 7, 7, '194614179', 41, '2006-04-03 18:12:49', '0', 1, 0.064);
INSERT INTO `colis` VALUES (31, 6, 1, 10, 4, 6, 6, 6, '025731053', 17, '2006-04-03 18:22:06', '0', 0, 0.072);
INSERT INTO `colis` VALUES (30, 29, 1, 1, 3, 8, 8, 8, '395545607', 18, '2006-04-03 18:21:55', '0', 1, 0.017255);
INSERT INTO `colis` VALUES (29, 28, 1, 3, 3, 1, 1, 1, '960660105', 64, '2006-04-03 18:21:31', '0', 0, 0.050653);
INSERT INTO `colis` VALUES (28, 5, 1, 3, 4, 6, 6, 6, '384959153', 45, '2006-04-03 18:21:12', '0', 2, 0.48);
INSERT INTO `colis` VALUES (27, 27, 1, 4, 1, 6, 6, 6, '951253355', 17, '2006-04-03 18:21:01', '0', 1, 0.274625);
INSERT INTO `colis` VALUES (26, 5, 1, 4, 9, 8, 8, 8, '133267654', 13, '2006-04-03 18:20:43', '0', 0, 0.48);
INSERT INTO `colis` VALUES (32, 3, 1, 11, 14, 7, 7, 7, '710460524', 16, '2006-04-03 18:23:15', '0', 1, 0.216);
INSERT INTO `colis` VALUES (33, 30, 1, 6, 3, 8, 8, 8, '421953900', 18, '2006-04-03 18:23:28', '0', 0, 0.912673);
INSERT INTO `colis` VALUES (34, 2, 1, 5, 16, 7, 7, 7, '596598209', 65, '2006-04-03 18:24:07', '0', 0, 0.064);
INSERT INTO `colis` VALUES (35, 2, 1, 6, 17, 7, 7, 7, '672030942', 54, '2006-04-03 18:24:34', '0', 2, 0.064);
INSERT INTO `colis` VALUES (36, 31, 1, 2, 8, 7, 7, 7, '322630275', 45, '2006-04-03 18:24:56', '0', 0, 0.149184);
INSERT INTO `colis` VALUES (37, 3, 1, 8, 2, 7, 7, 7, '218003877', 31, '2006-04-03 18:25:12', '0', 0, 0.216);
INSERT INTO `colis` VALUES (38, 6, 1, 6, 3, 1, 1, 1, '953920598', 27, '2006-04-03 18:25:22', '0', 0, 0.072);
INSERT INTO `colis` VALUES (39, 4, 1, 4, 14, 7, 7, 7, '269538688', 37, '2006-04-03 18:25:37', '0', 1, 0.024);
INSERT INTO `colis` VALUES (40, 5, 1, 2, 5, 1, 1, 1, '404375408', 41, '2006-04-03 18:25:49', '0', 1, 0.48);
INSERT INTO `colis` VALUES (41, 2, 1, 5, 6, 7, 7, 7, '577103963', 41, '2006-04-03 18:26:00', '0', 2, 0.064);
INSERT INTO `colis` VALUES (42, 1, 1, 12, 18, 1, 1, 1, '962858786', 19, '2006-04-03 18:28:09', '0', 0, 0.008);
INSERT INTO `colis` VALUES (43, 32, 1, 11, 4, 6, 6, 6, '641794200', 19, '2006-04-03 18:28:24', '0', 0, 0.009261);
INSERT INTO `colis` VALUES (44, 5, 1, 15, 4, 6, 6, 6, '176727529', 36, '2006-04-03 18:28:36', '0', 2, 0.48);
INSERT INTO `colis` VALUES (45, 2, 1, 19, 8, 7, 7, 7, '367964563', 53, '2006-04-03 18:29:02', '0', 0, 0.064);
INSERT INTO `colis` VALUES (46, 1, 1, 20, 6, 7, 7, 7, '370501082', 53, '2006-04-03 18:29:24', '0', 0, 0.008);
INSERT INTO `colis` VALUES (47, 5, 1, 16, 4, 6, 6, 6, '387038761', 14, '2006-04-03 18:29:34', '0', 0, 0.48);
INSERT INTO `colis` VALUES (48, 3, 1, 17, 5, 1, 1, 1, '735632760', 41, '2006-04-03 18:29:44', '0', 0, 0.216);
INSERT INTO `colis` VALUES (49, 33, 1, 12, 4, 6, 6, 6, '552281572', 41, '2006-04-03 18:30:02', '0', 0, 0.0078);
INSERT INTO `colis` VALUES (50, 6, 1, 6, 5, 1, 1, 1, '567818318', 64, '2006-04-03 18:30:19', '0', 1, 0.072);
INSERT INTO `colis` VALUES (51, 3, 1, 17, 5, 1, 1, 1, '695083767', 35, '2006-04-03 18:30:31', '0', 0, 0.216);
INSERT INTO `colis` VALUES (52, 5, 1, 6, 10, 6, 6, 6, '472298956', 13, '2006-04-03 18:30:53', '0', 1, 0.48);
INSERT INTO `colis` VALUES (53, 6, 1, 8, 10, 6, 6, 6, '564968490', 13, '2006-04-03 18:32:30', '0', 1, 0.072);
INSERT INTO `colis` VALUES (54, 34, 1, 5, 20, 1, 1, 1, '584987216', 16, '2006-04-03 18:32:46', '0', 1, 0.405224);
INSERT INTO `colis` VALUES (55, 4, 1, 3, 1, 6, 6, 6, '751030577', 18, '2006-04-03 18:32:58', '0', 0, 0.024);
INSERT INTO `colis` VALUES (56, 6, 1, 3, 1, 6, 6, 6, '499798555', 16, '2006-04-03 18:33:13', '0', 1, 0.072);
INSERT INTO `colis` VALUES (57, 35, 1, 16, 1, 6, 6, 6, '674286443', 63, '2006-04-03 18:33:29', '0', 0, 0.009261);
INSERT INTO `colis` VALUES (58, 6, 1, 18, 6, 7, 7, 7, '742757730', 17, '2006-04-03 18:33:40', '0', 0, 0.072);
INSERT INTO `colis` VALUES (59, 5, 1, 19, 11, 7, 7, 7, '612623925', 14, '2006-04-03 18:34:04', '0', 2, 0.48);
INSERT INTO `colis` VALUES (60, 6, 1, 8, 4, 6, 6, 6, '301567885', 17, '2006-04-03 18:34:29', '0', 0, 0.072);
INSERT INTO `colis` VALUES (61, 6, 1, 8, 15, 7, 7, 7, '625287796', 19, '2006-04-03 18:34:42', '0', 0, 0.072);
INSERT INTO `colis` VALUES (62, 36, 1, 12, 5, 1, 1, 1, '614062618', 3, '2006-04-03 18:34:56', '0', 0, 0.002744);
INSERT INTO `colis` VALUES (63, 6, 1, 20, 3, 1, 1, 1, '441050641', 16, '2006-04-03 18:35:08', '0', 0, 0.072);
INSERT INTO `colis` VALUES (64, 5, 1, 1, 3, 8, 8, 8, '617613701', 13, '2006-04-03 18:35:25', '0', 1, 0.48);
INSERT INTO `colis` VALUES (65, 37, 1, 14, 4, 6, 6, 6, '961751246', 13, '2006-04-03 18:35:43', '0', 0, 0.09282);
INSERT INTO `colis` VALUES (66, 6, 1, 8, 6, 7, 7, 7, '920523372', 19, '2006-04-03 18:35:57', '0', 1, 0.072);
INSERT INTO `colis` VALUES (67, 5, 1, 9, 4, 6, 6, 6, '973635844', 17, '2006-04-03 18:36:11', '0', 1, 0.48);
INSERT INTO `colis` VALUES (68, 5, 1, 20, 8, 7, 7, 7, '173402153', 13, '2006-04-03 18:36:24', '0', 2, 0.48);
INSERT INTO `colis` VALUES (69, 38, 1, 19, 20, 1, 1, 1, '474167874', 12, '2006-04-03 18:36:40', '0', 0, 0.175616);
INSERT INTO `colis` VALUES (70, 6, 1, 12, 3, 1, 1, 1, '696287581', 23, '2006-04-03 18:36:52', '0', 0, 0.072);
INSERT INTO `colis` VALUES (71, 5, 1, 18, 3, 8, 8, 8, '993716408', 16, '2006-04-03 18:37:12', '0', 0, 0.48);
INSERT INTO `colis` VALUES (72, 39, 1, 16, 1, 6, 6, 6, '065554244', 19, '2006-04-03 18:37:27', '0', 0, 0.046656);
INSERT INTO `colis` VALUES (73, 2, 1, 1, 11, 7, 7, 7, '409531417', 17, '2006-04-03 18:37:39', '0', 0, 0.064);
INSERT INTO `colis` VALUES (74, 1, 1, 19, 15, 7, 7, 7, '506125465', 63, '2006-04-03 18:37:50', '0', 0, 0.008);
INSERT INTO `colis` VALUES (75, 5, 1, 12, 4, 6, 6, 6, '551150453', 16, '2006-04-03 18:39:12', '0', 0, 0.48);
INSERT INTO `colis` VALUES (76, 40, 1, 17, 2, 7, 7, 7, '489038821', 13, '2006-04-03 18:39:25', '0', 0, 0.830584);
INSERT INTO `colis` VALUES (77, 6, 1, 6, 2, 7, 7, 7, '547397814', 19, '2006-04-03 18:39:36', '0', 0, 0.072);
INSERT INTO `colis` VALUES (78, 2, 1, 16, 3, 8, 8, 8, '826998551', 13, '2006-04-03 18:39:54', '0', 0, 0.064);
INSERT INTO `colis` VALUES (79, 4, 1, 17, 8, 7, 7, 7, '978004289', 15, '2006-04-03 18:40:07', '0', 1, 0.024);
INSERT INTO `colis` VALUES (80, 41, 1, 2, 3, 8, 8, 8, '912450062', 16, '2006-04-03 18:40:25', '0', 0, 0.111888);


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

INSERT INTO `localisation` VALUES (1, '2 rue Jean Jaur', '94800', 'Villejuif');
INSERT INTO `localisation` VALUES (2, '19 avenue Foch', '94300', 'Rungis');
INSERT INTO `localisation` VALUES (3, '27 avenue du Poitoux', '64000', 'Pau');
INSERT INTO `localisation` VALUES (4, '13 place du Moustique', '72000', 'Le Mans');
INSERT INTO `localisation` VALUES (5, '3 allee des Accacias', '14000', 'Caen');
INSERT INTO `localisation` VALUES (6, 'impasse des Condamines', '60000', 'Beauvais');
INSERT INTO `localisation` VALUES (0, NULL, NULL, 'Ind?fini');
INSERT INTO `localisation` VALUES (16, '9 impasse du vieux port', '84000', 'Avignon');
INSERT INTO `localisation` VALUES (15, '46 rue de villacoublay', '78000', 'Versailles');
INSERT INTO `localisation` VALUES (14, '6 rue des tombes', '12000', 'Rodez');
INSERT INTO `localisation` VALUES (13, '6 rue du clos', '78150', 'Le Chesnay');
INSERT INTO `localisation` VALUES (12, '9 rue de villacoublay', '78220', 'Viroflay');
INSERT INTO `localisation` VALUES (11, '6 rue de la dame', '46000', 'Cahors');
INSERT INTO `localisation` VALUES (10, '6 rue des tombes', '44000', 'Nantes');
INSERT INTO `localisation` VALUES (9, '4 rue de la grande arm', '91000', 'Evry');
INSERT INTO `localisation` VALUES (8, '4 rue de la montagne', '27000', 'Evreux');
INSERT INTO `localisation` VALUES (7, '98 rue Pommery', '51000', 'Chalons en Champagne');
INSERT INTO `localisation` VALUES (17, '8 Avenue de la mine', '28000', 'Chartres');
INSERT INTO `localisation` VALUES (18, '156 Impasse Camus', '06000', 'Nice');
INSERT INTO `localisation` VALUES (19, '56 avenue Ramier', '01000', 'Bourg-En-Bresse');
INSERT INTO `localisation` VALUES (20, '48 rue de la Chanterelle', '02000', 'Verneuil-sur-Serre');
INSERT INTO `localisation` VALUES (21, '25 Boulevard St Mand', '05000', 'Gap');
INSERT INTO `localisation` VALUES (22, '45 Avenue des longs cous', '07000', 'Privas');
INSERT INTO `localisation` VALUES (23, '65 Rue Ren? jean', '08000', 'Charleville Mezi?res');
INSERT INTO `localisation` VALUES (24, '8 rue de la rive', '09000', 'Foix');
INSERT INTO `localisation` VALUES (25, '8 Chemin des Trembliers', '10000', 'Troyes');
INSERT INTO `localisation` VALUES (26, '8 Avenue du rempart', '11000', 'Carcassonne');
INSERT INTO `localisation` VALUES (27, '45 Avenue Chaubert', '12000', 'Rodez');
INSERT INTO `localisation` VALUES (28, '8 rue du Vieux port', '13000', 'Marseille');
INSERT INTO `localisation` VALUES (29, '8 Avenue de l''Aubrac', '15000', 'Aurillac');
INSERT INTO `localisation` VALUES (30, '8 rue de la Chocolaterie', '16000', 'Angoul?me');
INSERT INTO `localisation` VALUES (31, '54 Voie du Port', '17000', 'La Rochelle');
INSERT INTO `localisation` VALUES (32, '45 Boulevard des Arches', '18000', 'Bourges');
INSERT INTO `localisation` VALUES (33, '87 rue des vignes', '19000', 'Tulles');
INSERT INTO `localisation` VALUES (34, '8 rue des sangliers', '20000', 'Ajaccio');
INSERT INTO `localisation` VALUES (35, '8 Boulevard Amora', '21000', 'Dijon');
INSERT INTO `localisation` VALUES (36, '32 Avenue Fauche', '22000', 'St Brieuc');
INSERT INTO `localisation` VALUES (37, '58 Avenue du port', '30000', 'N?mes');
INSERT INTO `localisation` VALUES (38, '5 Avenue du Capitole', '31000', 'Toulouse');
INSERT INTO `localisation` VALUES (39, '8 place des bovins', '32000', 'Auch');
INSERT INTO `localisation` VALUES (40, '8 Place du centre', '33000', 'Bordeaux');
INSERT INTO `localisation` VALUES (41, '56 Avenue du front de mer', '34000', 'Montpellier');
INSERT INTO `localisation` VALUES (42, '8 Rue des brumes', '35000', 'Rennes');
INSERT INTO `localisation` VALUES (43, '8 Avenue de l''a?roport', '36000', 'Ch?teauroux');
INSERT INTO `localisation` VALUES (44, '8 Rue de la gare', '37000', 'Tours');
INSERT INTO `localisation` VALUES (45, '8 Boulevard St Issoire', '38000', 'Grenoble');
INSERT INTO `localisation` VALUES (46, '8 rue des Pommiers', '39000', 'Lons Le Saunier');
INSERT INTO `localisation` VALUES (47, '6 Impasse des pin?des', '40000', 'Mont de Marsan');
INSERT INTO `localisation` VALUES (48, '8 Rue du Ch?teau', '41000', 'Blois');
INSERT INTO `localisation` VALUES (49, '98 Avenue du Stade', '42000', 'St Etienne');
INSERT INTO `localisation` VALUES (50, '8 rue du Puy', '43000', 'Le Puy en Velay');
INSERT INTO `localisation` VALUES (51, '56 Boulevard du Pont', '44000', 'Nantes');
INSERT INTO `localisation` VALUES (52, 'Place des orl?anais', '45000', 'Orl?ans');
INSERT INTO `localisation` VALUES (53, '2 Chemin du viaduc', '46000', 'Millau');
INSERT INTO `localisation` VALUES (54, '8 Boulevard des prunes', '47000', 'Agen');
INSERT INTO `localisation` VALUES (55, '8 rue des Anges', '49000', 'Angers');
INSERT INTO `localisation` VALUES (56, '8 rue de l''eau', '50000', 'St Lo');
INSERT INTO `localisation` VALUES (57, '8 rue Volta', '57000', 'Metz');
INSERT INTO `localisation` VALUES (58, '8 rue de Magnycourt', '58000', 'Nevers');
INSERT INTO `localisation` VALUES (59, '87 Avenue de Belgique', '59000', 'Lille');
INSERT INTO `localisation` VALUES (60, '27 avenue du Poitoux', '75000', 'Pau');
INSERT INTO `localisation` VALUES (61, '42 impasse du vieux port', '84000', 'Avignon');
INSERT INTO `localisation` VALUES (62, 'impasse des Condamines', '60000', 'Beauvais');
INSERT INTO `localisation` VALUES (63, '13 place du Moustique', '72000', 'Le Mans');
INSERT INTO `localisation` VALUES (64, '13 place du Moustique', '72000', 'Le Mans');
INSERT INTO `localisation` VALUES (65, '46 rue de villacoublay', '78140', 'Vélizy');
INSERT INTO `localisation` VALUES (66, '18 Champs Elysée', '75000', 'Paris');
INSERT INTO `localisation` VALUES (67, '15 place de l''amrmistice', '72000', 'Le Mans');
INSERT INTO `localisation` VALUES (68, '16 place du Moustique', '72000', 'Le Mans');
INSERT INTO `localisation` VALUES (69, '42 rue de villacoublay', '78140', 'Vélizy');
INSERT INTO `localisation` VALUES (70, '43 allee des Accacias', '14000', 'Caen');
INSERT INTO `localisation` VALUES (71, '43 Champs Elysée', '75000', 'Paris');
INSERT INTO `localisation` VALUES (72, '6 rue des tombes', '12000', 'Rodez');

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

INSERT INTO `modelescolis` VALUES (1, 0, 0, 0.2, 0.2, 0.2);
INSERT INTO `modelescolis` VALUES (2, 0, 1, 0.4, 0.4, 0.4);
INSERT INTO `modelescolis` VALUES (3, 0, 2, 0.6, 0.6, 0.6);
INSERT INTO `modelescolis` VALUES (4, 1, 0, 0.2, 0.6, 0.2);
INSERT INTO `modelescolis` VALUES (5, 1, 1, 0.6, 0.8, 1);
INSERT INTO `modelescolis` VALUES (6, 1, 2, 0.3, 0.6, 0.4);
INSERT INTO `modelescolis` VALUES (7, 0, 3, 12, 0, 0);
INSERT INTO `modelescolis` VALUES (8, 2, 3, 13, 42, 0);
INSERT INTO `modelescolis` VALUES (9, 1, 3, 65, 23, 12);
INSERT INTO `modelescolis` VALUES (35, 0, 3, 0.21, 0, 0);
INSERT INTO `modelescolis` VALUES (34, 0, 3, 0.74, 0, 0);
INSERT INTO `modelescolis` VALUES (33, 1, 3, 0.25, 0.26, 0.12);
INSERT INTO `modelescolis` VALUES (32, 0, 3, 0.21, 0, 0);
INSERT INTO `modelescolis` VALUES (31, 1, 3, 0.96, 0.21, 0.74);
INSERT INTO `modelescolis` VALUES (30, 0, 3, 0.97, 0, 0);
INSERT INTO `modelescolis` VALUES (29, 1, 3, 0.35, 0.17, 0.29);
INSERT INTO `modelescolis` VALUES (28, 0, 3, 0.37, 0, 0);
INSERT INTO `modelescolis` VALUES (27, 0, 3, 0.65, 0, 0);
INSERT INTO `modelescolis` VALUES (19, 0, 3, 0.23, 0, 0);
INSERT INTO `modelescolis` VALUES (20, 0, 3, 0.56, 0, 0);
INSERT INTO `modelescolis` VALUES (21, 1, 3, 0.41, 0.26, 0.17);
INSERT INTO `modelescolis` VALUES (22, 0, 3, 0.56, 0, 0);
INSERT INTO `modelescolis` VALUES (23, 0, 3, 0.96, 0, 0);
INSERT INTO `modelescolis` VALUES (24, 1, 3, 0.45, 0.86, 0.23);
INSERT INTO `modelescolis` VALUES (25, 0, 3, 0.26, 0, 0);
INSERT INTO `modelescolis` VALUES (26, 0, 3, 0.69, 0, 0);
INSERT INTO `modelescolis` VALUES (36, 0, 3, 0.14, 0, 0);
INSERT INTO `modelescolis` VALUES (37, 1, 3, 0.42, 0.85, 0.26);
INSERT INTO `modelescolis` VALUES (38, 0, 3, 0.56, 0, 0);
INSERT INTO `modelescolis` VALUES (39, 0, 3, 0.36, 0, 0);
INSERT INTO `modelescolis` VALUES (40, 0, 3, 0.94, 0, 0);
INSERT INTO `modelescolis` VALUES (41, 1, 3, 0.63, 0.24, 0.74);
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

INSERT INTO `personnes` VALUES (1, 3, 'Chaubert', 'Jean-Jacques', '0165654545', 'jj.chaubert@transtec.de');
INSERT INTO `personnes` VALUES (2, 4, 'Toumou', 'Raymong', '0124466224', 'r.dumou@wanadoo.fr');
INSERT INTO `personnes` VALUES (3, 5, 'Jardin', 'Maurice', '0123456598', 'hortifleur@trantec.com');
INSERT INTO `personnes` VALUES (4, 6, 'Banal', 'EncorePlus', '01000100001', 'neutre@neutral.com');
INSERT INTO `personnes` VALUES (7, 16, 'Jardin', 'Maurice', '0123456598', 'hortifleur@trantec.com');
INSERT INTO `personnes` VALUES (6, 15, 'Lacombe', 'Julien', '0134651037', 'neutre@neutral.com');
INSERT INTO `personnes` VALUES (5, 14, 'Sengler', 'nicolas', '0165654544', 'jj.chaubert@transtec.de');
INSERT INTO `personnes` VALUES (8, 60, 'Chaubert', 'julien', '0165654545', 'jj.chaubert@transtec.de');
INSERT INTO `personnes` VALUES (9, 61, 'Martin', 'Raymond', '0123456598', 'hortifleur@trantec.com');
INSERT INTO `personnes` VALUES (10, 62, 'Rabin', 'maurice', '01000100001', 'neutre@neutral.com');
INSERT INTO `personnes` VALUES (11, 63, 'Toumou', 'laurent', '0124466224', 'r.dumou@wanadoo.fr');
INSERT INTO `personnes` VALUES (12, 64, 'Dupond', 'Mireille', '0124466224', 'r.dumou@wanadoo.fr');
INSERT INTO `personnes` VALUES (13, 65, 'Lacombe', 'Julien', '0134651037', 'neutre@neutral.com');
INSERT INTO `personnes` VALUES (14, 66, 'Du Lac', 'Maurice', '0123456598', 'hortifleur@trantec.com');
INSERT INTO `personnes` VALUES (15, 67, 'Pailhes', 'laurent', '0124466224', 'r.dumou@wanadoo.fr');
INSERT INTO `personnes` VALUES (16, 68, 'Pillant', 'Loic', '0124466224', 'r.dumou@wanadoo.fr');
INSERT INTO `personnes` VALUES (17, 69, 'Lemaire', 'Marc', '0134651037', 'neutre@neutral.com');
INSERT INTO `personnes` VALUES (18, 70, 'Murt', 'Jacques', '0123456598', 'hortifleur@trantec.com');
INSERT INTO `personnes` VALUES (19, 71, 'Lamartine', 'Pascale', '0123456598', 'hortifleur@trantec.com');
INSERT INTO `personnes` VALUES (20, 72, 'Roche', 'Francois', '0165654544', 'jj.chaubert@transtec.de');

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
        