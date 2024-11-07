use product;

-- 1. owners 테이블
INSERT INTO `owners` (`id`, `created_at`, `updated_at`, `code`, `email`, `name`, `status`)
VALUES (1, NOW(), NOW(), 'owner_code_1', 'owner1@example.com', 'John Doe', 'ACTIVE'),
       (2, NOW(), NOW(), 'owner_code_2', 'owner2@example.com', 'Jane Smith', 'ACTIVE'),
       (3, NOW(), NOW(), 'owner_code_3', 'owner3@example.com', 'Mike Johnson', 'ACTIVE'),
       (4, NOW(), NOW(), 'owner_code_4', 'owner4@example.com', 'Emily Davis', 'ACTIVE'),
       (5, NOW(), NOW(), 'owner_code_5', 'owner5@example.com', 'Sarah Wilson', 'ACTIVE'),
       (6, NOW(), NOW(), 'owner_code_6', 'owner6@example.com', 'David Martinez', 'ACTIVE'),
       (7, NOW(), NOW(), 'owner_code_7', 'owner7@example.com', 'Chris Lee', 'ACTIVE'),
       (8, NOW(), NOW(), 'owner_code_8', 'owner8@example.com', 'Patricia Brown', 'ACTIVE'),
       (9, NOW(), NOW(), 'owner_code_9', 'owner9@example.com', 'Daniel Miller', 'ACTIVE'),
       (10, NOW(), NOW(), 'owner_code_10', 'owner10@example.com', 'Jessica Taylor', 'ACTIVE');

-- 2. restaurants 테이블
INSERT INTO `restaurants` (`id`, `latitude`, `longitude`, `open`, `owner_id`, `created_at`, `updated_at`, `address`,
                           `image`, `industry`, `name`, `phone_number`, `registration_number`, `status`)
VALUES (1, 37.7749, -122.4194, b'1', 1, NOW(), NOW(), '123 Main St, San Francisco, CA', 'image1.jpg', 'Food',
        'Tasty Restaurant', '555-1234', '123456789', 'ACTIVE'),
       (2, 34.0522, -118.2437, b'1', 2, NOW(), NOW(), '456 Elm St, Los Angeles, CA', 'image2.jpg', 'Food',
        'Yummy Bistro', '555-5678', '987654321', 'ACTIVE'),
       (3, 40.7128, -74.0060, b'1', 3, NOW(), NOW(), '789 Maple Ave, New York, NY', 'image3.jpg', 'Food',
        'Delicious Eats', '555-9101', '1122334455', 'ACTIVE'),
       (4, 41.8781, -87.6298, b'1', 4, NOW(), NOW(), '101 Oak Dr, Chicago, IL', 'image4.jpg', 'Food', 'Savory Spot',
        '555-1122', '5566778899', 'ACTIVE'),
       (5, 29.7604, -95.3698, b'1', 5, NOW(), NOW(), '202 Pine Rd, Houston, TX', 'image5.jpg', 'Food', 'Flavor Town',
        '555-3344', '9988776655', 'ACTIVE'),
       (6, 33.7490, -84.3880, b'1', 6, NOW(), NOW(), '303 Cedar St, Atlanta, GA', 'image6.jpg', 'Food', 'Spice Heaven',
        '555-5566', '4433221100', 'ACTIVE'),
       (7, 39.9526, -75.1652, b'1', 7, NOW(), NOW(), '404 Birch Ln, Philadelphia, PA', 'image7.jpg', 'Food',
        'Bite Delight', '555-7788', '6655443322', 'ACTIVE'),
       (8, 32.7767, -96.7970, b'1', 8, NOW(), NOW(), '505 Spruce St, Dallas, TX', 'image8.jpg', 'Food',
        'Taste Paradise', '555-9900', '8877665544', 'ACTIVE'),
       (9, 47.6062, -122.3321, b'1', 9, NOW(), NOW(), '606 Ash Ave, Seattle, WA', 'image9.jpg', 'Food', 'Gourmet Grub',
        '555-1123', '7766554433', 'ACTIVE'),
       (10, 39.7392, -104.9903, b'1', 10, NOW(), NOW(), '707 Willow Rd, Denver, CO', 'image10.jpg', 'Food', 'Epic Eats',
        '555-4455', '9988447766', 'ACTIVE');

