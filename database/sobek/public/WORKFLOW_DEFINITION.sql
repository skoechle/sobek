-- Table: workflow_definition

-- DROP TABLE workflow_definition;

CREATE TABLE workflow_definition
(
  name character varying(256),
  pgraph bytea,
  CONSTRAINT workflow_definition_pk PRIMARY KEY (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE workflow_definition
  OWNER TO "sobek";
