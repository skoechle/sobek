 -- Table: node
 
CREATE TABLE node
 (
    id bigserial PRIMARY KEY,
    pgraphId bigint NOT NULL,
    type character varying(64) NOT NULL,
    jndiName character varying NOT NULL
) 
WITH(OIDS=FALSE);

ALTER TABLE node OWNER TO "sobek";
ALTER TABLE node ADD CONSTRAINT pgraphIdFk FOREIGN KEY (pgraphID) REFERENCES pgraph (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE node ADD CONSTRAINT nodeTypeFk FOREIGN KEY (type) REFERENCES nodeType (name) ON DELETE NO ACTION ON UPDATE NO ACTION;
