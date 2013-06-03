-- Table: workflow

-- DROP TABLE workflow;

CREATE TABLE workflow
(
  id numeric(18,0) NOT NULL,
  name character varying(256) NOT NULL,
  pgraph_id numeric(18,0) NOT NULL,
  status character varying(64) NOT NULL,
  raw_material bytea NOT NULL,
  CONSTRAINT workflow_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE workflow
  OWNER TO "sobek";
