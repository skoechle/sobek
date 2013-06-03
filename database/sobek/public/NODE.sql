 -- Table: node
 
CREATE TABLE node
 (
    id numeric(18,0) NOT NULL,
    name character varying(256),
    node_type character varying(64) NOT NULL,
    CONSTRAINT node_pk PRIMARY KEY (id)
) 
WITH(OIDS=FALSE);

ALTER TABLE node OWNER TO "sobek";

ALTER TABLE node ADD CONSTRAINT node_type_fk FOREIGN KEY (node_type) REFERENCES node_type (name) ON DELETE NO ACTION ON UPDATE NO ACTION;
