// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: apache/rocketmq/v1/definition.proto

package apache.rocketmq.v1;

public final class MQDomain {
  private MQDomain() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_FilterExpression_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_FilterExpression_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_DeadLetterPolicy_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_DeadLetterPolicy_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_Resource_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_Resource_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_ProducerGroup_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_ProducerGroup_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_ConsumerGroup_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_ConsumerGroup_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_SubscriptionEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_SubscriptionEntry_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_HeartbeatEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_HeartbeatEntry_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_Address_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_Address_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_Endpoints_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_Endpoints_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_Broker_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_Broker_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_Partition_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_Partition_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_Digest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_Digest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_SystemAttribute_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_SystemAttribute_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_Message_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_Message_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_Message_UserAttributeEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_Message_UserAttributeEntry_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_apache_rocketmq_v1_Assignment_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_apache_rocketmq_v1_Assignment_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n#apache/rocketmq/v1/definition.proto\022\022a" +
      "pache.rocketmq.v1\032\037google/protobuf/times" +
      "tamp.proto\032\036google/protobuf/duration.pro" +
      "to\"T\n\020FilterExpression\022,\n\004type\030\001 \001(\0162\036.a" +
      "pache.rocketmq.v1.FilterType\022\022\n\nexpressi" +
      "on\030\002 \001(\t\"1\n\020DeadLetterPolicy\022\035\n\025max_deli" +
      "very_attempts\030\001 \001(\005\"%\n\010Resource\022\013\n\003arn\030\001" +
      " \001(\t\022\014\n\004name\030\002 \001(\t\"<\n\rProducerGroup\022+\n\005g" +
      "roup\030\001 \001(\0132\034.apache.rocketmq.v1.Resource" +
      "\"\356\002\n\rConsumerGroup\022+\n\005group\030\001 \001(\0132\034.apac" +
      "he.rocketmq.v1.Resource\022<\n\rsubscriptions" +
      "\030\002 \003(\0132%.apache.rocketmq.v1.Subscription" +
      "Entry\0227\n\rconsume_model\030\003 \001(\0162 .apache.ro" +
      "cketmq.v1.ConsumeModel\0229\n\016consume_policy" +
      "\030\004 \001(\0162!.apache.rocketmq.v1.ConsumePolic" +
      "y\022@\n\022dead_letter_policy\030\005 \001(\0132$.apache.r" +
      "ocketmq.v1.DeadLetterPolicy\022<\n\014consume_t" +
      "ype\030\006 \001(\0162&.apache.rocketmq.v1.ConsumeMe" +
      "ssageType\"z\n\021SubscriptionEntry\022+\n\005topic\030" +
      "\001 \001(\0132\034.apache.rocketmq.v1.Resource\0228\n\ne" +
      "xpression\030\002 \001(\0132$.apache.rocketmq.v1.Fil" +
      "terExpression\"\304\001\n\016HeartbeatEntry\022\021\n\tclie" +
      "nt_id\030\001 \001(\t\022;\n\016producer_group\030\002 \001(\0132!.ap" +
      "ache.rocketmq.v1.ProducerGroupH\000\022;\n\016cons" +
      "umer_group\030\003 \001(\0132!.apache.rocketmq.v1.Co" +
      "nsumerGroupH\000\022\026\n\016need_rebalance\030\004 \001(\010B\r\n" +
      "\013client_data\"%\n\007Address\022\014\n\004host\030\001 \001(\t\022\014\n" +
      "\004port\030\002 \001(\005\"n\n\tEndpoints\0221\n\006scheme\030\001 \001(\016" +
      "2!.apache.rocketmq.v1.AddressScheme\022.\n\ta" +
      "ddresses\030\002 \003(\0132\033.apache.rocketmq.v1.Addr" +
      "ess\"T\n\006Broker\022\014\n\004name\030\001 \001(\t\022\n\n\002id\030\002 \001(\005\022" +
      "0\n\tendpoints\030\003 \001(\0132\035.apache.rocketmq.v1." +
      "Endpoints\"\244\001\n\tPartition\022+\n\005topic\030\001 \001(\0132\034" +
      ".apache.rocketmq.v1.Resource\022\n\n\002id\030\002 \001(\005" +
      "\0222\n\npermission\030\003 \001(\0162\036.apache.rocketmq.v" +
      "1.Permission\022*\n\006broker\030\004 \001(\0132\032.apache.ro" +
      "cketmq.v1.Broker\"H\n\006Digest\022,\n\004type\030\001 \001(\016" +
      "2\036.apache.rocketmq.v1.DigestType\022\020\n\010chec" +
      "ksum\030\002 \001(\t\"\300\006\n\017SystemAttribute\022\013\n\003tag\030\001 " +
      "\001(\t\022\014\n\004keys\030\002 \003(\t\022\022\n\nmessage_id\030\003 \001(\t\022/\n" +
      "\013body_digest\030\004 \001(\0132\032.apache.rocketmq.v1." +
      "Digest\0223\n\rbody_encoding\030\005 \001(\0162\034.apache.r" +
      "ocketmq.v1.Encoding\0225\n\014message_type\030\006 \001(" +
      "\0162\037.apache.rocketmq.v1.MessageType\0222\n\016bo" +
      "rn_timestamp\030\007 \001(\0132\032.google.protobuf.Tim" +
      "estamp\022\021\n\tborn_host\030\010 \001(\t\0223\n\017store_times" +
      "tamp\030\t \001(\0132\032.google.protobuf.Timestamp\022\022" +
      "\n\nstore_host\030\n \001(\t\0228\n\022delivery_timestamp" +
      "\030\013 \001(\0132\032.google.protobuf.TimestampH\000\022\025\n\013" +
      "delay_level\030\014 \001(\005H\000\022\026\n\016receipt_handle\030\r " +
      "\001(\t\022\024\n\014partition_id\030\016 \001(\005\022\030\n\020partition_o" +
      "ffset\030\017 \001(\003\0223\n\020invisible_period\030\020 \001(\0132\031." +
      "google.protobuf.Duration\022\030\n\020delivery_att" +
      "empt\030\021 \001(\005\022\035\n\025max_delivery_attempts\030\022 \001(" +
      "\005\0224\n\016producer_group\030\023 \001(\0132\034.apache.rocke" +
      "tmq.v1.Resource\022\025\n\rmessage_group\030\024 \001(\t\022\025" +
      "\n\rtrace_context\030\025 \001(\t\022\026\n\016transaction_id\030" +
      "\026 \001(\t\022<\n\031transaction_resolve_delay\030\027 \001(\013" +
      "2\031.google.protobuf.DurationB\020\n\016timed_del" +
      "ivery\"\201\002\n\007Message\022+\n\005topic\030\001 \001(\0132\034.apach" +
      "e.rocketmq.v1.Resource\022F\n\016user_attribute" +
      "\030\002 \003(\0132..apache.rocketmq.v1.Message.User" +
      "AttributeEntry\022=\n\020system_attribute\030\003 \001(\013" +
      "2#.apache.rocketmq.v1.SystemAttribute\022\014\n" +
      "\004body\030\004 \001(\014\0324\n\022UserAttributeEntry\022\013\n\003key" +
      "\030\001 \001(\t\022\r\n\005value\030\002 \001(\t:\0028\001\"t\n\nAssignment\022" +
      "0\n\tPartition\030\001 \001(\0132\035.apache.rocketmq.v1." +
      "Partition\0224\n\004mode\030\002 \001(\0162&.apache.rocketm" +
      "q.v1.ConsumeMessageType*;\n\nPermission\022\010\n" +
      "\004NONE\020\000\022\010\n\004READ\020\001\022\t\n\005WRITE\020\002\022\016\n\nREAD_WRI" +
      "TE\020\003*\036\n\nFilterType\022\007\n\003TAG\020\000\022\007\n\003SQL\020\001*0\n\014" +
      "ConsumeModel\022\016\n\nCLUSTERING\020\000\022\020\n\014BROADCAS" +
      "TING\020\001*L\n\rConsumePolicy\022\n\n\006RESUME\020\000\022\014\n\010P" +
      "LAYBACK\020\001\022\013\n\007DISCARD\020\002\022\024\n\020TARGET_TIMESTA" +
      "MP\020\003*4\n\rAddressScheme\022\010\n\004IPv4\020\000\022\010\n\004IPv6\020" +
      "\001\022\017\n\013DOMAIN_NAME\020\002*\270\001\n\010Language\022\010\n\004JAVA\020" +
      "\000\022\007\n\003CPP\020\001\022\013\n\007C_SHARP\020\002\022\n\n\006PYTHON\020\003\022\n\n\006D" +
      "ELPHI\020\004\022\n\n\006ERLANG\020\005\022\010\n\004RUBY\020\006\022\006\n\002GO\020\007\022\007\n" +
      "\003PHP\020\010\022\010\n\004RUST\020\t\022\005\n\001C\020\n\022\010\n\004PERL\020\013\022\017\n\013OBJ" +
      "ECTIVE_C\020\014\022\010\n\004DART\020\r\022\n\n\006KOTLIN\020\016\022\013\n\007NODE" +
      "_JS\020\017*?\n\013MessageType\022\n\n\006NORMAL\020\000\022\010\n\004FIFO" +
      "\020\001\022\t\n\005DELAY\020\002\022\017\n\013TRANSACTION\020\003**\n\nDigest" +
      "Type\022\t\n\005CRC32\020\000\022\007\n\003MD5\020\001\022\010\n\004SHA1\020\002*.\n\010En" +
      "coding\022\014\n\010IDENTITY\020\000\022\010\n\004GZIP\020\001\022\n\n\006SNAPPY" +
      "\020\002*\'\n\022ConsumeMessageType\022\010\n\004PULL\020\000\022\007\n\003PO" +
      "P\020\001*;\n\021QueryOffsetPolicy\022\r\n\tBEGINNING\020\000\022" +
      "\007\n\003END\020\001\022\016\n\nTIME_POINT\020\002B&\n\022apache.rocke" +
      "tmq.v1B\010MQDomainP\001\240\001\001\330\001\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.TimestampProto.getDescriptor(),
          com.google.protobuf.DurationProto.getDescriptor(),
        });
    internal_static_apache_rocketmq_v1_FilterExpression_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_apache_rocketmq_v1_FilterExpression_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_FilterExpression_descriptor,
        new java.lang.String[] { "Type", "Expression", });
    internal_static_apache_rocketmq_v1_DeadLetterPolicy_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_apache_rocketmq_v1_DeadLetterPolicy_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_DeadLetterPolicy_descriptor,
        new java.lang.String[] { "MaxDeliveryAttempts", });
    internal_static_apache_rocketmq_v1_Resource_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_apache_rocketmq_v1_Resource_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_Resource_descriptor,
        new java.lang.String[] { "Arn", "Name", });
    internal_static_apache_rocketmq_v1_ProducerGroup_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_apache_rocketmq_v1_ProducerGroup_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_ProducerGroup_descriptor,
        new java.lang.String[] { "Group", });
    internal_static_apache_rocketmq_v1_ConsumerGroup_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_apache_rocketmq_v1_ConsumerGroup_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_ConsumerGroup_descriptor,
        new java.lang.String[] { "Group", "Subscriptions", "ConsumeModel", "ConsumePolicy", "DeadLetterPolicy", "ConsumeType", });
    internal_static_apache_rocketmq_v1_SubscriptionEntry_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_apache_rocketmq_v1_SubscriptionEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_SubscriptionEntry_descriptor,
        new java.lang.String[] { "Topic", "Expression", });
    internal_static_apache_rocketmq_v1_HeartbeatEntry_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_apache_rocketmq_v1_HeartbeatEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_HeartbeatEntry_descriptor,
        new java.lang.String[] { "ClientId", "ProducerGroup", "ConsumerGroup", "NeedRebalance", "ClientData", });
    internal_static_apache_rocketmq_v1_Address_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_apache_rocketmq_v1_Address_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_Address_descriptor,
        new java.lang.String[] { "Host", "Port", });
    internal_static_apache_rocketmq_v1_Endpoints_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_apache_rocketmq_v1_Endpoints_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_Endpoints_descriptor,
        new java.lang.String[] { "Scheme", "Addresses", });
    internal_static_apache_rocketmq_v1_Broker_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_apache_rocketmq_v1_Broker_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_Broker_descriptor,
        new java.lang.String[] { "Name", "Id", "Endpoints", });
    internal_static_apache_rocketmq_v1_Partition_descriptor =
      getDescriptor().getMessageTypes().get(10);
    internal_static_apache_rocketmq_v1_Partition_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_Partition_descriptor,
        new java.lang.String[] { "Topic", "Id", "Permission", "Broker", });
    internal_static_apache_rocketmq_v1_Digest_descriptor =
      getDescriptor().getMessageTypes().get(11);
    internal_static_apache_rocketmq_v1_Digest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_Digest_descriptor,
        new java.lang.String[] { "Type", "Checksum", });
    internal_static_apache_rocketmq_v1_SystemAttribute_descriptor =
      getDescriptor().getMessageTypes().get(12);
    internal_static_apache_rocketmq_v1_SystemAttribute_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_SystemAttribute_descriptor,
        new java.lang.String[] { "Tag", "Keys", "MessageId", "BodyDigest", "BodyEncoding", "MessageType", "BornTimestamp", "BornHost", "StoreTimestamp", "StoreHost", "DeliveryTimestamp", "DelayLevel", "ReceiptHandle", "PartitionId", "PartitionOffset", "InvisiblePeriod", "DeliveryAttempt", "MaxDeliveryAttempts", "ProducerGroup", "MessageGroup", "TraceContext", "TransactionId", "TransactionResolveDelay", "TimedDelivery", });
    internal_static_apache_rocketmq_v1_Message_descriptor =
      getDescriptor().getMessageTypes().get(13);
    internal_static_apache_rocketmq_v1_Message_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_Message_descriptor,
        new java.lang.String[] { "Topic", "UserAttribute", "SystemAttribute", "Body", });
    internal_static_apache_rocketmq_v1_Message_UserAttributeEntry_descriptor =
      internal_static_apache_rocketmq_v1_Message_descriptor.getNestedTypes().get(0);
    internal_static_apache_rocketmq_v1_Message_UserAttributeEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_Message_UserAttributeEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
    internal_static_apache_rocketmq_v1_Assignment_descriptor =
      getDescriptor().getMessageTypes().get(14);
    internal_static_apache_rocketmq_v1_Assignment_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_apache_rocketmq_v1_Assignment_descriptor,
        new java.lang.String[] { "Partition", "Mode", });
    com.google.protobuf.TimestampProto.getDescriptor();
    com.google.protobuf.DurationProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
