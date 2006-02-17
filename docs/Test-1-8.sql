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

