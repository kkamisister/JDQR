import { create } from "zustand";

export const usePaymentStore = create((set) => ({
  money: 0,
  setMoney: (value) => set({ money: value }),
}));
