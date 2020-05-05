package Controller;

import Model.PrgState;
import Model.Value.RefValue;
import Model.Value.Value;
import Repository.IRepository;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private ExecutorService executor;
    private IRepository MyRepo;
    private Map<Integer, Value> conservativeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap)
    {
        Map<Integer, Value> mp = heap.entrySet().stream() // only those reffered in symtable
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<Integer, Value> mpres = new HashMap<Integer, Value>();
        for(Map.Entry<Integer, Value> entry : mp.entrySet()) // prepare res, copy of filtered from symTbl variables
        {
            mpres.put(entry.getKey(), entry.getValue());
        }

        Map<Integer, Value> mpt = new HashMap<Integer, Value>();
        for(Map.Entry<Integer, Value> entry : heap.entrySet()) // copy entire initial heap
        {
            mpt.put(entry.getKey(), entry.getValue());
        }

        for(Map.Entry<Integer, Value> entry : mp.entrySet()) // iterate through symTbl refferenced variables to see if we have other inner refferenced variables
        {
            Integer k = entry.getKey();
            Value v = entry.getValue(); // current value

            while(v instanceof RefValue)
            {
                RefValue rv = (RefValue)v; // downcasted value
                int adr = rv.getAddress(); // get address
                Value v2 = mpt.get(adr); // new value which is reffered by the address
                mpres.put(adr, v2); // add the key which is refered in the heap
                v = v2; // keep looking for other refferences
            }
        }
        return mpres;

    }
    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){ return symTableValues.stream()
            .filter(v-> v instanceof RefValue)
            .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();}) .collect(Collectors.toList());
    }
    public Controller(IRepository repo){
        MyRepo = repo;
    }
    public void addPrg(PrgState ps){
        MyRepo.addPrgState(ps);
    }


    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public IRepository getMyRepo(){
        return MyRepo;
    }
    private void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();})) .collect(Collectors.toList());
        List<PrgState> newPrgList = executor.invokeAll(callList). stream()
                .map(future -> { try { return future.get();} catch(InterruptedException | ExecutionException e)
                    { e.getMessage();
                }
                    return null;
                }).filter(p -> p!=null)
                .collect(Collectors.toList());
        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                MyRepo.logPrgStateExec(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        MyRepo.setPrgList(prgList);
    }
    public void allStep() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList=removeCompletedPrg(MyRepo.getPrgList());
        while(prgList.size() > 0){
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(MyRepo.getPrgList());
        }
        executor.shutdownNow();
        MyRepo.setPrgList(prgList);
    }

    public List<PrgState> allStepGUI2() throws Exception {
        List<PrgState>programsList = removeCompletedPrg(MyRepo.getPrgList());
        if (programsList.size()>0){
            oneStepForAllPrg(programsList);
        }
        else{
            executor.shutdownNow();
            throw new Exception("Program is done\n");
        }
        MyRepo.setPrgList(programsList);
        return this.MyRepo.getPrgList();
    }
    public void allStepGUI(){
        executor = Executors.newFixedThreadPool(2);
    }

}
