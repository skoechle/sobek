-- Table: materialState

CREATE TABLE material_state
(
    id integer PRIMARY KEY,
    name character varying(64) NOT NULL
)
WITH(OIDS=FALSE);

ALTER TABLE material_state OWNER TO "sobek";

INSERT INTO material_state VALUES (0, 'NOT_AVAILABLE');
INSERT INTO material_state VALUES (1, 'AVAILABLE');