-- Table: node_type

CREATE TABLE node_type
(
    id integer NOT NULL,
    name character varying(64) NOT NULL,
    CONSTRAINT node_type_pk PRIMARY KEY (id),
    CONSTRAINT node_type_name_uk UNIQUE (name)
)
WITH(OIDS=FALSE);

ALTER TABLE node_type OWNER TO "sobek";

INSERT INTO node_type VALUES (0, 'RAW_MATERIAL');
INSERT INTO node_type VALUES (1, 'INTERMEDIATE_PRODUCT');
INSERT INTO node_type VALUES (2, 'PRODUCT');
INSERT INTO node_type VALUES (3, 'OPERATION');

