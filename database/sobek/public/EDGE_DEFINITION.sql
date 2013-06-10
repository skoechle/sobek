CREATE TABLE edge_definition
(
	name character varying(516), -- 256 + |->| + 256
	from_node_name character varying(256),
	to_node_name character varying(256),
	pgraph_name character varying(256),
	CONSTRAINT edge_definition_pk PRIMARY KEY (name, from_node_name, to_node_name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE edge_definition OWNER To "sobek";
ALTER TABLE edge_definition ADD CONSTRAINT edge_pgraph_name_fk FOREIGN KEY (pgraph_name) REFERENCES pgraph_definition(name) ON DELETE CASCADE;
ALTER TABLE edge_definition ADD CONSTRAINT edge_from_node_name_fk FOREIGN KEY (from_node_name) REFERENCES node_definition(name) ON DELETE CASCADE;
ALTER TABLE edge_definition ADD CONSTRAINT edge_to_node_name_fk FOREIGN KEY (to_node_name) REFERENCES node_definition(name) ON DELETE CASCADE;
