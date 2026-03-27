CREATE DATABASE utilisateurs_db;
CREATE DATABASE bibliotheque_db;
CREATE DATABASE emprunts_db;

CREATE USER 'ritice'@'%' IDENTIFIED BY 'ritice';

GRANT ALL PRIVILEGES ON utilisateurs_db.* TO 'ritice'@'%';
GRANT ALL PRIVILEGES ON bibliotheque_db.* TO 'ritice'@'%';
GRANT ALL PRIVILEGES ON emprunts_db.* TO 'ritice'@'%';

FLUSH PRIVILEGES;