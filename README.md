# Application de Gestion de Bibliothèque (Microservices)

## Description

Cette application est un système de gestion de bibliothèque basé sur une architecture **microservices** avec **Spring Boot**.

Elle permet de :

* Gérer les livres et leurs auteurs
* Gérer les utilisateurs et leurs rôles
* Gérer les emprunts et retours
* Suivre les actions (historique) via Kafka

---

##  Architecture

L’application est composée des microservices suivants :

* **biblliotheque-service** : gestion des livres et auteurs
* **emprunt-service** : gestion des emprunts et retours
* **utilisateur-service** : gestion des utilisateurs et authentification
* **suivi-service** : gestion de l’historique (Kafka)
* **gateway-service** : point d’entrée de l’application
*  **registry-service (Eureka)** : découverte des services
*  **property-service** : configuration centralisée

---

##  Technologies utilisées

* Java 21
* Spring Boot
* Spring Cloud (Eureka, Gateway, Config)
* Apache Kafka
* MySQL
* Spring Security + JWT
* Maven
* Docker (optionnel)

---

## Communication entre services

* REST (OpenFeign)
* Kafka (Event Driven)

## Diagrammes

* Diagramme de classes 
* Architecture microservices 

---

## Sécurité

* Authentification avec JWT
* Gestion des rôles (RBAC)

---

##  Lancer le projet

---

###  Lancer les microservices dans cet ordre :

1. property-service
2. registry-service
3. biblliotheque-service
4. utilisateur-service
5. emprunt-service
6. suivi-service
7. gateway-service

---

---

## Auteur

* Ngougueu Keumeni Ritice Jordan 

---


