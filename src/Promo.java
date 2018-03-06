public class Promo {
    private String gift;
    private String conditionString;
    private String name;
    private Double minSumm;
    private String citiesString ;
    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {

        return code;
    }
    //public Promo (String name, String gift, String sitiesString, Double minSumm, String conditionString){

   // }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public void setConditionString(String conditionString) {
        this.conditionString = conditionString;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGift() {
        return gift;
    }

    public String getConditionString() {
        return conditionString;
    }

    public String getName() {
        return name;
    }

    public Double getMinSumm() {
        return minSumm;
    }

    public String getCitiesString() {
        return citiesString;
    }

    public void setMinSumm(Double minSumm) {
        this.minSumm = minSumm;
    }

    public void setCitiesString(String citiesString) {
        this.citiesString = citiesString;
    }
}

