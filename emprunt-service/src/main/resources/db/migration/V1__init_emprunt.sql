CREATE TABLE emprunts (
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
  utilisateur_id      BIGINT        NOT NULL,
  livre_id            BIGINT        NOT NULL,
  livre_titre         VARCHAR(255),
  date_emprunt        DATETIME      NOT NULL,
  date_retour_prevue  DATETIME,
  date_retour_reelle  DATETIME,
  statut              VARCHAR(20)   NOT NULL
);

CREATE TABLE suivi_emprunts (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    emprunt_id      BIGINT       NOT NULL,
    livre_id        BIGINT       NOT NULL,
    livre_titre     VARCHAR(255),
    utilisateur_id  BIGINT       NOT NULL,
    type_action     VARCHAR(20)  NOT NULL,
    date_action     DATETIME     NOT NULL
);