-- Table: operation_state

CREATE TABLE operation_state
(
    id integer PRIMARY KEY,
    name character varying(64) NOT NULL
)
WITH(OIDS=FALSE);

ALTER TABLE operation_state OWNER TO "sobek";

INSERT INTO operation_state VALUES (0, 'UNEVALUATED');
INSERT INTO operation_state VALUES (1, 'UNEXECUTED');
INSERT INTO operation_state VALUES (2, 'WORKING');
INSERT INTO operation_state VALUES (3, 'COMPLETE');
INSERT INTO operation_state VALUES (4, 'FAILED');
INSERT INTO operation_state VALUES (5, 'CANCELED');