-- 3. restaurant_categories 테이블
INSERT INTO `restaurant_categories` (`id`, `created_at`, `updated_at`, `name`, `category_type`, `status`)
VALUES (1, NOW(), NOW(), 'Fast Food', 'MAJOR', 'ACTIVE'),
       (2, NOW(), NOW(), 'Healthy', 'MINOR', 'ACTIVE'),
       (3, NOW(), NOW(), 'Desserts', 'MAJOR', 'ACTIVE'),
       (4, NOW(), NOW(), 'Organic', 'MINOR', 'ACTIVE'),
       (5, NOW(), NOW(), 'Seafood', 'MAJOR', 'ACTIVE'),
       (6, NOW(), NOW(), 'Vegan', 'MINOR', 'ACTIVE'),
       (7, NOW(), NOW(), 'Italian', 'MAJOR', 'ACTIVE'),
       (8, NOW(), NOW(), 'Mexican', 'MINOR', 'ACTIVE'),
       (9, NOW(), NOW(), 'BBQ', 'MAJOR', 'ACTIVE'),
       (10, NOW(), NOW(), 'Asian', 'MINOR', 'ACTIVE');

-- 4. dish_categories 테이블
INSERT INTO `dish_categories` (`id`, `restaurant_id`, `created_at`, `updated_at`, `name`, `status`)
VALUES (1, 1, NOW(), NOW(), 'Appetizers', 'ACTIVE'),
       (2, 1, NOW(), NOW(), 'Main Course', 'ACTIVE'),
       (3, 1, NOW(), NOW(), 'Desserts', 'ACTIVE'),
       (4, 1, NOW(), NOW(), 'Beverages', 'ACTIVE'),
       (5, 1, NOW(), NOW(), 'Specials', 'ACTIVE'),
       (6, 1, NOW(), NOW(), 'Snacks', 'ACTIVE'),
       (7, 1, NOW(), NOW(), 'Salads', 'ACTIVE'),
       (8, 1, NOW(), NOW(), 'Soups', 'ACTIVE'),
       (9, 1, NOW(), NOW(), 'Sides', 'ACTIVE'),
       (10, 1, NOW(), NOW(), 'Combos', 'ACTIVE');

-- 5. dish 테이블
INSERT INTO `dish` (`dish_category_id`, `id`, `price`, `created_at`, `updated_at`, `description`, `image`, `name`,
                    `status`)
VALUES (1, 1, 500, NOW(), NOW(), 'Crispy French Fries', 'french_fries.jpg', 'French Fries', 'ACTIVE'),
       (2, 2, 700, NOW(), NOW(), 'Cheesy Nachos', 'nachos.jpg', 'Nachos', 'ACTIVE'),
       (3, 3, 1500, NOW(), NOW(), 'Grilled Chicken Sandwich', 'chicken_sandwich.jpg', 'Chicken Sandwich', 'ACTIVE'),
       (4, 4, 1300, NOW(), NOW(), 'Veggie Burger', 'veggie_burger.jpg', 'Veggie Burger', 'ACTIVE'),
       (5, 5, 1200, NOW(), NOW(), 'Steak Burrito', 'burrito.jpg', 'Burrito', 'ACTIVE'),
       (6, 6, 800, NOW(), NOW(), 'Chocolate Cake', 'chocolate_cake.jpg', 'Chocolate Cake', 'ACTIVE'),
       (7, 7, 400, NOW(), NOW(), 'Garlic Bread', 'garlic_bread.jpg', 'Garlic Bread', 'ACTIVE'),
       (8, 8, 350, NOW(), NOW(), 'Caesar Salad', 'caesar_salad.jpg', 'Caesar Salad', 'ACTIVE'),
       (9, 9, 600, NOW(), NOW(), 'Onion Rings', 'onion_rings.jpg', 'Onion Rings', 'ACTIVE'),
       (10, 10, 1000, NOW(), NOW(), 'Pizza Combo', 'pizza_combo.jpg', 'Pizza Combo', 'ACTIVE');

