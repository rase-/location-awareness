package acs.fluffy.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
/**le
 * Each user has from 0 to n roles which are used to control user priviledges. The role consists of a name and the users that have the role
 * @author tonykovanen
 */
@Entity(name = "ROLES")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String rolename;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @Override
    public boolean equals(Object o) {
        if (o instanceof Role) {
            Role role = (Role) o;
            if (role.getRolename().equals(this.rolename)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.rolename != null ? this.rolename.hashCode() : 0);
        return hash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
