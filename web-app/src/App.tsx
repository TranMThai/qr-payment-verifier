import { useEffect, useState } from "react";
import { NOTIFICATION_SOCKET } from "./api/base_api";
import BankQR from "./components/bank-qr";
import useSocket from "./hooks/useSocket";
import useTTS from "./hooks/useTTS";
import type { Transaction } from "./types/transaction";
import { Bounce, toast, ToastContainer } from "react-toastify";

function App() {
  const { speak } = useTTS();
  const { connectSocket } = useSocket();
  const [pendingNotifyTransactions, setPendingNotifyTransactions] = useState<Transaction[]>([]);
  const [isClicked, setIsClicked] = useState(false);

  useEffect(() => {
    const action = (message: any) => {
      const data: Transaction[] = JSON.parse(message.body);
      if (data.length > 0) {
        data.forEach(transaction => {
          toast.success(`Đã nhận được ${transaction.amountIn.toLocaleString("vi-VN")}đ`)
        })
        setPendingNotifyTransactions(prev => [...prev, ...data])
      }
    }
    const stompClient = connectSocket(NOTIFICATION_SOCKET, '/notification', action)
    return () => {
      stompClient.deactivate();
    };
  }, []);

  useEffect(() => {
    document.addEventListener("click", () => {
      setIsClicked(true)
    }, { once: true })
  }, [])

  useEffect(() => {
    speakPendingTransactions()
  }, [pendingNotifyTransactions])

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
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100 p-6">
      <h1 className="text-3xl font-bold text-blue-600 mb-4">
        Trang nhận thông báo thanh toán
      </h1>

      {!isClicked && (
        <div className="bg-yellow-100 text-yellow-800 px-4 py-2 rounded-md shadow mb-6 animate-pulse">
          Click bất kỳ đâu để bật âm thanh thông báo giao dịch
        </div>
      )}

      <BankQR />
      <ToastContainer position="top-center"/>
    </div>
  );
}

export default App;
