use
    product;

-- 1. owners 테이블
INSERT INTO `owners` (`id`, `created_at`, `updated_at`, `code`, `email`, `name`, `status`)
VALUES (1, NOW(), NOW(), 'owner_code_1', 'owner1@example.com', 'John Doe', 'ACTIVE'),

       -- 진짜 데이터
       (11, NOW(), NOW(), 'ssafyyongsoo608', 'yongsu0201@gmail.com', '조용수', 'ACTIVE'),
       (12, NOW(), NOW(), 'ssafyyeongpyo608', 'kyp0416@gmail.com', '김영표', 'ACTIVE'),
       (13, NOW(), NOW(), 'ssafygyeonghwan608', 'gjrudghks1669@gmail.com', '허경환', 'ACTIVE'),
       (14, NOW(), NOW(), 'ssafygyeonghyun608', 'sujipark2009@gmail.com', '김경현', 'ACTIVE'),
       (15, NOW(), NOW(), 'ssafyyoonji608', 'kkamisister1207@gmail.com', '김윤지', 'ACTIVE'),
       (16, NOW(), NOW(), 'ssafyhayeon608', 'hayeonful@gmail.com', '김하연', 'ACTIVE'),
       (17, NOW(), NOW(), 'ssafycoldsteel608','coldsteel@gmail.com','한기철','ACTIVE'),
       (18, NOW(), NOW(), 'ssafychinese608','chinese@gmail.com','중국인','ACTIVE')
;

-- 2. restaurants 테이블
INSERT INTO `restaurants` (`id`, `latitude`, `longitude`, `open`, `owner_id`, `created_at`, `updated_at`, `address`,
                           `image`, `industry`, `name`, `phone_number`, `registration_number`, `status`)
VALUES (1, 37.7749, -122.4194, b'1', 1, NOW(), NOW(), '123 Main St, San Francisco, CA', 'image1.jpg', 'Food',
        'Tasty Restaurant', '555-1234', '123456789', 'ACTIVE'),
       -- 진짜 데이터
--     진대감 역삼점
       (11, 37.5027474950576, 127.03720072319, b'1', 11, NOW(), NOW(), '서울 강남구 봉은사로30길 75',
        'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/restaurant_11_0.jfif', 'Food', '진대감 역삼점',
        '02-552-2472', '261-81-00884', 'ACTIVE'),
--     공차 역삼점
       (12, 37.5010235882489, 127.034572307912, b'1', 12, NOW(), NOW(), '서울 강남구 강남대로94길 91',
        'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/restaurant_12.jfif', 'Food', '공차 역삼점',
        '02-779-7758', '214-88-84534', 'ACTIVE'),
--     오봉집 역삼역점
       (13, 37.5021681398693, 127.035769697653, b'1', 13, NOW(), NOW(), '서울특별시 강남구 논현로93길 4',
        'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/restaurant_13.jpg', 'Food', '오봉집 역삼역점',
        '02-554-4490', '212-86-08370', 'ACTIVE'),
--     바스버거
       (14, 37.4996347605088, 127.035838830564, b'1', 14, NOW(), NOW(), '서울특별시 강남구 테헤란로26길 10 성보빌딩 지하1층',
        'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/restaurant_14.jpg', 'Food', '바스버거',
        '02-568-6654', '106-87-05191', 'ACTIVE'),
--     농민백암순대 본점
       (15, 37.5037329376349, 127.052982069884, b'1', 15, NOW(), NOW(), '서울특별시 강남구 선릉로86길 40-4',
        'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/restaurant_15.jpg', 'Food', '농민백암순대 본점',
        '02-555-9603', '254-12-01439', 'ACTIVE'),
--     멀티캠퍼스
       (16, 37.5012767241426, 127.039600248343, b'1', 16, NOW(), NOW(), '서울특별시 강남구 테헤란로 212',
        'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/restaurant_16.jpg', 'Food', '멀티캠퍼스 20층',
        '1544-9001', '104-81-53114', 'ACTIVE'),

--     긴자료코
       (17,37.498470,127.035136,b'1',17,NOW(),NOW(),'긴자료코 역삼점,서울특별시 강남구 역삼동 736-24 1층',
         'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/ginzaryoko.png','Food','긴자료코',
         '02-554-5112','539-39-01171','ACTIVE')

--     안사부
       (18,37.502364,127.036724,b'1',18,NOW(),NOW(),'안사부,서울특별시 강남구 논현로94길 3',
         'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/ansabu.jpg','Food','안사부',
         '0507-1365-8662','664-06-02672','ACTIVE')

;

-- 3. restaurant_categories 테이블
INSERT INTO `restaurant_categories` (`id`, `created_at`, `updated_at`, `name`, `category_type`, `status`)
VALUES
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
VALUES (1, 11, 1, NOW(), NOW(), 'ACTIVE'), -- Tasty Restaurant: Fast Food

--        진짜 데이터
       (11, 11, 11, NOW(), NOW(), 'ACTIVE'),
       (12, 16, 12, NOW(), NOW(), 'ACTIVE'),
       (13, 11, 13, NOW(), NOW(), 'ACTIVE'),
       (14, 12, 14, NOW(), NOW(), 'ACTIVE'),
       (15, 11, 15, NOW(), NOW(), 'ACTIVE'),
       (16, 11, 16, NOW(), NOW(), 'ACTIVE'),
       (17, 14, 17, NOW(), NOW(), 'ACTIVE'),
       (18, 13, 18, NOW(), NOW(), 'ACTIVE')
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
--        진대감 역삼점
       (11, 11, NOW(), NOW(), '점심', 'ACTIVE'),
       (12, 11, NOW(), NOW(), '본차림', 'ACTIVE'),

