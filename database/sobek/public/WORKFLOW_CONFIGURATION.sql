-- Table: workflow

-- DROP TABLE workflow;

CREATE TABLE workflow_configuration
(
  name character varying(256),
  pgraph text,
  CONSTRAINT workflow_configuration_pk PRIMARY KEY (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE workflow
  OWNER TO "sobek";
