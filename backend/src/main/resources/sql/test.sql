use product;

-- 1. owners 테이블
INSERT INTO `owners` (`id`, `created_at`, `updated_at`, `code`, `email`, `name`, `status`)
VALUES (1, NOW(), NOW(), 'owner_code_1', 'owner1@example.com', 'John Doe', 'ACTIVE'),

       -- 진짜 데이터
       (11, NOW(), NOW(), 'ssafyyongsoo608', 'yongsu0201@gmail.com', '조용수', 'ACTIVE'),
       (12, NOW(), NOW(), 'ssafyyeongpyo608', 'kyp0416@gmail.com', '김영표', 'ACTIVE'),
       (13, NOW(), NOW(), 'ssafygyeonghwan608', 'gjrudghks1669@gmail.com', '허경환', 'ACTIVE'),
       (14, NOW(), NOW(), 'ssafygyeonghyun608', 'sujipark2009@gmail.com', '김경현', 'ACTIVE'),
       (15, NOW(), NOW(), 'ssafyyoonji608', 'kkamisister1207@gmail.com', '김윤지', 'ACTIVE'),
       (16, NOW(), NOW(), 'ssafyhayeon608', 'hayeonful@gmail.com', '김하연', 'ACTIVE')
;

-- 2. restaurants 테이블
INSERT INTO `restaurants` (`id`, `latitude`, `longitude`, `open`, `owner_id`, `created_at`, `updated_at`, `address`,
                           `image`, `industry`, `name`, `phone_number`, `registration_number`, `status`)
VALUES (1, 37.7749, -122.4194, b'1', 1, NOW(), NOW(), '123 Main St, San Francisco, CA', 'image1.jpg', 'Food',
        'Tasty Restaurant', '555-1234', '123456789', 'ACTIVE'),
       -- 진짜 데이터
       -- 진대감 역삼점
       (11, 37.5027474950576, 127.03720072319, b'1', 11, NOW(), NOW(), '서울 강남구 봉은사로30길 75', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/restaurant_11_0.jfif', 'Food', '진대감 역삼점',
        '02-552-2472', '261-81-00884', 'ACTIVE'),
       (12, 37.5010235882489, 127.034572307912, b'1', 12, NOW(), NOW(), '서울 강남구 강남대로94길 91', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/restaurant_12.jfif', 'Food', '공차 역삼점',
        '02-779-7758', '214-88-84534', 'ACTIVE')
;

-- 3. restaurant_categories 테이블
INSERT INTO `restaurant_categories` (`id`, `created_at`, `updated_at`, `name`, `category_type`, `status`)
VALUES (1, NOW(), NOW(), 'Fast Food', 'MAJOR', 'ACTIVE'),

       -- 진짜 데이터
       (11, NOW(), NOW(), '한식', 'MAJOR', 'ACTIVE'),
       (12, NOW(), NOW(), '양식', 'MAJOR', 'ACTIVE'),
       (13, NOW(), NOW(), '중식', 'MAJOR', 'ACTIVE'),
       (14, NOW(), NOW(), '일식', 'MAJOR', 'ACTIVE'),
       (15, NOW(), NOW(), '술집', 'MAJOR', 'ACTIVE'),
       (16, NOW(), NOW(), '카페', 'MAJOR', 'ACTIVE')
;

-- 4. restaurant_category_map 테이블
INSERT INTO `restaurant_category_map` (`id`, `restaurant_category_id`, `restaurant_id`, `created_at`, `updated_at`,
                                       `status`)
VALUES (1, 1, 1, NOW(), NOW(), 'ACTIVE'), -- Tasty Restaurant: Fast Food

--        진짜 데이터
       (11, 11, 11, NOW(), NOW(), 'ACTIVE'),
       (12, 16, 12, NOW(), NOW(), 'ACTIVE')
;

