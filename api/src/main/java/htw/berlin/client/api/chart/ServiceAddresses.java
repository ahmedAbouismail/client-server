package htw.berlin.client.api.chart;

import java.util.List;

public class ServiceAddresses {

    private final String cmc;
    private final String cha;
    private final List<String> dat;

    public ServiceAddresses() {
        cmc = null;
        cha = null;
        dat = null;
    }

    public ServiceAddresses(String cmc, String cha, List<String> dat) {
        this.cmc = cmc;
        this.cha = cha;
        this.dat = dat;
    }

    public String getCmc() {
        return cmc;
    }

    public String getCha() {
        return cha;
    }

    public List<String> getDat() {
        return dat;
    }
}
