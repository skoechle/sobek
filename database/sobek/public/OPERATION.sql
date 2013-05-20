-- Table: operation

CREATE TABLE operation
(
    nodeId numeric(18,0) NOT NULL,
    state character varying(64) NOT NULL,
    CONSTRAINT operation_pk PRIMARY KEY (nodeId)
)
WITH(OIDS=FALSE);

ALTER TABLE operation OWNER TO "sobek";

ALTER TABLE operation ADD CONSTRAINT state_fk FOREIGN KEY (state) REFERENCES operation_state (name) ON DELETE NO ACTION ON UPDATE NO ACTION;
