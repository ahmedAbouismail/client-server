package htw.berlin.clientserver.clientserver.dto;

import java.util.ArrayList;
import java.util.List;

public class DataDtoList {

    private List<DataDto> dataDtoList;

    public DataDtoList() {
        dataDtoList = new ArrayList<>();
    }

    public DataDtoList(List<DataDto> dataDtoList) {
        this.dataDtoList = dataDtoList;
    }

    public List<DataDto> getDataDtoList() {
        return dataDtoList;
    }

    public void setDataDtoList(List<DataDto> dataDtoList) {
        this.dataDtoList = dataDtoList;
    }
}