--        공차 역삼점
       (13, 12, NOW(), NOW(), '밀크티', 'ACTIVE'),
       (14, 12, NOW(), NOW(), '오리지널 티', 'ACTIVE'),
       (15, 12, NOW(), NOW(), '커피', 'ACTIVE'),
       (16, 12, NOW(), NOW(), '스무디', 'ACTIVE'),

--        오봉집 역삼점
       (17, 13, NOW(), NOW(), '메인 메뉴', 'ACTIVE'),
       (18, 13, NOW(), NOW(), '사이드', 'ACTIVE'),

--        바스버거
       (19, 14, NOW(), NOW(), '버거', 'ACTIVE'),
       (20, 14, NOW(), NOW(), '세트 메뉴', 'ACTIVE'),
       (21, 14, NOW(), NOW(), '사이드', 'ACTIVE'),
       (22, 14, NOW(), NOW(), '음료', 'ACTIVE'),

--        농민백암순대
       (23, 15, NOW(), NOW(), '메인 메뉴', 'ACTIVE'),
,
--        멀티캠퍼스
       (24, 16, NOW(), NOW(), 'A: 한식', 'ACTIVE'),
       (25, 16, NOW(), NOW(), 'B: 일품', 'ACTIVE'),
       (26, 16, NOW(), NOW(), '도시락', 'ACTIVE'),
       (27, 16, NOW(), NOW(), '샌드위치', 'ACTIVE'),
       (29, 16, NOW(), NOW(), '샐러드', 'ACTIVE'),

--      긴자료코
       (30, 17, NOW(), NOW(), '돈까스', 'ACTIVE'),
       (31, 17, NOW(), NOW(), '함박스테이크', 'ACTIVE'),
       (32, 17, NOW(), NOW(), '덮밥', 'ACTIVE'),
       (33, 17, NOW(), NOW(), '카레라이스', 'ACTIVE'),
       (34, 17, NOW(), NOW(), '우동', 'ACTIVE')

--      안사부
       (35, 18, NOW(), NOW(), '짜장면', 'ACTIVE'),
       (36, 18, NOW(), NOW(), '짬뽕', 'ACTIVE'),
       (37, 18, NOW(), NOW(), '볶음밥', 'ACTIVE'),
       (38, 18, NOW(), NOW(), '요리', 'ACTIVE');

;

-- 6. dish 테이블
INSERT INTO `dish` (`id`, `dish_category_id`, `price`, `created_at`, `updated_at`, `description`, `image`, `name`,
                    `tags`,
                    `status`)
VALUES (1, 1, 500, NOW(), NOW(), 'Crispy French Fries', 'french_fries.jpg', 'French Fries', '[]', 'ACTIVE'),
       (2, 2, 700, NOW(), NOW(), 'Cheesy Nachos', 'nachos.jpg', 'Nachos', '[]', 'ACTIVE'),
       (3, 3, 1500, NOW(), NOW(), 'Grilled Chicken Sandwich', 'chicken_sandwich.jpg', 'Chicken Sandwich', '[]',
        'ACTIVE'),
       (4, 4, 1300, NOW(), NOW(), 'Veggie Burger', 'veggie_burger.jpg', 'Veggie Burger', '[]', 'ACTIVE'),
       (5, 5, 1200, NOW(), NOW(), 'Steak Burrito', 'burrito.jpg', 'Burrito', '[]', 'ACTIVE'),
       (6, 6, 800, NOW(), NOW(), 'Chocolate Cake', 'chocolate_cake.jpg', 'Chocolate Cake', '[]', 'ACTIVE'),
       (7, 7, 400, NOW(), NOW(), 'Garlic Bread', 'garlic_bread.jpg', 'Garlic Bread', '[]', 'ACTIVE'),
       (8, 8, 350, NOW(), NOW(), 'Caesar Salad', 'caesar_salad.jpg', 'Caesar Salad', '[]', 'ACTIVE'),
       (9, 9, 600, NOW(), NOW(), 'Onion Rings', 'onion_rings.jpg', 'Onion Rings', '[]', 'ACTIVE'),
       (10, 10, 1000, NOW(), NOW(), 'Pizza Combo', 'pizza_combo.jpg', 'Pizza Combo', '[]', 'ACTIVE'),

-- 진짜 데이터
       -- 진대감 역삼점
       (11, 11, 14000, NOW(), NOW(), '(평일 점심한정)', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_11.jfif',
        '점심) 즉석한우불고기', '["한정", "시그니처"]', 'ACTIVE'),
       (12, 12, 29000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_12.jfif', '한우차돌삼합',
        '[]', 'ACTIVE'),
       (13, 12, 119000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_13.jfif', '한우투뿔모듬',
        '[]', 'ACTIVE'),
       (14, 12, 55000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_14.jfif', '한우파김치전골',
        '[]', 'ACTIVE'),
       (15, 12, 12000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_15.jfif', '한우꽃게된장찌개',
        '[]', 'ACTIVE'),
       (16, 12, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_16.jfif', '한우차돌짬뽕라면',
        '[]', 'ACTIVE'),
       (17, 12, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_17.jfif', '갓김치물냉면',
        '[]', 'ACTIVE'),
       (18, 12, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_18.jfif', '갓김치비빔냉면',
        '[]', 'ACTIVE'),
       (19, 11, 12000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_19.jfif',
        '점심) 한우꽃게된장찌개밥상', '["한정"]', 'ACTIVE'),
       (20, 11, 11000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_20.jfif',
        '점심) 한돈파김지전골밥상', '["한정"]', 'ACTIVE'),
       (21, 11, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_21.jfif',
        '점심) 한우차돌짬뽕라면밥상', '["한정"]', 'ACTIVE'),
       (22, 11, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_22.png',
        '점심) 갓김치비빔냉면밥상', '["한정"]', 'ACTIVE'),

