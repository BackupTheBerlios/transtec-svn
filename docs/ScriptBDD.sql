-- phpMyAdmin SQL Dump
-- version 2.6.1
-- http://www.phpmyadmin.net
-- 
-- Serveur: localhost
-- Généré le : Lundi 27 Mars 2006 à 15:06
-- Version du serveur: 4.1.9
-- Version de PHP: 4.3.10
-- 
-- Base de données: `transtec`
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

INSERT DELAYED IGNORE INTO `colis` (`idColis`, `ModelesColis_idModelesColis`, `Createur`, `Expediteur`, `Destinataire`, `Origine`, `Destination`, `EntrepotEnCours`, `Code_barre`, `Poids`, `DateDepot`, `Valeur`, `Fragilite`, `Volume`) VALUES (8, 1, 3, 5, 1, 1, 4, 1, '225300223', 5, '2006-03-05 13:44:21', '0', 0, 615250),
(9, 3, 3, 1, 5, 1, 4, 1, '786086629', 12, '2006-03-05 13:45:59', '0', 0, 125),
(6, 5, 3, 6, 6, 1, 7, 1, '756576279', 54, '2006-03-05 13:42:55', '0', 0, 5832),
(7, 5, 3, 1, 4, 1, 7, 1, '070431174', 14, '2006-03-05 13:43:50', '0', 0, 75480),
(5, 9, 3, 5, 3, 1, 7, 1, '676132437', 524, '2006-03-05 13:37:36', '0', 0, 4050),
(4, 2, 3, 3, 2, 1, 7, 1, '049138101', 19, '2006-02-21 23:18:16', '0', 0, 17035),
(3, 6, 3, 7, 5, 1, 4, 1, '355987463', 11, '2006-02-21 23:14:17', '0', 0, 72000),
(2, 7, 3, 6, 5, 1, 4, 1, '218245514', 23, '2006-02-21 23:12:53', '0', 2, 340929),
(1, 8, 3, 4, 2, 1, 9, 1, '866312927', 18, '2006-02-21 23:11:46', '0', 1, 3142);
        