-- 6. tag 테이블
INSERT INTO `tag` (`id`, `created_at`, `updated_at`, `name`, `status`)
VALUES (1, NOW(), NOW(), 'Spicy', 'ACTIVE'),
       (2, NOW(), NOW(), 'Vegan', 'ACTIVE'),
       (3, NOW(), NOW(), 'Popular', 'ACTIVE'),
       (4, NOW(), NOW(), 'Gluten-Free', 'ACTIVE'),
       (5, NOW(), NOW(), 'Low-Calorie', 'ACTIVE'),
       (6, NOW(), NOW(), 'New', 'ACTIVE'),
       (7, NOW(), NOW(), 'Seasonal', 'ACTIVE'),
       (8, NOW(), NOW(), 'Family-Sized', 'ACTIVE'),
       (9, NOW(), NOW(), 'Chef Special', 'ACTIVE'),
       (10, NOW(), NOW(), 'Kid-Friendly', 'ACTIVE');

-- 7. dish_tag 테이블
INSERT INTO `dish_tag` (`dish_id`, `id`, `tag_id`, `created_at`, `updated_at`, `status`)
VALUES (1, 1, 3, NOW(), NOW(), 'ACTIVE'),
       (2, 2, 1, NOW(), NOW(), 'ACTIVE'),
       (3, 3, 3, NOW(), NOW(), 'ACTIVE'),
       (4, 4, 2, NOW(), NOW(), 'ACTIVE'),
       (5, 5, 4, NOW(), NOW(), 'ACTIVE'),
       (6, 6, 5, NOW(), NOW(), 'ACTIVE'),
       (7, 7, 6, NOW(), NOW(), 'ACTIVE'),
       (8, 8, 7, NOW(), NOW(), 'ACTIVE'),
       (9, 9, 8, NOW(), NOW(), 'ACTIVE'),
       (10, 10, 9, NOW(), NOW(), 'ACTIVE');

-- 8. floors 테이블
INSERT INTO `floors` (`floor_number`, `height`, `id`, `restaurant_id`, `width`, `created_at`, `updated_at`, `status`)
VALUES (1, 10, 1, 1, 15, NOW(), NOW(), 'ACTIVE'),
       (2, 8, 2, 2, 12, NOW(), NOW(), 'ACTIVE'),
       (3, 9, 3, 3, 14, NOW(), NOW(), 'ACTIVE'),
       (4, 7, 4, 4, 11, NOW(), NOW(), 'ACTIVE'),
       (5, 6, 5, 5, 10, NOW(), NOW(), 'ACTIVE'),
       (6, 12, 6, 6, 16, NOW(), NOW(), 'ACTIVE'),
       (7, 10, 7, 7, 14, NOW(), NOW(), 'ACTIVE'),
       (8, 9, 8, 8, 13, NOW(), NOW(), 'ACTIVE'),
       (9, 11, 9, 9, 15, NOW(), NOW(), 'ACTIVE'),
       (10, 8, 10, 10, 10, NOW(), NOW(), 'ACTIVE');

-- 9. options 테이블
INSERT INTO `options` (`id`, `restaurant_id`, `max_choice_count`, `mandatory`, `created_at`, `updated_at`, `name`,
                       `status`)
VALUES (1, 1, 1, b'1', NOW(), NOW(), 'Size', 'ACTIVE'),
       (2, 1, 3, b'0', NOW(), NOW(), 'Extra Option', 'ACTIVE');


-- 10. choices 테이블
INSERT INTO `choices` (`id`, `option_id`, `price`, `created_at`, `updated_at`, `name`, `status`)
VALUES (1, 1, 100, NOW(), NOW(), 'Small Size', 'ACTIVE'),
       (2, 1, 200, NOW(), NOW(), 'Large Size', 'ACTIVE'),
       (3, 2, 150, NOW(), NOW(), 'Extra Cheese', 'ACTIVE'),
       (4, 2, 100, NOW(), NOW(), 'Extra Sauce', 'ACTIVE'),
       (5, 2, 0, NOW(), NOW(), 'No Salt', 'ACTIVE'),
       (6, 2, 50, NOW(), NOW(), 'Add Spicy', 'ACTIVE'),
       (7, 2, 250, NOW(), NOW(), 'Double Meat', 'ACTIVE'),
       (8, 2, 0, NOW(), NOW(), 'No Onion', 'ACTIVE'),
       (9, 2, 150, NOW(), NOW(), 'Whole Wheat Bread', 'ACTIVE'),
       (10, 2, 50, NOW(), NOW(), 'Extra Ice', 'ACTIVE');

