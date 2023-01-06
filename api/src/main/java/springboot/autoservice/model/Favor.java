package springboot.autoservice.model;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "favors")
public class Favor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Worker worker;
    @Column(name = "favor_name")
    private String favorName;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Favor() {
    }

    public enum Status {
        PAID,
        NOT_PAID
    }

    public Favor(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public Favor setStatus(Status status) {
        this.status = status;
        return this;
    }

    public String getFavorName() {
        return favorName;
    }

    public void setFavorName(String favorName) {
        this.favorName = favorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favor favor = (Favor) o;
        return Objects.equals(id, favor.id)
                && Objects.equals(worker, favor.worker)
                && Objects.equals(favorName, favor.favorName)
                && Objects.equals(price, favor.price)
                && status == favor.status;
    }
}
