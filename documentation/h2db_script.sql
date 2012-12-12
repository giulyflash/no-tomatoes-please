DROP TABLE IF EXISTS size;
DROP TABLE IF EXISTS dough;
DROP TABLE IF EXISTS sauce;
DROP TABLE IF EXISTS topping;

CREATE TABLE size (
    "name"  VARCHAR(50) NOT NULL PRIMARY KEY,
    "price" INT NOT NULL
);

CREATE TABLE dough (
    "name"    VARCHAR(50) NOT NULL PRIMARY KEY,
    "price"   INT NOT NULL,
    "visible" BOOLEAN NOT NULL
);

CREATE TABLE sauce (
    "name"    VARCHAR(50) NOT NULL PRIMARY KEY,
    "price"   INT NOT NULL,
    "visible" BOOLEAN NOT NULL
);

CREATE TABLE topping (
    "name"    VARCHAR(50) NOT NULL PRIMARY KEY,
    "price"   INT NOT NULL,
    "visible" BOOLEAN NOT NULL
);

INSERT INTO size ("name", "price") VALUES
    ('Junior (15 cm)', 170),
    ('Klein (20 cm)', 190),
    ('Medium (25 cm)', 210),
    ('Groß (35 cm)', 260),
    ('Wagenrad (55 cm)', 290);

INSERT INTO dough ("name", "price", "visible") VALUES
    ('Weizen', 450, true),
    ('Roggen', 510, true),
    ('Käserand', 640, true),
    ('Hefe', 420, true),
    ('Bierteig', 570, true);

INSERT INTO sauce ("name", "price", "visible") VALUES
    ('Tomatensauce', 90, true),
    ('Sauce Hollandaise', 110, true),
    ('Barbecue Sauce', 130, true),
    ('Curry Sauce', 120, true),
    ('Chili Sauce', 120, true);

INSERT INTO topping ("name", "price", "visible") VALUES
    ('Emmentaler', 50, true),
    ('Oliven schwarz', 75, true),
    ('Schinken', 60, true),
    ('Salami', 50, true),
    ('Artischocken', 70, true),
    ('Ananas', 80, true),
    ('Bacon', 60, true),
    ('Broccoli', 60, true),
    ('Champignons', 45, true),
    ('Ei', 55, true),
    ('Gorgonzola', 95, true),
    ('Gyros', 85, true),
    ('Hähnchenfleisch', 110, true),
    ('Hackfleisch', 75, true),
    ('Mais', 40, true),
    ('Mozzarella', 65, true),
    ('Paprika', 80, true),
    ('Parmesan', 90, true),
    ('Rucola', 50, true),
    ('Sardellen', 95, true),
    ('Schafskäse', 70, true),
    ('Spinat', 65, true),
    ('Thunfisch', 80, true),
    ('Tomaten', 50, true),
    ('Zwiebeln', 45, true);

