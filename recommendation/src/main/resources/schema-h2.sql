DROP TABLE IF EXISTS `SKU`;
CREATE TABLE  `SKU` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `beta` double NOT NULL,
  `feature1Name` varchar(255) DEFAULT NULL,
  `feature1Value` double NOT NULL,
  `feature2Name` varchar(255) DEFAULT NULL,
  `feature2Value` double NOT NULL,
  `feature3Name` varchar(255) DEFAULT NULL,
  `feature3Value` double NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `USER`;
CREATE TABLE  `USER` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `feature1Name` varchar(255) DEFAULT NULL,
  `feature1Value` double NOT NULL,
  `feature2Name` varchar(255) DEFAULT NULL,
  `feature2Value` double NOT NULL,
  `feature3Name` varchar(255) DEFAULT NULL,
  `feature3Value` double NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `thetha0` double NOT NULL,
  `thetha1` double NOT NULL,
  `thetha2` double NOT NULL,
  PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `USERSKUMATRIX`;
CREATE TABLE  `USERSKUMATRIX` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rank` double NOT NULL,
  `skuId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`userId`) REFERENCES `USER` (`id`),
  FOREIGN KEY (`skuId`) REFERENCES `SKU` (`id`)
);