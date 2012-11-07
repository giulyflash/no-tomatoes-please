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
    ('Small (20 cm)', 190),
    ('Medium (25 cm)', 210),
    ('Large (35 cm)', 260),
    ('Wagenrad (55 cm)', 290);




