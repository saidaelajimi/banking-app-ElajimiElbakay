# 🏦 Digital Banking Application

Application bancaire distribuée basée sur une architecture microservices avec Spring Boot et React.

## 📋 Table des Matières

- [Architecture](#architecture)
- [Technologies Utilisées](#technologies-utilisées)
- [Prérequis](#prérequis)
- [Installation et Démarrage](#installation-et-démarrage)
- [Documentation des API](#documentation-des-api)
- [Structure du Projet](#structure-du-projet)
- [Tests Manuels](#tests-manuels)

---

## 🏗️ Architecture

```
┌─────────────────┐
│   FRONTEND      │
│   (React)       │
│   Port: 3000    │
└────────┬────────┘
         │
         ├──────────────────┬──────────────────┐
         │                  │                  │
         ▼                  ▼                  ▼
┌────────────────┐  ┌────────────────┐  ┌────────────────┐
│ account-service│  │transaction-svc │  │reporting-svc   │
│ (Spring Data   │  │ (OpenFeign)    │  │ (WebClient)    │
│  REST)         │  │                │  │                │
│ Port: 8081     │◄─┤ Port: 8082     │  │ Port: 8083     │
│                │  │                │  │                │
│ H2 Database    │  │ H2 Database    │  │ API Externe    │
└────────────────┘  └────────────────┘  └────────────────┘
```

---

## 🛠️ Technologies Utilisées

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data REST** (account-service)
- **Spring Cloud OpenFeign** (transaction-service)
- **Spring WebFlux / WebClient** (reporting-service)
- **H2 Database** (base de données en mémoire)
- **Lombok** (réduction du code boilerplate)
- **Maven** (gestion des dépendances)

### Frontend
- **React 18+**
- **Axios** (requêtes HTTP)
- **React Router** (navigation)
- **Tailwind CSS** (styling)

### API Externe
- **ExchangeRate-API** (https://api.exchangerate-api.com)

---

## 📦 Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- ✅ **Java JDK 17+** : [Télécharger](https://adoptium.net/)
- ✅ **Maven 3.8+** : [Télécharger](https://maven.apache.org/download.cgi)
- ✅ **Node.js 18+** : [Télécharger](https://nodejs.org/)
- ✅ **Git** : [Télécharger](https://git-scm.com/)
- ✅ **IDE** : IntelliJ IDEA / VS Code

Vérifiez les installations :
```bash
java -version
mvn -version
node -version
npm -version
```

---

## 🚀 Installation et Démarrage

### 1️⃣ Cloner le projet

```bash
git clone https://github.com/votre-repo/digital-banking.git
cd digital-banking
```

### 2️⃣ Démarrer les microservices (Backend)

**Option A : Démarrage manuel (3 terminaux)**

```bash
# Terminal 1 - Account Service
cd account-service
mvn clean install
mvn spring-boot:run

# Terminal 2 - Transaction Service
cd transaction-service
mvn clean install
mvn spring-boot:run

# Terminal 3 - Reporting Service
cd reporting-service
mvn clean install
mvn spring-boot:run
```

**Option B : Démarrage avec script**

```bash
# Linux/Mac
./start-services.sh

# Windows
start-services.bat
```

### 3️⃣ Vérifier que les services sont lancés

- **account-service** : http://localhost:8081/api/accounts
- **transaction-service** : http://localhost:8082/api/transfers
- **reporting-service** : http://localhost:8083/api/exchange/rates?base=USD

### 4️⃣ Démarrer le Frontend

```bash
cd frontend
npm install
npm start
```

Le frontend sera accessible sur **http://localhost:3000**

---

## 📚 Documentation des API

### 🟦 account-service (Port 8081)

**Base URL:** `http://localhost:8081/api`

| Méthode | Endpoint | Description | Body |
|---------|----------|-------------|------|
| GET | `/accounts` | Liste tous les comptes | - |
| GET | `/accounts/{id}` | Détails d'un compte | - |
| POST | `/accounts` | Créer un nouveau compte | `{"owner":"John","balance":1000.0,"currency":"EUR"}` |
| PATCH | `/accounts/{id}` | Mise à jour partielle | `{"balance":1500.0}` |
| DELETE | `/accounts/{id}` | Supprimer un compte | - |

**Exemple de requête :**
```bash
curl -X POST http://localhost:8081/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"owner":"Alice Doe","balance":5000.0,"currency":"EUR"}'
```

**Console H2 :** http://localhost:8081/h2-console
- JDBC URL: `jdbc:h2:mem:accountdb`
- User: `sa`
- Password: *(vide)*

---

### 🟧 transaction-service (Port 8082)

**Base URL:** `http://localhost:8082/api`

| Méthode | Endpoint | Description | Body |
|---------|----------|-------------|------|
| POST | `/transfers` | Effectuer un transfert | `{"fromAccountId":1,"toAccountId":2,"amount":100.0,"currency":"EUR"}` |

**Exemple de requête :**
```bash
curl -X POST http://localhost:8082/api/transfers \
  -H "Content-Type: application/json" \
  -d '{
    "fromAccountId": 1,
    "toAccountId": 2,
    "amount": 100.0,
    "currency": "EUR"
  }'
```

**Logique du transfert :**
1. Récupère les comptes via Feign (`account-service`)
2. Vérifie le solde suffisant
3. Débite le compte source
4. Crédite le compte destination
5. Enregistre la transaction

**Console H2 :** http://localhost:8082/h2-console
- JDBC URL: `jdbc:h2:mem:transactiondb`

---

### 🟩 reporting-service (Port 8083)

**Base URL:** `http://localhost:8083/api/exchange`

| Méthode | Endpoint | Description | Params/Body |
|---------|----------|-------------|-------------|
| GET | `/rates?base={currency}` | Tous les taux de change | `?base=USD` |
| GET | `/rate?from={cur1}&to={cur2}` | Taux spécifique | `?from=USD&to=EUR` |
| POST | `/convert` | Convertir un montant | `{"amount":100,"from":"USD","to":"EUR"}` |

**Exemples de requêtes :**

```bash
# Obtenir tous les taux pour USD
curl "http://localhost:8083/api/exchange/rates?base=USD"

# Obtenir le taux USD -> EUR
curl "http://localhost:8083/api/exchange/rate?from=USD&to=EUR"

# Convertir 100 USD en EUR
curl -X POST http://localhost:8083/api/exchange/convert \
  -H "Content-Type: application/json" \
  -d '{"amount":100,"from":"USD","to":"EUR"}'
```

**API Externe utilisée :** https://api.exchangerate-api.com/v4/latest/{base}

---

## 📁 Structure du Projet

```
digital-banking/
├── account-service/
│   ├── src/main/java/com/bank/accountservice/
│   │   ├── AccountServiceApplication.java
│   │   ├── model/
│   │   │   └── Account.java
│   │   ├── repository/
│   │   │   └── AccountRepository.java
│   │   └── config/
│   │       └── DataLoader.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
│
├── transaction-service/
│   ├── src/main/java/com/bank/transactionservice/
│   │   ├── TransactionServiceApplication.java
│   │   ├── model/
│   │   │   └── Transaction.java
│   │   ├── dto/
│   │   │   ├── TransferRequest.java
│   │   │   └── AccountDTO.java
│   │   ├── client/
│   │   │   └── AccountClient.java (Feign)
│   │   ├── service/
│   │   │   └── TransactionService.java
│   │   ├── controller/
│   │   │   └── TransactionController.java
│   │   └── repository/
│   │       └── TransactionRepository.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
│
├── reporting-service/
│   ├── src/main/java/com/bank/reportingservice/
│   │   ├── ReportingServiceApplication.java
│   │   ├── dto/
│   │   │   ├── ExchangeRateResponse.java
│   │   │   ├── ConversionRequest.java
│   │   │   └── ConversionResponse.java
│   │   ├── service/
│   │   │   └── ExchangeRateService.java (WebClient)
│   │   ├── controller/
│   │   │   └── ReportingController.java
│   │   └── config/
│   │       └── WebClientConfig.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── App.js
│   ├── package.json
│   └── README.md
│
└── README.md
```

---

## 🧪 Tests Manuels

### Scénario 1 : Créer des comptes

```bash
# Créer compte 1
curl -X POST http://localhost:8081/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"owner":"Alice","balance":5000.0,"currency":"EUR"}'

# Créer compte 2
curl -X POST http://localhost:8081/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"owner":"Bob","balance":3000.0,"currency":"EUR"}'
```

### Scénario 2 : Effectuer un transfert

```bash
# Transfert de 500 EUR du compte 1 vers compte 2
curl -X POST http://localhost:8082/api/transfers \
  -H "Content-Type: application/json" \
  -d '{
    "fromAccountId": 1,
    "toAccountId": 2,
    "amount": 500.0,
    "currency": "EUR"
  }'
```

### Scénario 3 : Vérifier les soldes

```bash
# Vérifier compte 1 (devrait avoir 4500 EUR)
curl http://localhost:8081/api/accounts/1

# Vérifier compte 2 (devrait avoir 3500 EUR)
curl http://localhost:8081/api/accounts/2
```

### Scénario 4 : Taux de change

```bash
# Obtenir le taux USD -> MAD
curl "http://localhost:8083/api/exchange/rate?from=USD&to=MAD"
```

---

## 🎯 Critères de Réussite

- ✅ Les 3 microservices démarrent sans erreur
- ✅ Les endpoints Spring Data REST fonctionnent
- ✅ OpenFeign communique avec account-service
- ✅ WebClient récupère les taux de change
- ✅ Le frontend affiche toutes les données
- ✅ Les transferts sont persistés en base

---

## 🐛 Dépannage

### Problème : Port déjà utilisé

```bash
# Vérifier les ports occupés
netstat -ano | findstr :8081
netstat -ano | findstr :8082
netstat -ano | findstr :8083

# Tuer le processus (remplacer PID)
taskkill /PID <PID> /F
```

### Problème : Feign ne trouve pas account-service

- Vérifiez que account-service est démarré sur le port 8081
- Vérifiez l'URL dans `AccountClient` : `url = "http://localhost:8081/api"`

### Problème : Erreur CORS sur le frontend

Ajoutez cette configuration dans chaque microservice :

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE");
    }
}
```

---

## 📸 Captures d'écran

*(Ajoutez vos captures d'écran ici)*

1. Liste des comptes
2. Formulaire de transfert
3. Page de reporting
4. Console H2

---

## 👥 Contributeurs

- **Votre Nom** - Développement complet

---

## 📄 Licence

Ce projet est réalisé dans le cadre d'un projet académique.

---

## 📞 Contact

Pour toute question : votre-email@example.com