-- 11. dish_options 테이블
INSERT INTO `dish_options` (`dish_id`, `id`, `option_id`, `created_at`, `updated_at`, `status`)
VALUES (1, 1, 1, NOW(), NOW(), 'ACTIVE'), -- French Fries: Small Size
       (2, 2, 2, NOW(), NOW(), 'ACTIVE'), -- Nachos: Extra Cheese
       (3, 3, 1, NOW(), NOW(), 'ACTIVE'), -- Chicken Sandwich: Double Meat
       (3, 4, 2, NOW(), NOW(), 'ACTIVE'), -- Veggie Burger: No Onion
       (5, 5, 2, NOW(), NOW(), 'ACTIVE'), -- Burrito: Extra Sauce
       (6, 6, 2, NOW(), NOW(), 'ACTIVE'), -- Chocolate Cake: Add Spicy
       (7, 7, 2, NOW(), NOW(), 'ACTIVE'), -- Garlic Bread: Whole Wheat Bread
       (8, 8, 1, NOW(), NOW(), 'ACTIVE'), -- Caesar Salad: Large Size
       (9, 9, 2, NOW(), NOW(), 'ACTIVE'), -- Onion Rings: No Salt
       (10, 10, 2, NOW(), NOW(), 'ACTIVE');
-- Pizza Combo: Extra Ice

-- 12. restaurant_category_map 테이블
INSERT INTO `restaurant_category_map` (`id`, `restaurant_category_id`, `restaurant_id`, `created_at`, `updated_at`,
                                       `status`)
VALUES (1, 1, 1, NOW(), NOW(), 'ACTIVE'), -- Tasty Restaurant: Fast Food
       (2, 2, 1, NOW(), NOW(), 'ACTIVE'), -- Tasty Restaurant: Healthy
       (3, 3, 1, NOW(), NOW(), 'ACTIVE'), -- Yummy Bistro: Desserts
       (4, 4, 1, NOW(), NOW(), 'ACTIVE'), -- Yummy Bistro: Organic
       (5, 5, 1, NOW(), NOW(), 'ACTIVE'), -- Delicious Eats: Seafood
       (6, 6, 1, NOW(), NOW(), 'ACTIVE'), -- Delicious Eats: Vegan
       (7, 7, 1, NOW(), NOW(), 'ACTIVE'), -- Savory Spot: Italian
       (8, 8, 1, NOW(), NOW(), 'ACTIVE'), -- Flavor Town: Mexican
       (9, 9, 1, NOW(), NOW(), 'ACTIVE'), -- Spice Heaven: BBQ
       (10, 10, 1, NOW(), NOW(), 'ACTIVE');
-- Bite Delight: Asian

-- 13. orders 테이블
INSERT INTO `orders` (`id`, `menu_cnt`, `created_at`, `updated_at`, `table_id`, `order_status`, `payment_method`,
                      `status`)
VALUES (1, 3, NOW(), NOW(), '6721aa9b0d22a923091eef73', 'PENDING', 'MENU_DIVIDE', 'ACTIVE'),
       (2, 2, NOW(), NOW(), '6721aa9b0d22a923091eef73', 'PAID', 'MONEY_DIVIDE', 'ACTIVE'),
       (3, 4, NOW(), NOW(), '6721aa9b0d22a923091eef73', 'CANCELLED', 'UNDEFINED', 'ACTIVE'),
       (4, 1, NOW(), NOW(), '672ad435085e4f131b0ecadd', 'PENDING', 'MENU_DIVIDE', 'ACTIVE'),
       (5, 2, NOW(), NOW(), '672ad435085e4f131b0ecadd', 'PAID', 'MONEY_DIVIDE', 'ACTIVE'),
       (6, 3, NOW(), NOW(), '672ad435085e4f131b0ecadd', 'CANCELLED', 'UNDEFINED', 'ACTIVE'),
       (7, 2, NOW(), NOW(), '6721aa9b0d22a923091eef73', 'PENDING', 'MENU_DIVIDE', 'ACTIVE'),
       (8, 5, NOW(), NOW(), '672ad435085e4f131b0ecadd', 'PAID', 'MONEY_DIVIDE', 'ACTIVE'),
       (9, 3, NOW(), NOW(), '6721aa9b0d22a923091eef73', 'CANCELLED', 'UNDEFINED', 'ACTIVE'),
       (10, 4, NOW(), NOW(), '672ad435085e4f131b0ecadd', 'PAID', 'MENU_DIVIDE', 'ACTIVE');

