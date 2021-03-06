XspeedIt
========

1. [Objectif](#Objectif)
2. [Solution](#Solution)

XspeedIt est une société d'import / export ayant robotisé toute sa chaîne d'emballage de colis.  
Elle souhaite trouver un algorithme permettant à ses robots d'optimiser le nombre de cartons d'emballage utilisés.

Les articles à emballer sont de taille variable, représentée par un entier compris entre 1 et 9.  
Chaque carton a une capacité de contenance de 10.  
Ainsi, un carton peut par exemple contenir un article de taille 3, un article de taille 1, et un article de taille 6.

La chaîne d'articles à emballer est représentée par une suite de chiffres, chacun représentant un article par sa taille.  
Après traitement par le robot d'emballage, la chaîne est séparée par des "/" pour représenter les articles contenus dans un carton.

*Exemple*  
```python
Chaîne d'articles en entrée : 163841689525773  
Chaîne d'articles emballés  : 163/8/41/6/8/9/52/5/7/73
```

L'algorithme actuel du robot d'emballage est très basique.  
Il prend les articles les uns après les autres, et les mets dans un carton.  
Si la taille totale dépasse la contenance du carton, le robot met l'article dans le carton suivant.

Objectif
--------
<a name="Objectif"></a>
Implémenter un algorithme qui permettrait de maximiser le nombre d'articles par carton, en utilisant un langage pouvant être exécuté sur une JVM 1.7 minimum ou en node.js.  
L'ordre des cartons et des articles n'a pas d'importance.

*Exemple*  
```python
Articles      : 163841689525773  
Robot actuel  : 163/8/41/6/8/9/52/5/7/73 => 10 cartons utilisés  
Robot optimisé: 163/82/46/19/8/55/73/7   => 8  cartons utilisés
```

Solution
--------
<a name="Solution"></a>
#### Stack technique
- Java 8
- Spring Boot pour une configuration simplifiée de l'application 
- Maven pour build/test

#### Organisation du code
> **Packages:**
>- **com.xpeedit.domain** Entités (paquet, produit, stokage)
>- **com.xpeedit.repository** Couche d'accès aux données (les paquets dans l'espace de stockage)
>- **com.xpeedit.service** Services de distribution des produits dans les paquets.
Deux implémentations sont proposées:
PackageEngineV1 et PackageEngineV2.

Pour chaque implémentation (V1 et V2), des tests unitaires permettent de valider la bonne répartition des produits dans les paquets.

#### Installation et démarrage
- Installer Java 8 (depuis le website Oracle).
- (Optionnel) Installer Maven 3.5. Normalement pas besoin de l'installer, maven sera automatiquement installé au démarrage du script mvnw.
> **Lancer les tests:**
>- mvnw.cmd test

> **Lancer en ligne de commande avec la chaine de produits en argument:**
>- mvnw.cmd spring-boot:run -Drun.arguments="--products=163841689525773"

> **Lancer en utilisant votre IDE préféré:**
>- Il faut lancer la main class **com.xpeedit.XpeeditApplication** en lui passant les produits en argument **--products=163841689525773**