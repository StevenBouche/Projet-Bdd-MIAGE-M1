package service;

import dal.MarkLogicUtility;
import models.LinkPerson;
import viewModel.NodePerson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LinkPersonManager extends MarkLogicManager<LinkPerson, String> {

    public LinkPersonManager(MarkLogicUtility utility) {
        super(utility, LinkPerson.class, String.class);
    }

    public Map<String, NodePerson> getGraph() throws InterruptedException {

        Map<String, NodePerson> nodes = new HashMap<>();

        List<LinkPerson> links = this.readAll();

        List<String> ids = links.stream()
                .map(LinkPerson::getIdPersonPrimary)
                .distinct()
                .collect(Collectors.toList());

        ids.forEach(id -> {
            NodePerson n = new NodePerson();
            n.idPerson = id;
            nodes.put(id, n);
        });

        links.forEach(link -> {
            String idPrimary = link.getIdPersonPrimary();
            String idSecondary = link.getIdPersonSecondary();
            if(nodes.containsKey(idPrimary)&&
                    nodes.containsKey(idSecondary)){
                NodePerson n1 = nodes.get(idPrimary);
                NodePerson n2 = nodes.get(idSecondary);
                n1.childrens.add(n2);
                n2.fathers.add(n1);
            }
        });

        return nodes;

    }

    public void getFriends(int i, String currentPerson, int currentI, List<String> result, Map<String, NodePerson> nodes) {

        if(currentI>i) return;

        if(nodes.containsKey(currentPerson)){

            NodePerson n = nodes.get(currentPerson);

            for (NodePerson children : n.childrens){

                result.add(children.idPerson);

                this.getFriends(i, children.idPerson, currentI+1, result, nodes);

            }

        }

    }





}
