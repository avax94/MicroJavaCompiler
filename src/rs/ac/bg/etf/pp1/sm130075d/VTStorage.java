package rs.ac.bg.etf.pp1.sm130075d;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;

/**
 * Created by avax94 on 26.1.17..
 */
public class VTStorage {
    private static Map<String, VirtualTable> vTables = new HashMap<String, VirtualTable>();
    private static parser parser = null;

    public static VirtualTable fill(Obj class1) {
        VirtualTable vt = vTables.get(class1.getType().toString());

        class1.getType().getMembers().stream().forEach(x -> {
            if (x.getKind() == Obj.Meth) {
                vt.insertMethod(x);
            }
        });

        Obj sClass = class1.getType().getMembersTable().searchKey("$super");

        if (sClass != null) {
            String sClassString = sClass.getType().getElemType().toString();
            VirtualTable vtSclass = null;

            if (vTables.containsKey(sClassString)) {
                vtSclass = vTables.get(sClass.getType().getElemType().toString());
                vt.insertVirtualTable(vtSclass);
            } else {
                parser.report_error("Ne postoji virtuelna tabela nasledjene klase!", null);
            }
        }

        return vt;
    }

    public static VirtualTable insertVTClass(String s) {
        VirtualTable vt = new VirtualTable();
        vTables.putIfAbsent(s, vt);

        return vt;
    }

    public static VirtualTable get(Obj class1) {
        if(class1.getKind() == Obj.Type)
            return vTables.get(class1.getType().toString());
        else
            return vTables.get(class1.getType().getElemType().toString());
    }

    public static VirtualTable get(Struct type) {
        return vTables.get(type.toString());
    }


    public static void setParser(parser p) {
        parser = p;
    }
}
