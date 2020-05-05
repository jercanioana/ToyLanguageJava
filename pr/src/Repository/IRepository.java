package Repository;

import Model.PrgState;
import Model.adt.MyIList;
import Model.adt.MyList;

import java.io.IOException;
import java.util.List;

public interface IRepository {

    void addPrgState(PrgState ps);
    void logPrgStateExec(PrgState myPrgState) throws IOException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> list);
    PrgState getProgramByID(Integer id);
}
