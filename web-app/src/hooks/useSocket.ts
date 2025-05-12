import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

const useSocket = () => {
    const connectSocket = (
        endpoint: string,
        topic: string,
        action: (message: any) => void
    ) => {
        const socket = new SockJS(endpoint, null, {});
        const stompClient = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 2000,
        });

        stompClient.onConnect = () => {
            console.log('✅ Đã kết nối WebSocket');
            stompClient.subscribe(topic, action);
        };

        stompClient.onStompError = (frame) => {
            console.error('❌ Lỗi STOMP:', frame.headers['message']);
            console.error('Chi tiết:', frame.body);
        };

        stompClient.activate();

        return stompClient;
    };

    return { connectSocket };
};

export default useSocket;
