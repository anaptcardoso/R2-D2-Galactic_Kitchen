# R2-D2 Galactic Kitchen

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring MVC](https://img.shields.io/badge/Spring%20MVC-6.1.8-green)
![Hibernate](https://img.shields.io/badge/Hibernate-6.2.6-blue)
![H2](https://img.shields.io/badge/H2-Database-informational)
![Groq](https://img.shields.io/badge/AI-Groq-purple)
![Vanilla JS](https://img.shields.io/badge/Frontend-Vanilla%20JavaScript-yellow)

**R2-D2 Galactic Kitchen** is a Star Wars inspired web application for recipe management, user nutrition profiles, weekly meal planning, and AI-powered food assistance.

The application is built with **Spring MVC** on the backend and **Vanilla JavaScript** on the frontend. It includes an AI chatbot and nutritionist powered by the **Groq API**, as well as planet-inspired recipe generation using **SWAPI**.

---

## Features

- Browse, search and filter recipes
- View recipe details, ingredients, preparation steps and nutrition values
- Manage user profiles
- Store nutrition goals, diet preferences, allergies and activity levels
- Create and manage weekly meal plans
- Generate shopping lists from selected recipes
- Chat with an AI-powered R2-D2 ChefBot
- Analyse foods with an AI nutritionist
- Generate personalized meal plans
- Generate Star Wars planet-inspired recipes using SWAPI

---

## Tech Stack

### Backend

| Technology | Purpose |
|---|---|
| Java 17 | Backend programming language |
| Spring MVC | REST controllers and web layer |
| Spring Context | Dependency injection and bean management |
| Spring Transactions | Transaction management |
| JPA / Hibernate | Object-relational mapping and persistence |
| EntityManager | Direct database access in repositories |
| H2 Database | In-memory development database |
| Jackson | JSON serialization and deserialization |
| Apache PDFBox | Reads recipe data from a PDF file |
| Groq API | AI chatbot and nutritionist responses |
| SWAPI | Star Wars planet data |
| Tomcat 10 | Servlet container |

### Frontend

| Technology | Purpose |
|---|---|
| HTML5 | Page structure |
| CSS3 | Styling and layout |
| Vanilla JavaScript | SPA logic, routing, DOM manipulation and API calls |

---

## Architecture

The project follows a layered architecture:

```text
Frontend SPA
   ↓
REST Controllers
   ↓
Services
   ↓
Repositories / DAOs
   ↓
Database
```

### Main layers

- **Frontend SPA**  
  Handles page rendering, navigation, user interaction and API calls.

- **Controllers**  
  Expose REST endpoints and receive HTTP requests.

- **Services**  
  Contain the main business logic of the application.

- **Repositories / DAOs**  
  Access the database using `EntityManager`.

- **DTOs**  
  Transfer data between backend and frontend.

- **Entities**  
  Represent database objects such as users, recipes and weekly plans.

---

## Project Structure

```text
R2-D2-Galactic_Kitchen/
├── src/
│   ├── main/
│   │   ├── java/org/
│   │   │   ├── config/
│   │   │   ├── controllers/
│   │   │   │   ├── rest/
│   │   │   │   └── web/
│   │   │   ├── converters/
│   │   │   ├── dtos/
│   │   │   ├── errors/
│   │   │   ├── exceptions/
│   │   │   ├── factories/
│   │   │   ├── model/
│   │   │   │   ├── entity/
│   │   │   │   ├── enums/
│   │   │   │   └── valueObject/
│   │   │   ├── persistence/
│   │   │   │   └── daos/
│   │   │   └── services/
│   │   ├── resources/
│   │   │   ├── ai/
│   │   │   │   ├── templates/
│   │   │   │   └── r2d2_galactic_recipes.pdf
│   │   │   ├── META-INF/
│   │   │   └── config.properties
│   │   └── webapp/
│   │       ├── css/
│   │       ├── js/
│   │       │   ├── components/
│   │       │   └── pages/
│   │       └── WEB-INF/
├── pom.xml
└── README.md
```

---

## Main Modules

### Recipes

The recipes module allows users to browse and manage the recipe catalogue.

Main features:

- View all recipes
- Search recipes by name
- Filter recipes by category or diet type
- View ingredients, preparation steps and nutrition values
- Create, update and delete recipes

---

### Profiles

The profiles module allows users to manage personal and nutritional information.

Main features:

- View all users
- Search users by name
- Create new profiles
- Edit user information
- Delete users
- Manage nutrition data

---

### Weekly Plan

The weekly plan module allows users to organize recipes into weekly meal plans.

Main features:

- Create weekly plans
- View plans by user
- View plans by week
- Add recipes to a plan
- Remove recipes from a plan
- Generate a shopping list from selected recipes

---

### R2-D2 ChefBot

The R2-D2 ChefBot is an AI assistant with a Star Wars inspired personality.

Main features:

- General food-related chat
- Recipe suggestions
- Ingredient-based recipe ideas
- Star Wars themed recipe generation
- Planet-inspired recipes using SWAPI data

---

### AI Nutritionist

The AI Nutritionist provides nutrition-related support using the Groq API.

Main features:

- Personalized nutrition consultations
- Food and meal analysis
- Meal plan generation
- Recipe suitability evaluation based on a user profile

---

## External API Integrations

### Groq API

The **Groq API** is used to generate AI responses for:

- R2-D2 ChefBot
- Nutritionist consultations
- Food analysis
- Meal plan suggestions
- Recipe evaluation

The API key is loaded from `config.properties`:

```properties
groq.api.key=your_groq_api_key_here
```

---

### SWAPI

The **Star Wars API (SWAPI)** is used to fetch planet data.

This data is sent to the AI service so it can generate recipes inspired by Star Wars planets such as:

- Tatooine
- Hoth
- Dagobah
- Coruscant
- Endor
- Naboo
- Mustafar
- Alderaan

---

## API Endpoints

### Recipes

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/recipes` | Get all recipes |
| GET | `/api/recipes/{id}` | Get recipe by ID |
| GET | `/api/recipes/category/{category}` | Get recipes by category |
| GET | `/api/recipes/search?name=value` | Search recipes by name |
| POST | `/api/recipes` | Create a new recipe |
| PUT | `/api/recipes/{id}` | Update a recipe |
| DELETE | `/api/recipes/{id}` | Delete a recipe |

---

### Users

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/search` | Search users by first and last name |
| POST | `/api/users/register` | Register a new user |
| PUT | `/api/users/{id}` | Update user personal data |
| DELETE | `/api/users/{id}` | Delete a user |
| GET | `/api/users/{id}/nutrition` | Get user nutrition profile |
| PUT | `/api/users/{id}/nutrition` | Update user nutrition profile |

---

### Nutrition

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/nutrition/{userId}` | Get a user's nutrition profile |
| PUT | `/api/nutrition/{userId}` | Update nutrition goals |
| POST | `/api/nutrition/analyse` | Analyse food using AI |
| GET | `/api/nutrition/recipe/{recipeId}` | Get nutrition data for a recipe |
| GET | `/api/nutrition/below/{maxCalories}` | Get recipes below a calorie limit |
| POST | `/api/nutrition/total` | Calculate total macros for multiple recipes |

---

### Weekly Plans

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/plan/{userId}` | Get all weekly plans for a user |
| GET | `/api/plan/{userId}/week/{weekStart}` | Get a plan for a specific week |
| POST | `/api/plan` | Create a weekly plan |
| POST | `/api/plan/{planId}/recipes/{recipeId}` | Add a recipe to a plan |
| DELETE | `/api/plan/{planId}/recipes/{recipeId}` | Remove a recipe from a plan |
| DELETE | `/api/plan/{planId}` | Delete a weekly plan |

---

### Chat

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/chat` | General R2-D2 chat |
| POST | `/api/chat/suggest` | Suggest recipes based on user input |
| POST | `/api/chat/planet` | Generate a recipe inspired by a Star Wars planet |

---

### Nutritionist

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/nutritionist/consult/{userId}` | Personalized nutrition consultation |
| POST | `/api/nutritionist/analyse` | Analyse food nutritional values |
| POST | `/api/nutritionist/meal-plan` | Generate a personalized meal plan |
| POST | `/api/nutritionist/evaluate/{recipeId}/{userId}` | Evaluate if a recipe suits a user |

---

## How to Run

### Requirements

- Java 17
- Maven
- Tomcat 10
- Groq API key

---

### 1. Clone the repository

```bash
git clone https://github.com/anaptcardoso/R2-D2-Galactic_Kitchen.git
cd R2-D2-Galactic_Kitchen
```

---

### 2. Configure the Groq API key

Create a `config.properties` file inside:

```text
src/main/resources/
```

Add:

```properties
groq.api.key=your_groq_api_key_here
```

---

### 3. Build the project

```bash
mvn clean install
```

---

### 4. Deploy to Tomcat

Copy the generated `.war` file to the Tomcat `webapps` folder:

```bash
cp target/R2-D2-Galactic_Kitchen.war /path/to/tomcat/webapps/
```

---

### 5. Run the application

Start Tomcat and open the application in the browser.

Example:

```text
http://localhost:8080/R2-D2-Galactic_Kitchen/
```

---

## Database

The project uses an **H2 in-memory database**.

This means:

- No external database setup is required
- Sample data is loaded automatically when the application starts
- Data is reset when the application restarts

---

## Team

This project was developed by:

| Name | Role |
|---|---|
| Ana Cardoso | Frontend & Backend Developer |
| Igor Saldanha | Frontend & Backend Developer |
| Inês Azevedo | Frontend & Backend Developer |
| Yasmin Pires | Frontend & Backend Developer |

---

## Future Improvements

Possible improvements for future versions:

- Add authentication and login system
- Use a persistent database instead of H2 in-memory database
- Improve weekly plan visualization
- Add more advanced nutrition statistics
- Improve AI response formatting
- Add automated tests
- Improve mobile responsiveness

---

## License

This project was developed for academic purposes.
