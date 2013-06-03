-- Sequence: material_value_id_generator

-- DROP SEQUENCE material_value_id_generator;

CREATE SEQUENCE material_value_id_generator
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 999999999999999999
  START 320
  CACHE 20;
ALTER SEQUENCE material_value_id_generator OWNER TO "sobek";
