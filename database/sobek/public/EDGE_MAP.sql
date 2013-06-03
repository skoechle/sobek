 -- Table: edge_map
 
CREATE TABLE edge_map
(
    from_node_id numeric(18,0) NOT NULL,
    to_node_id numeric(18,0) NOT NULL,
    CONSTRAINT edge_map_pk PRIMARY KEY (from_node_id, to_node_id)
)
WITH(OIDS=FALSE);
 
ALTER TABLE edge_map OWNER TO "sobek";

ALTER TABLE edge_map ADD CONSTRAINT edge_map_from_node_id_fk FOREIGN KEY (from_node_id) REFERENCES node (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE edge_map ADD CONSTRAINT edge_map_to_node_id_fk FOREIGN KEY (to_node_id) REFERENCES node (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
 