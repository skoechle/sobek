-- Table: pgraph

CREATE TABLE pgraph
(
    id numeric(18,0) NOT NULL,
    CONSTRAINT pgraph_pk PRIMARY KEY (id)
)
WITH(OIDS=FALSE);
 
ALTER TABLE pgraph OWNER TO "sobek";
 













