package com.sobek.pgraph;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType(namespace = "com.sobek.pgraph")
public enum NodeType{
    RAW_MATERIAL,
    INTERMEDIATE_PRODUCT,
    PRODUCT,
    OPERATION
}
