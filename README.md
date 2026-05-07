# R2-D2 Galactic Kitchen 🚀

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring](https://img.shields.io/badge/Spring-6.1.8-green)
![H2](https://img.shields.io/badge/H2-Database-blue)
![Groq](https://img.shields.io/badge/Groq-AI-purple)

A web application for recipe management and AI-powered meal planning, built with Spring MVC and Vanilla JavaScript.

---

## Features

- Recipe management with filters by category, diet type, difficulty and meal type
- Weekly meal planning with recipe assignment
- AI-powered nutritionist consultation (Groq AI)
- Food nutritional analysis via AI
- User profile management with nutritional preferences
- REST API with JSON responses

---

## Tech Stack

### Backend
| Technology | Version |
|---|---|
| Java | 17 |
| Spring MVC | 6.1.8 |
| Spring Data JPA | 3.2.6 |
| Hibernate | 6.2.6 |
| H2 Database | in-memory |
| Groq AI | - |
| Jackson | 2.15.2 |
| Tomcat | 10 |

### Frontend
| Technology | Usage |
|---|---|
| HTML5 | Semantic structure |
| CSS3 | Styling and layout |
| Vanilla JavaScript | DOM manipulation and API calls |

---
## Project Structure

\```
R2-D2-Galactic_Kitchen/
├── backend/
\```

R2-D2-Galactic_Kitchen/
├── backend/
│   └── src/main/java/org/
│       ├── config/
│       │   └── AppConfig.java
│       ├── controllers/
│       │   ├── rest/
│       │   │   ├── ChatController.java
│       │   │   ├── NutritionController.java
│       │   │   ├── NutritionistController.java
│       │   │   ├── PlanController.java
│       │   │   ├── RecipeController.java
│       │   │   ├── RestIndexController.java
│       │   │   └── UserController.java
│       │   └── web/
│       │       └── HomeController.java
│       ├── converters/
│       │   ├── AbstractConverter.java
│       │   ├── IngredientToDTO.java
│       │   ├── RecipeToDTO.java
│       │   ├── UserToDTO.java
│       │   └── WeeklyPlanToDTO.java
│       ├── dtos/
│       │   ├── ChatMessageDTO.java
│       │   ├── IngredientDTO.java
│       │   ├── NutritionDTO.java
│       │   ├── RecipeDTO.java
│       │   ├── UserProfileDTO.java
│       │   └── WeeklyPlanDTO.java
│       ├── errors/
│       │   └── ErrorMessage.java
│       ├── exceptions/
│       │   ├── AIServiceException.java
│       │   ├── DuplicateRecipeException.java
│       │   ├── ExternalServiceException.java
│       │   ├── InvalidInputException.java
│       │   ├── NutritionNotFoundException.java
│       │   ├── PlanNotFoundException.java
│       │   ├── R2D2ChefBotException.java
│       │   ├── RecipeNotFoundException.java
│       │   ├── UnauthorizedException.java
│       │   └── UserNotFoundException.java
│       ├── factories/
│       │   ├── DataLoader.java
│       │   ├── RecipeFactory.java
│       │   └── UserFactory.java
│       ├── model/
│       │   ├── entity/
│       │   │   ├── Ingredient.java
│       │   │   ├── Recipe.java
│       │   │   ├── UserProfile.java
│       │   │   └── WeeklyPlan.java
│       │   ├── enums/
│       │   │   ├── DietType.java
│       │   │   ├── DifficultyLevel.java
│       │   │   └── MealType.java
│       │   └── valueObject/
│       │       └── NutritionProfile.java
│       ├── persistence.daos/
│       │   ├── IngredientRepository.java
│       │   ├── RecipeRepository.java
│       │   ├── UserProfileRepository.java
│       │   └── WeeklyPlanRepository.java
│       └── services/
│           ├── AIService.java
│           ├── AIServiceImpl.java
│           ├── NutritionistService.java
│           ├── NutritionistServiceImpl.java
│           ├── NutritionService.java
│           ├── NutritionServiceImpl.java
│           ├── PlanService.java
│           ├── PlanServiceImpl.java
│           ├── RecipeService.java
│           ├── RecipeServiceImpl.java
│           ├── UserService.java
│           └── UserServiceImpl.java
└── frontend/
├── css/
│   └── style.css
├── js/
│   ├── components/
│   │   ├── chat.js
│   │   ├── helpers.js
│   │   └── modals.js
│   ├── pages/
│   │   ├── home.js
│   │   ├── nutritionist.js
│   │   ├── plan.js
│   │   ├── profile.js
│   │   ├── profiles.js
│   │   └── recipes.js
│   ├── api.js
│   └── app.js
└── index.html


---

## API Endpoints

### Recipes
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/recipes` | Get all recipes |
| GET | `/api/recipes/{id}` | Get recipe by ID |
| GET | `/api/recipes/category/{category}` | Get recipes by category |
| GET | `/api/recipes/search?name=` | Search recipes by name |
| GET | `/api/recipes/difficulty/{level}` | Get recipes by difficulty |
| GET | `/api/recipes/mealtype/{mealType}` | Get recipes by meal type |
| POST | `/api/recipes` | Create a new recipe |
| PUT | `/api/recipes/{id}` | Update a recipe |
| DELETE | `/api/recipes/{id}` | Delete a recipe |

### Users
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/search` | Search users by name |
| POST | `/api/users/register` | Register a new user |
| PUT | `/api/users/{id}` | Update a user |
| DELETE | `/api/users/{id}` | Delete a user |
| GET | `/api/users/{id}/nutrition` | Get user nutrition profile |
| PUT | `/api/users/{id}/nutrition` | Update user nutrition profile |

### Weekly Plan
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/plan/{userId}` | Get all plans for a user |
| GET | `/api/plan/{userId}/week/{weekStart}` | Get plan by week |
| POST | `/api/plan` | Create a new plan |
| POST | `/api/plan/{planId}/recipes/{recipeId}` | Add recipe to plan |
| DELETE | `/api/plan/{planId}/recipes/{recipeId}` | Remove recipe from plan |
| DELETE | `/api/plan/{planId}` | Delete a plan |

### Nutritionist AI
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/nutritionist/consult/{userId}` | AI nutritionist consultation |
| POST | `/api/nutritionist/analyse` | Analyse food nutritional values |
| POST | `/api/nutritionist/meal-plan` | Generate AI meal plan |
| POST | `/api/nutritionist/evaluate/{recipeId}/{userId}` | Evaluate recipe for user |

---

## How to Run

### Requirements
- Java 17
- Maven
- Tomcat 10
- Groq API Key

### Steps

1. Clone the repository
```bash
   git clone https://github.com/anaptcardoso/R2-D2-Galactic_Kitchen.git
   cd R2-D2-Galactic_Kitchen
```

2. Configure the Groq API Key

Create a `config.properties` file in `backend/src/main/resources/`:
```properties
   groq.api.key=your_api_key_here
```

3. Build the project
```bash
   cd backend
   mvn clean install
```

4. Deploy to Tomcat
```bash
   cp target/R2-D2-Galactic_Kitchen.war /opt/tomcat/webapps/
```

5. Open the frontend
```
   Open frontend/index.html in your browser
```

---
## Team 

| Name          | Role |
|---------------|---|
| Ana Cardoso   | Frontend & Backend |
| Igor Saldanha | Frontend & Backend |
| Inês Azevedo  | Frontend & Backend |
| Yasmin Pires  | Frontend & Backend |

