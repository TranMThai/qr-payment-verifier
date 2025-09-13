import React, { useEffect } from 'react'
import type { Transaction } from '../types/transaction';

interface IProps {
    transactions: Transaction[]
}

const History: React.FC<IProps> = ({ transactions }) => {

    useEffect(() => {
        console.log(transactions);
    }, [transactions])

    return (
        <div className="bg-white shadow rounded-2xl py-6 w-full md:w-1/2 my-5 max-h-[95vh] flex flex-col">
            <h2 className="px-6 text-xl font-semibold mb-4 text-gray-700">
                Lịch sử giao dịch gần đây
            </h2>
            <div className="overflow-y-auto flex-1">
                <ul className="space-y-4 px-6">
                    {[...transactions].reverse().map((trans,index) => (
                        <li
                            key={index}
                            className="flex items-center justify-between bg-gray-50 p-4 rounded-xl shadow-sm hover:bg-gray-100 transition"
                        >
                            <div className="flex items-center gap-3">
                                <div className="bg-green-100 text-green-600 rounded-full w-10 h-10 flex items-center justify-center text-sm font-bold">
                                    #{trans.id}
                                </div>
                                <div>
                                    <p className="text-sm text-gray-500">Thời gian: {trans.transactionDate.toLocaleTimeString("vi-VN")} | {trans.transactionDate.toLocaleDateString("vi-VN")}</p>
                                </div>
                            </div>
                            <p className="text-lg font-semibold text-green-600">
                                +{trans.amountIn.toLocaleString("vi-VN")} ₫
                            </p>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    )
}

export default History