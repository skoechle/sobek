package com.sobek.pgraph;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType(namespace = "com.sobek.pgraph")
public enum OperationState{
	UNEVALUATED, 
    UNEXECUTED, 
    WORKING,
    SUSPENDED,
	CANCELED,
	COMPLETE,
	FAILED
}
