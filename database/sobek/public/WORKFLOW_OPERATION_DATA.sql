-- Table: workflow_operation_data

-- DROP TABLE workflow_operation_data;

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
ALTER TABLE workflow_operation_data
  OWNER TO "sobek";
