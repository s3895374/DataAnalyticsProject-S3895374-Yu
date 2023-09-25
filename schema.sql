

CREATE TABLE posts (
                       id INTEGER PRIMARY KEY autoincrement,
                       content TEXT,
                       author TEXT,
                       likes INTEGER,
                       shares INTEGER,
                       createBy TEXT,
                       datetime DATETIME
);

CREATE TABLE users (
                       username TEXT PRIMARY KEY,
                       password TEXT,
                       first_name TEXT,
                       last_name TEXT,
                       is_vip BOOLEAN
);