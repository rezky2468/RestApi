package com.example.restapi.pokemon;

import java.util.List;

public class Pokedex {

    private List<Pokemon> pokemons;

    public Pokedex() {

    }

    public Pokedex(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

}
