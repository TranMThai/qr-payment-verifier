import axios from "axios";
import { BASE_API } from "./base_api";

const axiosInstance = axios.create({
    baseURL: BASE_API,
    withCredentials: true,
    headers: {
        'ngrok-skip-browser-warning': 'true'
    }
})

export default axiosInstance