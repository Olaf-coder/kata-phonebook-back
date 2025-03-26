# Phonebook-Backend

Ce projet de repertoire de contact est un exercice mandaté par un client afin de juger de mes compétence en developpement full-stack Java 17 & angular 19

Ce repository git contient le code back end, pour pouvoir lancer le projet, vous aurez besoin du front end [Phonebook-Frontend](https://github.com/Olaf-coder/kata-phonebook-front)

## Sujet

Ce projet demande de pouvoir gerer une liste de contact simple (ajouter, supprimer, lister, mettre a jour et filtrer par id, ou bien par nom et/ou prenom

## Choix des technologies

Une des contraintes demandé était d'avoir une API, du stockage en mémoire (donc local), et les composantes techniques suivantes étaient imposées pour le Back:

- Spring Boot
- Maven
- Java 17

C'est pour toutes ces raisons pour lesquelles j'ai les dépendances suivantes:

- Spring Web
- API JDBC
- Spring Data JPA
- H2 Database (pour sa facilité de configuration, propose un stockage local dans un fichier, et se gére comme une base de données classique (requêtes SQL, facilité d'accés via une API

## Information supplémentaire
Le back-end qui contient l'API doit se lancer idéalement en premier. Dans tous les cas, il est nécessaire pour faire fonctionner le projet. 
Sur intelliJ, il faut creer puis lancer une configurations de type Spring avec kata-phonebook-back comme classPath

Pour la configuration, quand le projet se lance il est actuellement sur le port 8080. Pour changer le port d'écoute, il faut remplacer la ligne suivante dans src/main/resources/application.properties:

    server.port=8080

De la documentation API existe sur ce projet sous la forme d'une page Swagger. Une fois le backend lancé, vous pouvez acceder a Swagger sur le lien suivant:

    http://localhost:8080/swagger-ui/index.html#/

Lorsque des données sont enregistrées, vous les trouverez à la racine du dossier dans phonebookDB.mv.db.
A savoir qu'il existe des solutions pour transformer ce fichier en Json.
