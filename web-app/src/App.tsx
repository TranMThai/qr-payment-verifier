import { Client } from "@stomp/stompjs";
import { useEffect, useState } from "react";
import SockJS from 'sockjs-client';
import useTTS from "./hooks/useTTS";
import type { Transaction } from "./types/transaction";

function App() {

  const { speak } = useTTS()
  const [pendingNotifyTransactions, setPendingNotifyTransactions] = useState<Transaction[]>([])

  useEffect(() => {
    const socket = new SockJS('http://localhost:8080/ws/notification');
    const stompClient = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 2000,
    });

    stompClient.onConnect = () => {
      console.log('✅ Đã kết nối WebSocket');
      stompClient.subscribe('/notification', (message) => {
        const data: Transaction[] = JSON.parse(message.body);
        if (data.length > 0) {
          setPendingNotifyTransactions(prev => [...prev, ...data])
        }
      });
    };

    stompClient.onStompError = (frame) => {
      console.error('❌ Lỗi', frame);
    };

    stompClient.activate();

    return () => {
      stompClient.deactivate();
    };
  }, []);

  useEffect(() => {
    speakPendingTransactions()
  }, [pendingNotifyTransactions])

  const speakTest = () => {
    speak(`Thanh toán thành công 5000 đồng`);
  }

  const waitForAudioToEnd = (audio: HTMLAudioElement): Promise<void> => {
    return new Promise((resolve) => {
      audio.onended = () => resolve();
      audio.onerror = () => resolve();
    });
  };

  const speakPendingTransactions = async () => {
    if (pendingNotifyTransactions.length) {
      const notifyingTransaction: Transaction = pendingNotifyTransactions[0]
      const audio = await speak(`Thanh toán thành công ${notifyingTransaction.amountIn} đồng`);
      await waitForAudioToEnd(audio);
      setPendingNotifyTransactions(prev => prev.filter(transaction => transaction.id !== notifyingTransaction.id))
    }
  }

  return (
    <>
      <h1>Trang nhận thông báo thanh toán</h1>
      <button onClick={speakTest}>Test</button>
      <button id="startSpeaking">bắt đầu nói</button>
    </>
  );
}

export default App;
