CREATE TABLE `wares_info` (
	`id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`code` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '商品编号' COLLATE 'utf8_general_ci',
	`name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '商品名称' COLLATE 'utf8_general_ci',
	`manufacturer` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '生产厂商' COLLATE 'utf8_general_ci',
	`shop_code` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '店铺编号' COLLATE 'utf8_general_ci',
	`inventory` INT(10) NOT NULL DEFAULT '0' COMMENT '库存量',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='商品信息表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
