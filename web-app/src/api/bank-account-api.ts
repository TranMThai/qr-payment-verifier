import axiosInstance from "./axios-instance";
import { BASE_API } from "./base_api";

export const callBankAccount = async () => {
    const response = await axiosInstance({
        method: 'GET',
        url: `${BASE_API}/api/bank-account`
    })
    return response.data
}