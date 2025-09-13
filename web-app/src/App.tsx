import { useEffect, useState } from "react";
import { toast, ToastContainer } from "react-toastify";
import { NOTIFICATION_SOCKET } from "./api/base_api";
import BankQR from "./components/bank-qr";
import useSocket from "./hooks/useSocket";
import useTTS from "./hooks/useTTS";
import type { Transaction } from "./types/transaction";
import { callSpeak } from "./api/tts-api";
import { convertBlobToBase64 } from "./utils/file-converter-utils";
import History from "./components/history";

function App() {
  const { speakByBase64, waitForAudioToEnd } = useTTS();
  const { connectSocket } = useSocket();
  const [pendingNotifyTransactions, setPendingNotifyTransactions] = useState<Transaction[]>([]);
  const [historyTransactions, setHistoryTransactions] = useState<Transaction[]>([]);
  const [isClicked, setIsClicked] = useState(false);

  useEffect(() => {
    const action = (message: any) => {

      const data: Transaction[] = JSON.parse(message.body);

      if (data.length > 0) {
        data.forEach(transaction => {
          toast.success(`Đã nhận được ${transaction.amountIn.toLocaleString("vi-VN")}đ`)
        })

        const clearSpeech: Transaction[] = data.map(d => ({
          ...d,
          speech: undefined
        }));

        setHistoryTransactions(prev => [
          ...prev,
          ...clearSpeech.map((d, i) => ({
            ...d,
            id: prev.length + i + 1 + "",
            transactionDate: new Date(d.transactionDate)
          }))
        ]);
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
    if (pendingNotifyTransactions.length) {
      speakPendingTransactions()
    }
  }, [pendingNotifyTransactions])

  const speakPendingTransactions = async () => {
    const notifyingTransaction: Transaction = pendingNotifyTransactions[0]
    const audio = await speakByBase64(notifyingTransaction.speech || "")
    await waitForAudioToEnd(audio)
    setPendingNotifyTransactions(prev => prev.filter(transaction => transaction.id !== notifyingTransaction.id))
  }

  const testSound = async () => {
    //START
    const start = new Date()
    //
    const money = (Math.floor(Math.random() * 100000) + 1) * 1000;
    const blob = await callSpeak(`Bạn đã nhận được ${money} đồng`)
    const base64 = await convertBlobToBase64(blob)
    toast.success(`Đã nhận được ${money.toLocaleString("vi-VN")}đ`)
    const transaction: Transaction = { id: `${Math.floor(Math.random() * 1000) + 1}`, amountIn: money, speech: base64 + "", transactionDate: new Date() }
    setHistoryTransactions(prev => [...prev, { ...transaction, id: prev.length + 1 + "", speech: undefined }])
    setPendingNotifyTransactions(prev => [...prev, { ...transaction }])
    //END
    const end = new Date()
    const time = end.getTime() - start.getTime()
    console.log(`${time / 1000}s`);
    //
  }

  return (
    <>
      <div
        className="min-h-screen bg-gray-100 flex flex-col md:flex-row justify-between px-5 md:px-10 gap-10"
        onClick={() => !isClicked && setIsClicked(true)}
      >
        <div className="flex flex-col items-center justify-center p-6 md:w-5/6">
          <h1 className="text-3xl font-bold text-blue-600 mb-4">
            Trang nhận thông báo thanh toán
          </h1>

          {!isClicked ? (
            <div
              className="bg-yellow-100 text-yellow-800 px-4 py-2 rounded-md shadow mb-6 animate-pulse text-center"
              aria-live="polite"
            >
              Click bất kỳ đâu để bật âm thanh thông báo giao dịch
            </div>
          ) : (
            <BankQR />
          )}

          <button
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-5"
            onClick={testSound}
          >
            Test sound
          </button>
        </div>

        <History transactions={historyTransactions} />

      </div>
      <ToastContainer autoClose={15000} />
    </>
  );
}

export default App;
