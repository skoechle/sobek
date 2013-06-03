-- Table: pgraph

CREATE TABLE pgraph
(
    id numeric(18,0) NOT NULL,
    raw_material_id numeric(18,0),
    CONSTRAINT pgraph_pk PRIMARY KEY (id)
)
WITH(OIDS=FALSE);
 
ALTER TABLE pgraph OWNER TO "sobek";
ALTER TABLE pgraph ADD CONSTRAINT pgraph_raw_material_id_fk FOREIGN KEY (raw_material_id) REFERENCES material (id);
 













