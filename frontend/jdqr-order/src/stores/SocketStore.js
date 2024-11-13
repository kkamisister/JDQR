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
    console.log("이게토큰", token);
    client.connect(
      {
        Authorization: `Bearer ${token}`,
      },
      () => {
        console.log("STOMP 연결 성공");
        set({ client });
      }
    );
  },
  disconnect: () => {
    set((state) => {
      if (state.client && state.client.connected) {
        state.client.disconnect(() => {
          console.log("STOMP 연결 종료");
        });
      }
      return { client: null };
    });
  },
}));

export default useWebSocketStore;
