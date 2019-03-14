CREATE TABLE `tb_bill` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` varchar(100) NOT NULL DEFAULT '' COMMENT '用户ID信息',
  `consume_type` varchar(20) NOT NULL DEFAULT '0' COMMENT '消费类型',
  `money` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '消费金额',
  `consume_info` varchar(1024) NOT NULL DEFAULT '' COMMENT '消费信息',
  `gmt_create` datetime(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3)  COMMENT '创建时间',
  `gmt_modified` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  `year` int(10) NOT NULL DEFAULT '0' COMMENT '年份',
  `month` int(10) NOT NULL DEFAULT '0' COMMENT '月份',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:支出，1：收入',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账单表'

