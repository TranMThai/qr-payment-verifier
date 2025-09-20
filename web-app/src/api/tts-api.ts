import axiosInstance from "./axios-instance";

export const callSpeak = async (text: string) => {
    const response = await axiosInstance.get('/api/speak', {
        params: { text },
        responseType: 'blob'
    })
    console.log(response)
    return response.data
}