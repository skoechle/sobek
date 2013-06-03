 -- Table: edge
 
CREATE TABLE edge
(
    pgraph_id numeric(18,0) NOT NULL,
    from_node_id numeric(18,0) NOT NULL,
    to_node_id numeric(18,0) NOT NULL,
    CONSTRAINT edge_pk PRIMARY KEY (pgraph_id, from_node_id, to_node_id)
)
WITH(OIDS=FALSE);
 
ALTER TABLE edge OWNER TO "sobek";

ALTER TABLE edge ADD CONSTRAINT graph_id_fk FOREIGN KEY (pgraph_id) REFERENCES pgraph (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE edge ADD CONSTRAINT from_node_id_fk FOREIGN KEY (from_node_id) REFERENCES node (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE edge ADD CONSTRAINT to_node_id_fk FOREIGN KEY (to_node_id) REFERENCES node (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
 