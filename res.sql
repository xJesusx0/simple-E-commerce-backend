CREATE TABLE `users` (
  `user_id` int PRIMARY KEY,
  `username` varchar(255),
  `password` varchar(255),
  `role` varchar(255)
);

CREATE TABLE `products` (
  `product_id` int PRIMARY KEY,
  `name` varchar(255),
  `price` decimal(10,2),
  `amount` int
);

CREATE TABLE `purchases` (
  `user_id` int,
  `product_id` int,
  `quantity` int,
  `purchase_date` datetime,
  FOREIGN KEY(user_id) REFERENCES users(user_id),
  FOREIGN KEY(product_id) REFERENCES products(product_id),
  PRIMARY KEY (`user_id`, `product_id`, `purchase_date`)
);