-- 5. dish_categories 테이블
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
       (10, 1, NOW(), NOW(), 'Combos', 'ACTIVE'),
       -- 진짜 데이터
       (11, 11, NOW(), NOW(), '점심', 'ACTIVE'),
       (12, 11, NOW(), NOW(), '본차림', 'ACTIVE'),
       (13, 12, NOW(), NOW(), '커피', 'ACTIVE'),
       (14, 12, NOW(), NOW(), '밀크티', 'ACTIVE')

#        (12, 11, NOW(), NOW(), '본차림', 'ACTIVE'),
#        (12, 11, NOW(), NOW(), '본차림', 'ACTIVE'),
#        (12, 11, NOW(), NOW(), '본차림', 'ACTIVE')

;

-- 6. dish 테이블
INSERT INTO `dish` (`id`, `dish_category_id`, `price`, `created_at`, `updated_at`, `description`, `image`, `name`, `tags`,
                    `status`)
VALUES (1, 1, 500, NOW(), NOW(), 'Crispy French Fries', 'french_fries.jpg', 'French Fries', '[]', 'ACTIVE'),
       (2, 2, 700, NOW(), NOW(), 'Cheesy Nachos', 'nachos.jpg', 'Nachos', '[]', 'ACTIVE'),
       (3, 3, 1500, NOW(), NOW(), 'Grilled Chicken Sandwich', 'chicken_sandwich.jpg', 'Chicken Sandwich', '[]', 'ACTIVE'),
       (4, 4, 1300, NOW(), NOW(), 'Veggie Burger', 'veggie_burger.jpg', 'Veggie Burger', '[]', 'ACTIVE'),
       (5, 5, 1200, NOW(), NOW(), 'Steak Burrito', 'burrito.jpg', 'Burrito', '[]', 'ACTIVE'),
       (6, 6, 800, NOW(), NOW(), 'Chocolate Cake', 'chocolate_cake.jpg', 'Chocolate Cake', '[]', 'ACTIVE'),
       (7, 7, 400, NOW(), NOW(), 'Garlic Bread', 'garlic_bread.jpg', 'Garlic Bread', '[]', 'ACTIVE'),
       (8, 8, 350, NOW(), NOW(), 'Caesar Salad', 'caesar_salad.jpg', 'Caesar Salad', '[]', 'ACTIVE'),
       (9, 9, 600, NOW(), NOW(), 'Onion Rings', 'onion_rings.jpg', 'Onion Rings', '[]', 'ACTIVE'),
       (10, 10, 1000, NOW(), NOW(), 'Pizza Combo', 'pizza_combo.jpg', 'Pizza Combo', '[]', 'ACTIVE'),

