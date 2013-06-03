 -- Table: material_to_operation_map
 
CREATE TABLE material_to_operation_map
(
    material_id numeric(18,0) NOT NULL,
    operation_id numeric(18,0) NOT NULL,
    CONSTRAINT material_to_operation_map_pk PRIMARY KEY (material_id, operation_id)
)
WITH(OIDS=FALSE);
 
ALTER TABLE material_to_operation_map OWNER TO "sobek";

ALTER TABLE material_to_operation_map ADD CONSTRAINT m_to_o_material_id_fk FOREIGN KEY (material_id) REFERENCES node (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE material_to_operation_map ADD CONSTRAINT m_to_o_operation_id_fk FOREIGN KEY (operation_id) REFERENCES node (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
 