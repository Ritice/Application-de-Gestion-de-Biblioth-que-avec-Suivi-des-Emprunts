CREATE TABLE IF NOT EXISTS livre (
       id BIGINT PRIMARY KEY AUTO_INCREMENT,
       titre VARCHAR(255) NOT NULL,
       resume TEXT,
       seuil_minimal INT NOT NULL DEFAULT 0,
       total_exemplaires INT NOT NULL DEFAULT 0,
       exemplaires_disponibles INT NOT NULL DEFAULT 0,
       img_couverture LONGBLOB,
       category VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