--     공차 역삼점
       (23, 13, 5300, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_23.png',
        '딸기 쥬얼리 밀크티', '', 'ACTIVE'),
       (24, 13, 5100, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_24.png',
        '코코넛 밀크', '', 'ACTIVE'),
       (25, 13, 4900, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_25.png',
        '코코넛 블랙 밀크티', '', 'ACTIVE'),
       (26, 13, 4500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_26.png',
        '블랙 밀크티', '["인기"]', 'ACTIVE'),
       (27, 13, 4500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_27.png',
        '얼그레이 밀크티', '', 'ACTIVE'),
       (28, 13, 4500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_28.png',
        '자스민 밀크티', '', 'ACTIVE'),
       (29, 13, 4500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_29.png',
        '우롱 밀크티', '', 'ACTIVE'),
       (30, 13, 4500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_30.png',
        '타로 밀크티', '["인기"]', 'ACTIVE'),

       (31, 14, 4000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_31.png',
        '블랙티', '', 'ACTIVE'),
       (32, 14, 4000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_32.png',
        '얼그레이 스파클링 티', '', 'ACTIVE'),
       (33, 14, 4000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_33.png',
        '우롱티', '', 'ACTIVE'),
       (34, 14, 4000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_34.png',
        '자스민티', '', 'ACTIVE'),
       (35, 14, 4300, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_35.png',
        '블랙 스파클링 티', '', 'ACTIVE'),
       (36, 14, 4300, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_36.png',
        '우롱 스파클링 티', '', 'ACTIVE'),

       (37, 15, 3700, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_37.png',
        '아메리카노', '', 'ACTIVE'),
       (38, 15, 4200, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_38.png',
        '얼그레이 아메리카노', '', 'ACTIVE'),
       (39, 15, 4500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_39.png',
        '카페 모카', '', 'ACTIVE'),
       (40, 15, 4500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_40.png',
        '바닐라 카페라떼', '', 'ACTIVE'),
       (41, 15, 5200, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_41.png',
        '공차슈페너', '', 'ACTIVE'),

       (42, 16, 5300, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_42.png',
        '밀크 쿠앤크 스무디', '', 'ACTIVE'),
       (43, 16, 5700, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_43.png',
        '브라운슈가 치즈폼 스무디', '', 'ACTIVE'),
       (44, 16, 5700, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_44.png',
        '딸기 쿠키 스무디', '', 'ACTIVE'),
       (45, 16, 5700, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_45.png',
        '초코멜로 스무디', '', 'ACTIVE'),
       (46, 16, 5700, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_46.png',
        '제주 그린 스무디', '', 'ACTIVE'),
       (47, 16, 5300, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_47.png',
        '청포도 스무디', '', 'ACTIVE'),

--     오봉집 역삼점
       (48, 17, 58000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_48.jpg',
        '낙지오봉스페셜', '["대표메뉴"]', 'ACTIVE'),
       (49, 17, 56000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_49.jpg',
        '오징어오봉스페셜', '["대표메뉴"]', 'ACTIVE'),
       (50, 17, 33000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_50.jpg',
        '오봉보쌈', '', 'ACTIVE'),
       (51, 17, 14000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_51.jpg',
        '직화낙지볶음', '', 'ACTIVE'),
       (52, 17, 13000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_52.jpg',
        '직화제낙볶음', '', 'ACTIVE'),
       (53, 17, 11000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_53.jpg',
        '직화제육볶음', '', 'ACTIVE'),
       (54, 18, 9000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_54.jpg',
        '쟁반막국수', '', 'ACTIVE'),
       (55, 18, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_55.jpg',
        '바지락칼국수', '', 'ACTIVE'),
       (56, 18, 17000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_56.jpg',
        '직화오돌뼈주먹밥', '', 'ACTIVE'),
       (57, 18, 15000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_57.jpg',
        '쫀득편육', '', 'ACTIVE'),

--     바스버거
       (58, 19, 8500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_58.png',
        '바스버거', '["인기"]', 'ACTIVE'),
       (59, 19, 7400, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_59.png',
        '치즈버거', '', 'ACTIVE'),
       (60, 19, 12200, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_60.png',
        '머쉬룸버거', '', 'ACTIVE'),
       (61, 19, 15000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_61.png',
        '탐욕버거', '', 'ACTIVE'),
       (62, 19, 12200, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_62.png',
        '더블베이컨치즈버거', '', 'ACTIVE'),
       (63, 19, 10500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_63.png',
        '하와이안버거', '["인기"]', 'ACTIVE'),
       (64, 22, 3500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_64.png',
        '펩시콜라', '', 'ACTIVE'),
       (65, 22, 2500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_65.png',
        '사이다', '', 'ACTIVE'),
       (66, 22, 5000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_66.png',
        '바스라거', '', 'ACTIVE'),
       (67, 22, 2500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_67.png',
        '마운틴듀', '', 'ACTIVE'),

