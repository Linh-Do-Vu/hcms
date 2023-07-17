package com.example.ezhcm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "core_setting")
public class
CoreSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settingid")
    private int settingId;

    @Column(name = "settingsysname")
    private String settingSysName;

    @Column(name = "settingname")
    private String settingName;

    @Column(name = "settingvalue")
    private String settingValue;
}
