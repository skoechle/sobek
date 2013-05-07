-- Table: workflow

-- DROP TABLE workflow;

CREATE TABLE workflow_operation_data
(
  workflow_id numeric(18,0) NOT NULL,
  operation_name character varying(256),
  state character varying(256),
  CONSTRAINT workflow_operation_data_pk PRIMARY KEY (workflow_id, operation_name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE workflow
  OWNER TO "sobek";
