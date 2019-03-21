package src.Tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NodeCommandTree {

    private String value;
    private ArrayList<NodeCommandTree> childs = new ArrayList<>();
    private ArrayList<String> results = new ArrayList<>();

    public NodeCommandTree(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<NodeCommandTree> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<NodeCommandTree> childs) {
        this.childs = childs;
    }

    public void addChild(NodeCommandTree child) {
        childs.add(child);
    }

    public void print(String begin) {
        System.out.println(begin + value);
        for (NodeCommandTree child : childs) {
            child.print(begin + "\t");
        }
    }

    public void addResults(ArrayList<String> new_results) {
        results.addAll(new_results);
    }

    public void addResult(String result) {
        results.add(result);
    }

    public Set<String> getAllExistResults () {
        Set<String> allExistResults = new HashSet<>();

        allExistResults.addAll(results);
        for (NodeCommandTree nodeCommandTree : childs) {
            allExistResults.addAll(nodeCommandTree.getResults());
        }

        return allExistResults;
    }

    public ArrayList<String> getResults() {
        return results;
    }
}
