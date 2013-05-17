-- Table: nodeType

CREATE TABLE nodeType
(
    id integer PRIMARY KEY,
    name character varying(64) NOT NULL
)
WITH(OIDS=FALSE);

ALTER TABLE nodeType OWNER TO "sobek";
ALTER TABLE nodeType ADD CONSTRAINT name UNIQUE (name);

INSERT INTO nodeType VALUES (0, 'RAW_MATERIAL');
INSERT INTO nodeType VALUES (1, 'INTERMEDIATE_PRODUCT');
INSERT INTO nodeType VALUES (2, 'PRODUCT');
INSERT INTO nodeType VALUES (3, 'OPERATION');

