-- Table: pgraph

CREATE TABLE pgraph
(
    id bigserial PRIMARY KEY
)
WITH(OIDS=FALSE);
 
ALTER TABLE pgraph OWNER TO "sobek";
 
 -- Table: edge
 
CREATE TABLE edge
(
    pgraphId bigint PRIMARY KEY,
    fromNodeId bigint PRIMARY KEY,
    toNodeId bigint PRIMARY KEY
)
WITH(OIDS=FALSE);
 
ALTER TABLE edge OWNER TO "sobek";
ALTER TABLE edge ADD CONSTRAINT graphIdFk FOREIGN KEY (pgraphId) REFERENCES pgraph (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE edge ADD CONSTRAINT fromNodeIdFk FOREIGN KEY (fromNodeId) REFERENCES node (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE edge ADD CONSTRAINT toNodeIdFk FOREIGN KEY (toNodeId) REFERENCES node (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
 
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

-- Table: nodeType

CREATE TABLE nodeType
(
    id integer PRIMARY KEY,
    name character varying(64) NOT NULL
)
WITH(OIDS=FALSE);

ALTER TABLE nodeType OWNER TO "sobek";
ALTER TABLE nodeType ADD CONSTRAINT name UNIQUE (name);

INSERT INTO nodeType VALUES (0, 'RAW_MATERIAL');
INSERT INTO nodeType VALUES (1, 'INTERMEDIATE_PRODUCT');
INSERT INTO nodeType VALUES (2, 'PRODUCT');
INSERT INTO nodeType VALUES (3, 'OPERATION');

