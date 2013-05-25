-- Table: workflow_configuration

-- DROP TABLE workflow_configuration;

CREATE TABLE workflow_configuration
(
  name character varying(256),
  pgraph bytea,
  CONSTRAINT workflow_configuration_pk PRIMARY KEY (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE workflow_configuration
  OWNER TO "sobek";
