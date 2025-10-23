package org.example.explorador_de_paises.Model;

public class Country {
    private String code;
    private String name;
    private String continent;
    private String region;
    private double surfaceArea;
    private Integer indepYear;
    private int population;
    private Double lifeExpectancy;
    private Double gnp;
    private String governmentForm;
    private String headOfState;
    private Integer capital;

    public Country(String code, String name, String continent, String region,
                   double surfaceArea, Integer indepYear, int population,
                   Double lifeExpectancy, Double gnp, String governmentForm,
                   String headOfState, Integer capital) {
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.surfaceArea = surfaceArea;
        this.indepYear = indepYear;
        this.population = population;
        this.lifeExpectancy = lifeExpectancy;
        this.gnp = gnp;
        this.governmentForm = governmentForm;
        this.headOfState = headOfState;
        this.capital = capital;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public String getContinent() { return continent; }
    public String getRegion() { return region; }
    public double getSurfaceArea() { return surfaceArea; }
    public Integer getIndepYear() { return indepYear; }
    public int getPopulation() { return population; }
    public Double getLifeExpectancy() { return lifeExpectancy; }
    public Double getGnp() { return gnp; }
    public String getGovernmentForm() { return governmentForm; }
    public String getHeadOfState() { return headOfState; }
    public Integer getCapital() { return capital; }
}