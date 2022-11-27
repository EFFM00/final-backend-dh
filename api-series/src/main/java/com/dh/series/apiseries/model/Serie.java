package com.dh.series.apiseries.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "series")
@JsonPropertyOrder()
public class Serie{

    private Long id;
    private String name;
    private String genre;
}