-- 진짜 데이터
       (11, 11, 14000, NOW(), NOW(), '(평일 점심한정)', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_11.jfif', '점심) 즉석한우불고기', '["한정", "시그니처"]', 'ACTIVE'),
       (12, 12, 29000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_12.jfif', '한우차돌삼합', '[]', 'ACTIVE'),
       (13, 12, 119000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_13.jfif', '한우투뿔모듬', '[]', 'ACTIVE'),
       (14, 12, 55000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_14.jfif', '한우파김치전골', '[]', 'ACTIVE'),
       (15, 12, 12000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_15.jfif', '한우꽃게된장찌개', '[]', 'ACTIVE'),
       (16, 12, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_16.jfif', '한우차돌짬뽕라면', '[]', 'ACTIVE'),
       (17, 12, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_17.jfif', '갓김치물냉면', '[]', 'ACTIVE'),
       (18, 12, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_18.jfif', '갓김치비빔냉면', '[]', 'ACTIVE'),
       (19, 11, 12000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_19.jfif', '점심) 한우꽃게된장찌개밥상', '["한정"]', 'ACTIVE'),
       (20, 11, 11000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_20.jfif', '점심) 한돈파김지전골밥상', '["한정"]', 'ACTIVE'),
       (21, 11, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_21.jfif', '점심) 한우차돌짬뽕라면밥상', '["한정"]', 'ACTIVE'),
       (22, 11, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_22.jfif', '점심) 갓김치비빔냉면밥상', '["한정"]', 'ACTIVE'),

       #커피
       (23, 13, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_21.jfif', '점심) 한우차돌짬뽕라면밥상', '["한정"]', 'ACTIVE'),
       (24, 13, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_21.jfif', '점심) 한우차돌짬뽕라면밥상', '["한정"]', 'ACTIVE'),
       (25, 13, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_21.jfif', '점심) 한우차돌짬뽕라면밥상', '["한정"]', 'ACTIVE'),
       (26, 13, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_21.jfif', '점심) 한우차돌짬뽕라면밥상', '["한정"]', 'ACTIVE'),
       #밀크티
       (27, 14, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_21.jfif', '점심) 한우차돌짬뽕라면밥상', '["한정"]', 'ACTIVE'),
       (28, 14, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_21.jfif', '점심) 한우차돌짬뽕라면밥상', '["한정"]', 'ACTIVE'),
       (29, 14, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_21.jfif', '점심) 한우차돌짬뽕라면밥상', '["한정"]', 'ACTIVE'),
       (30, 14, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_21.jfif', '점심) 한우차돌짬뽕라면밥상', '["한정"]', 'ACTIVE')
;

-- -- 6. tag 테이블
-- INSERT INTO `tag` (`id`, `created_at`, `updated_at`, `name`, `status`)
-- VALUES (1, NOW(), NOW(), 'Spicy', 'ACTIVE'),
--        (2, NOW(), NOW(), 'Vegan', 'ACTIVE'),
--        (3, NOW(), NOW(), 'Popular', 'ACTIVE'),
--        (4, NOW(), NOW(), 'Gluten-Free', 'ACTIVE'),
--        (5, NOW(), NOW(), 'Low-Calorie', 'ACTIVE'),
--        (6, NOW(), NOW(), 'New', 'ACTIVE'),
--        (7, NOW(), NOW(), 'Seasonal', 'ACTIVE'),
--        (8, NOW(), NOW(), 'Family-Sized', 'ACTIVE'),
--        (9, NOW(), NOW(), 'Chef Special', 'ACTIVE'),
--        (10, NOW(), NOW(), 'Kid-Friendly', 'ACTIVE');
--
-- -- 7. dish_tag 테이블
-- INSERT INTO `dish_tag` (`dish_id`, `id`, `tag_id`, `created_at`, `updated_at`, `status`)
-- VALUES (1, 1, 3, NOW(), NOW(), 'ACTIVE'),
--        (2, 2, 1, NOW(), NOW(), 'ACTIVE'),
--        (3, 3, 3, NOW(), NOW(), 'ACTIVE'),
--        (4, 4, 2, NOW(), NOW(), 'ACTIVE'),
--        (5, 5, 4, NOW(), NOW(), 'ACTIVE'),
--        (6, 6, 5, NOW(), NOW(), 'ACTIVE'),
--        (7, 7, 6, NOW(), NOW(), 'ACTIVE'),
--        (8, 8, 7, NOW(), NOW(), 'ACTIVE'),
--        (9, 9, 8, NOW(), NOW(), 'ACTIVE'),
--        (10, 10, 9, NOW(), NOW(), 'ACTIVE');

-- 7. options 테이블
INSERT INTO `options` (`id`, `restaurant_id`, `max_choice_count`, `mandatory`, `created_at`, `updated_at`, `name`,
                       `status`)
VALUES (1, 1, 1, b'1', NOW(), NOW(), 'Size', 'ACTIVE'),
       (2, 1, 3, b'0', NOW(), NOW(), 'Extra Option', 'ACTIVE')
;


-- 8. choices 테이블
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

-- 9. dish_options 테이블
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
       (10, 10, 2, NOW(), NOW(), 'ACTIVE'); -- Pizza Combo: Extra Ice

-- 10. orders 테이블
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

-- 11. order_items 테이블
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

-- 12. order_item_options 테이블
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
