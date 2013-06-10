CREATE TABLE pgraph_definition
(
  name character varying(256),
  CONSTRAINT pgraph_definition_pk PRIMARY KEY (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE pgraph_definition
  OWNER TO "sobek";