-- 14. order_items 테이블
INSERT INTO `order_items` (`dish_id`, `id`, `order_id`, `order_price`, `quantity`, `created_at`, `ordered_at`,
                           `updated_at`, `user_id`, `status`)
VALUES (1, 1, 1, 500, 2, NOW(), NOW(), NOW(), 'user_1', 'ACTIVE'),
       (2, 2, 1, 700, 1, NOW(), NOW(), NOW(), 'user_2', 'ACTIVE'),
       (3, 3, 2, 1500, 3, NOW(), NOW(), NOW(), 'user_3', 'ACTIVE'),
       (4, 4, 2, 1300, 1, NOW(), NOW(), NOW(), 'user_4', 'ACTIVE'),
       (5, 5, 3, 1200, 2, NOW(), NOW(), NOW(), 'user_5', 'ACTIVE'),
       (6, 6, 3, 800, 1, NOW(), NOW(), NOW(), 'user_6', 'ACTIVE'),
       (7, 7, 4, 400, 3, NOW(), NOW(), NOW(), 'user_7', 'ACTIVE'),
       (8, 8, 4, 350, 2, NOW(), NOW(), NOW(), 'user_8', 'ACTIVE'),
       (9, 9, 5, 600, 1, NOW(), NOW(), NOW(), 'user_9', 'ACTIVE'),
       (10, 10, 5, 1000, 1, NOW(), NOW(), NOW(), 'user_10', 'ACTIVE');

-- 15. order_item_options 테이블
INSERT INTO `order_item_choices` (`id`, `choice_id`, `order_item_id`, `created_at`, `updated_at`, `status`)
VALUES (1, 1, 1, NOW(), NOW(), 'ACTIVE'), -- Order Item 1: Small Size
       (2, 2, 2, NOW(), NOW(), 'ACTIVE'), -- Order Item 2: Large Size
       (3, 3, 3, NOW(), NOW(), 'ACTIVE'), -- Order Item 3: Extra Cheese
       (4, 4, 4, NOW(), NOW(), 'ACTIVE'), -- Order Item 4: Extra Sauce
       (5, 5, 5, NOW(), NOW(), 'ACTIVE'), -- Order Item 5: No Salt
       (6, 6, 6, NOW(), NOW(), 'ACTIVE'), -- Order Item 6: Add Spicy
       (7, 7, 7, NOW(), NOW(), 'ACTIVE'), -- Order Item 7: Double Meat
       (8, 8, 8, NOW(), NOW(), 'ACTIVE'), -- Order Item 8: No Onion
       (9, 9, 9, NOW(), NOW(), 'ACTIVE'), -- Order Item 9: Whole Wheat Bread
       (10, 10, 10, NOW(), NOW(), 'ACTIVE');
-- Order Item 10: Extra Ice


