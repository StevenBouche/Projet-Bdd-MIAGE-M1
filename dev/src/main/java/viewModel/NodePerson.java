package viewModel;

import java.util.ArrayList;
import java.util.List;

public class NodePerson {
    public String idPerson;
    public List<NodePerson> childrens = new ArrayList<>();
    public List<NodePerson> fathers = new ArrayList<>();
}
