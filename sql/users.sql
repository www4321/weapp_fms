CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` varchar(100) NOT NULL DEFAULT '' COMMENT '用户ID信息',
  `nickName` varchar(100) NOT NULL DEFAULT '' COMMENT '用户昵称信息',
  `gmt_create` datetime(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3)  COMMENT '用户创建时间',
  `gmt_modified` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
	primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表'