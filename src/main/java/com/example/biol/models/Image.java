package com.example.biol.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;
import javax.persistence.*;

@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String originalFileName;
    private Long size;
    private String contentType;
    private boolean isPreviewImage;
    @Lob
    private byte[] bytes;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Product product;

}
