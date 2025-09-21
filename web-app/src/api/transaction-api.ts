import { dateToLocalDateTime } from "../utils/date-utils";
import axiosInstance from "./axios-instance"

interface TransactionListParams {
    date?: string,
    size?: number
}

export const callGetTransactionList = async (params: TransactionListParams = {date: dateToLocalDateTime(new Date), size: 10}) => {
    const response = await axiosInstance.get("/api/transaction/list", {
        params: params
    })
    return response.data.result
}