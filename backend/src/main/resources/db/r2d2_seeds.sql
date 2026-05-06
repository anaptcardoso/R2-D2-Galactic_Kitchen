-- ── Clear existing data (safe re-run) ────────────────────────────────────────
DELETE FROM recipe_diet_types;
DELETE FROM weekly_plan_recipes;
DELETE FROM weekly_plan;
DELETE FROM ingredient;
DELETE FROM recipe;
DELETE FROM user_profile;

-- ── Recipes ───────────────────────────────────────────────────────────────────

INSERT INTO recipe (id, name, description, category, preparation_time, servings,
                    calories, protein, carbs, fat, difficulty_level, meal_type, tip)
VALUES
    (1, 'Bantha Stew',
     'A hearty stew from the desert planet Tatooine, made with Bantha meat and desert spices.',
     'OMNIVORE', 60, 4, 450, 35.0, 20.0, 15.0, 'MEDIUM', 'DINNER',
     'Best served under a double sunset!'),

    (2, 'Coruscant Noodles',
     'A quick and flavourful noodle dish from the city-planet Coruscant, inspired by its multicultural cuisine.',
     'VEGAN', 20, 2, 320, 12.0, 55.0, 8.0, 'EASY', 'LUNCH',
     'Add extra spice if you''re feeling like a Sith Lord!'),

    (3, 'Dagobah Swamp Soup',
     'A mysterious green soup full of herbs and vegetables, just like Master Yoda would make in his swamp hut.',
     'VEGAN', 30, 3, 180, 8.0, 25.0, 5.0, 'EASY', 'LUNCH',
     'Patience you must have when cooking this soup!'),

    (4, 'Ewok Forest Roast',
     'A festive roast inspired by the Ewok celebrations on the forest moon of Endor.',
     'OMNIVORE', 120, 6, 580, 45.0, 15.0, 25.0, 'HARD', 'DINNER',
     'Celebrate like the Rebellion just won the war!'),

    (5, 'Mandalorian Brisket',
     'A slow-cooked brisket with bold spices, fit for a Mandalorian warrior on a long journey.',
     'OMNIVORE', 180, 5, 620, 50.0, 10.0, 30.0, 'HARD', 'DINNER',
     'This is the way... to cook a perfect brisket!');

-- ── Recipe diet types ─────────────────────────────────────────────────────────

INSERT INTO recipe_diet_types (recipe_id, diet_types) VALUES
  (1, 'OMNIVORE'),
  (2, 'VEGAN'),
  (3, 'VEGAN'),
  (3, 'GLUTEN_FREE'),
  (4, 'OMNIVORE'),
  (4, 'GLUTEN_FREE'),
  (5, 'OMNIVORE'),
  (5, 'KETO');

-- ── Users ─────────────────────────────────────────────────────────────────────

INSERT INTO user_profile (id, first_name, last_name, email, phone, date_of_birth, country, bio,
                          weight, height, goal, activity_level, allergies)
VALUES
    (1, 'Luke', 'Skywalker', 'luke@rebellion.com', '123456789', '0019-05-25',
     'Tatooine', 'I am a Jedi, like my father before me.',
     75.0, 172.0, 'Become a Jedi Master', 'very active', null),

    (2, 'Leia', 'Organa', 'leia@rebellion.com', '987654321', '0019-05-25',
     'Alderaan', 'Help me, Obi-Wan Kenobi. You''re my only hope.',
     58.0, 150.0, 'Maintain fitness for the Rebellion', 'active', 'gluten'),

    (3, 'Han', 'Solo', 'han@millenniumfalcon.com', '555123456', '0029-07-13',
     'Corellia', 'Never tell me the odds!',
     85.0, 180.0, 'Survive another day', 'moderate', null);

-- ── User diet preferences ─────────────────────────────────────────────────────

INSERT INTO user_profile_diet_preferences (user_profile_id, diet_preferences) VALUES
  (1, 'OMNIVORE'),
  (2, 'VEGETARIAN'),
  (3, 'OMNIVORE');

-- ── Reset sequences (PostgreSQL) ──────────────────────────────────────────────

SELECT setval('recipe_id_seq', (SELECT MAX(id) FROM recipe));
SELECT setval('user_profile_id_seq', (SELECT MAX(id) FROM user_profile));

