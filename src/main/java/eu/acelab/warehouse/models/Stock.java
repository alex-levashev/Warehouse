package eu.acelab.warehouse.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity

public class Stock {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Integer id;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @NotEmpty(message = "Location should not be empty")
    private String location;
    @ManyToMany
    private List<MainItem> mainItemList;

    public List<MainItem> getMainItemList() {
        return mainItemList;
    }

    public void setMainItemList(List<MainItem> mainItemList) {
        this.mainItemList = mainItemList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
