import { create } from "zustand";
import { Stomp } from "@stomp/stompjs";
import { initializeToken } from "../utils/apis/axiosInstance";

const useWebSocketStore = create((set) => ({
  client: null,
  connect: () => {
    if (!localStorage.getItem("tableToken")) {
      initializeToken();
    }
    const client = Stomp.client("wss://jdqr608.duckdns.org/ws");
    const token = localStorage.getItem("tableToken");
    console.log("이게토큰", token);
    client.connect(
      {
        Authorization: token,
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