--     농민백암순대
       (68, 23, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_68.jpg',
        '국밥', '', 'ACTIVE'),
       (69, 23, 36000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_69.jpg',
        '모듬 수육', '', 'ACTIVE'),
       (70, 23, 13000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dish_70.jpg',
        '토종순대', '', 'ACTIVE'),

--     멀티캠퍼스 (카테고리id: 한식=24, 일품=25, 도시락=26, 샌드위치=27, 샐러드=28)
       (81, 24, 12000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.amazonaws.com/bb442326-3bcf-4abd-81c9-ab1dea4e332cdish_71.jpg', '차돌짬뽕밥',
        '[]', 'ACTIVE'),
       (82, 24, 11000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.amazonaws.com/39951d54-6229-4da8-8615-369d1dcffeeedish_72.jpg', '매운닭고구마찜',
        '[]', 'ACTIVE'),
       (83, 25, 11000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.amazonaws.com/284e8702-16bc-4a52-bd75-91cad44419aedish_73.jpg', '해산물토마토스파게티',
        '[]', 'ACTIVE'),
       (84, 25, 8000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.amazonaws.com/617ffda4-8dd8-46aa-b753-22a3d21fc2bedish_74.jpg', '비빔밥',
        '[]', 'ACTIVE'),
       (85, 26, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.amazonaws.com/eeb836c5-a2a3-4670-abc3-c10bbf0ba327dish_75.jpg', '매운제육고추장볶음',
        '[]', 'ACTIVE'),
       (86, 26, 9000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.amazonaws.com/fa328549-8ec1-4f03-b214-2a885482b860dish_76.jpg', '해물데리야끼볶음',
        '[]', 'ACTIVE'),
       (87, 27, 7000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.amazonaws.com/1281fb75-5af5-4537-b39b-ce3ec5338ec3dish_77.jpg', '바질토마토 크림치즈베이글',
        '[]', 'ACTIVE'),
       (88, 27, 7500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.amazonaws.com/53693f66-3c5c-431b-ad9c-fc64ecf98f2bdish_78.jpg', '인기가요 샌드위치',
        '[]', 'ACTIVE'),
       (89, 29, 9000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.amazonaws.com/fda0435a-3a4e-41f8-88cb-de11c8373df5dish_79.jpg', '쉬림프 보울',
        '[]', 'ACTIVE'),
       (90, 29, 9500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.amazonaws.com/9db92db5-002c-4770-bb7c-7b7e5eadbd63dish_80.jpg', '훈제오리 파스타 샐러드',
        '[]', 'ACTIVE')

--      긴자료코
       (91, 30, 10500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_91.jpg', '데미그라스 돈까스',
        '[]', 'ACTIVE'),
       (92, 30, 12000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_92.jpg', '머슈룸데미그라스 돈까스',
        '[]', 'ACTIVE'),
       (93, 31, 15500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_93.jpg', '데미그라스 함박스테이크',
        '[]', 'ACTIVE'),
       (94, 31, 13500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_94.jpg', '데미그라스 돈까스 함박세트',
        '[]', 'ACTIVE'),
       (95, 32, 8500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_95.jpg', '가츠동',
        '[]', 'ACTIVE'),
       (96, 32, 8500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_96.jpg', '오야꼬동',
        '[]', 'ACTIVE'),
       (97, 33, 8000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_97.jpg', '소고기매콤카레라이스',
        '[]', 'ACTIVE'),
       (98, 33, 8500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_98.jpg', '불맛카레라이스',
        '[]', 'ACTIVE'),
       (99, 34, 9000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_99.jpg', '매콤카레우동',
        '[]', 'ACTIVE'),
       (100, 34, 9500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_100.jpg', '불맛카레우동',
        '[]', 'ACTIVE')

--      안사부
        (101, 35, 8500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_101.jpg', '짜장면',
        '[]', 'ACTIVE'),
       (102, 36, 11000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_102.jpg', '안사부백짬뽕',
        '[]', 'ACTIVE'),
       (103, 36, 12000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_103.jpg', '안사부백짬뽕밥',
        '[]', 'ACTIVE'),
       (104, 37, 9500, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_104.jpg', '안사부볶음밥',
        '[]', 'ACTIVE'),
       (105, 37, 10000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_105.jpg', '마파덮밥',
        '[]', 'ACTIVE'),
       (106, 38, 25000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_106.jpg', '해물짬뽕탕',
        '[]', 'ACTIVE'),
       (107, 38, 35000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_107.jpg', '오징어탕수육',
        '[]', 'ACTIVE'),
       (108, 38, 27000, NOW(), NOW(), '', 'https://jdqr-aws-bucket.s3.us-east-1.amazonaws.com/dishes_108.jpg', '유린기',
        '[]', 'ACTIVE');

;
-- 7. options 테이블
INSERT INTO `options` (`id`, `restaurant_id`, `max_choice_count`, `mandatory`, `created_at`, `updated_at`, `name`,
                       `status`)
