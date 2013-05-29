-- Table: operation

CREATE TABLE operation
(
    id numeric(18,0) NOT NULL,
    state character varying(64) NOT NULL,
    percent_complete numeric(18,0) NOT NULL,
    message_queue_name character varying(1024) NOT NULL,
    CONSTRAINT operation_pk PRIMARY KEY (id)
)
WITH(OIDS=FALSE);

ALTER TABLE operation OWNER TO "sobek";

ALTER TABLE operation ADD CONSTRAINT state_fk FOREIGN KEY (state) REFERENCES operation_state (name) ON DELETE NO ACTION ON UPDATE NO ACTION;
