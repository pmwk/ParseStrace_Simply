package src.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class NodeCommandTree {

    private String value; //значение текущего Node. Самый первые Node - "no value"
    private ArrayList<NodeCommandTree> childs = new ArrayList<>(); //список дочерних элементов
    private Set<String> results = new HashSet<>(); //Список результатов для текущего уровня Node

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

    /*public void setChilds(ArrayList<NodeCommandTree> childs) {
        this.childs = childs;
    }*/

    public void addBranch(ArrayList<String> nodes, String result) {
        if (nodes.size() == 0) {
            addResult(result);
            return;
        } //если оставшихся аргументов нуль, то обработка заканчивается +проверка, что если мы в самом начале (то есть случай, когда аргументов в принципе не было), то добавляем соответсвующую Pfgbcm
        String first = nodes.get(0);
        nodes.remove(0);
        boolean isExist = false;
        for (NodeCommandTree child : childs) {
            if (child.getValue().equals(first)) {
                isExist = true;
                child.addBranch(nodes, result);
                break;
            }
        }
        if (!isExist) {
            NodeCommandTree node_new = new NodeCommandTree(first);
            addChild(node_new);
            node_new.addBranch(nodes, result);
        }
    }

    public void addChild(NodeCommandTree child) {
        childs.add(child);
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
            allExistResults.addAll(nodeCommandTree.getAllExistResults());
        }


        return allExistResults;
    }

    public Set<String> getAllExistResultsFrom (ArrayList<String> nodes) {
        NodeCommandTree nodeCommandTree = findNode(nodes);
        if (nodeCommandTree == null) {
            return null;
        } else {
            return nodeCommandTree.getAllExistResults();
        }
    }

    private NodeCommandTree findNode(ArrayList<String> nodes) {
        if (nodes.size() == 0) {
            return this;
        } else {
            String first = nodes.get(0);
            nodes.remove(0);
            if (first == null) {
                return this;
            }
            for (NodeCommandTree child : childs) {
                if (child.value.equals(first)) {
                    if (nodes.size() > 0) {
                        if (nodes.get(0) == null) {
                            return child;
                        }
                    }
                    return child.findNode(nodes);
                }
            }
        }
        return null;
    }

    /*private NodeCommandTree findNodeWithNullArgs(ArrayList<String> nodes, NodeCommandTree src) {
        if (nodes.size() == 0) {
            return this;
        } else {
            String first = nodes.get(0);
            nodes.remove(0);
            if (first == null) {

            } else {
                for (NodeCommandTree child : childs) {
                    if (child.value.equals(first)) {
                        return child.findNodeWithNullArgs(nodes, this);
                    }
                }
            }
        }
        return null;
    }//возвращет МОДИФИЦИРОВАННЫЙ NODE*/

    public Set<String> getResults() {
        return results;
    }

    public void print(String begin) {
        System.out.println(begin + value);
        for (NodeCommandTree child : childs) {
            child.print(begin + "\t");
        }
    }

    public void print () {
        print("");
    }

    public ArrayList<String> getChildsFor (ArrayList<String> nodes) {
        NodeCommandTree nodeCommandTree = findNode(nodes);
        if (nodeCommandTree == null) {
            return null;
        } else {
            return nodeCommandTree.getChildsValues();
        }
        /*if (nodes.size() == 0) {
            return getChildsValues();
        } else {
            String first = nodes.get(0);
            nodes.remove(0);
            for (NodeCommandTree nodeCommandTree : childs) {
                if (nodeCommandTree.getValue().equals(first)) {
                    return nodeCommandTree.getChildsFor(nodes);
                }
            }
            return null;
        }*/
    }


    public ArrayList<String> getChildsValues() {
        ArrayList<String> values = new ArrayList<>();
        for (NodeCommandTree nodeCommandTree : childs) {
            values.add(nodeCommandTree.getValue());
        }
        if (value == "no value") {
            values.add("no value");
        }
        return values;
    }

    public NodeCommandTree copy() {
        NodeCommandTree copy = new NodeCommandTree(value);
        Collections.copy(copy.childs, childs);
        copy.results = new HashSet<>(results);

        return copy;
    }
}