VALUES (1, 1, 1, b'1', NOW(), NOW(), 'Size', 'ACTIVE'),
       (2, 1, 3, b'0', NOW(), NOW(), 'Extra Option', 'ACTIVE'),

--        공차 역삼점
       (3, 12, 1, b'1', NOW(), NOW(), 'SUGAR 선택', 'ACTIVE'),
       (4, 12, 2, b'0', NOW(), NOW(), '토핑 선택', 'ACTIVE'),
       (5, 12, 1, b'1', NOW(), NOW(), 'ICE 선택', 'ACTIVE'),
       (6, 12, 1, b'0', NOW(), NOW(), '토핑 선택', 'ACTIVE'),

--        바스버거
       (7, 14, 1, b'0', NOW(), NOW(), '양파 선택(B)', 'ACTIVE'),
       (8, 14, 2, b'0', NOW(), NOW(), '버거 토핑 추가 선택(A)', 'ACTIVE'),
       (9, 14, 4, b'0', NOW(), NOW(), '추가 선택', 'ACTIVE'),
       (10, 14, 1, b'1', NOW(), NOW(), '패티 선택', 'ACTIVE'),
       (11, 14, 1, b'0', NOW(), NOW(), '양파 선택(A)', 'ACTIVE'),
       (12, 14, 6, b'0', NOW(), NOW(), '버거 토핑 추가 선택(B)', 'ACTIVE'),

--        농민백암순대
       (13, 15, 1, b'1', NOW(), NOW(), '양 선택', 'ACTIVE'),

--        멀티캠퍼스
       (23, 16, 1, b'0', NOW(), NOW(), '후식 선택', 'ACTIVE'),
       (24, 16, 1, b'1', NOW(), NOW(), '음료 선택', 'ACTIVE'),

--      긴자료코
       (25, 17, 1, b'0', NOW(), NOW(), '추가 선택', 'ACTIVE'),
       (26, 17, 1, b'1', NOW(), NOW(), '사이드 추가선택', 'ACTIVE')
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
       (10, 2, 50, NOW(), NOW(), 'Extra Ice', 'ACTIVE'),

       --        공차 역삼점
       (21, 5, 0, NOW(), NOW(), '얼음 보통', 'ACTIVE'),
       (22, 5, 0, NOW(), NOW(), '얼음 가득', 'ACTIVE'),
       (23, 5, 0, NOW(), NOW(), '얼음 적게', 'ACTIVE'),
       (24, 4, 500, NOW(), NOW(), '펄', 'ACTIVE'),
       (25, 4, 500, NOW(), NOW(), '미니펄', 'ACTIVE'),
       (26, 4, 500, NOW(), NOW(), '화이트펄', 'ACTIVE'),
       (27, 4, 500, NOW(), NOW(), '알로에', 'ACTIVE'),
       (28, 4, 500, NOW(), NOW(), '코코넛', 'ACTIVE'),
       (29, 4, 500, NOW(), NOW(), '밀크폼', 'ACTIVE'),
       (30, 4, 700, NOW(), NOW(), '치즈폼', 'ACTIVE'),
       (31, 3, 0, NOW(), NOW(), '0%', 'ACTIVE'),
       (32, 3, 0, NOW(), NOW(), '30%', 'ACTIVE'),
       (33, 3, 0, NOW(), NOW(), '50%', 'ACTIVE'),
       (34, 3, 0, NOW(), NOW(), '70%', 'ACTIVE'),
       (35, 3, 0, NOW(), NOW(), '100%', 'ACTIVE'),
       (36, 6, 500, NOW(), NOW(), '화이트펄', 'ACTIVE'),
       (37, 6, 500, NOW(), NOW(), '알로에', 'ACTIVE'),
       (38, 6, 500, NOW(), NOW(), '코코넛', 'ACTIVE'),

       --        바스버거
       (39, 7, 0, NOW(), NOW(), '구운양파 추가', 'ACTIVE'),
       (40, 7, 0, NOW(), NOW(), '구운양파로 변경', 'ACTIVE'),
       (41, 8, 1000, NOW(), NOW(), '계란프라이 추가', 'ACTIVE'),
       (42, 8, 1000, NOW(), NOW(), '해쉬브라운 추가', 'ACTIVE'),
       (43, 9, 2500, NOW(), NOW(), '제임스 감', 'ACTIVE'),
       (44, 9, 5300, NOW(), NOW(), '버팔로 치킨 윙 (4P)', 'ACTIVE'),
       (45, 9, 8900, NOW(), NOW(), '버팔로 치킨 윙 (6P)', 'ACTIVE'),
       (46, 9, 10600, NOW(), NOW(), '버팔로 치킨 윙 (10P)', 'ACTIVE'),
       (47, 10, 0, NOW(), NOW(), '싱글', 'ACTIVE'),
       (48, 10, 3500, NOW(), NOW(), '더블', 'ACTIVE'),
       (49, 10, 7000, NOW(), NOW(), '트리플', 'ACTIVE'),
       (50, 11, 0, NOW(), NOW(), '생양파 X', 'ACTIVE'),
       (51, 11, 0, NOW(), NOW(), '생양파 추가', 'ACTIVE'),
       (52, 11, 0, NOW(), NOW(), '구운양파로 변경', 'ACTIVE'),
       (53, 12, 0, NOW(), NOW(), '구운양파 추가', 'ACTIVE'),
       (54, 12, 1000, NOW(), NOW(), '체다치즈 추가', 'ACTIVE'),
       (55, 12, 1000, NOW(), NOW(), '계란프라이 추가', 'ACTIVE'),
       (56, 12, 1000, NOW(), NOW(), '해쉬브라운 추가', 'ACTIVE'),
       (57, 12, 1000, NOW(), NOW(), '베이컨 추가', 'ACTIVE'),
       (58, 12, 3500, NOW(), NOW(), '더블(패티&치즈) 추가', 'ACTIVE'),
       (59, 13, 0, NOW(), NOW(), '보통', 'ACTIVE'),
       (60, 13, 2000, NOW(), NOW(), '특', 'ACTIVE'),

       --        멀티캠퍼스
       (85, 23, 0, NOW(), NOW(), 'ICE초코', 'ACTIVE'),
       (86, 23, 0, NOW(), NOW(), '아이스티', 'ACTIVE'),
       (87, 23, 0, NOW(), NOW(), '식혜', 'ACTIVE'),
       (88, 24, 0, NOW(), NOW(), '두유', 'ACTIVE'),
       (89, 24, 0, NOW(), NOW(), '바나나두유', 'ACTIVE'),
       (90, 24, 0, NOW(), NOW(), '검은콩두유', 'ACTIVE'),
       (91, 24, 0, NOW(), NOW(), '제로사이다', 'ACTIVE')

