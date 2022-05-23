package foldersystem.app.model;

import javax.persistence.*;

@Entity
@Table(name = "Objects")
public class StorageObject {
    @Id
    @Column
    private int id;

    @Column
    private Integer parent;

    @Column(name = "objectname")
    private String objectName;

    @Column(name = "objecttype")
    private int objectType;

    @Column(name = "hashname")
    private String hashname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public String getHashname() {
        return hashname;
    }

    public void setHashname(String hashname) {
        this.hashname = hashname;
    }
}
