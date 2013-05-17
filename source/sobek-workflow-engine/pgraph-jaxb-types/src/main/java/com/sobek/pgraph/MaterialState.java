package com.sobek.pgraph;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType(namespace = "com.sobek.pgraph")
public enum MaterialState{
    AVAILABLE,
    NOT_AVAILABLE
}
