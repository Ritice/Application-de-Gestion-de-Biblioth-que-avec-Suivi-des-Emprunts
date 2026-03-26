
CREATE TABLE IF NOT EXISTS auteur(
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        nom VARCHAR(100) NOT NULL,
        prenom VARCHAR(100) NOT NULL,
        adresse VARCHAR(255),
        nationalite VARCHAR(100)
) ENGINE=InnoDB;

