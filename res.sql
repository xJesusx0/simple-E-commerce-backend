CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL
);

CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(255) NOT NULL UNIQUE,
  `price` decimal(10,2) NOT NULL,
  `amount` int NOT NULL
);

CREATE TABLE `purchases` (
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `purchase_date` datetime NOT NULL,
  FOREIGN KEY(user_id) REFERENCES users(user_id),
  FOREIGN KEY(product_id) REFERENCES products(product_id),
  PRIMARY KEY (`user_id`, `product_id`, `purchase_date`)
);
