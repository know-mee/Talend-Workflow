package routines;

/*
 * user specification: the function's comment should contain keys as follows: 1. write about the function's comment.but
 * it must be before the "{talendTypes}" key.
 * 
 * 2. {talendTypes} 's value must be talend Type, it is required . its value should be one of: String, char | Character,
 * long | Long, int | Integer, boolean | Boolean, byte | Byte, Date, double | Double, float | Float, Object, short |
 * Short
 * 
 * 3. {Category} define a category for the Function. it is required. its value is user-defined .
 * 
 * 4. {param} 's format is: {param} <type>[(<default value or closed list values>)] <name>[ : <comment>]
 * 
 * <type> 's value should be one of: string, int, list, double, object, boolean, long, char, date. <name>'s value is the
 * Function's parameter name. the {param} is optional. so if you the Function without the parameters. the {param} don't
 * added. you can have many parameters for the Function.
 * 
 * 5. {example} gives a example for the Function. it is optional.
 */
public class FileUtil {

	// split one line, honouring quotes and doubled quotes
    public static String[] splitLine(String line, char delim, char quote) {
        java.util.List<String> out = new java.util.ArrayList<String>();
        StringBuilder cur = new StringBuilder();
        boolean inQ = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQ) {
                if (c == quote) {
                    if (i + 1 < line.length() && line.charAt(i + 1) == quote) { cur.append(quote); i++; }
                    else inQ = false;
                } else cur.append(c);
            } else if (c == quote) inQ = true;
            else if (c == delim) { out.add(cur.toString()); cur.setLength(0); }
            else cur.append(c);
        }
        out.add(cur.toString());
        return out.toArray(new String[0]);
        
    }
    
    public static String[] sanitize(String[] raw) {
        java.util.Set<String> seen = new java.util.HashSet<String>();
        String[] res = new String[raw.length];
        for (int i = 0; i < raw.length; i++) {
            String n = (raw[i] == null ? "" : raw[i].trim()).replaceAll("[^A-Za-z0-9_]", "_").toLowerCase();
            if (n.length() == 0) n = "col" + (i + 1);
            if (Character.isDigit(n.charAt(0))) n = "c_" + n;
            if (n.length() > 55) n = n.substring(0, 55);
            String base = n; int k = 2;
            while (!seen.add(n)) n = base + "_" + k++;
            res[i] = n;
        }
        return res;
    }

    // quote an identifier
    public static String q(String id) {
        return "\"" + id.replace("\"", "\"\"") + "\"";
    }
}
