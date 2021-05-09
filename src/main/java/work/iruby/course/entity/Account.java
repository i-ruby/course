package work.iruby.course.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import work.iruby.course.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "account")
public class Account extends BaseEntity {

    private String username;
    private String encryptedPassword;


}
