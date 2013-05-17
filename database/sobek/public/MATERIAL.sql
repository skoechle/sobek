-- Table: material

CREATE TABLE material
(
    id bigint PRIMARY KEY,
    state character varying(64) NOT NULL
)
WITH(OIDS=FALSE);

ALTER TABLE material OWNER TO "sobek";

ALTER TABLE material ADD CONSTRAINT state_fk FOREIGN KEY (state) REFERENCES material_state(name) ON DELETE NO ACTION ON UPDATE NO ACTION;