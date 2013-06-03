-- Sequence: node_id_generator

-- DROP SEQUENCE node_id_generator;

CREATE SEQUENCE node_id_generator
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 999999999999999999
  START 320
  CACHE 20;
ALTER SEQUENCE node_id_generator
  OWNER TO "sobek";
