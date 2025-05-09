import { Client } from "@stomp/stompjs";
import { useEffect, useState } from "react";
import SockJS from 'sockjs-client';
import useTTS from "./hooks/useTTS";
import type { Transaction } from "./types/transaction";
import BankQR from "./components/bank-qr";
import useSocket from "./hooks/useSocket";
import { NOTIFICATION_SOCKET } from "./api/base_api";

function App() {

  const { speak } = useTTS()
  const { connectSocket } = useSocket()
  const [pendingNotifyTransactions, setPendingNotifyTransactions] = useState<Transaction[]>([])

  useEffect(() => {
    
    const action = (message: any) => {
      const data: Transaction[] = JSON.parse(message.body);
      console.log(data);
      
      if (data.length > 0) {
        setPendingNotifyTransactions(prev => [...prev, ...data])
      }
    }

    const stompClient = connectSocket(NOTIFICATION_SOCKET, '/notification', action)

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
      <BankQR />
      <button onClick={speakTest}>Test</button>
      <button id="startSpeaking">bắt đầu nói</button>
    </>
  );
}

export default App;
