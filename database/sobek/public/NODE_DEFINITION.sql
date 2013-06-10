CREATE TABLE node_definition
(
	name character varying(256),
	node_type character varying(256),
	CONSTRAINT node_definition_pk PRIMARY KEY (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE node_definition OWNER To "sobek";
