package db_pojo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-24T21:35:55")
@StaticMetamodel(InboundMessage.class)
public class InboundMessage_ { 

    public static volatile SingularAttribute<InboundMessage, Integer> memIndex;
    public static volatile SingularAttribute<InboundMessage, Date> dateRecived;
    public static volatile SingularAttribute<InboundMessage, Integer> mpMaxNo;
    public static volatile SingularAttribute<InboundMessage, Boolean> isRead;
    public static volatile SingularAttribute<InboundMessage, Integer> mpRefNo;
    public static volatile SingularAttribute<InboundMessage, Integer> mpSeqNo;
    public static volatile SingularAttribute<InboundMessage, Integer> id;
    public static volatile SingularAttribute<InboundMessage, String> originator;
    public static volatile SingularAttribute<InboundMessage, String> message;
    public static volatile SingularAttribute<InboundMessage, String> memLocation;
    public static volatile SingularAttribute<InboundMessage, Integer> mpMemIndex;

}