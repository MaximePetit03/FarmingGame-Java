# 💎 Emerald Valley

> **Simulation agricole dynamique développée en JavaFX.**
> Gérez votre ferme, dominez l'économie locale et bravez les caprices de la météo.

---

## 🌟 Points Forts du Projet

* **🌦️ Météo Dynamique** : Un système cyclique qui impacte la vitesse de croissance des plantes en temps réel.
* **🚜 Gestion de Production** : Cycle complet de la plantation à la récolte (Blé, Pastèques) et de l'élevage à la vente (Vaches, Poules).
* **🎧 Expérience Sonore** : Musiques d'ambiance adaptatives gérées de manière asynchrone pour une fluidité parfaite.
* **📈 Économie & Marché** : Système de vente et d'achat d'émeraudes avec inventaire persistant.

---

## 🛠️ Stack Technique

| Technologie | Usage |
| :--- | :--- |
| **Java 25** | Coeur de la logique métier (POO) |
| **JavaFX** | Interface graphique réactive et moderne |
| **FXML & CSS** | Structure des vues et design de l'interface |
| **MVC Pattern** | Architecture logicielle pour la séparation des données |

---

## 🧠 Compétences & Concepts Maîtrisés

### 🏗️ Architecture Logicielle
* **Pattern MVC** : Organisation stricte du code (Models, Views, Controllers).
* **Encapsulation POO** : Classes autonomes pour les champs (`CultivableField`) et les animaux.

### ⚡ Performances & Multithreading
* **UI Threading** : Utilisation de `Platform.runLater` pour les mises à jour asynchrones.
* **Media Caching** : Chargement et mise en cache des sons pour éviter les latences au changement d'onglet.
* **Game Loop** : Utilisation de `Timeline` JavaFX pour synchroniser les cycles de pousse.

### 🎨 Design & UX
* **Visual Feedback** : Effets de survol (hover) personnalisés et animations de translation.
* **HUD Overlay** : Superposition de l'interface météo via un système de `StackPane`.

---

## 🚀 Installation & Test

1.  **Pré-requis** : Avoir le JDK 25+ installé.
2.  **Lancement** :
    ```bash
    java -jar Projet-Java.jar
    ```
