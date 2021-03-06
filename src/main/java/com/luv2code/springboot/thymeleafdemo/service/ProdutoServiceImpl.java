package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.FIELD;
import com.luv2code.springboot.thymeleafdemo.dao.ProdutoRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Cliente;
import com.luv2code.springboot.thymeleafdemo.entity.Produto;
import com.luv2code.springboot.thymeleafdemo.paging.Page;
import com.luv2code.springboot.thymeleafdemo.paging.Paged;
import com.luv2code.springboot.thymeleafdemo.paging.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoServiceImpl implements Servicer<Produto>{

    private ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    @Override
    public Paged<Produto> findAll(int pageNumber, int size) {

        List<Produto> produtos = produtoRepository.findAll();
        List<Produto> paged = produtos.stream()
                                        .skip(pageNumber>1?pageNumber*size:0)
                                        .limit(size)
                                        .collect(Collectors.toList());
        int totalPages = produtos.size()/size;
        return new Paged<>(new Page<>(paged,totalPages), Paging.of(totalPages, pageNumber,size));
    }

    public Paged<Produto>  findAllByOrderByNomeAsc(){
        List<Produto> produtos =  produtoRepository.findAllByOrderByNomeAsc();
        List<Produto> paged = produtos.stream()
                                        .limit(8)
                                        .collect(Collectors.toList());
        int totalPages = produtos.size()/8;
        return new Paged<>(new Page<>(paged,totalPages), Paging.of(totalPages, 1, 8));
    }

    public Paged<Produto> findAllByOrderByPrecoAsc(){
        List<Produto> produtos = produtoRepository.findAllByOrderByPrecoAsc();
        List<Produto> paged = produtos.stream().limit(8).collect(Collectors.toList());
        int totalPages = produtos.size()/8;
        return new Paged<>(new Page<>(paged, totalPages),Paging.of(totalPages, 1, 8));
    }

    public Paged<Produto> findAllByOrderByPrecoDesc(){
        List<Produto> produtos = produtoRepository.findAllByOrderByPrecoDesc();
        List<Produto> paged = produtos.stream().limit(8).collect(Collectors.toList());
        int totalPages = produtos.size()/8;
        return new Paged<>(new Page<>(paged,totalPages), Paging.of(totalPages, 1, 8));
    }

    @Override
    public Produto findById(int Id) {
        Optional<Produto> result = produtoRepository.findById(Id);

        Produto produto = null;

        if(result.isPresent()){
            produto = result.get();
        }else{
            throw new RuntimeException("Produto n??o encontrado" + Id);
        }

        return produto;
    }

    @Override
    public Produto save(Produto produto) {
        produtoRepository.save(produto);
        return produto;
    }

    @Override
    public void deleteById(int id) {
        produtoRepository.deleteById(id);
    }

    @Override
    public Paged<Cliente> search(int pageNumber, int size, String keyword, FIELD field) {
        return null;
    }


}
