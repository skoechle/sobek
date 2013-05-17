 -- Table: edge
 
CREATE TABLE edge
(
    pgraph_id bigint PRIMARY KEY,
    from_node_id bigint PRIMARY KEY,
    to_node_id bigint PRIMARY KEY
)
WITH(OIDS=FALSE);
 
ALTER TABLE edge OWNER TO "sobek";

ALTER TABLE edge ADD CONSTRAINT graph_id_fk FOREIGN KEY (pgraph_id) REFERENCES pgraph (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE edge ADD CONSTRAINT from_node_id_fk FOREIGN KEY (from_node_id) REFERENCES node (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE edge ADD CONSTRAINT to_node_idFk FOREIGN KEY (to_node_id) REFERENCES node (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
 