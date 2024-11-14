import { create } from "zustand";
import { Stomp } from "@stomp/stompjs";
import { initializeToken } from "../utils/apis/axiosInstance";

const useWebSocketStore = create((set) => ({
  client: null,
  connect: () => {
    if (!sessionStorage.getItem("tableToken")) {
      initializeToken();
    }
    const client = Stomp.client("wss://jdqr608.duckdns.org/ws");
    const token = sessionStorage.getItem("tableToken");
    client.connect(
      {
        Authorization: `Bearer ${token}`,
      },
      () => {
        set({ client });
      }
    );
  },
  disconnect: () => {
    set((state) => {
      if (state.client && state.client.connected) {
        state.client.disconnect(() => {});
      }
      return { client: null };
    });
  },
}));

export default useWebSocketStore;
