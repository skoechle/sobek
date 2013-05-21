-- Table: material

CREATE TABLE material
(
    id numeric(18,0) NOT NULL,
    state character varying(64) NOT NULL,
    value text,
    CONSTRAINT material_pk PRIMARY KEY (id)
)
WITH(OIDS=FALSE);

ALTER TABLE material OWNER TO "sobek";

ALTER TABLE material ADD CONSTRAINT state_fk FOREIGN KEY (state) REFERENCES material_state(name) ON DELETE NO ACTION ON UPDATE NO ACTION;