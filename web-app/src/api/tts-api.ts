import axiosInstance from "./axios-instance";
import { BASE_API } from "./base_api";

export const callSpeak = async (text: string) => {
    const response = await axiosInstance({
        method: 'GET',
        params: {text},
        url: `${BASE_API}/api/speak`,
        responseType: 'blob'
    })
    return response.data
}