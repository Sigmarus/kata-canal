--
-- This is the mysql data structure of https://www.imdb.com/interfaces/ as of 07/02/2022 (little-endian date format).
--

DROP TABLE IF EXISTS principals_characters;
DROP TABLE IF EXISTS principals;
DROP TABLE IF EXISTS characters;
DROP TABLE IF EXISTS episodes;
DROP TABLE IF EXISTS title_crew;
DROP TABLE IF EXISTS known_for;
DROP TABLE IF EXISTS persons;
DROP TABLE IF EXISTS titles_genres;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS titles;
DROP TABLE IF EXISTS title_akas_types;
DROP TABLE IF EXISTS title_types;
DROP TABLE IF EXISTS title_akas;


CREATE TABLE title_akas (
    title_id        varchar(9)      NOT NULL, --a tconst, an alphanumeric unique identifier of the title
    ordering        integer         NOT NULL, --a number to uniquely identify rows for a given title_id
    title           varchar(255)    NOT NULL, --the localized title
    region          varchar(50)     NOT NULL, --the region for this version of the title
    language        varchar(50)     NOT NULL, --the language of the title
    attributes      varchar(255),             --Additional terms to describe this alternative title, not enumerated. Use comma-separated-values format
    is_original_title boolean       NOT NULL, --0: not original title; 1: original title
    PRIMARY KEY (title_id)
)

CREATE TABLE title_types (
    type_id         integer         NOT NULL AUTO_INCREMENT,
    type_name       varchar(20)     NOT NULL, --enumerated set of attributes for this alternative title. One or more of the following: "alternative", "dvd", "festival", "tv", "video", "working", "original", "imdbDisplay". New values may be added in the future without warning
    PRIMARY KEY (type_id)
)

CREATE TABLE title_akas_types (
    title_id        varchar(9)      NOT NULL,
    type_id         integer         NOT NULL,
    PRIMARY KEY (title_id, type_id),
    CONSTRAINT fk-title_akas_types-title_akas FOREIGN KEY (title_id) REFERENCES title_akas (title_id),
    CONSTRAINT fk-title_akas_types-title_types FOREIGN KEY (type_id) REFERENCES title_types (type_id)
)

CREATE TABLE titles (
    tconst          varchar(9)      NOT NULL, --alphanumeric unique identifier of the title
    title_type      varchar(50)     NOT NULL, --the type/format of the title (e.g. movie, short, tvseries, tvepisodes, video, etc)
    primary_title   varchar(255)    NOT NULL, --the more popular title / the title used by the filmmakers on promotional materials at the point of release
    original_title  varchar(255)    NOT NULL, --original title, in the original language
    is_adult        boolean         NOT NULL, --0: non-adult title; 1: adult title
    start_year      integer         NOT NULL, --(YYYY) represents the release year of a title. In the case of TV Series, it is the series start year
    end_year        integer,                  --(YYYY) TV Series end year. ‘\N’ for all other title types
    runtime_minutes integer         NOT NULL, --primary runtime of the title, in minutes
    average_ratings integer,                  --weighted average of all the individual user ratings
    num_votes       integer,                  --number of votes the title has received
    PRIMARY KEY (tconst)
)

CREATE TABLE genres (
    genre_id        integer         NOT NULL AUTO_INCREMENT,
    genre_name      varchar(50)     NOT NULL, --includes up to three genres associated with the title
    PRIMARY KEY (genre_id)
)

CREATE TABLE titles_genres (
    title_id        varchar(9)      NOT NULL,
    genre_id        integer         NOT NULL,
    PRIMARY KEY (title_id, genre_id),
    CONSTRAINT fk-titles_genres-title_types FOREIGN KEY (type_id) REFERENCES title_types (type_id),
    CONSTRAINT fk-titles_genres-genres FOREIGN KEY (genre_id) REFERENCES genres (genre_id)
)

CREATE TABLE persons (
    nconst              varchar(9)      NOT NULL, --alphanumeric unique identifier of the name/person
    primary_name        varchar(50)     NOT NULL, -- name by which the person is most often credited
    birth_year          integer         NOT NULL, --in YYYY format
    death_year          integer,            --in YYYY format if applicable, else NULL
    primary_profession  varchar(255),       --(array of strings) the top-3 professions of the person. Use comma-separated-values format
    PRIMARY KEY (nconst)
)

CREATE TABLE known_for (
    nconst          varchar(9)      NOT NULL,
    tconst          varchar(9)      NOT NUll, --titles the person is known for
    PRIMARY KEY (nconst, tconst),
    CONSTRAINT fk-known_for-persons FOREIGN KEY (nconst) REFERENCES persons (nconst),
    CONSTRAINT fk-known_for-titles FOREIGN KEY (tconst) REFERENCES titles (tconst)
)

CREATE TABLE title_crew (
    tconst          varchar(9)      NOT NULL, --alphanumeric unique identifier of the title
    nconst          varchar(9)      NOT NULL, --(string) alphanumeric unique identifier of the person
    type            varchar(20)     NOT NULL, --enumerated role of the person's regarding the title. One of the following: "director", "writer"
    PRIMARY KEY (tconst, nconst),
    CONSTRAINT fk-title_crew-titles FOREIGN KEY (tconst) REFERENCES titles (tconst),
    CONSTRAINT fk-title_crew-persons FOREIGN KEY (nconst) REFERENCES persons (nconst),
    CONSTRAINT (type) CHECK (type in ("director", "writer"))
)

CREATE TABLE episodes (
    tconst          varchar(20)     NOT NULL, --alphanumeric identifier of episode
    parent_tconst   varchar(20)     NOT NULL, --alphanumeric identifier of the parent TV Series
    season_number   integer,            --season number the episodes belongs to
    episode_number  integer,            --episode number of the title in the TV series
    PRIMARY KEY (tconst),
    CONSTRAINT fk-episodes-titles FOREIGN KEY (parent_tconst) REFERENCES titles (tconst)
)

CREATE TABLE principals (
    principal_id integer        NOT NUll AUTO_INCREMENT,
    tconst      varchar(9)      NOT NULL, --alphanumeric unique identifier of the title
    ordering    integer         NOT NULL, --a number to uniquely identify rows for a given title_id
    nconst      varchar(9)      NOT NULL, --alphanumeric unique identifier of the name/person
    category    varchar(50)     NOT NULL, --the category of job that person was in
    job         varchar(50),        --the specific job title if applicable, else NULL
    PRIMARY KEY (principal_id),
    CONSTRAINT fk-principals-titles FOREIGN KEY (tconst) REFERENCES titles (tconst),
    CONSTRAINT fk-principals-persons FOREIGN KEY (nconst) REFERENCES persons (nconst)
)

CREATE TABLE characters (
    character_id    int             NOT NUll AUTO_INCREMENT,
    character_name  varchar(50)     NOT NULL, --the names of the principal_characters played if applicable, else NULL
    PRIMARY KEY (character_id)
)

CREATE TABLE principals_characters (
    principal_id    varchar(9)  NOT NUll,
    character_id    integer     NOT NULL,
    PRIMARY KEY (principal_id, character_id),
    CONSTRAINT fk-principals_characters-principals FOREIGN KEY (principal_id) REFERENCES principals (principal_id),
    CONSTRAINT fk-principals_characters-characters FOREIGN KEY (character_id) REFERENCES characters (character_id)
)


INSERT INTO title_types
 VALUES ("alternative"), ("dvd"), ("festival"), ("tv"), ("video"), ("working"), ("original"), ("imdbDisplay");
