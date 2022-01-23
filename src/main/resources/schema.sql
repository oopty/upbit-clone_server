CREATE TABLE IF NOT EXISTS `user` (
    `id` int PRIMARY KEY,
    `password` varchar(255),
    `created_at` timestamp
);

CREATE TABLE IF NOT EXISTS `currency` (
    `id` int PRIMARY KEY,
    `code` varchar(255)
);

CREATE TABLE IF NOT EXISTS `balance` (
   `id` int PRIMARY KEY,
   `currency` int,
   `user` int,
   `balance` double,
   `available_balance` double,
   `locked_balacne` double
);

CREATE TABLE IF NOT EXISTS `transaction` (
   `id` int PRIMARY KEY,
   `curreny` int,
   `volume` double,
   `created_at` datetime,
   `bid_user` int,
   `ask_user` int
);

CREATE TABLE IF NOT EXISTS `order` (
     `id` int PRIMARY KEY,
     `uuid` varchar(255),
     `currency` int,
     `user` int,
     `side` varchar(255),
     `type` varchar(255),
     `avg_price` double,
     `state` varchar(255),
     `created_at` datetime,
     `volume` double,
     `executed_volume` double,
     `remaining_volume` double,
     `trade_count` int
);

CREATE TABLE IF NOT EXISTS `withdraw` (
    `id` int PRIMARY KEY,
    `user` int,
    `created_at` datetime,
    `amount` double
);

CREATE TABLE IF NOT EXISTS `deposit` (
   `id` int PRIMARY KEY,
   `user` int,
   `created_at` datetime,
   `amount` double
);

ALTER TABLE `balance` ADD FOREIGN KEY (`user`) REFERENCES `user` (`id`);

ALTER TABLE `balance` ADD FOREIGN KEY (`currency`) REFERENCES `currency` (`id`);

ALTER TABLE `transaction` ADD FOREIGN KEY (`bid_user`) REFERENCES `user` (`id`);

ALTER TABLE `transaction` ADD FOREIGN KEY (`ask_user`) REFERENCES `user` (`id`);

ALTER TABLE `order` ADD FOREIGN KEY (`user`) REFERENCES `user` (`id`);

ALTER TABLE `order` ADD FOREIGN KEY (`currency`) REFERENCES `currency` (`id`);

ALTER TABLE `withdraw` ADD FOREIGN KEY (`user`) REFERENCES `user` (`id`);

ALTER TABLE `deposit` ADD FOREIGN KEY (`user`) REFERENCES `user` (`id`);
