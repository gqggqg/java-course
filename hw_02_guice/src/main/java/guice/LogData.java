package guice;

public class LogData {

    private static int ordinalNumber = 1;

    private String strToLog;
    public void setStrToLog(String str) {
        strToLog = str;
    }

    private String tag;
    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStrToLog() {
        return String.format("%d) %s\n", ordinalNumber, strToLog);
    }

    public String getTaggedStrToLog() {
        return String.format("%d) <%s>%s</%s>\n", ordinalNumber, tag, strToLog, tag);
    }

    public void goodLog() {
        ordinalNumber++;
    }
}
