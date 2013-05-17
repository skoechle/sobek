-- Table: node_type

CREATE TABLE node_type
(
    id integer PRIMARY KEY,
    name character varying(64) NOT NULL
)
WITH(OIDS=FALSE);

ALTER TABLE node_type OWNER TO "sobek";

ALTER TABLE node_type ADD CONSTRAINT name UNIQUE (name);

INSERT INTO node_type VALUES (0, 'RAW_MATERIAL');
INSERT INTO node_type VALUES (1, 'INTERMEDIATE_PRODUCT');
INSERT INTO node_type VALUES (2, 'PRODUCT');
INSERT INTO node_type VALUES (3, 'OPERATION');

