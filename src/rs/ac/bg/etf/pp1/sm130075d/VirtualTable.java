package rs.ac.bg.etf.pp1.sm130075d;

import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by avax94 on 26.1.17..
 */
public class VirtualTable {
    private Map<String, Obj> methods = new HashMap<String, Obj>();
    private boolean result;
    private static parser parser = null;
    private int tSize = 1;
    private int idx = 0;
    private int address;
    private boolean checkStatic(Obj o) {
        if (o != null && o.getKind() == Obj.Meth) {
            return o.getLocalSymbols().stream().filter((Obj xx) -> xx.getName() == "this").count() == 0;
        }

        return false;
    }


    private boolean compareArguments(Obj meth1, Obj meth2) {
        if (meth1.getLevel() != meth2.getLevel()) { //if different num of arguments
            return false;
        }

        result = true;
        //check each argument type
        meth2.getLocalSymbols().stream().filter(x -> x.getFpPos() != 0).forEach(y -> {
            meth1.getLocalSymbols().stream().filter(z -> z.getFpPos() == y.getFpPos()).forEach(z -> { //expect only one element here
                if (z.getType().getKind() == Struct.Class && y.getType().getKind() == Struct.Class)
                    result = result && z.getType().getElemType().equals(y.getType().getElemType());
                else
                    result = result && z.getType().equals(y.getType());
            });
        });

        return result;
    }

    private boolean checkValid(Obj meth1, Obj meth2) {
        return meth1.getType().equals(meth2.getType()) &&
                compareArguments(meth1, meth2) &&
                !(checkStatic(meth1) ^ checkStatic(meth2));
    }

    public static void setParser(parser p) {
        parser = p;
    }

    public void insertMethod(Obj method) {
        String name = method.getName();
        int addr = method.getAdr();

        if (methods.containsKey(name)) {
            Obj meth2 = methods.get(name);

            if (!checkValid(method, meth2)) {
                parser.report_error("Razliciti potpisi metoda!", null);
            }

            return;
        }

        tSize += method.getName().length() + 2;
        methods.put(method.getName(), method);
    }

    private int[] serialize() {
        int[] s = new int[tSize];
        idx = 0;
        methods.forEach((x, y) -> {
            for(int j = 0; j < x.length(); j++, idx++) {
                s[idx] = x.charAt(j);
            }

            s[idx++] = -1;
            s[idx++] = y.getAdr();
        });

        s[idx++] = -2;
        return s;
    }

    public int writeToData(int addr) {
        int currAddr = addr;

        for (int x : serialize()) {
            Code.loadConst(x);
            Code.store(new Obj(Obj.Var, "", Tab.intType, currAddr, 0));
            currAddr++;
        }

        //returns size of allocated object
        return tSize;
    }

    public int getSize() { return tSize; }

    public int getAddr() {
        return address;
    }

    public void setAddr(int addr) {
        address = addr;
    }

    public void insertVirtualTable(VirtualTable vt) {
        vt.methods.forEach((x,y) -> insertMethod(y));
    }
}