--      긴자료코
       (95, 25, 0, NOW(), NOW(), '장국 추가', 'ACTIVE'),
       (96, 25, 0, NOW(), NOW(), '김치 추가', 'ACTIVE'),
       (97, 25, 2500, NOW(), NOW(), '데미그라스 소스 추가', 'ACTIVE'),
       (98, 26, 4000, NOW(), NOW(), '가라아게 5pc 추가', 'ACTIVE'),
       (99, 26, 6500, NOW(), NOW(), '굴튀김 5pc 추가', 'ACTIVE'),
       (100, 26, 1500, NOW(), NOW(), '수란 추가', 'ACTIVE'),
       (101, 26, 2000, NOW(), NOW(), '스프라이트 355ml', 'ACTIVE'),
       (102, 26, 2000, NOW(), NOW(), '코카콜라 355ml', 'ACTIVE');
;

-- 9. dish_options 테이블
INSERT INTO `dish_options` (`id`, `dish_id`, `option_id`, `created_at`, `updated_at`, `status`)
VALUES (1, 1, 1, NOW(), NOW(), 'ACTIVE'), -- French Fries: Small Size
       (2, 2, 2, NOW(), NOW(), 'ACTIVE'), -- Nachos: Extra Cheese
       (3, 3, 1, NOW(), NOW(), 'ACTIVE'), -- Chicken Sandwich: Double Meat
       (4, 4, 2, NOW(), NOW(), 'ACTIVE'), -- Veggie Burger: No Onion
       (5, 5, 2, NOW(), NOW(), 'ACTIVE'), -- Burrito: Extra Sauce
       (6, 6, 2, NOW(), NOW(), 'ACTIVE'), -- Chocolate Cake: Add Spicy
       (7, 7, 2, NOW(), NOW(), 'ACTIVE'), -- Garlic Bread: Whole Wheat Bread
       (8, 8, 1, NOW(), NOW(), 'ACTIVE'), -- Caesar Salad: Large Size
       (9, 9, 2, NOW(), NOW(), 'ACTIVE'), -- Onion Rings: No Salt
       (10, 10, 2, NOW(), NOW(), 'ACTIVE'),

