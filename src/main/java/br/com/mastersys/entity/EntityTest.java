//package br.com.mastersys.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.Instant;
//
//@Entity
//@Table(name = "tb_entities")
//@Data
//public class EntityTest {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true, nullable = false)
//    private String entityName;
//
//    @JsonFormat(pattern = "dd/MM/yyyy")
//    private Instant timestamp = Instant.now();
//
//}