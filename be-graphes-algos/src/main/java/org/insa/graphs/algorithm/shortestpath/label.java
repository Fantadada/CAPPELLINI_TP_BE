package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Node;


public class label implements Comparable<label> {
    private float self_cost;
    private boolean marked , exist;
    private Node parent , sommet;

    public label(Node sommet) {
        this.sommet = sommet;
        this.parent = null;
        this.exist = false;
        this.marked = false;

    }

    public float getSelf_cost() {return self_cost;}
    public void setSelf_cost(float self_cost) {this.self_cost = self_cost;}

    public boolean isMarked() {return marked;}
    public void setMarked(boolean marked) {this.marked = marked;}

    public boolean isExist() {return exist;}
    public void setExist(boolean exist) {this.exist = exist;}
    public Node getParent() {return parent;}
    public void setParent(Node parent) {this.parent = parent;}
    public Node getSommet() {return sommet;}
    public void setSommet(Node sommet) {this.sommet = sommet;}
    @Override
    public int compareTo(label o) {

        return 0;
    }
}