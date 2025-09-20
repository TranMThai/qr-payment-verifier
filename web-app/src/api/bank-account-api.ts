import axiosInstance from "./axios-instance";

export const callBankAccount = async () => {
    const response = await axiosInstance.get('/api/bank-account')
    return response.data.result
}