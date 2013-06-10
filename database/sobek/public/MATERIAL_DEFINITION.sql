CREATE TABLE material_definition
(
	name character varying(256),
	material_type character varying(1024),
	CONSTRAINT material_definition_pk PRIMARY KEY (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE material_definition OWNER To "sobek";
ALTER TABLE material_definition ADD CONSTRAINT material_def_name_fk FOREIGN KEY (name) REFERENCES node_definition(name) ON DELETE CASCADE;