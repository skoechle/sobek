 -- Table: node
 
CREATE TABLE node
 (
    id bigserial PRIMARY KEY,
    pgraph_id bigint NOT NULL,
    type character varying(64) NOT NULL,
    message_queue_name character varying NOT NULL
) 
WITH(OIDS=FALSE);

ALTER TABLE node OWNER TO "sobek";

ALTER TABLE node ADD CONSTRAINT pgraph_id_fk FOREIGN KEY (pgraph_id) REFERENCES pgraph (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE node ADD CONSTRAINT node_type_fk FOREIGN KEY (type) REFERENCES node_type (name) ON DELETE NO ACTION ON UPDATE NO ACTION;
