package com.jm.domain;

import net.didion.jwnl.data.POS;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * com.jm.domain.Word Class will represent words searched and stored from WordNet.
 *
 * Created by James Millner on 20/11/2016 at 23:01.
 */
@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String search;

    private String type;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Meaning> meanings = new ArrayList<Meaning>();

    public Word() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }
}
