 -- Table: node
 
CREATE TABLE node
 (
    id numeric(18,0) NOT NULL,
    name character varying(256),
    pgraph_id numeric(18,0) NOT NULL,
    type character varying(64) NOT NULL,
    CONSTRAINT node_pk PRIMARY KEY (id)
) 
WITH(OIDS=FALSE);

ALTER TABLE node OWNER TO "sobek";

ALTER TABLE node ADD CONSTRAINT pgraph_id_fk FOREIGN KEY (pgraph_id) REFERENCES pgraph (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE node ADD CONSTRAINT node_type_fk FOREIGN KEY (type) REFERENCES node_type (name) ON DELETE NO ACTION ON UPDATE NO ACTION;
