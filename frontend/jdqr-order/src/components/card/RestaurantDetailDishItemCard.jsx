import React from "react"

const RestaurantDetailDishItemCard = ({ dish }) => {
  const mockDishes = [
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
  ]

  return <div>RestaurantDetailDishItemCard</div>
}

export default RestaurantDetailDishItemCard
