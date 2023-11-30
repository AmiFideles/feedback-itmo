package ru.niu.itmo.feedback.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author amifideles
 */
@Getter @Setter
@NoArgsConstructor
@Entity
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String color;
    private String light;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color1 = (Color) o;
        return Objects.equals(id, color1.id) && Objects.equals(color, color1.color) && Objects.equals(light, color1.light);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, color, light);
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", light='" + light + '\'' +
                '}';
    }
}