--        진짜 데이터
--        공차 역삼점
       (11, 23, 3, NOW(), NOW(), 'ACTIVE'),
       (12, 23, 4, NOW(), NOW(), 'ACTIVE'),
       (13, 24, 3, NOW(), NOW(), 'ACTIVE'),
       (14, 24, 4, NOW(), NOW(), 'ACTIVE'),
       (15, 25, 3, NOW(), NOW(), 'ACTIVE'),
       (16, 25, 4, NOW(), NOW(), 'ACTIVE'),
       (17, 26, 3, NOW(), NOW(), 'ACTIVE'),
       (18, 26, 4, NOW(), NOW(), 'ACTIVE'),
       (19, 26, 5, NOW(), NOW(), 'ACTIVE'),
       (20, 27, 3, NOW(), NOW(), 'ACTIVE'),
       (21, 27, 4, NOW(), NOW(), 'ACTIVE'),
       (22, 27, 5, NOW(), NOW(), 'ACTIVE'),
       (23, 28, 3, NOW(), NOW(), 'ACTIVE'),
       (24, 28, 4, NOW(), NOW(), 'ACTIVE'),
       (25, 28, 5, NOW(), NOW(), 'ACTIVE'),
       (26, 29, 3, NOW(), NOW(), 'ACTIVE'),
       (27, 29, 4, NOW(), NOW(), 'ACTIVE'),
       (28, 29, 5, NOW(), NOW(), 'ACTIVE'),
       (29, 30, 3, NOW(), NOW(), 'ACTIVE'),
       (30, 30, 4, NOW(), NOW(), 'ACTIVE'),
       (31, 30, 5, NOW(), NOW(), 'ACTIVE'),
       (32, 31, 3, NOW(), NOW(), 'ACTIVE'),
       (33, 31, 4, NOW(), NOW(), 'ACTIVE'),
       (34, 31, 5, NOW(), NOW(), 'ACTIVE'),
       (35, 32, 3, NOW(), NOW(), 'ACTIVE'),
       (36, 32, 6, NOW(), NOW(), 'ACTIVE'),
       (37, 33, 3, NOW(), NOW(), 'ACTIVE'),
       (38, 33, 4, NOW(), NOW(), 'ACTIVE'),
       (39, 33, 5, NOW(), NOW(), 'ACTIVE'),
       (40, 34, 3, NOW(), NOW(), 'ACTIVE'),
       (41, 34, 4, NOW(), NOW(), 'ACTIVE'),
       (42, 34, 5, NOW(), NOW(), 'ACTIVE'),
       (43, 35, 3, NOW(), NOW(), 'ACTIVE'),
       (44, 35, 6, NOW(), NOW(), 'ACTIVE'),
       (45, 36, 3, NOW(), NOW(), 'ACTIVE'),
       (46, 36, 6, NOW(), NOW(), 'ACTIVE'),
       (47, 37, 3, NOW(), NOW(), 'ACTIVE'),
       (48, 37, 4, NOW(), NOW(), 'ACTIVE'),
       (49, 38, 3, NOW(), NOW(), 'ACTIVE'),
       (50, 38, 4, NOW(), NOW(), 'ACTIVE'),
       (51, 39, 3, NOW(), NOW(), 'ACTIVE'),
       (52, 39, 4, NOW(), NOW(), 'ACTIVE'),
       (53, 40, 3, NOW(), NOW(), 'ACTIVE'),
       (54, 40, 4, NOW(), NOW(), 'ACTIVE'),
       (55, 41, 3, NOW(), NOW(), 'ACTIVE'),
       (56, 41, 4, NOW(), NOW(), 'ACTIVE'),
       (57, 42, 4, NOW(), NOW(), 'ACTIVE'),
       (58, 43, 4, NOW(), NOW(), 'ACTIVE'),
       (59, 44, 4, NOW(), NOW(), 'ACTIVE'),
       (60, 45, 4, NOW(), NOW(), 'ACTIVE'),
       (61, 46, 4, NOW(), NOW(), 'ACTIVE'),
       (62, 47, 4, NOW(), NOW(), 'ACTIVE'),
--        바스버거
       (63, 58, 10, NOW(), NOW(), 'ACTIVE'),
       (64, 58, 11, NOW(), NOW(), 'ACTIVE'),
       (65, 58, 12, NOW(), NOW(), 'ACTIVE'),
       (66, 58, 9, NOW(), NOW(), 'ACTIVE'),
       (67, 59, 7, NOW(), NOW(), 'ACTIVE'),
       (68, 59, 8, NOW(), NOW(), 'ACTIVE'),
       (69, 59, 9, NOW(), NOW(), 'ACTIVE'),
       (70, 60, 12, NOW(), NOW(), 'ACTIVE'),
       (71, 60, 9, NOW(), NOW(), 'ACTIVE'),
       (72, 61, 7, NOW(), NOW(), 'ACTIVE'),
       (73, 61, 9, NOW(), NOW(), 'ACTIVE'),
       (74, 62, 12, NOW(), NOW(), 'ACTIVE'),
       (75, 62, 9, NOW(), NOW(), 'ACTIVE'),
       (76, 63, 12, NOW(), NOW(), 'ACTIVE'),
       (77, 63, 9, NOW(), NOW(), 'ACTIVE'),

--        농민백암순대
       (78, 68, 13, NOW(), NOW(), 'ACTIVE'),
--        멀티캠퍼스
       (98, 81, 14, NOW(), NOW(), 'ACTIVE'),
       (99, 82, 14, NOW(), NOW(), 'ACTIVE'),
       (100, 83, 14, NOW(), NOW(), 'ACTIVE'),
       (101, 84, 14, NOW(), NOW(), 'ACTIVE'),
       (102, 85, 15, NOW(), NOW(), 'ACTIVE'),
       (103, 86, 15, NOW(), NOW(), 'ACTIVE'),
       (104, 87, 15, NOW(), NOW(), 'ACTIVE'),
       (105, 88, 15, NOW(), NOW(), 'ACTIVE')

;

-- parent_orders 테이블
INSERT INTO `parent_orders` (id, created_at, updated_at, table_id, order_status, payment_method, status)
values (1, NOW(), NOW(), '6721aa9b0d22a923091eef73', 'PAID', 'MENU_DIVIDE', 'ACTIVE'),
       (2, NOW(), NOW(), '6721aa9b0d22a923091eef73', 'PAID', 'MONEY_DIVIDE', 'ACTIVE'),
       (3, NOW(), NOW(), '6721aa9b0d22a923091eef73', 'CANCELLED', 'UNDEFINED', 'ACTIVE'),
       (4, NOW(), NOW(), '672ad435085e4f131b0ecadd', 'PAID', 'MENU_DIVIDE', 'ACTIVE'),
       (5, NOW(), NOW(), '672ad435085e4f131b0ecadd', 'PAID', 'MONEY_DIVIDE', 'ACTIVE'),
       (6, NOW(), NOW(), '672ad435085e4f131b0ecadd', 'CANCELLED', 'UNDEFINED', 'ACTIVE'),
       (7, NOW(), NOW(), '6721aa9b0d22a923091eef73', 'PAID', 'MENU_DIVIDE', 'ACTIVE'),
       (8, NOW(), NOW(), '672ad435085e4f131b0ecadd', 'PAID', 'MONEY_DIVIDE', 'ACTIVE'),
       (9, NOW(), NOW(), '6721aa9b0d22a923091eef73', 'CANCELLED', 'UNDEFINED', 'ACTIVE'),
       (10, NOW(), NOW(), '672ad435085e4f131b0ecadd', 'PAID', 'MENU_DIVIDE', 'ACTIVE'),

