package de.innogy.emobility.springtraining.beershop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="BEER_ITEM")
public class BeerItem {

    @NotNull
    @Column(name="BEER_NAME")
    @Id
    private String name;

    @NotNull
    @Column(name="BOTTLE_SIZE_ML")
    private Integer bottleSizeInMl;

    @NotNull
    @Column(name="ALCOHOL_VOL")
    private Double alcoholVol;

    @NotNull
    @JsonIgnore
    @Column(name="STOCK")
    private Integer stock;

}
