    package com.example.ezhcm.model;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import javax.persistence.Column;
    import javax.persistence.Entity;
    import javax.persistence.Id;
    import javax.persistence.Table;

    @Entity
    @Data
    @Table(name = "role")
    @AllArgsConstructor
    @NoArgsConstructor
    public class Role {
        @Id
        @Column(name ="roleid")
        private Long id;

        @Column
        private String name;


    }
