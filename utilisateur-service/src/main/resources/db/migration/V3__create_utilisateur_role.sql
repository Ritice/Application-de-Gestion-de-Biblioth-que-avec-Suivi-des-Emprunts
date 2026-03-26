CREATE TABLE IF NOT EXISTS utilisateur_role (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   role_id BIGINT NOT NULL,
   utilisateur_id BIGINT NOT NULL,
   CONSTRAINT fk_userrole_role FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
   CONSTRAINT fk_userrole_utilisateur FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id) ON DELETE CASCADE
)ENGINE=InnoDB;