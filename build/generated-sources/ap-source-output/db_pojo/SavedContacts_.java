package db_pojo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-24T21:35:55")
@StaticMetamodel(SavedContacts.class)
public class SavedContacts_ { 

    public static volatile SingularAttribute<SavedContacts, String> number;
    public static volatile SingularAttribute<SavedContacts, String> contactName;
    public static volatile SingularAttribute<SavedContacts, String> description;
    public static volatile SingularAttribute<SavedContacts, Integer> id;
    public static volatile SingularAttribute<SavedContacts, Date> dateAdded;
    public static volatile SingularAttribute<SavedContacts, Date> dateUpdated;
    public static volatile SingularAttribute<SavedContacts, Boolean> status;

}