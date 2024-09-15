package ru.job4j.pooh;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] contentArray = content.split(System.lineSeparator());
        String reqContent = contentArray[0].split(" ")[0];
        String modeContent = contentArray[0].split(" ")[1].split("/")[1];
        String nameContent = contentArray[0].split(" ")[1].split("/")[2];
        String paramContent = contentArray[contentArray.length - 1];
        if (reqContent.equals("GET")) {
            String[] reqParamArray = contentArray[0].split(" ")[1].split("/");
         paramContent = reqParamArray.length == 4 ? reqParamArray[3] : "";
        }
        return new Req(reqContent, modeContent, nameContent, paramContent);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }

}
