package de.stw.phoenix.game.test;

import com.google.common.collect.Maps;
import de.stw.phoenix.game.engine.resources.api.Resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Map;

@Entity
@Table(name="tests")
public class TestEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="count")
    private int count = 0;

//    @ElementCollection
//    @CollectionTable(name="player_resources", joinColumns=@JoinColumn(name="player_id"))
//    @MapKeyJoinColumn(name="resource_id")
//    @Column(name="amount")
    @Transient
    private Map<Resource, Double> resources = Maps.newHashMap();

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void add(Resource resource, double amount) {
        resources.putIfAbsent(resource, 0d);
        double newAmount = resources.get(resource) + amount;
        resources.put(resource, newAmount);
    }

    public void incCount() {
        count++;
    }
}
