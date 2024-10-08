package com.example.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Table(name = "attach")
@Entity
public class AttachEntity {
    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "orginal_name")
    private String originalName;

    private String path;

    private Long size;

    private String extension;

    @Column(name = "created_date")
    private String createdDate;

}
