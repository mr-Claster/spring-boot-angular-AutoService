package springboot.autoservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Car car;
    @Column(name = "problem_description")
    private String problemDescription;
    @Column(name = "acceptance_date")
    private LocalDateTime acceptanceDate;
    @OneToMany
    private List<Favor> favors;
    @ManyToMany
    @JoinTable(
            name = "orders_goods",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "goods_id"))
    private List<Goods> goods;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "final_price")
    private BigDecimal finalPrice;
    @Column(name = "end_date")
    private LocalDateTime endDate;

    public Order() {
        favors = new ArrayList<>();
        goods = new ArrayList<>();
    }

    public enum Status {
        IN_PROCESS,
        ACCEPTED,
        COMPLETED,
        FAILURE,
        PAID
    }

    public Order(Status status) {
        favors = new ArrayList<>();
        goods = new ArrayList<>();
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public LocalDateTime getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(LocalDateTime acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<Favor> getFavors() {
        return favors;
    }

    public void setFavors(List<Favor> favors) {
        this.favors = favors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id)
                && Objects.equals(car, order.car)
                && Objects.equals(problemDescription, order.problemDescription)
                && Objects.equals(acceptanceDate, order.acceptanceDate)
                && Objects.equals(favors, order.favors)
                && Objects.equals(goods, order.goods)
                && status == order.status
                && Objects.equals(finalPrice, order.finalPrice)
                && Objects.equals(endDate, order.endDate);
    }
}

