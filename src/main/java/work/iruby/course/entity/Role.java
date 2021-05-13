package work.iruby.course.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table
public class Role extends BaseEntity {

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Set<Permission> permissions;
}
