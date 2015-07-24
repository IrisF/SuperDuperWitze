package com.example.witz;

/**
 * Created by Iris on 24.07.2015.
 */
public class Joke {

    private int id;

    private String joke;

    private String categories;



    public Joke(){}



    public Joke(String joke, String categories) {

        super();

        this.joke = joke;

        this.categories = categories;

    }



    public int getId() {

        return id;

    }

    public void setId(int id) {

        this.id = id;

    }

    public String getJoke() {

        return joke;
       }

    public void setJoke(String joke) {

        this.joke = joke;

    }

    public String getCategories() {

        return categories;

    }

    public void setCategories(String categories) {

        this.categories = categories;

    }



    @Override

    public String toString() {

        return "Book [id=" + id + ", joke=" + joke + ", categories=" + categories

                + "]";

    }
}
