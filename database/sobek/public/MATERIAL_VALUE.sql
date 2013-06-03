-- Table: material_value

CREATE TABLE material_value
(
    id numeric(18,0) NOT NULL,
    material_id numeric(18,0) NOT NULL,
	material_value bytea,
    CONSTRAINT material_value_pk PRIMARY KEY (id)
)
WITH(OIDS=FALSE);

ALTER TABLE material_value OWNER TO "sobek";

ALTER TABLE material_value ADD CONSTRAINT material_value_material_id_fk FOREIGN KEY (material_id) REFERENCES material(id) ON DELETE CASCADE;