--     진짜 데이터
       (11, NOW(), NOW(), '6721aa9b0d22a923091eef73', 'PENDING', 'UNDEFINED', 'ACTIVE'),
       (12, NOW(), NOW(), '67345a90fb5b4a3df7c2076a', 'PAY_WAITING', 'MENU_DIVIDE', 'ACTIVE'),
       (13, NOW(), NOW(), '67345b67fb5b4a3df7c2076c', 'PAY_WAITING', 'MONEY_DIVIDE', 'ACTIVE')
;

-- 10. orders 테이블
INSERT INTO `orders` (id, parent_order_id, created_at, updated_at, status)
VALUES (1, 1, NOW(), NOW(), 'ACTIVE'),
       (2, 2, NOW(), NOW(), 'ACTIVE'),
       (3, 3, NOW(), NOW(), 'ACTIVE'),
       (4, 4, NOW(), NOW(), 'ACTIVE'),
       (5, 5, NOW(), NOW(), 'ACTIVE'),
       (6, 6, NOW(), NOW(), 'ACTIVE'),
       (7, 7, NOW(), NOW(), 'ACTIVE'),
       (8, 8, NOW(), NOW(), 'ACTIVE'),
       (9, 9, NOW(), NOW(), 'ACTIVE'),
       (10, 10, NOW(), NOW(), 'ACTIVE'),

       -- 진짜 데이터
       (11, 11, NOW(), NOW(), 'ACTIVE'),
       (12, 11, NOW(), NOW(), 'ACTIVE'),
       (13, 12, NOW(), NOW(), 'ACTIVE'),
       (14, 12, NOW(), NOW(), 'ACTIVE'),
       (15, 12, NOW(), NOW(), 'ACTIVE'),
       (16, 13, NOW(), NOW(), 'ACTIVE'),
       (17, 13, NOW(), NOW(), 'ACTIVE')
;

--
-- -- 11. order_items 테이블
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
       (10, 10, 5, 1000, 1, NOW(), NOW(), NOW(), 'user_10', 'ACTIVE')
;

-- 진짜 데이터
INSERT INTO `order_items` (id, dish_id, order_id, order_price, paid_quantity, quantity, created_at, ordered_at,
                           updated_at, user_id, status)
values (11, 27, 11, 5000, 0, 2, NOW(), NOW(), NOW(), '550e8400-e29b-41d4-a716-446655440000', 'ACTIVE'),
       (12, 27, 11, 4500, 0, 4, NOW(), NOW(), NOW(), '550e8400-e29b-41d4-a716-446655440000', 'ACTIVE'),
       (13, 35, 12, 4800, 0, 5, NOW(), NOW(), NOW(), 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'ACTIVE'),

       (14, 30, 13, 5200, 1, 2, NOW(), NOW(), NOW(), 'a8098c1a-f86e-11da-bd1a-00112444be1e', 'ACTIVE'),
       (15, 30, 13, 5200, 1, 2, NOW(), NOW(), NOW(), '16fd2706-8baf-433b-82eb-8c7fada847da', 'ACTIVE'),
       (16, 44, 13, 6200, 0, 1, NOW(), NOW(), NOW(), 'd94f8b9f-1527-423d-90ed-23668f31d3b0', 'ACTIVE'),

       (17, 47, 14, 5300, 0, 2, NOW(), NOW(), NOW(), 'd94f8b9f-1527-423d-90ed-23668f31d3b0', 'ACTIVE'),
       (18, 31, 15, 4500, 0, 2, NOW(), NOW(), NOW(), 'd94f8b9f-1527-423d-90ed-23668f31d3b0', 'ACTIVE'),
       (19, 36, 16, 4300, 1, 2, NOW(), NOW(), NOW(), 'd94f8b9f-1527-423d-90ed-23668f31d3b0', 'ACTIVE'),
       (20, 39, 17, 4500, 2, 3, NOW(), NOW(), NOW(), 'd94f8b9f-1527-423d-90ed-23668f31d3b0', 'ACTIVE')
;

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

-- 진짜 데이터
INSERT INTO `order_item_choices` (id, order_item_id, choice_id, created_at, updated_at, status)
VALUES (11, 11, 25, NOW(), NOW(), 'ACTIVE'),
       (12, 11, 33, NOW(), NOW(), 'ACTIVE'),
       (13, 12, 33, NOW(), NOW(), 'ACTIVE'),
       (14, 13, 36, NOW(), NOW(), 'ACTIVE'),

       (15, 14, 30, NOW(), NOW(), 'ACTIVE'),
       (16, 15, 30, NOW(), NOW(), 'ACTIVE'),
       (17, 16, 29, NOW(), NOW(), 'ACTIVE'),

       (18, 18, 27, NOW(), NOW(), 'ACTIVE'),
       (19, 18, 35, NOW(), NOW(), 'ACTIVE'),
       (20, 20, 31, NOW(), NOW(), 'ACTIVE')
;
update dish
set tags = '[]'
where tags = '';