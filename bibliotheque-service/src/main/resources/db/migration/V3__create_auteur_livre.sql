CREATE TABLE IF NOT EXISTS auteur_livre (
      id BIGINT PRIMARY KEY AUTO_INCREMENT,
      auteur_id BIGINT NOT NULL,
      livre_id BIGINT NOT NULL,
      CONSTRAINT fk_auteurlivre_auteur FOREIGN KEY (auteur_id) REFERENCES auteur(id) ON DELETE CASCADE,
      CONSTRAINT fk_auteurlivre_livre FOREIGN KEY (livre_id) REFERENCES livre(id) ON DELETE CASCADE
) ENGINE=InnoDB;