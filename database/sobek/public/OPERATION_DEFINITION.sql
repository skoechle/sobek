CREATE TABLE operation_definition
(
	name character varying(256),
	queue_name character varying(1024),
	CONSTRAINT operation_definition_pk PRIMARY KEY (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE operation_definition OWNER To "sobek";
ALTER TABLE operation_definition ADD CONSTRAINT operation_def_name_fk FOREIGN KEY (name) REFERENCES node_definition(name) ON DELETE CASCADE;