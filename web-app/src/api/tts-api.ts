import axios from "axios";
import { BASE_API } from "./base_api";

export const callSpeak = async (text: string) => {
    const response = await axios({
        method: 'GET',
        params: {text},
        url: `${BASE_API}/api/speak`,
        responseType: 'blob'
    })
    return response
}