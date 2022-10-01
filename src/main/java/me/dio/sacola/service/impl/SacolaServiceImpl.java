package me.dio.sacola.service.impl;

import lombok.RequiredArgsConstructor;
import me.dio.sacola.enumeration.FormaPagamento;
import me.dio.sacola.model.Item;
import me.dio.sacola.model.Restaurante;
import me.dio.sacola.model.Sacola;
import me.dio.sacola.repository.ItemRepository;
import me.dio.sacola.repository.ProdutoRepository;
import me.dio.sacola.repository.SacolaRepository;
import me.dio.sacola.resource.dto.ItemDto;
import me.dio.sacola.service.SacolaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {
  private final SacolaRepository sacolaRepository;
  private final ProdutoRepository produtoRepository;
  private final ItemRepository itemRepository;

  @Override
  public Item incluirItemSacola(ItemDto itemDto) {
    Sacola sacola = verSacola(itemDto.getSacolaId());

    if(sacola.isFechada()){
      throw new RuntimeException("Esta sacola está fechada!");
    }

    Item itemParaSerInserido = Item.builder()
      .quantidade(itemDto.getQuantidade())
      .sacola(sacola)
      .produto(produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(
        () -> {
          throw new RuntimeException("Esse produto não existe!");
        }
      ))
      .build();

    List<Item>itensSacola = sacola.getItens();
    if(itensSacola.isEmpty()){
      itensSacola.add(itemParaSerInserido);
    }else {
      Restaurante restauranteAtual = itensSacola.get(0).getProduto().getRestaurante();
      Restaurante restauranteItemParaAdicionar = itemParaSerInserido.getProduto().getRestaurante();
      if(restauranteAtual.equals(restauranteItemParaAdicionar)){
        itensSacola.add(itemParaSerInserido);
      }else {
        throw new RuntimeException("Não é possível adicionar produtos de restaurantes diferentes. Feche a sacola ou esvazie!");
      }
    }

    List<Double> valorDosItens = new ArrayList<>();
    for (Item itemSacola: itensSacola) {
      double valorTotalItem =
        itemSacola.getProduto().getValorUnitario() * itemSacola.getQuantidade();
      valorDosItens.add(valorTotalItem);
    }

    double valorTotalSacola = valorDosItens.stream()
      .mapToDouble(valoTotalDeCadaItem -> valoTotalDeCadaItem)
      .sum();

    sacola.setValorTotal(valorTotalSacola);
    sacolaRepository.save(sacola);
    return itemParaSerInserido;
  }
  @Override
  public Sacola verSacola(Long id) {
    return sacolaRepository.findById(id).orElseThrow(
      () -> {
        throw new RuntimeException("Essa sacola não existe!");
      }
    );
  }

  @Override
  public Sacola fecharSacola(Long id, int numeroformaPagamento) {
    Sacola sacola = verSacola(id);

    if (sacola.getItens().isEmpty()) {
      throw new RuntimeException("Inclua itens na sacola!");
    }
    FormaPagamento formaPagamento =
      numeroformaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;

    sacola.setFormaPagamento(formaPagamento);
    sacola.setFechada(true);
    return sacolaRepository.save(sacola);
  }
}
