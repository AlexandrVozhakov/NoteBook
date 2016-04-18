
CREATE TABLE IF NOT EXISTS books (
    "id" INTEGER NOT NULL PRIMARY KEY,
    "name" TEXT NOT NULL UNIQUE,
    "create_date" TEXT,
    "mod_date" TEXT
);

CREATE TABLE IF NOT EXISTS notes (
    "id" INTEGER NOT NULL PRIMARY KEY,
    "book_id" INTEGER,
    "in_up" INTEGER,
    "font_size" INTEGER,
    "font_style" TEXT,
    "create_date" TEXT,
    "mod_date" TEXT,
    "text" TEXT NOT NULL,
    FOREIGN KEY(book_id) REFERENCES books(id)
);
