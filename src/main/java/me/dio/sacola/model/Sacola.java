package me.dio.sacola.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dio.sacola.enumeration.FormaPagamento;
import me.dio.sacola.enumeration.StatusSacola;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class Sacola {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne(cascade={CascadeType.ALL}, optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name="cliente_id", nullable = false)
  private Cliente cliente;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "restaurante_id", nullable = false)
  private Restaurante restaurante;
  @OneToMany(
      mappedBy = "sacola",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<Item> itensSacola;
  private double valorTotalSacola;

  @Enumerated(EnumType.STRING)
  private FormaPagamento formaPagamento;

  @Enumerated(EnumType.STRING)
  private StatusSacola statusSacola;
}
