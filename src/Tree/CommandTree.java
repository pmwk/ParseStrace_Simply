package src.Tree;

import java.util.ArrayList;

public class CommandTree {

    /*private NodeCommandTree nodeCommandTree;
    private ArrayList<NodeCommandTree> childs;

    public CommandTree(String root_value) {
        nodeCommandTree = new NodeCommandTree(root_value);
        childs = nodeCommandTree.getChilds();
    }

    public void addBranch(ArrayList<String> nodes, String result) {
        if (nodes.size() == 0) {
            boolean isExist = false;
            for (NodeCommandTree nodeCommandTree : childs) {
                if (nodeCommandTree.getValue().equals("no value")) {
                    isExist = true;
                    nodeCommandTree.addResult(result);
                    break;
                }
            }
            if (!isExist) {
                NodeCommandTree noValue = new NodeCommandTree("no value");
                noValue.addResult(result);
                childs.add(noValue);
            }

        } else {
            addBranch(nodes, nodeCommandTree, result);
        }
    }

    public void addBranch(ArrayList<String> nodes, NodeCommandTree node, String result) {
        if (nodes.size() == 0) {
            node.addResult(result);
            return;
        } //если оставшихся аргументов нуль, то обработка заканчивается +проверка, что если мы в самом начале (то есть случай, когда аргументов в принципе не было), то добавляем соответсвующую Pfgbcm
        String first = nodes.get(0);
        nodes.remove(0);
        boolean isExist = false;
        for (NodeCommandTree nodeCommandTree : childs) {
            if (nodeCommandTree.getValue().equals(first)) {
                isExist = true;
                addBranch(nodes, nodeCommandTree, result);
                break;
            }
        }
        if (!isExist) {
            NodeCommandTree node_new = new NodeCommandTree(first);
            node.addChild(node_new);
            addBranch(nodes, node_new, result);
        }
    }

    public ArrayList<String> getChildsFor(ArrayList<String> node) {
        ArrayList<String> list = new ArrayList<>();
        if (node == null || node.size() == 0) {
            NodeCommandTree noValue = getNoValue();
            if (noValue != null) {
                list = noValue.getResults();
            }
        } else {
            while (node.size() != 0) {

                node.remove(0);
            }
        }

        return list;
    }

    public NodeCommandTree getNoValue() {
        return null;
    }

    /*public void addBranch(ArrayList<String> nodes, NodeCommandTree node) {
        if (nodes.size() == 0) {
            return;
        } //если оставшихся аргументов нуль, то обработка заканчивается +проверка, что если мы в самом начале (то есть случай, когда аргументов в принципе не было), то добавляем соответсвующую Pfgbcm
        String first = nodes.get(0);
        nodes.remove(0);
        boolean isExist = false;
        for (NodeCommandTree nodeCommandTree : childs) {
            if (nodeCommandTree.getValue().equals(first)) {
                isExist = true;
                addBranch(nodes, nodeCommandTree);
                break;
            }
        }
        if (!isExist) {
            NodeCommandTree node_new = new NodeCommandTree(first);
            node.addChild(node_new);
            addBranch(nodes, node_new);
        }
    }*/

    /*public void print() {
        for (NodeCommandTree child : childs) {
            child.print("");
        }
    }*/

    //получить список с одного уровня
    //получить список детей от одного узла
    // взаимодействие с результатом


}