# -- 추가 owners 테이블 데이터
# INSERT INTO `owners` (`id`, `created_at`, `updated_at`, `code`, `email`, `name`, `status`) VALUES
# (11, NOW(), NOW(), 'owner_code_11', 'owner11@example.com', 'Laura Palmer', 'ACTIVE'),
# (12, NOW(), NOW(), 'owner_code_12', 'owner12@example.com', 'James Cooper', 'ACTIVE'),
# (13, NOW(), NOW(), 'owner_code_13', 'owner13@example.com', 'Olivia Green', 'ACTIVE'),
# (14, NOW(), NOW(), 'owner_code_14', 'owner14@example.com', 'Sophia White', 'ACTIVE'),
# (15, NOW(), NOW(), 'owner_code_15', 'owner15@example.com', 'Robert Brown', 'ACTIVE');
#
# -- 추가 restaurants 테이블 데이터
# INSERT INTO `restaurants` (`id`, `latitude`, `longitude`, `open`, `owner_id`, `created_at`, `updated_at`, `address`, `image`, `industry`, `name`, `phone_number`, `registration_number`, `status`) VALUES
# (11, 35.6895, 139.6917, b'1', 11, NOW(), NOW(), '123 Cherry Blossom St, Tokyo, Japan', 'image11.jpg', 'Japanese', 'Sakura Bistro', '555-1111', '1234567890', 'ACTIVE'),
# (12, 40.7128, -74.0060, b'1', 12, NOW(), NOW(), '456 Sushi Ave, New York, NY', 'image12.jpg', 'Japanese', 'Sushi Place', '555-2222', '0987654321', 'ACTIVE'),
# (13, 51.5074, -0.1278, b'1', 13, NOW(), NOW(), '789 Tower Bridge Rd, London, UK', 'image13.jpg', 'British', 'London Pub', '555-3333', '1122334455', 'ACTIVE'),
# (14, 48.8566, 2.3522, b'1', 14, NOW(), NOW(), '101 Champs Elysees, Paris, France', 'image14.jpg', 'French', 'Paris Cafe', '555-4444', '2233445566', 'ACTIVE'),
# (15, 55.7558, 37.6173, b'1', 15, NOW(), NOW(), '202 Red Square, Moscow, Russia', 'image15.jpg', 'Russian', 'Moscow Diner', '555-5555', '3344556677', 'ACTIVE');
#
# -- 추가 restaurant_categories 테이블 데이터
# INSERT INTO `restaurant_categories` (`id`, `created_at`, `updated_at`, `name`, `category_type`, `status`) VALUES
# (11, NOW(), NOW(), 'Burgers', 'MAJOR', 'ACTIVE'),
# (12, NOW(), NOW(), 'Pasta', 'MAJOR', 'ACTIVE'),
# (13, NOW(), NOW(), 'Bakery', 'MINOR', 'ACTIVE'),
# (14, NOW(), NOW(), 'Curry', 'MAJOR', 'ACTIVE'),
# (15, NOW(), NOW(), 'Steakhouse', 'MAJOR', 'ACTIVE');
#
# -- 추가 dish_categories 테이블 데이터
# INSERT INTO `dish_categories` (`id`, `restaurant_id`, `created_at`, `updated_at`, `name`, `status`) VALUES
# (11, 11, NOW(), NOW(), 'Special Rolls', 'ACTIVE'),
# (12, 12, NOW(), NOW(), 'Sashimi', 'ACTIVE'),
# (13, 13, NOW(), NOW(), 'Burgers', 'ACTIVE'),
# (14, 14, NOW(), NOW(), 'Pastries', 'ACTIVE'),
# (15, 15, NOW(), NOW(), 'Grilled Specials', 'ACTIVE');
#
# -- 추가 dish 테이블 데이터
# INSERT INTO `dish` (`dish_category_id`, `id`, `price`, `created_at`, `updated_at`, `description`, `image`, `name`, `status`) VALUES
# (11, 21, 1200, NOW(), NOW(), 'Salmon Roll with Avocado', 'salmon_roll.jpg', 'Salmon Roll', 'ACTIVE'),
# (12, 22, 1500, NOW(), NOW(), 'Assorted Sashimi Platter', 'sashimi_platter.jpg', 'Sashimi Platter', 'ACTIVE'),
# (13, 23, 1000, NOW(), NOW(), 'Classic Cheeseburger', 'cheeseburger.jpg', 'Cheeseburger', 'ACTIVE'),
# (14, 24, 800, NOW(), NOW(), 'Chocolate Croissant', 'croissant.jpg', 'Chocolate Croissant', 'ACTIVE'),
# (15, 25, 2000, NOW(), NOW(), 'Grilled Ribeye Steak', 'ribeye.jpg', 'Ribeye Steak', 'ACTIVE');
#
# -- 추가 tag 테이블 데이터
# INSERT INTO `tag` (`id`, `created_at`, `updated_at`, `name`, `status`) VALUES
# (11, NOW(), NOW(), 'Kid-Friendly', 'ACTIVE'),
# (12, NOW(), NOW(), 'Organic', 'ACTIVE'),
# (13, NOW(), NOW(), 'Locally Sourced', 'ACTIVE'),
# (14, NOW(), NOW(), 'Limited Edition', 'ACTIVE'),
# (15, NOW(), NOW(), 'High Protein', 'ACTIVE');
#
#
# -- 추가 dish_tag 테이블 데이터
# INSERT INTO `dish_tag` (`dish_id`, `id`, `tag_id`, `created_at`, `updated_at`, `status`) VALUES
# (21, 31, 11, NOW(), NOW(), 'ACTIVE'),  -- Salmon Roll: Kid-Friendly
# (22, 32, 12, NOW(), NOW(), 'ACTIVE'),  -- Sashimi Platter: Organic
# (23, 33, 13, NOW(), NOW(), 'ACTIVE'),  -- Cheeseburger: Locally Sourced
# (24, 34, 14, NOW(), NOW(), 'ACTIVE'),  -- Chocolate Croissant: Limited Edition
# (25, 35, 15, NOW(), NOW(), 'ACTIVE');  -- Ribeye Steak: High Protein
#
# -- 추가 options 테이블 데이터
# INSERT INTO `options` (`id`, `mandatory`, `created_at`, `updated_at`, `name`, `status`) VALUES
# -- (21, b'1', NOW(), NOW(), 'No Spicy', 'ACTIVE'),
# -- (22, b'0', NOW(), NOW(), 'Gluten-Free Bread', 'ACTIVE'),
# -- (23, b'1', NOW(), NOW(), 'Extra Cheese', 'ACTIVE'),
# -- (24, b'0', NOW(), NOW(), 'Add Bacon', 'ACTIVE'),
# -- (25, b'0', NOW(), NOW(), 'No Salt', 'ACTIVE')
# -- (10, b'0', NOW(), NOW(), 'Mini Size', 'ACTIVE'),
# (11, b'0', NOW(), NOW(), 'Cheese', 'ACTIVE');
#
#
# -- 추가 choices 테이블 데이터
# INSERT INTO `choices` (`id`, `option_id`, `price`, `created_at`, `updated_at`, `name`, `status`) VALUES
# (11, 1, 50, NOW(), NOW(), 'Mini Size', 'ACTIVE'),
# (12, 1, 250, NOW(), NOW(), 'Extra Large Size', 'ACTIVE'),
# (13, 11, 100, NOW(), NOW(), 'Less Cheese', 'ACTIVE'),
# (14, 11, 150, NOW(), NOW(), 'Light Sauce', 'ACTIVE'),
# (15, 2, 0, NOW(), NOW(), 'No Sugar', 'ACTIVE');
#
# select * from options;
# -- 추가 dish_options 테이블 데이터
# INSERT INTO `dish_options` (`dish_id`, `id`, `option_id`, `created_at`, `updated_at`, `status`) VALUES
# (1, 11, 11, NOW(), NOW(), 'ACTIVE'),  -- French Fries: Mini Size
# (2, 12, 11, NOW(), NOW(), 'ACTIVE'),  -- Nachos: Less Cheese
# (3, 13, 7, NOW(), NOW(), 'ACTIVE'),  -- Chicken Sandwich: Double Meat
# (4, 14, 8, NOW(), NOW(), 'ACTIVE'),  -- Veggie Burger: No Onion
# (5, 15, 10, NOW(), NOW(), 'ACTIVE'); -- Pizza Combo: Extra Ice
#
# -- 추가 restaurant_category_map 테이블 데이터
# INSERT INTO `restaurant_category_map` (`id`, `restaurant_category_id`, `restaurant_id`, `created_at`, `updated_at`, `status`) VALUES
# (11, 5, 8, NOW(), NOW(), 'ACTIVE'),  -- Taste Paradise: Seafood
# (12, 6, 9, NOW(), NOW(), 'ACTIVE'),  -- Gourmet Grub: Vegan
# (13, 7, 10, NOW(), NOW(), 'ACTIVE'),  -- Epic Eats: Italian
# (14, 8, 2, NOW(), NOW(), 'ACTIVE'),  -- Yummy Bistro: Mexican
# (15, 9, 1, NOW(), NOW(), 'ACTIVE');  -- Tasty Restaurant: BBQ
#
# -- 추가 orders 테이블 데이터
# INSERT INTO `orders` (`id`, `menu_cnt`, `created_at`, `updated_at`, `table_id`, `order_status`, `payment_method`, `status`) VALUES
# (11, 2, NOW(), NOW(), '672ad435085e4f131b0ecadd', 'PENDING', 'MENU_DIVIDE', 'ACTIVE'),
# (12, 3, NOW(), NOW(), '6721aa9b0d22a923091eef73', 'PAID', 'MONEY_DIVIDE', 'ACTIVE'),
# (13, 1, NOW(), NOW(), '672ad435085e4f131b0ecadd', 'CANCELLED', 'UNDEFINED', 'ACTIVE'),
# (14, 4, NOW(), NOW(), '6721aa9b0d22a923091eef73', 'PENDING', 'MENU_DIVIDE', 'ACTIVE'),
# (15, 2, NOW(), NOW(), '672ad435085e4f131b0ecadd', 'PAID', 'MENU_DIVIDE', 'ACTIVE');
#
# -- 추가 order_items 테이블 데이터
# INSERT INTO `order_items` (`dish_id`, `id`, `order_id`, `order_price`, `quantity`, `created_at`, `ordered_at`, `updated_at`, `user_id`, `status`) VALUES
# (1, 21, 11, 400, 1, NOW(), NOW(), NOW(), 'user_11', 'ACTIVE'),
# (2, 22, 12, 750, 1, NOW(), NOW(), NOW(), 'user_12', 'ACTIVE'),
# (3, 23, 13, 1400, 2, NOW(), NOW(), NOW(), 'user_13', 'ACTIVE'),
# (4, 24, 14, 1100, 1, NOW(), NOW(), NOW(), 'user_14', 'ACTIVE'),
# (5, 25, 15, 1250, 1, NOW(), NOW(), NOW(), 'user_15', 'ACTIVE');
#
# -- 추가 order_item_options 테이블 데이터
# INSERT INTO `order_item_choices` (`id`, `choice_id`, `order_item_id`, `created_at`, `updated_at`, `status`) VALUES
# (21, 11, 21, NOW(), NOW(), 'ACTIVE'), -- Order Item 21: Mini Size
# (22, 12, 22, NOW(), NOW(), 'ACTIVE'), -- Order Item 22: Extra Large Size
# (23, 13, 23, NOW(), NOW(), 'ACTIVE'), -- Order Item 23: Less Cheese
# (24, 14, 24, NOW(), NOW(), 'ACTIVE'), -- Order Item 24: Light Sauce
# (25, 15, 25, NOW(), NOW(), 'ACTIVE'); -- Order Item 25: No Sugar
#
# select * from order_items;
# -- 주문 항목 추가: 동일한 dishId와 옵션을 가진 주문을 추가하여 수량 확인
# INSERT INTO `order_items` (`dish_id`, `id`, `order_id`, `order_price`, `quantity`, `created_at`, `ordered_at`, `updated_at`, `user_id`, `status`) VALUES
# (1, 26, 1, 500, 1, NOW(), NOW(), NOW(), 'user_2', 'ACTIVE'),  -- French Fries: 옵션 Small
# (1, 27, 1, 500, 1, NOW(), NOW(), NOW(), 'user_2', 'ACTIVE'),  -- French Fries: 옵션 Small
# (2, 28, 2, 700, 1, NOW(), NOW(), NOW(), 'user_3', 'ACTIVE'),  -- Nachos: 옵션 Extra Cheese
# (2, 29, 2, 700, 1, NOW(), NOW(), NOW(), 'user_3', 'ACTIVE');  -- Nachos: 옵션 Extra Cheese
#
# -- 주문 옵션 추가
# INSERT INTO `order_item_choices` (`id`, `choice_id`, `order_item_id`, `created_at`, `updated_at`, `status`) VALUES
# (21, 1, 26, NOW(), NOW(), 'ACTIVE'),  -- French Fries: Small 옵션
# (22, 1, 27, NOW(), NOW(), 'ACTIVE'),  -- French Fries: Small 옵션
# (23, 3, 28, NOW(), NOW(), 'ACTIVE'),  -- Nachos: Extra Cheese 옵션
# (24, 3, 29, NOW(), NOW(), 'ACTIVE');  -- Nachos: Extra Cheese 옵션
