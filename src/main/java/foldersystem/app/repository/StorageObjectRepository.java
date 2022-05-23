package foldersystem.app.repository;

import foldersystem.app.data.StorageObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageObjectRepository extends JpaRepository<StorageObject, Integer> {

    @Query(value = "SELECT * FROM filedb.Objects WHERE objectname = \"root\" AND parent IS NULL", nativeQuery = true)
    StorageObject getRoot();

    @Query(value = "SELECT * FROM Objects WHERE parent = ?1", nativeQuery = true)
    List<StorageObject> getChildren(int folderId);

    @Modifying
    @Query(value = "CALL AddObject(:i_parent, :i_objectName, :i_objectType, :i_hashname);", nativeQuery = true)
    void addObject(@Param("i_parent") int parent,
                   @Param("i_objectName") String objectName,
                   @Param("i_objectType") int objectType,
                   @Param("i_hashname") String hashName);

    @Modifying
    @Query(value = "CALL MoveObject(:objectid, :parentid);", nativeQuery = true)
    void moveObject(@Param("objectid") int objectId,
                    @Param("parentid") int parentId);
}
