-- Table: material

CREATE TABLE material
(
    id numeric(18,0) NOT NULL,
    material_state character varying(64) NOT NULL,
    CONSTRAINT material_pk PRIMARY KEY (id)
)
WITH(OIDS=FALSE);

ALTER TABLE material OWNER TO "sobek";

ALTER TABLE material ADD CONSTRAINT state_fk FOREIGN KEY (material_state) REFERENCES material_state(name) ON DELETE NO ACTION ON UPDATE NO ACTION;
