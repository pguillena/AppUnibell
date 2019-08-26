package pe.com.app.unibell.appunibell.Util;

public class RestResult {
    private String result;
    private int statusCode;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "RestResult{" +
                "result='" + result + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}

