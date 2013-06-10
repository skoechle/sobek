CREATE TABLE pgraph_to_node_def_map
(
	pgraph_name character varying(256),
	node_name character varying(256),
	CONSTRAINT pgraph_to_node_def_map_pk PRIMARY KEY (pgraph_name, node_name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE pgraph_to_node_def_map OWNER To "sobek";
ALTER TABLE pgraph_to_node_def_map ADD CONSTRAINT pgraph_name_fk FOREIGN KEY (pgraph_name) REFERENCES pgraph_definition(name) ON DELETE CASCADE;
ALTER TABLE pgraph_to_node_def_map ADD CONSTRAINT node_name_fk FOREIGN KEY (node_name) REFERENCES node_definition(name) ON DELETE CASCADE;
