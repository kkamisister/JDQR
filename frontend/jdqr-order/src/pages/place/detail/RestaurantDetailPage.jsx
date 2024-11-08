import React from "react"
import KakaoMap from "../../../components/map/KakaoMap"
import RestaurantDetailBox from "./RestaurantDetailBox"
import MapBackButtonHeader from "../../../components/header/MapBackButtonHeader"

const RestaurantDetailPage = () => {
  const mockData = {
    status: 200,
    message: "식당 상세정보 조회에 성공하였습니다",
    data: {
      restaurant: {
        id: 1,
        restaurantName: "Tasty Restaurant",
        restaurantCategories: [
          {
            restaurantCategoryId: 1,
            restaurantCategoryName: "Fast Food",
          },
          {
            restaurantCategoryId: 2,
            restaurantCategoryName: "Healthy",
          },
          {
            restaurantCategoryId: 3,
            restaurantCategoryName: "Desserts",
          },
        ],
        restTableNum: 1,
        restSeatNum: 6,
        maxPeopleNum: 6,
        address: "123 Main St, San Francisco, CA",
        image: "image1.jpg",
        lat: 37.7749,
        lng: -122.4194,
        open: true,
      },
      dishInfo: {
        dishCategories: [
          "Appetizers",
          "Breakfast",
          "Lunch Specials",
          "Dinner Platters",
          "Beverages",
          "Desserts",
        ],
        dishes: [
          {
            dishCategoryId: 1,
            dishCategoryName: "Appetizers",
            items: [
              {
                dishId: 1,
                dishName: "French Fries",
                price: 500,
                description: "Crispy French Fries",
                image: "french_fries.jpg",
                options: [],
                tags: ["Popular"],
              },
            ],
          },
          {
            dishCategoryId: 11,
            dishCategoryName: "Breakfast",
            items: [
              {
                dishId: 11,
                dishName: "Pancakes",
                price: 800,
                description: "Classic Pancakes with Syrup",
                image: "pancakes.jpg",
                options: [
                  {
                    optionId: 11,
                    optionName: "No Spicy",
                    choices: [
                      {
                        choiceId: 1,
                        choiceName: "Mild",
                        price: 0,
                      },
                      {
                        choiceId: 2,
                        choiceName: "Medium",
                        price: 0,
                      },
                      {
                        choiceId: 3,
                        choiceName: "Hot",
                        price: 0,
                      },
                    ],
                  },
                  {
                    optionId: 12,
                    optionName: "Extra Cheese",
                    choices: [
                      {
                        choiceId: 4,
                        choiceName: "Mozzarella Cheese",
                        price: 200,
                      },
                      {
                        choiceId: 5,
                        choiceName: "Cheddar Cheese",
                        price: 300,
                      },
                    ],
                  },
                ],
                tags: ["Breakfast", "Popular"],
              },
              {
                dishId: 12,
                dishName: "French Toast",
                price: 900,
                description: "French Toast with Berries",
                image: "french_toast.jpg",
                options: [
                  {
                    optionId: 13,
                    optionName: "Whole Grain Bread",
                    choices: [
                      {
                        choiceId: 6,
                        choiceName: "Whole Grain",
                        price: 100,
                      },
                    ],
                  },
                  {
                    optionId: 17,
                    optionName: "Low Salt",
                    choices: [
                      {
                        choiceId: 10,
                        choiceName: "Low Salt",
                        price: 0,
                      },
                    ],
                  },
                ],
                tags: ["Breakfast", "Healthy"],
              },
            ],
          },
        ],
      },
    },
  }
  return (
    <>
      <div
        style={{
          position: "fixed",
          top: 0,
          left: 0,
          right: 0,
          zIndex: 1,
        }}
      >
        <MapBackButtonHeader />
      </div>
      <div
        style={{
          position: "fixed",
          top: 0,
          left: 0,
          right: 0,
          width: "100%",
          height: "100%",
        }}
      >
        <KakaoMap />
      </div>
      <div
        style={{
          position: "relative",
          bottom: -700,
          left: 0,
          right: 0,
          maxHeight: "80vh",
          overflowY: "auto",
          zIndex: 1,
        }}
      >
        <RestaurantDetailBox
          categories={mockData.data.dishInfo.dishCategories}
          dishes={mockData.data.dishInfo.dishes}
        />
      </div>
    </>
  )
}

export default RestaurantDetailPage
