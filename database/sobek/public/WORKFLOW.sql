-- Table: workflow

-- DROP TABLE workflow;

CREATE TABLE workflow
(
  id numeric(18,0) NOT NULL,
  name character varying(256),
  status character varying(64),
  parameters text,
  CONSTRAINT workflow_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE workflow
  OWNER TO "sobek";
