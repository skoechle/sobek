 -- Table: operation_to_material_map
 
CREATE TABLE operation_to_material_map
(
    operation_id numeric(18,0) NOT NULL,
    material_id numeric(18,0) NOT NULL,
    CONSTRAINT operation_to_material_map_pk PRIMARY KEY (operation_id, material_id),
	CONSTRAINT operation_to_material_map_uk UNIQUE (operation_id)
)
WITH(OIDS=FALSE);
 
ALTER TABLE operation_to_material_map OWNER TO "sobek";

ALTER TABLE operation_to_material_map ADD CONSTRAINT o_to_m_material_id_fk FOREIGN KEY (material_id) REFERENCES node (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE operation_to_material_map ADD CONSTRAINT o_to_m_operation_id_fk FOREIGN KEY (operation_id) REFERENCES node (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
 