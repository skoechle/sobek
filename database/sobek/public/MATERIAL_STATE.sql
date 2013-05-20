-- Table: materialState

CREATE TABLE material_state
(
    id integer NOT NULL,
    name character varying(64) NOT NULL,
    CONSTRAINT material_state_pk PRIMARY KEY (id),
    CONSTRAINT material_state_name_uk UNIQUE (name)
)
WITH(OIDS=FALSE);

ALTER TABLE material_state OWNER TO "sobek";

INSERT INTO material_state VALUES (0, 'NOT_AVAILABLE');
INSERT INTO material_state VALUES (1, 'AVAILABLE');